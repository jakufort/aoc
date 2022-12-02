(ns aoc-2022.day1
  (:gen-class))

(def input-file "./resources/day1/input")

(defn group-calories [lines]
  (partition-by #(= "" %) lines))

(defn grouped-by-calories [lines]
  (filter #(not= '("") %) (group-calories lines)))

(defn to-numbers [groups]
  (map #(map (fn [x] (Integer/parseInt x)) %) groups))

(defn sum-groups [grouped]
  (map #(reduce + 0 %) grouped))

(defn file-lines [file-name]
  (clojure.string/split-lines (slurp file-name)))

(defn sort-desc [coll]
  (reverse (sort coll)))

(defn elfs []
  (-> input-file file-lines grouped-by-calories to-numbers sum-groups sort-desc))

(defn first-elf [elfs]
  (first elfs))

(defn first-three-elfs-sum [elfs]
  (reduce + (take 3 elfs)))

(defn -main
  "Day 1"
  [& _]
  (let [elfs (elfs)]
    (println (str "Highest elf: " (first-elf elfs)))
    (println (str "first three: " (first-three-elfs-sum elfs)))))

