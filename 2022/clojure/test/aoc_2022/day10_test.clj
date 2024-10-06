(ns aoc-2022.day10-test
  (:require [aoc-2022.day10 :refer :all]
            [clojure.test :refer :all]))

(deftest parse-line-tests
  (is (= (parse-line "noop") ["noop" 1]))
  (is (= (parse-line "addx 1") ["addx" 2 1])))

(deftest do-cycle-tests
  (is (= (do-cycle [[nil nil 0 1] [["noop" 1] ["addx" 2 1]]]) [[["addx" 2 1] 2 1 1] []]))
  (is (= (do-cycle [[["addx" 2 1] 2 1 1] []]) [[["addx" 2 1] 1 2 1] []]))
  (is (= (do-cycle [[["addx" 2 1] 1 2 1] []]) [[nil nil 3 2] []]))
  (is (= (do-cycle [[nil nil 3 2] []]) [[nil nil 3 2] []])))

(deftest signal-strengths-tests
  (is (=
        (signal-strengths
          [20]
          (map parse-line ["addx 15" "addx -11" "addx 6" "addx -3" "addx 5" "addx -1" "addx -8" "addx 13" "addx 4" "noop" "addx -1"]))
        [[20 21]])))

(deftest part1-tests
  (is (= (part-1 (aoc-2022.utils/file-lines "./resources/day10/test_input")) 13140)))