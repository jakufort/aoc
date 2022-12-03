(ns aoc-2022.day3
  (:gen-class)
  (:require [aoc-2022.utils :as utils]
            [clojure.set :refer [intersection]]))

; line processing
(defn line-split [line]
  (let [size (count line)
        half (/ size 2)]
    [(subs line 0 half) (subs line half size)]))

(defn to-sets [[left right]]
  [(set left) (set right)])

(defn common-element [[left right]]
  (first (intersection left right)))

(defn common-item [line]
  (common-element (to-sets (line-split line))))

; priority compute
(defn offset [^Character character]
  (if (Character/isLowerCase character) 96 38))

(defn priority [character]
  (- (int character) (offset character)))

; part 2
(defn three-elf-groups [lines]
  (partition 3 lines))

(defn find-badge [[fst snd thr]]
  (first (intersection (set fst) (set snd) (set thr))))

; solutions
(defn sum-priorities [characters]
  (reduce + (map priority characters)))

(defn item-in-both-compartments-priorities [lines]
  (sum-priorities (map common-item lines)))

(defn badges-priority [lines]
  (sum-priorities (map find-badge (three-elf-groups lines))))

(defn -main
  "Day 3"
  [& _]
  (let [lines (utils/file-lines "./resources/day3/input")]
    (println (str "part1: " (item-in-both-compartments-priorities lines)))
    (println (str "part2: " (badges-priority lines)))))