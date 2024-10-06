(ns aoc-2022.day13-test
  (:require [aoc-2022.day13 :refer :all]
            [clojure.test :refer :all]))

(deftest read-packets-tests
  (is (=
        (packets-pairs ["[1,1,3,1,1]" "[1,1,5,1,1]" "" "[[1],[2,3,4]]" "[[1],4]"])
        [[[1 1 3 1 1] [1 1 5 1 1]] [[[1] [2 3 4]] [[1] 4]]])))

(deftest check-packets-tests
  (is (right-order? [[1 1 3 1 1] [1 1 5 1 1]]))
  (is (right-order? [[[1] [2 3 4]] [[1] 4]]))
  (is (not (right-order? [[9] [[8 7 6]]])))
  (is (right-order? [[[4 4] 4 4] [[4, 4], 4, 4, 4]]))
  (is (not (right-order? [[7, 7, 7, 7] [7, 7, 7]])))
  (is (right-order? [[] [3]]))
  (is (not (right-order? [[[[]]] [[]]])))
  (is (not (right-order? [[1, [2, [3, [4, [5, 6, 7]]]], 8, 9] [1, [2, [3, [4, [5, 6, 0]]]], 8, 9]]))))

(def input-file-lines (aoc-2022.utils/file-lines "./resources/day13/test_input"))

(deftest part1-tests
  (is (=
        (part-1 input-file-lines)
        13)))

(deftest all-packets-tests
  (is (=
        (all-packets ["[1,1,3,1,1]" "[1,1,5,1,1]" "" "[[1],[2,3,4]]" "[[1],4]"])
        [[1 1 3 1 1] [1 1 5 1 1] [[1] [2 3 4]] [[1] 4]])))

(deftest with-dividers-tests
  (is (= (with-dividers [[1 1 3] [[[3]]]]) [[1 1 3] [[[3]]] [[2]] [[6]]])))

(deftest sort-packets-tests
  (let [packets (-> input-file-lines all-packets with-dividers)]
    (is (= (sort-packets packets) [[]
                                   [[]]
                                   [[[]]]
                                   [1, 1, 3, 1, 1]
                                   [1, 1, 5, 1, 1]
                                   [[1], [2, 3, 4]]
                                   [1, [2, [3, [4, [5, 6, 0]]]], 8, 9]
                                   [1, [2, [3, [4, [5, 6, 7]]]], 8, 9]
                                   [[1], 4]
                                   [[2]]
                                   [3]
                                   [[4, 4], 4, 4]
                                   [[4, 4], 4, 4, 4]
                                   [[6]]
                                   [7, 7, 7]
                                   [7, 7, 7, 7]
                                   [[8, 7, 6]]
                                   [9]]))))

(deftest find-dividers-tests
  (let [sorted (-> input-file-lines all-packets with-dividers sort-packets)]
    (is (= (find-dividers sorted) [10 14]))))

(deftest part2-tests
  (is (= (part-2 input-file-lines) 140)))