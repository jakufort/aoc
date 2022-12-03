(ns aoc-2022.day2
  (:gen-class))

; round logic
(def outcomes
  {:rock     {:rock :draw
              :paper    :win
              :scissors :lose}
   :paper    {:paper :draw
              :scissors :win
              :rock     :lose}
   :scissors {:scissors :draw
              :rock  :win
              :paper :lose}})
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

(defn compute-round [pair]
  (let [[opponent outcome] pair]
    (vector opponent (hand-to-play opponent outcome))))


; parsing strings
(def opponent-mapping {"A" :rock
                       "B" :paper
                       "C" :scissors})
(def me-simple-mapping {"X" :rock
                        "Y" :paper
                        "Z" :scissors})
(def me-to-result {"X" :lose
                   "Y" :draw
                   "Z" :win})

(defn parse-line [line columns-mapping]
  (map #(get columns-mapping %) (clojure.string/split line #" ")))

(defn parse-lines [lines columns-mapping]
  (map #(parse-line % columns-mapping) lines))


; computing scores
(defn compute-score [games]
  (reduce + (map single-game-score games)))

(def naive-mapping (merge opponent-mapping me-simple-mapping))

(def desired-results-mapping (merge opponent-mapping me-to-result))

(defn compute-score-naive [lines]
  (compute-score (parse-lines lines naive-mapping)))

(defn compute-score-choose-hand [lines]
  (compute-score (map compute-round (parse-lines lines desired-results-mapping))))


; reading input
(def input-file "./resources/day2/input")

(defn lines []
  (clojure.string/split-lines (slurp input-file)))

(defn -main
  "Day 2"
  [& _]
  (println (str "my score: " (compute-score-naive (lines))))
  (println (str "second algo: " (compute-score-choose-hand (lines)))))