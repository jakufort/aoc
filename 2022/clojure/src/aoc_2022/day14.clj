(ns aoc-2022.day14
  (:gen-class)
  (:require [aoc-2022.utils :as utils]
            [clojure.set :refer [union]]))

(defn coordinates [point]
  (map #(Integer/parseInt %) (clojure.string/split point #",")))

(defn point-pairs [line]
  (partition 2 1 (map coordinates (clojure.string/split line #" -> "))))

(defn next-dim [a b]
  (let [diff (- a b)]
    (if (= 0 diff)
      a
      (if (< 0 diff)
        (dec a)
        (inc a)))))

(defn next-rock [[x y] [ex ey]]
  [(next-dim x ex) (next-dim y ey)])

(defn line-of-rocks [[start end]]
  (loop [rocks #{}
         current-point (vec start)]
    (if (= current-point end)
      (conj rocks current-point)
      (recur (conj rocks current-point) (next-rock current-point end)))))

(defn rocks-lines [pairs]
  (map #(reduce (fn [acc x] (union acc (line-of-rocks x))) #{} %) pairs))

(defn rocks [lines]
  (let [pairs (map point-pairs lines)]
    (reduce (fn [acc x] (union acc x)) #{} (rocks-lines pairs))))

; boundaries
(defn cave-bottom [rocks]
  (apply max (map second rocks)))

; simulation
(def sand-start [500 0])

(defn not-in? [set element]
  (not (contains? set element)))

(defn sand-options [[x y :as current]]
  (let [y-down (inc y)
        down [x y-down]
        left [(dec x) y-down]
        right [(inc x) y-down]]
    [down left right current]))

(defn next-sand-position [rocks sand-at-rest current]
  (let [options (sand-options current)]
    (loop [to-check (first options)
           rest-options (rest options)]
      (if (= to-check current)
        current
        (if (and (not-in? rocks to-check) (not-in? sand-at-rest to-check))
          to-check
          (recur (first rest-options) (rest rest-options)))))))

(defn falling-down [rocks]
  (let [bottom (cave-bottom rocks)]
    (loop [position sand-start
           at-rest #{}]
      (let [next-position (next-sand-position rocks at-rest position)]
        (if (or (= bottom (second next-position)) (contains? at-rest sand-start))
          (count at-rest)
          (if (= next-position position)
            (recur sand-start (conj at-rest position))
            (recur next-position at-rest)))))))


(defn part-1 [lines]
  (falling-down (rocks lines)))

(defn brute-force-part2 [rocks bottom]
  (let [real-bottom (+ 2 bottom)]
    (union rocks (into #{} (map (fn [x] [x real-bottom]) (range 0 1000))))))

(defn part-2 [lines]
  (let [rocks (rocks lines)]
    (falling-down (brute-force-part2 rocks (cave-bottom rocks)))))

(defn -main
  "Day 14"
  [& _]
  (let [lines (utils/file-lines "./resources/day14/input")]
    (println (str "part1: " (part-1 lines)))
    (println (str "part2: " (part-2 lines)))))
