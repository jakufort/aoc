(ns aoc-2022.day18
  (:gen-class)
  (:require [aoc-2022.utils :as utils]
            [clojure.set :refer [union]]))

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

(defn boundary [cubes]
  (loop [seen #{}
         todo #{[-1 -1 -1]}]
    (let [here (first todo)]
      (if (nil? here)
        seen
        (let [new-todo (rest todo)
              neigh (neighbours here)
              not-seen (filter #(not (contains? seen %)) neigh)
              not-in-cubes (filter #(not (contains? cubes %)) not-seen)
              ; assumes that cubes are in range of 0-25
              in-boundary (filter (fn [x] (every? #(and (>= % -1) (<= % 25)) x)) not-in-cubes)]
          (recur (union seen #{here}) (union new-todo in-boundary)))))))

(defn part-2 [lines]
  (let [cubes (read-cubes lines)
        boundary (boundary cubes)
        neighbours (map neighbours cubes)
        only-on-boundary (map #(filter (fn [x] (contains? boundary x)) %) neighbours)]
    (reduce + 0 (map count only-on-boundary))))

(defn -main
  "Day 18"
  [& _]
  (let [lines (utils/file-lines "./resources/day18/input")]
    (println (str "part1: " (part-1 lines)))
    (println (str "part2: " (part-2 lines)))))