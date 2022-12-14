(ns aoc-2022.day14-test
  (:require [aoc-2022.day14 :refer :all]
            [clojure.test :refer :all]))

(def test-input ["498,4 -> 498,6 -> 496,6" "503,4 -> 502,4 -> 502,9 -> 494,9"])

(deftest read-input-tests
  (is (=
        (rocks test-input)
        #{[498 4]
          [498 5]
          [498 6]
          [497 6]
          [496 6]
          [503 4]
          [502 4]
          [502 5]
          [502 6]
          [502 7]
          [502 8]
          [502 9]
          [501 9]
          [500 9]
          [499 9]
          [498 9]
          [497 9]
          [496 9]
          [495 9]
          [494 9]})))


(def test-rocks (rocks test-input))

(deftest cave-bottom-tests
  (is (= (cave-bottom test-rocks) 9)))

(deftest next-sand-position-tests
  (is (= (next-sand-position test-rocks #{} [500 0]) [500 1]))
  (is (= (next-sand-position test-rocks #{} [500 8]) [500 8]))
  (is (= (next-sand-position test-rocks #{[500 8]} [500 7]) [499 8])))

(deftest sand-simulation-tests
  (is (= (falling-down test-rocks) 24)))

(deftest part1-tests
  (is (= (part-1 test-input) 24)))

(deftest part2-tests
  (is (= (part-2 test-input) 93)))