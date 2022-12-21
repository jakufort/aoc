(ns aoc-2022.day18
  (:gen-class)
  (:require [aoc-2022.utils :as utils]))

(defn cube [line]
  (map #(Integer/parseInt %) (clojure.string/split line #",")))

(defn read-cubes [lines]
  (into #{} (map cube lines)))

(defn neighbours [[x y z]]
  [[(+ x 1) y z]
   [(- x 1) y z]
   [x (+ y 1) z]
   [x (- y 1) z]
   [x y (+ z 1)]
   [x y (- z 1)]])

(defn surface-count [cubes]
  (let [neighbours (map neighbours cubes)
        not-covered (map #(filter (fn [x] (not (contains? cubes x))) %) neighbours)]
    (reduce + 0 (map count not-covered))))

(defn part-1 [lines]
  (surface-count (read-cubes lines)))

(defn part-2 [lines]
  0)

(defn -main
  "Day 18"
  [& _]
  (let [lines (utils/file-lines "./resources/day18/input")]
    (println (str "part1: " (part-1 lines)))
    (println (str "part2: " (part-2 lines)))))