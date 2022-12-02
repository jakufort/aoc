(ns aoc-2022.day1-test
  (:require [clojure.test :refer :all]
            [aoc-2022.day1 :refer :all]))

(deftest grouping-tests
  (is (= (grouped-by-calories ["1"]) [["1"]]))
  (is (= (grouped-by-calories ["1" "2" "" "3"]) [["1" "2"] ["3"]])))

(deftest to-numbers-tests
  (is (= (to-numbers [["1"]]) [[1]]))
  (is (= (to-numbers [["1" "2" "3"] ["5"]]) [[1 2 3] [5]])))

(deftest summation-tests
  (is (= (sum-groups [[1]]) [1]))
  (is (= (sum-groups [[1 2]]) [3]))
  (is (= (sum-groups [[2 2] [1 1 1 1]]) [4 4])))

(deftest sort-desc-tests
  (is (= (sort-desc [1 2 3]) [3 2 1]))
  (is (= (sort-desc [1 4 2 56 2]) [56 4 2 2 1])))

(deftest elfs-tests
  (is (= (first-elf [1]) 1))
  (is (= (first-elf [4 3 1]) 4))
  (is (= (first-three-elfs-sum [3 2 1]) 6))
  (is (= (first-three-elfs-sum [10 4 3 2 2 1]) 17)))