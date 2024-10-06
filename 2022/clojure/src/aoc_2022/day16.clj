(ns aoc-2022.day16
  (:gen-class)
  (:require [aoc-2022.utils :as utils])
  (:import (clojure.lang PersistentQueue)))

(defn valve-and-tunnels [line]
  (clojure.string/split line #"; "))

(defn rate-from-string [rate-string]
  (Integer/parseInt (second (clojure.string/split rate-string #"="))))

(defn valve-map [valve-string]
  (let [parts (utils/split-space valve-string)
        id (second parts)
        rate (last parts)]
    [id {:rate (rate-from-string rate)}]))

(defn tunnels [tunnels-string]
  (clojure.string/split
    (clojure.string/replace
      (clojure.string/replace tunnels-string #"tunnels lead to valves " "")
      #"tunnel leads to valve " "")
    #", "))

(defn valve [line]
  (let [[valve tunnels-string] (valve-and-tunnels line)
        [valve-id v-map] (valve-map valve)]
    [valve-id (assoc v-map :tunnels (tunnels tunnels-string))]))

(defn load-valves [lines]
  (into {} (map valve lines)))

(defn with-non-empty-rates [valves]
  (into #{} (map (fn [[id _]] id) (filter (fn [[_ v]] (< 0 (v :rate))) valves))))

(defn paths [graph start]
  (loop [queue (conj PersistentQueue/EMPTY start)
         visited #{}
         paths {}]
    (cond
      (empty? queue) paths
      (visited (peek queue)) (recur (pop queue) visited paths)
      :else (let [current (peek queue)
                  node (graph current)
                  tunnels (:tunnels node)
                  visited (conj visited current)
                  queue (into (pop queue) tunnels)
                  paths (merge paths (into {} (map (fn [x] [x current]) (filter #(not (visited %)) tunnels))))]
              (recur queue visited paths)))))

(defn shortest-path [graph start end]
  (let [paths (paths graph start)]
    (loop [s end
           path [s]]
      (let [next (paths s)]
        (if (= start next)
          (vec (reverse path))
          (recur next (conj path next)))))))

(defn shortest-paths [valves start ends]
  (into {} (map (fn [x] [x (shortest-path valves start x)]) ends)))

(defn shortest-paths-between [valves to-visit]
  (into {} (map
             (fn [x]
               (let [rest-valves (disj to-visit x)]
                 [x (shortest-paths valves x rest-valves)]))
             to-visit)))

(defn rate [valves valve]
  ((valves valve) :rate))

(defn traverse [valves current minutes valves-left shortest-paths]
  (let [score (* minutes (rate valves current))
        left-to-search (filter #(< (count ((shortest-paths current) %)) minutes) valves-left)]
    (if (empty? left-to-search)
      score
      (let [traverse-left (map
                            (fn [next]
                              (let [path ((shortest-paths current) next)
                                    distance (count path)
                                    minutes-left (- minutes 1 distance)]
                                (traverse valves next minutes-left (disj valves-left next) shortest-paths)))
                            left-to-search)
            next-score (if (empty? traverse-left) 0 (apply max traverse-left))]
        (+ score next-score)))))

(defn part-1 [lines]
  (let [valves (load-valves lines)
        valves-to-visit (with-non-empty-rates valves)
        paths (shortest-paths-between valves (conj valves-to-visit "AA"))]
    (traverse valves "AA" 30 valves-to-visit paths)))

(defn part-2 [lines]
  0)

(defn -main
  "Day 16"
  [& _]
  (let [lines (utils/file-lines "./resources/day16/input")]
    (println (str "part1: " (part-1 lines)))
    (println (str "part2: " (part-2 lines)))))
