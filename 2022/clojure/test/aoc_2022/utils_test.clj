(ns aoc-2022.utils-test
  (:require [aoc-2022.utils :refer :all]
            [clojure.test :refer :all]))

(deftest split-space-tests
  (is (= (split-space "1 2") ["1" "2"]))
  (is (= (split-space "foo bar baz") ["foo" "bar" "baz"])))