(ns aoc-2022.day8
  (:gen-class)
  (:require [aoc-2022.utils :as utils]))

; loading forest
(defn row [line]
  (map (fn [^Character x] (Character/digit x 10)) line))

(defn forest [lines]
  (map row lines))

; finding line of sights
(defn split [line-of-sight position]
  [(reverse (take position line-of-sight)) (drop (+ 1 position) line-of-sight)])

(defn up-down [forest row column]
  (split (reduce (fn [acc x] (conj acc (nth x column))) [] forest) row))

(defn left-right [forest row column]
  (split (nth forest row) column))

(defn every-lower? [direction tree]
  (every? #(> tree %) direction))

(defn tree-with-all-directions [forest row column]
  (let [tree (nth (nth forest row) column)
        [up down] (up-down forest row column)
        [left right] (left-right forest row column)
        all-directions [up down left right]]
    [tree all-directions]))

; finding visible trees
(defn visible [forest row column]
  (let [[tree all-directions] (tree-with-all-directions forest row column)]
    (or
      (not (not-any? empty? all-directions))
      (not (not-any? #(every-lower? % tree) all-directions)))))

(defn map-indexed-2d [_2d func-to-call]
  (map-indexed
    (fn [r row] (map-indexed (fn [c _] (func-to-call _2d r c)) row))
    _2d))

(defn trees-visibility [forest]
  (map-indexed-2d forest visible))

(defn count-visible [trees-visibility]
  (count (filter #(true? %) (flatten trees-visibility))))

; scenic score
(defn view-score [tree line-of-sight]
  (let [seen (split-with #(> tree %) line-of-sight)
        blocking-tree (if (empty? (second seen)) 0 1)]
    (+ blocking-tree (count (first seen)))))

(defn scenic-score [forest row column]
  (let [[tree all-directions] (tree-with-all-directions forest row column)
        scores (map #(view-score tree %) all-directions)]
    (reduce * 1 scores)))

(defn scenic-scores [forest]
  (flatten (map-indexed-2d forest scenic-score)))

(defn part-1 [lines]
  (let [trees-visibility (trees-visibility (forest lines))]
    (count-visible trees-visibility)))

(defn part-2 [lines]
  (let [scores (scenic-scores (forest lines))]
    (last (sort scores))))

(defn -main
  "Day 8"
  [& _]
  (let [lines (utils/file-lines "./resources/day8/input")]
    (println (str "part1: " (part-1 lines)))
    (println (str "part2: " (part-2 lines)))))
