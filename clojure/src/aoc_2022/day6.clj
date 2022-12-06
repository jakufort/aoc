(ns aoc-2022.day6
  (:gen-class))

(defn window [size stream]
  (partition size 1 stream))

(defn distinct-size-eq [expected itm]
  (= expected (count (distinct itm))))

(defn find-first [predicate coll]
  (first (filter predicate coll)))

(defn find-unique [unique-size stream]
  (let [windowed (window unique-size stream)
        with-index (map-indexed (fn [idx itm] [idx (distinct-size-eq unique-size itm)]) windowed)
        unique (find-first (fn [x] (second x)) with-index)]
    (+ unique-size (first unique))))

(defn part-1 [stream]
  (find-unique 4 stream))

(defn part-2 [stream]
  (find-unique 14 stream))

(defn -main
  "Day 6"
  [& _]
  (let [file-content (slurp "./resources/day6/input")]
    (println (str "part1: " (part-1 file-content)))
    (println (str "part2: " (part-2 file-content)))))
