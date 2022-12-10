(ns aoc-2022.day9
  (:gen-class)
  (:require [aoc-2022.utils :as utils]))

; input parsing
(defn parse-move [line]
  (let [[direction amount-str] (clojure.string/split line #" ")
        amount (Integer/parseInt amount-str)]
    [direction amount]))

; simulation
(defn move-head [[x y] move-type]
  (case move-type
    "R" [(+ x 1) y]
    "L" [(- x 1) y]
    "U" [x (+ y 1)]
    "D" [x (- y 1)]))

(defn move-one-dimension [dimension other-dimension element]
  (let [abs-el (abs dimension)]
    (if (or (= 2 abs-el) (and (= 1 abs-el) (= 2 (abs other-dimension))))
      ((if (neg? dimension) - +) element 1)
      element)))

(defn move-tail [[x1 y1] [x2 y2]]
  (let [[dx dy] [(- x1 x2) (- y1 y2)]]
    [(move-one-dimension dx dy x2) (move-one-dimension dy dx y2)]))

(defn make-single-move [move-type [knots visited]]
  (let [head (move-head (first knots) move-type)
        new-knots (reduce #(conj % (move-tail (last %) %2)) [head] (rest knots))]
    [new-knots (conj visited (last new-knots))]))

(defn make-move [[move-type amount] state]
  (loop [counter amount
         new-state state]
    (if (= 0 counter)
      new-state
      (recur (dec counter) (make-single-move move-type new-state)))))

(defn simulate-n-knots [lines n]
  (loop [line (first lines)
         lines-rest (rest lines)
         state [(vec (take n (repeat [0 0]))) #{[0 0]}]]
    (if (nil? line)
      state
      (recur (first lines-rest) (rest lines-rest) (make-move (parse-move line) state)))))

(defn tail-visited-count [lines n]
  (let [state (simulate-n-knots lines n)]
    (count (second state))))

; part 1
(defn part-1 [lines]
  (tail-visited-count lines 2))

; part 2
(defn part-2 [lines]
  (tail-visited-count lines 10))

(defn -main
  "Day 9"
  [& _]
  (let [lines (utils/file-lines "./resources/day9/input")]
    (println (str "part1: " (part-1 lines)))
    (println (str "part2: " (part-2 lines)))))
