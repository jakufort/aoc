(ns aoc-2022.day8-test
  (:require [aoc-2022.day8 :refer :all]
            [clojure.test :refer :all]))

(deftest load-forest-tests
  (is (= (forest ["303" "255" "653"]) [[3 0 3] [2 5 5] [6 5 3]])))

(def test-forest-lines ["30373" "25512" "65332" "33549" "35390"])
(def test-forest (forest test-forest-lines))

(deftest left-right-tests
  (is (= (left-right test-forest 0 0) [[] [0 3 7 3]]))
  (is (= (left-right test-forest 2 2) [[5 6] [3 2]]))
  (is (= (left-right test-forest 4 4) [[9 3 5 3] []])))

(deftest up-down-tests
  (is (= (up-down test-forest 0 0) [[] [2 6 3 3]]))
  (is (= (up-down test-forest 2 2) [[5 3] [5 3]]))
  (is (= (up-down test-forest 4 4) [[9 2 2 3] []])))

(deftest visible-tests
  (is (visible test-forest 0 0))
  (is (visible test-forest 1 1))
  (is (not (visible test-forest 1 3)))
  (is (visible test-forest 2 1))
  (is (not (visible test-forest 2 2))))

(deftest part1-tests
  (is (= (part-1 test-forest-lines) 21)))

(deftest scenic-score-tests
  (is (= (scenic-score test-forest 1 2) 4))
  (is (= (scenic-score test-forest 3 2) 8)))