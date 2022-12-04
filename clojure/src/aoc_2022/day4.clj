(ns aoc-2022.day4
  (:gen-class)
  (:require [aoc-2022.utils :as utils]))

; data parsing
(defn to-int [string]
  (Integer/parseInt string))

(defn range-to-int [[low high]]
  [(to-int low) (to-int high)])

(defn work-range [elf]
  (range-to-int (clojure.string/split elf #"-")))

(defn pair-to-range [[left right]]
  [(work-range left) (work-range right)])

(defn line-into-range [line]
  (pair-to-range (clojure.string/split line #",")))

; overlap
(defn overlap [[[left-low left-high] [right-low right-high]]]
  (or
    (and (>= left-low right-low) (<= left-high right-high))
    (and (<= left-low right-low) (>= left-high right-high))))

(defn partial-overlap [[[left-low left-high] [right-low right-high]]]
  (or
    (and (>= left-high right-low) (not (> left-low right-high)))
    (and (>= right-high left-low) (not (< left-high right-low)))))


; solution
(defn part-1 [lines]
  (count (filter overlap (map line-into-range lines))))

(defn part-2 [lines]
  (count (filter partial-overlap (map line-into-range lines))))

(defn -main
  "Day 4"
  [& _]
  (let [lines (utils/file-lines "./resources/day4/input")]
    (println (str "part1: " (part-1 lines)))
    (println (str "part2: " (part-2 lines)))))
