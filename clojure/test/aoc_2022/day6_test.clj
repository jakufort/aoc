(ns aoc-2022.day6-test
  (:require [aoc-2022.day6 :refer :all]
            [clojure.test :refer :all]))

(deftest grouped-tests
  (is (= (window 4 "bvwbjp") [[\b \v \w \b] [\v \w \b \j] [\w \b \j \p]])))

(deftest part1-tests
  (is (= (part-1 "bvwbjplbgvbhsrlpgdmjqwftvncz") 5))
  (is (= (part-1 "nppdvjthqldpwncqszvftbrmjlhg") 6))
  (is (= (part-1 "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg") 10))
  (is (= (part-1 "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw") 11)))

(deftest part2-tests
  (is (= (part-2 "mjqjpqmgbljsphdztnvjfqwrcgsmlb") 19))
  (is (= (part-2 "bvwbjplbgvbhsrlpgdmjqwftvncz") 23))
  (is (= (part-2 "nppdvjthqldpwncqszvftbrmjlhg") 23))
  (is (= (part-2 "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg") 29))
  (is (= (part-2 "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw") 26)))