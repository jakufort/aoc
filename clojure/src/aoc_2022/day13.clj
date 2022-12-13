(ns aoc-2022.day13
  (:gen-class)
  (:require [aoc-2022.utils :as utils]))

; input parsing
(defn pair [[one two]]
  [(clojure.edn/read-string one) (clojure.edn/read-string two)])

(defn packets-pairs [lines]
  (map pair (filter #(not= '("") %) (partition-by #(= "" %) lines))))

; packets checking
(defn compare-lists [l r elements-comparator]
  (loop [l-el (first l)
         r-el (first r)
         rest-l (rest l)
         rest-r (rest r)]
    (if (nil? l-el)
      (if (nil? r-el)
        :unknown
        :right)
      (if (nil? r-el)
        :not-right
        (let [compare-result (elements-comparator l-el r-el)]
          (case compare-result
            :right :right
            :not-right :not-right
            :unknown (recur (first rest-l) (first rest-r) (rest rest-l) (rest rest-r))))))))

(defn compare-elements [l r]
  (if (number? l)
    (if (number? r)
      (if (= l r)
        :unknown
        (if (> l r)
          :not-right
          :right))
      (compare-elements [l] r))
    (if (number? r)
      (compare-elements l [r])
      (compare-lists l r compare-elements))))

(defn right-order? [[left right]]
  (case (compare-lists left right compare-elements)
    :right true
    false))


(defn part-1 [lines]
  (let [packets (packets-pairs lines)
        checked (map-indexed #(vector (right-order? %2) (inc %)) packets)
        only-right (filter first checked)]
    (reduce + 0 (map second only-right))))

; part 2
(defn all-packets [lines]
  (map clojure.edn/read-string (filter #(not (empty? %)) lines)))

(defn with-dividers [packets]
  (concat packets [[[2]] [[6]]]))

(defn sort-packets [packets]
  (sort #(right-order? [% %2]) packets))

(defn find-dividers [packets]
  (reduce-kv
    (fn [dividers idx itm]
      (if (or (= itm [[2]]) (= itm [[6]]))
        (conj dividers (inc idx))
        dividers))
    []
    (vec packets)))

(defn part-2 [lines]
  (reduce * 1 (-> (all-packets lines) with-dividers sort-packets find-dividers)))

(defn -main
  "Day 13"
  [& _]
  (let [lines (utils/file-lines "./resources/day13/input")]
    (println (str "part1: " (part-1 lines)))
    (println (str "part2: " (part-2 lines)))))
