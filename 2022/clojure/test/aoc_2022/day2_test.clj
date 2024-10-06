(ns aoc-2022.day2-test
  (:require [clojure.test :refer :all]
            [aoc-2022.day2 :refer :all]))

(defn play-round-score-equal [me opponent expected-score]
  (= (single-game-score [opponent me]) expected-score))

(deftest play-round-score-tests
  (is (play-round-score-equal :rock :rock 4))
  (is (play-round-score-equal :rock :paper 1))
  (is (play-round-score-equal :rock :scissors 7))
  (is (play-round-score-equal :paper :rock 8))
  (is (play-round-score-equal :paper :paper 5))
  (is (play-round-score-equal :paper :scissors 2))
  (is (play-round-score-equal :scissors :rock 3))
  (is (play-round-score-equal :scissors :paper 9))
  (is (play-round-score-equal :scissors :scissors 6)))

(deftest lines-parse-tests
  (is (= (parse-lines ["A X" "B Z" "C Y"] map-to-hand) [[:rock :rock] [:paper :scissors] [:scissors :paper]])))

(deftest compute-score-tests
  (is (= (compute-score-part-1 ["A Y"]) 8))
  (is (= (compute-score-part-1 ["A Y" "B X" "C Z"]) 15)))

(deftest compute-my-hand-tests
  (is (= (hand-to-play :rock :draw) :rock))
  (is (= (hand-to-play :rock :win) :paper))
  (is (= (hand-to-play :rock :lose) :scissors)))

(deftest lines-parse-with-outcome-mapping-tests
  (is (= (parse-lines ["A X" "B Y" "C Z"] map-outcome-to-hand) [[:rock :scissors] [:paper :paper] [:scissors :rock]])))

(deftest compute-score-choose-hand-tests
  (is (= (compute-score-part-2 ["A Y"]) 4))
  (is (= (compute-score-part-2 ["A Y" "B X" "C Z"]) 12)))

(deftest line-parse-tests
  (is (= (parse-line "A Y" map-to-hand) [:rock :paper])))