(ns aoc-2022.day2
  (:gen-class)
  (:require [aoc-2022.utils :as utils]))

; round logic
(def outcomes
  {:rock     {:rock     :draw
              :paper    :win
              :scissors :lose}
   :paper    {:paper    :draw
              :scissors :win
              :rock     :lose}
   :scissors {:scissors :draw
              :rock     :win
              :paper    :lose}})
(def hand-score
  {:rock     1
   :paper    2
   :scissors 3})
(def outcome-score
  {:win  6
   :draw 3
   :lose 0})

(defn single-game-score [[opponent me]]
  (+ (outcome-score ((outcomes opponent) me)) (hand-score me)))

; computing hand
(defn outcome-matches? [[_ outcome] wanted-outcome]
  (= outcome wanted-outcome))

(defn matching-outcome [outcomes wanted-outcome]
  (first (filter #(outcome-matches? % wanted-outcome) outcomes)))

(defn hand-to-play [opponent outcome]
  (first (matching-outcome (outcomes opponent) outcome)))

; parsing strings
(def opponent-mapping
  {"A" :rock
   "B" :paper
   "C" :scissors})
(defn parse-line [line snd-column-mapping]
  (let [[opponent-action me] (utils/split-space line)
        opponent (opponent-mapping opponent-action)]
    (vector opponent (snd-column-mapping opponent me))))

(defn parse-lines [lines snd-column-mapper]
  (map #(parse-line % snd-column-mapper) lines))

(def hand-mapping
  {"X" :rock
   "Y" :paper
   "Z" :scissors})
(defn map-to-hand [_ me]
  (hand-mapping me))

(def outcome-mapping
  {"X" :lose
   "Y" :draw
   "Z" :win})
(defn map-outcome-to-hand [opponent me]
  (hand-to-play opponent (outcome-mapping me)))

; computing scores
(defn compute-score [lines mapper]
  (reduce + (map single-game-score (parse-lines lines mapper))))

(defn compute-score-part-1 [lines]
  (compute-score lines map-to-hand))

(defn compute-score-part-2 [lines]
  (compute-score lines map-outcome-to-hand))


(defn -main
  "Day 2"
  [& _]
  (let [lines (utils/file-lines "./resources/day2/input")]
    (println (str "my score: " (compute-score-part-1 lines)))
    (println (str "second algo: " (compute-score-part-2 lines)))))