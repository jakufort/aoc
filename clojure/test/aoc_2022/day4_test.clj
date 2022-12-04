(ns aoc-2022.day4-test
  (:require [aoc-2022.day4 :refer :all]
            [clojure.test :refer :all]))

(deftest split-line-into-range-tests
  (is (= (line-into-range "2-4,6-8") [[2 4] [6 8]])))

(deftest overlap-tests
  (is (overlap [[2 4] [2 5]]))
  (is (overlap [[2 4] [2 4]]))
  (is (overlap [[1 5] [2 3]]))
  (is (not (overlap [[2 4] [6 8]])))
  (is (not (overlap [[1 3] [2 5]]))))

(deftest part-1-tests
  (is (= (part-1 ["2-8,3-7" "2-4,6-8"]) 1))
  (is (= (part-1 ["2-3,4-5" "5-7,7-9" "2-6,4-8"]) 0)))

(deftest partial-overlap-tests
  (is (partial-overlap [[1 3] [2 5]]))
  (is (partial-overlap [[5 7] [7 9]]))
  (is (partial-overlap [[3 5] [1 3]]))
  (is (partial-overlap [[1 5] [2 3]]))
  (is (not (partial-overlap [[1 3] [4 6]])))
  (is (not (partial-overlap [[4 6] [1 3]]))))