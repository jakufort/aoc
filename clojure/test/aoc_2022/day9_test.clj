(ns aoc-2022.day9-test
  (:require [aoc-2022.day9 :refer :all]
            [clojure.test :refer :all]))

(deftest moves-parser-tests
  (is (= (parse-move "R 4") ["R" 4])))

(deftest making-move-tests
  (is (= (make-single-move "R" [[[0 0] [0 0]] #{[0 0]}]) [[[1 0] [0 0]] #{[0 0]}]))
  (is (= (make-single-move "R" [[[1 0] [0 0]] #{[0 0]}]) [[[2 0] [1 0]] #{[0 0] [1 0]}]))
  (is (= (make-single-move "U" [[[2 0] [1 0]] #{[1 0]}]) [[[2 1] [1 0]] #{[1 0]}]))
  (is (= (make-single-move "U" [[[2 1] [1 0]] #{[1 0]}]) [[[2 2] [2 1]] #{[1 0] [2 1]}]))
  (is (= (make-single-move "U" [[[1 0] [0 0]] #{[0 0]}]) [[[1 1] [0 0]] #{[0 0]}]))
  (is (= (make-single-move "L" [[[1 1] [2 0]] #{[2 0]}]) [[[0 1] [1 1]] #{[2 0] [1 1]}]))
  (is (= (make-single-move "R" [[[1 0] [0 1]] #{[0 1]}]) [[[2 0] [1 0]] #{[0 1] [1 0]}]))
  (is (= (make-single-move "L" [[[0 0] [1 0]] #{[1 0]}]) [[[-1 0] [0 0]] #{[1 0] [0 0]}]))
  (is (= (make-single-move "D" [[[0 0] [0 1]] #{[1 0]}]) [[[0 -1] [0 0]] #{[1 0] [0 0]}])))

(deftest multiple-moves-tests
  (is (= (make-move ["R" 4] [[[0 0] [0 0]] #{[0 0]}]) [[[4 0] [3 0]] #{[0 0] [1 0] [2 0] [3 0]}]))
  (is (= (make-move ["U" 4] [[[4 0] [3 0]] #{[3 0]}]) [[[4 4] [4 3]] #{[3 0] [4 1] [4 2] [4 3]}])))

(deftest simulate-tests
  (is (=
        (simulate-n-knots ["R 4" "U 4"] 2)
        [[[4 4] [4 3]] #{[0 0] [1 0] [2 0] [3 0] [4 1] [4 2] [4 3]}])))

(deftest part1-tests
  (is (= (part-1 ["R 4" "U 4" "L 3" "D 1" "R 4" "D 1" "L 5" "R 2"]) 13)))

(deftest part2-tests
  (is (= (part-2 ["R 4" "U 4" "L 3" "D 1" "R 4" "D 1" "L 5" "R 2"]) 1))
  (is (= (part-2 ["R 5" "U 8" "L 8" "D 3" "R 17" "D 10" "L 25" "U 20"]) 36)))
