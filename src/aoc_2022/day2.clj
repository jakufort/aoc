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

; parsing strings
(def column-mapping {"A" :rock
                     "X" :rock
                     "B" :paper
                     "Y" :paper
                     "C" :scissors
                     "Z" :scissors})

(defn parse-line [line]
  (map #(get column-mapping %) (clojure.string/split line #" ")))

(defn parse-lines [lines]
  (map #(parse-line %) lines))


; computing scores
(defn compute-score-naive [lines]
  (reduce + (map #(play-round (second %) (first %)) (parse-lines lines))))


; reading input
(def input-file "./resources/day2/input")

(defn lines []
  (clojure.string/split-lines (slurp input-file)))

(defn -main
  "Day 2"
  [& _]
  (println (str "my score: " (compute-score-naive (lines)))))