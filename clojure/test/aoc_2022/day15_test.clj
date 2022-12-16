(ns aoc-2022.day15-test
  (:require [aoc-2022.day15 :refer :all]
            [clojure.test :refer :all]))

(deftest read-sensors-tests
  (is (=
        (sensors ["Sensor at x=2, y=18: closest beacon is at x=-2, y=15"])
        [[[2 18] [-2 15]]])))

(def test-input "./resources/day15/test_input")
(def test-sensors (sensors (aoc-2022.utils/file-lines test-input)))

(deftest sensor-range-tests
  (is (= (sensor-range [2 18] [-2 15]) 7))
  (is (= (sensor-range [8 7] [2 10]) 9)))

(deftest already-covered-tests
  (is (= (count (already-covered-in-line test-sensors 10)) 26)))