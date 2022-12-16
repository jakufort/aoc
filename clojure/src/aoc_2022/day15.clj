(ns aoc-2022.day15
  (:gen-class)
  (:require [aoc-2022.utils :as utils]
            [clojure.set :refer [union]]))

; parsing
(defn clean-up [line]
  (clojure.string/replace
    (clojure.string/replace line #"Sensor at " "")
    #": closest beacon is at "
    ":"))

(defn coord-to-int [coord]
  (Integer/parseInt (subs coord 2)))

(defn coordinates-from-string [coord]
  (map coord-to-int (clojure.string/split coord #", ")))

(defn sensor [cleaned-line]
  (let [[sensor beacon] (map coordinates-from-string (clojure.string/split cleaned-line #":"))]
    [sensor beacon]))

(defn sensors [lines]
  (map sensor (map clean-up lines)))

; computing
(defn distance [x y]
  (abs (- x y)))

(defn sensor-range [[x y] [bx by]]
  (+ (distance x bx) (distance y by)))

(defn covered-points [[[sx sy :as sensor] beacon] line]
  (let [sensor-range (sensor-range sensor beacon)
        distance-to-line (distance sy line)]
    (if (>= sensor-range distance-to-line)
      (let [range-half-in-line (- sensor-range distance-to-line)
            x-values (range (- sx range-half-in-line) (+ sx range-half-in-line))]
        (set (map (fn [x] [x line]) x-values)))
      #{})))

(defn already-covered-in-line [all-sensors line]
  (let [from-sensors (map #(covered-points % line) all-sensors)
        result (reduce union #{} from-sensors)]
    result))

(defn part-1 [lines]
  (let [sensors (sensors lines)
        covered (already-covered-in-line sensors 2000000)]
    (count covered)))


(defn part-2 [_]
  0)

(defn -main
  "Day 15"
  [& _]
  (let [lines (utils/file-lines "./resources/day15/input")]
    (println (str "part1: " (part-1 lines)))
    (println (str "part2: " (part-2 lines)))))
