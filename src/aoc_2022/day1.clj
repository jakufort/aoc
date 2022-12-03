(ns aoc-2022.day1
  (:gen-class)
  (:require [aoc-2022.utils :as utils]))

(defn group-separator? [element]
  (= "" element))

(defn grouped-by-calories [lines]
  (filter #(not= '("") %) (partition-by group-separator? lines)))

(defn to-int [string]
  (Integer/parseInt string))

(defn calories-groups [lines]
  (map #(map to-int %) (grouped-by-calories lines)))

(defn sum-groups [grouped]
  (map #(reduce + 0 %) grouped))

(defn sort-desc [coll]
  (reverse (sort coll)))

(defn first-elf [elfs]
  (first elfs))

(defn first-three-elfs-sum [elfs]
  (reduce + (take 3 elfs)))

(defn -main
  "Day 1"
  [& _]
  (let [elfs (-> (utils/file-lines "./resources/day1/input") calories-groups sum-groups sort-desc)]
    (println (str "Highest elf: " (first-elf elfs)))
    (println (str "first three: " (first-three-elfs-sum elfs)))))