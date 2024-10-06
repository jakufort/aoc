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
(defn in-range [low up to-check]
  (and (<= low to-check) (>= up to-check)))

(defn overlap [[[ll lh] [rl rh]]]
  (or
    (and (in-range ll lh rl) (in-range ll lh rh))
    (and (in-range rl rh ll) (in-range rl rh lh))))

(defn partial-overlap [[[ll lh] [rl rh]]]
  (or
    (and (in-range ll lh rl) (not (> ll rh)))
    (and (in-range rl rh ll) (not (< lh rl)))))

; solution
(defn count-overlaps [lines overlap?]
  (count (filter overlap? (map line-into-range lines))))

(defn part-1 [lines]
  (count-overlaps lines overlap))

(defn part-2 [lines]
  (count-overlaps lines partial-overlap))

(defn -main
  "Day 4"
  [& _]
  (let [lines (utils/file-lines "./resources/day4/input")]
    (println (str "part1: " (part-1 lines)))
    (println (str "part2: " (part-2 lines)))))
