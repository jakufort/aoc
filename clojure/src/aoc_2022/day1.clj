(ns aoc-2022.day1
  (:gen-class)
  (:require [aoc-2022.utils :as utils]))

(defn grouped-by-calories [lines]
  (filter #(not= '("") %) (partition-by #(= "" %) lines)))

(defn to-int [groups]
  (map #(map (fn [x] (Integer/parseInt x)) %) groups))

(defn calories-groups [lines]
  (to-int (grouped-by-calories lines)))

(defn sum-groups [grouped]
  (map #(reduce + 0 %) grouped))

(defn sort-desc [coll]
  (reverse (sort coll)))

(defn first-elf [elves]
  (first elves))

(defn first-three-elves-sum [elves]
  (reduce + (take 3 elves)))

(defn -main
  "Day 1"
  [& _]
  (let [elves (-> (utils/file-lines "./resources/day1/input") calories-groups sum-groups sort-desc)]
    (println (str "Highest elf: " (first-elf elves)))
    (println (str "first three: " (first-three-elves-sum elves)))))