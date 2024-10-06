(ns aoc-2022.day17
  (:gen-class)
  (:require [aoc-2022.utils :as utils]
            [clojure.set :refer [union]]))

(def shapes
  {:horizontal_line [[2 0] [3 0] [4 0] [5 0]]
   :plus            [[3 0] [2 1] [3 1] [4 1] [3 2]]
   :reversed_l      [[2 0] [3 0] [4 0] [4 1] [4 2]]
   :vertical_line   [[2 0] [2 1] [2 2] [2 3]]
   :square          [[2 0] [3 0] [2 1] [3 1]]})

(def shapes-order (map shapes [:horizontal_line :plus :reversed_l :vertical_line :square]))

(def shapes-stream
  (cycle shapes-order))

(defn jet-stream [lines]
  (cycle (first lines)))

(defn update-state [current-state changes]
  (reduce (fn [m [k new-val]] (assoc m k new-val)) current-state changes))

(defn op-from-direction [direction]
  (case direction
    \> +
    \< -))

(defn shift-rock [rock func]
  (map func rock))

(defn jet-push [direction rock]
  (let [op (op-from-direction direction)]
    (shift-rock rock (fn [[x y]] [(op x 1) y]))))

(defn shift-down [rock]
  (shift-rock rock (fn [[x y]] [x (- y 1)])))

; (some (fn [[x y]] (>= (get current-shape x) y)) rock)
(defn collides-with-shape? [rock current-shape]
  (some (fn [point] (contains? current-shape point)) rock))

(defn collides-sides? [rock current-shape]
  (or (some (fn [[x _]] (or (= -1 x) (= 7 x))) rock)
      (collides-with-shape? rock current-shape)))

; (reduce (fn [m [x y]] (assoc m x (max y (get m x)))) current-shape rock)
; TODO need to prune it - it should store only accessible contour of shape
(defn compute-current-shape [current-shape rock]
  (union current-shape (into #{} rock)))

(defn max-height [current-shape]
  (apply max (map second current-shape)))

(defn move-up [rock current-shape]
  (let [offset (max-height current-shape)]
    (map (fn [[x y]] [x (+ y offset 4)]) rock)))

(defn rock-move [{rock          :rock,
                  current-shape :current-shape,
                  shapes        :shapes,
                  fallen-count  :fallen-count
                  :as           state}]
  (if (nil? rock)
    (let [r (move-up (first shapes) current-shape)]
      (update-state state [[:rock r]
                           [:shapes (rest shapes)]]))
    (let [new-rock (shift-down rock)]
      (if (collides-with-shape? new-rock current-shape)
        (let [new-shape (compute-current-shape current-shape rock)
              count (inc fallen-count)]
          (update-state state [[:rock nil]
                               [:current-shape new-shape]
                               [:fallen-count count]]))
        (update-state state [[:rock new-rock]])))))

(defn jet-move [{rock :rock, jets :jets, current-shape :current-shape, :as state}]
  (if (nil? rock)
    state
    (let [direction (first jets)
          new-rock (jet-push direction rock)]
      (if (collides-sides? new-rock current-shape)
        (update-state state [[:jets (rest jets)]])
        (update-state state [[:rock new-rock] [:jets (rest jets)]])))))

(defn init-state [jets]
  {:rock          nil
   :current-shape (into #{} (map-indexed (fn [i x] [i x]) (take 7 (repeat -1))))
   :shapes        shapes-stream
   :fallen-count  0
   :jets          jets})

(defn height [{shape :current-shape}]
  (+ 1 (apply max (map second shape))))

(defn simulate [rocks-limit jets]
  (loop [state (init-state jets)]
    (if (= rocks-limit (:fallen-count state))
      (height state)
      (let [after-rock (rock-move state)
            after-jet (jet-move after-rock)]
        (recur after-jet)))))

(defn part-1 [lines]
  (simulate 2022 (jet-stream lines)))

(defn part-2 [lines]
  0)

(defn -main
  "Day 17"
  [& _]
  (let [lines (utils/file-lines "./resources/day17/input")]
    (println (str "part1: " (part-1 lines)))
    (println (str "part2: " (part-2 lines)))))