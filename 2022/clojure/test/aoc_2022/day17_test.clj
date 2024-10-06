(ns aoc-2022.day17-test
  (:require [aoc-2022.day17 :refer :all]
            [clojure.test :refer :all]))

(def test-jets (cycle ">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>"))

(deftest jet-push-tests
  (is (= (jet-push \> [[0 0] [1 0] [1 1]]) [[1 0] [2 0] [2 1]]))
  (is (= (jet-push \< [[0 0] [1 0] [1 1]]) [[-1 0] [0 0] [0 1]])))


(deftest simulation-tests
  (is (= (simulate 2022 test-jets) 3068)))