(ns aoc-2022.day15
  (:gen-class)
  (:require [aoc-2022.utils :as utils]))

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
            covered-range [(- sx range-half-in-line) (+ sx range-half-in-line)]]
        covered-range)
      [])))

(defn sum-range [[ll lh :as left] [rl rh :as right]]
  ; assuming that ll <= rl
  (if (empty? left)
    [right]
    (cond
      (> rl lh) [left right]
      (and (<= ll rl) (>= lh rh)) [left]
      (<= rl lh) [[ll rh]])))

(defn sum-ranges [ranges]
  (let [sorted (filter #(not (empty? %)) (sort-by first ranges))]
    (reduce
      (fn [acc r]
        (let [sum (sum-range (last acc) r)]
          (if (< 1 (count acc))
            (into [] (concat (drop-last acc) sum))
            sum)))
      []
      sorted)))

(defn count-covered [ranges]
  (reduce (fn [acc [l r]] (+ acc (- r l))) 0 ranges))

(defn already-covered-in-line [all-sensors line]
  (let [from-sensors (map #(covered-points % line) all-sensors)
        all-ranges (sum-ranges from-sensors)]
    all-ranges))

(defn part-1 [lines]
  (let [sensors (sensors lines)
        all-ranges (already-covered-in-line sensors 2000000)]
    (count-covered all-ranges)))

(defn find-gap [all-ranges limit]
  (let [gaps (filter #(or (< 0 (first %)) (> limit (second %))) all-ranges)
        fst (first gaps)]
    (if (nil? fst)
      -1
      (inc (second fst)))))


(defn find-beacon-position-brute-force [sensors limit]
  (loop [y 0]
    (let [all-ranges (already-covered-in-line sensors y)]
      (if (= 0 (mod y 10000))
        (do (println y)))
      (if (= y limit)
        [-1 -1]
        (let [x (find-gap all-ranges limit)]
          (if (= -1 x)
            (recur (inc y))
            [x y]))))))

(defn tuning-frequency [sensors limit]
  (let [[x y] (find-beacon-position-brute-force sensors limit)]
    (println x y)
    (+ (* x 4000000) y)))

(defn part-2 [lines]
  (let [sensors (sensors lines)]
    (tuning-frequency sensors 4000000)))


(defn -main
  "Day 15"
  [& _]
  (let [lines (utils/file-lines "./resources/day15/input")]
    (println (str "part1: " (part-1 lines)))
    (println (str "part2: " (part-2 lines)))))
