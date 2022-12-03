(ns aoc-2022.day3-test
  (:require [aoc-2022.day3 :refer :all]
            [clojure.test :refer :all]))

(deftest split-line-in-half
  (is (= (line-split "abcd") ["ab" "cd"]))
  (is (= (line-split "BhshUIhjkjfA") ["BhshUI" "hjkjfA"])))

(deftest unique-tests
  (is (= (to-sets ["aba" "abc"]) [#{\a \b} #{\a \b \c}]))
  (is (= (to-sets ["abaaaaa" "bbdbc"]) [#{\a \b} #{\b \d \c}])))

(deftest common-element-tests
  (is (= (common-element [#{\a} #{}]) nil))
  (is (= (common-element [#{\a} #{\a}]) \a)))

(deftest common-item-tests
  (is (= (common-item "abac") \a))
  (is (= (common-item "vJrwpWtwJgWrhcsFMMfFFhFp") \p))
  (is (= (common-item "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL") \L)))

(deftest priority-tests
  (is (= (priority \a)) 1)
  (is (= (priority \b)) 2)
  (is (= (priority \z)) 26)
  (is (= (priority \A)) 27)
  (is (= (priority \B)) 28)
  (is (= (priority \Z)) 52))

(deftest item-in-both-compartments-priorities-tests
  (is (=
        (item-in-both-compartments-priorities ["vJrwpWtwJgWrhcsFMMfFFhFp" "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL"])
        54)))

(deftest elfs-groups-tests
  (is (= (three-elf-groups ["a" "b" "c" "d" "e" "f"]) [["a" "b" "c"] ["d" "e" "f"]])))

(deftest find-badge-tests
  (is (= (find-badge ["ab" "ac" "ad"]) \a)))

(deftest badges-priority-tests
  (is (=
        (badges-priority ["vJrwpWtwJgWrhcsFMMfFFhFp" "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL" "PmmdzqPrVvPwwTWBwg"])
        18)))