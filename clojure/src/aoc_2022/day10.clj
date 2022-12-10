(ns aoc-2022.day10
  (:gen-class)
  (:require [aoc-2022.utils :as utils]))

(defn parse-line [line]
  (let [[cmd & args] (utils/split-space line)]
    (case cmd
      "noop" [cmd 1]
      "addx" [cmd 2 (Integer/parseInt (first args))])))

(defn thr [coll]
  (nth coll 2))

(defn do-cycle [[[current-cmd cycles-left cycles reg-x :as whole-state] cmds]]
  (if (nil? current-cmd)
    (if (not (empty? cmds))
      (let [new-cmd (first cmds)]
        (do-cycle [[new-cmd (second new-cmd) cycles reg-x] (rest cmds)]))
      [whole-state cmds])
    (if (> cycles-left 1)
      [[current-cmd (dec cycles-left) (inc cycles) reg-x] cmds]
      (if (<= cycles-left 1)
        (let [new-cmd (first cmds)
              next-state [new-cmd (second new-cmd) (inc cycles)]
              rest-cmds (rest cmds)]
          (case (first current-cmd)
            "noop" [(conj next-state reg-x) rest-cmds]
            "addx" [(conj next-state (+ reg-x (thr current-cmd))) rest-cmds]))))))

(defn reg-value [[[_ _ _ reg] _]]
  reg)

(defn signal-strengths [cycles-to-check cmds]
  (loop [cycle 0
         execution-state [[nil nil 0 1] cmds]
         left-to-check cycles-to-check
         strengths []]
    (if (empty? left-to-check)
      strengths
      (let [new-cycle (inc cycle)
            new-state (do-cycle execution-state)]
        (if (some #(= % new-cycle) left-to-check)
          ; assumes that cycles-to-check is ordered list
          (recur new-cycle new-state (rest left-to-check) (conj strengths [new-cycle (reg-value execution-state)]))
          (recur new-cycle new-state left-to-check strengths))))))

(defn sum-strengths [strengths]
  (reduce + 0 (map #(* (first %) (second %)) strengths)))

; part 1
(defn part-1 [lines]
  (let [cmds (map parse-line lines)
        strengths (signal-strengths [20 60 100 140 180 220] cmds)]
    (println strengths)
    (sum-strengths strengths)))

; part 2
(defn part-2 [lines]
  0)

(defn -main
  "Day 10"
  [& _]
  (let [lines (utils/file-lines "./resources/day10/input")]
    (println (str "part1: " (part-1 lines)))
    (println (str "part2: " (part-2 lines)))))