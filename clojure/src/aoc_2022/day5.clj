(ns aoc-2022.day5
  (:gen-class)
  (:require [aoc-2022.utils :as utils]))

; crates
(defn crates-from-string [line]
  (map #(nth % 2) (partition 4 (str " " line))))

(defn fill-stacks [stacks crates]
  (if (= (count stacks) 0)
    (map (fn [x] [x]) crates)
    (map-indexed (fn [idx itm] (conj itm (nth crates idx))) stacks)))

(defn clear-nils [stack]
  (filter #(not= \space %) stack))

(defn crates-list [lines]
  (map reverse (map clear-nils (reduce fill-stacks [] (map crates-from-string lines)))))

(defn crates-lists [crate-lines]
  (map vec (crates-list crate-lines)))

(defn parse-crates [crate-lines]
  (into {} (map-indexed (fn [idx itm] [(inc idx) itm]) (crates-lists crate-lines))))

; movements
(defn parse-move [line]
  (map #(Integer/parseInt %) (take-nth 2 (drop 1 (clojure.string/split line #" ")))))

(defn parse-moves [move-lines]
  (map parse-move move-lines))

(defn change-stacks [stacks from to to-move-func new-from-func new-to-func]
  (let [current-from (get stacks from)
        to-move (to-move-func current-from)
        new-from (new-from-func current-from)
        new-to (new-to-func (get stacks to) to-move)]
    (assoc (assoc stacks from new-from) to new-to)))

; CrateMover 9000
(defn apply-single-move [stacks from to]
  (change-stacks stacks from to peek pop conj))

(defn crate-mover-9000 [stacks [n from to]]
  (if (= n 0)
    stacks
    (crate-mover-9000 (apply-single-move stacks from to) [(dec n) from to])))

; CrateMover 9001
(defn crate-mover-9001 [stacks [n from to]]
  (change-stacks stacks from to (partial take-last n) #(take (- (count %) n) %) concat))

; operating
(defn operate-crane [start-stacks movements move-strategy]
  (reduce (fn [acc x] (move-strategy acc x)) start-stacks movements))

(defn run-crate-using-notes [lines move-strategy]
  (let [stacks-lines (take-while #(not (clojure.string/starts-with? % " 1")) lines)
        move-lines (filter #(clojure.string/starts-with? % "move") lines)
        stacks (parse-crates stacks-lines)
        moves (parse-moves move-lines)
        end-state (operate-crane stacks moves move-strategy)]
    (apply str (map last (map #(get end-state %) (sort (keys end-state)))))))

; solution
(defn part-1 [lines]
  (run-crate-using-notes lines crate-mover-9000))

(defn part-2 [lines]
  (run-crate-using-notes lines crate-mover-9001))

(defn -main
  "Day 5"
  [& _]
  (let [lines (utils/file-lines "./resources/day5/input")]
    (println (str "part1: " (part-1 lines)))
    (println (str "part2: " (part-2 lines)))))
