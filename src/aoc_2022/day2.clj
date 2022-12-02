(ns aoc-2022.day2
  (:gen-class))

; round logic
(defn hand-points [hand]
  (case hand
    :rock 1
    :paper 2
    :scissors 3))

(defn round-score [me opponent]
  (if (= me opponent)
    3
    (case me
      :rock (case opponent
              :paper 0
              :scissors 6)
      :paper (case opponent
               :rock 6
               :scissors 0)
      :scissors (case opponent
                  :rock 0
                  :paper 6))))

(defn play-round [me opponent]
  (+ (round-score me opponent) (hand-points me)))

; computing hand
(defn compute-hand [opponent desired-result]
  (if (= desired-result :draw)
    opponent
    (case desired-result
      :win (case opponent
             :rock :paper
             :paper :scissors
             :scissors :rock)
      :lose (case opponent
              :rock :scissors
              :paper :rock
              :scissors :paper))))

(defn compute-hands [pairs-coll]
  (map (fn [pair]
         (let [opponent (first pair)]
           (vector opponent (compute-hand opponent (second pair))))) pairs-coll))


; mappings
(def opponent-mapping {"A" :rock
                       "B" :paper
                       "C" :scissors})
(def me-simple-mapping {"X" :rock
                        "Y" :paper
                        "Z" :scissors})
(def me-to-result {"X" :lose
                   "Y" :draw
                   "Z" :win})

; parsing strings
(defn parse-line [line columns-mapping]
  (map #(get columns-mapping %) (clojure.string/split line #" ")))

(defn parse-lines [lines columns-mapping]
  (map #(parse-line % columns-mapping) lines))

(defn parse-lines-naive [lines]
  (parse-lines lines (merge opponent-mapping me-simple-mapping)))

(defn parse-lines-results [lines]
  (parse-lines lines (merge opponent-mapping me-to-result)))

; computing scores
(defn compute-score [games]
  (reduce + (map #(play-round (second %) (first %)) games)))

(defn compute-score-naive [lines]
  (compute-score (parse-lines-naive lines)))

(defn compute-score-choose-hand [lines]
  (compute-score (compute-hands (parse-lines-results lines))))


; reading input
(def input-file "./resources/day2/input")

(defn lines []
  (clojure.string/split-lines (slurp input-file)))

(defn -main
  "Day 2"
  [& _]
  (println (str "my score: " (compute-score-naive (lines))))
  (println (str "second algo: " (compute-score-choose-hand (lines)))))