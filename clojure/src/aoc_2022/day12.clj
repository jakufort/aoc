(ns aoc-2022.day12
  (:gen-class)
  (:require [aoc-2022.utils :as utils]
            [clojure.data.priority-map :refer [priority-map]]))

(defn to-height [point]
  (case point
    \S 1
    \E 26
    (- (int point) 96)))

(defn line-to-map [line y]
  (loop [x 0
         graph {}]
    (if (= x (count line))
      graph
      (let [point (nth line x)]
        (recur (inc x) (assoc graph [x y] {:height (to-height point)}))))))

(defn neighbours [[x y]]
  [[(- x 1) y] [(+ x 1) y] [x (- y 1)] [x (+ y 1)]])

(defn can-traverse [from to]
  (or (>= from to) (= 1 (- to from))))

(defn available-paths [point map-with-height graph]
  (let [height (map-with-height :height)
        existing-neighbours (filter #(contains? graph %) (neighbours point))
        traversable (filter #(can-traverse height ((get graph %) :height)) existing-neighbours)]
    (assoc map-with-height :paths (into #{} traversable))))

(defn add-paths [graph]
  (reduce-kv
    (fn [acc k v]
      (assoc acc k (available-paths k v graph)))
    {}
    graph))

(defn graph-with-heights [lines]
  (reduce-kv
    (fn [acc y line]
      (merge acc (line-to-map line y)))
    {}
    lines))

(defn check-line-for-start-and-end [line y]
  (loop [x 0
         start-end {}]
    (if (= x (count line))
      start-end
      (let [point (nth line x)]
        (recur (inc x) (case point
                         \S (assoc start-end :start [x y])
                         \E (assoc start-end :end [x y])
                         start-end))))))


(defn find-start-and-end [lines]
  (loop [y 0
         points {:start nil :end nil}]
    (let [from-line (merge points (check-line-for-start-and-end (nth lines y) y))]
      (if (and (not (nil? (from-line :start))) (not (nil? (from-line :end))))
        from-line
        (recur (inc y) from-line)))))



(defn load-graph [lines]
  (let [with-heights (graph-with-heights lines)
        with-paths (add-paths with-heights)
        start-end (find-start-and-end lines)]
    [with-paths start-end]))

; dijkstra
(defn map-vals [m f]
  (into {} (for [[k v] m] [k (f v)])))

(defn remove-keys [m pred]
  (select-keys m (filter (complement pred) (keys m))))

(defn dijkstra [start neighbour-weights]
  (loop [queue (priority-map start 0)
         distances {}]
    (if-let [[vertex v-distance] (peek queue)]
      (let [new-distance (-> (neighbour-weights vertex) (remove-keys distances) (map-vals (partial + v-distance)))]
        (recur (merge-with min (pop queue) new-distance) (assoc distances vertex v-distance)))
      distances)))


(defn all-distances [start traversable-paths]
  (dijkstra
    start
    (fn [point] (reduce (fn [acc path] (assoc acc path 1)) {} (traversable-paths point)))))

(defn paths [graph point]
  ((graph point) :paths))

(defn distance [graph start end]
  (let [all-distances (all-distances start #(paths graph %))]
    (get all-distances end)))

(defn part-1 [lines]
  (let [[graph start-end] (load-graph lines)]
    (distance graph (start-end :start) (start-end :end))))

(defn brute-force-distances [graph end all-a]
  (map (fn [start] (distance graph start end)) all-a))

(defn part-2 [lines]
  (let [[graph start-end] (load-graph lines)
        end (start-end :end)
        all-a (map first (filter #(= 1 ((second %) :height)) graph))
        distances (filter #(not (nil? %)) (brute-force-distances graph end all-a))]
    (first (sort distances))))

(defn -main
  "Day 12"
  [& _]
  (let [lines (utils/file-lines "./resources/day12/input")]
    (println (str "part1: " (part-1 lines)))
    (println (str "part2: " (part-2 lines)))))