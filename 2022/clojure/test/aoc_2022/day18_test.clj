(ns aoc-2022.day18-test
  (:require [aoc-2022.day18 :refer :all]
            [clojure.test :refer :all]))

(deftest reading-cubes-tests
  (is (= (read-cubes ["1,1,1" "2,1,1"]) #{[1 1 1] [2 1 1]})))

(def test-input (aoc-2022.utils/file-lines "./resources/day18/test_input"))

(deftest surface-count-tests
  (is (= (surface-count #{[1 1 1] [2 1 1]}) 10))
  (is (= (surface-count (read-cubes test-input)) 64)))