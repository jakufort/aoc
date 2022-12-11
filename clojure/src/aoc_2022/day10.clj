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

(defn cpu-cycle [[[_ _ c _] _]]
  c)

; part 1
(defn signal-strengths [cycles-to-check cmds]
  (loop [state [[nil nil 0 1] cmds]
         left-to-check cycles-to-check
         strengths []]
    (if (empty? left-to-check)
      strengths
      (let [new-cycle (inc (cpu-cycle state))
            new-state (do-cycle state)]
        (if (some #(= % new-cycle) left-to-check)
          ; assumes that cycles-to-check is ordered list
          (recur new-state (rest left-to-check) (conj strengths [new-cycle (reg-value state)]))
          (recur new-state left-to-check strengths))))))

(defn sum-strengths [strengths]
  (reduce + 0 (map #(* (first %) (second %)) strengths)))

(defn part-1 [lines]
  (let [cmds (map parse-line lines)
        strengths (signal-strengths [20 60 100 140 180 220] cmds)]
    (sum-strengths strengths)))

; part 2
(defn print? [crt-position reg-x]
  (or (= (- reg-x 1) crt-position) (= (+ reg-x 1) crt-position) (= reg-x crt-position)))

(defn render-point [crt-position reg-x]
  (let [new-line (if (= 0 crt-position) "\n" "")]
    (if (print? crt-position reg-x)
      (str new-line "▓")
      (str new-line "░"))))

(defn render [cmds]
  (loop [state [[nil nil 0 1] cmds]
         display ""]
    (let [current-cycle (cpu-cycle state)
          crt-position (mod current-cycle 40)
          reg-x (reg-value state)]
      (if (>= current-cycle 240)
        display
        (recur (do-cycle state) (str display (render-point crt-position reg-x)))))))


(defn part-2 [lines]
  (let [cmds (map parse-line lines)]
    (render cmds)))

(defn -main
  "Day 10"
  [& _]
  (let [lines (utils/file-lines "./resources/day10/input")]
    (println (str "part1: " (part-1 lines)))
    (println (str "part2: " (part-2 lines)))))