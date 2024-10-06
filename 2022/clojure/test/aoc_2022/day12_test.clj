(ns aoc-2022.day12-test
  (:require [aoc-2022.day12 :refer :all]
            [clojure.test :refer :all]))

(deftest graph-loading-tests
  (is (=
        (load-graph ["Sab" "abc" "aEc"])
        [{[0 0] {:height 1 :paths #{[1 0] [0 1]}}
          [1 0] {:height 1 :paths #{[0 0] [2 0] [1 1]}}
          [2 0] {:height 2 :paths #{[1 0] [2 1]}}
          [0 1] {:height 1 :paths #{[0 0] [1 1] [0 2]}}
          [1 1] {:height 2 :paths #{[0 1] [1 0] [2 1]}}
          [2 1] {:height 3 :paths #{[1 1] [2 0] [2 2]}}
          [0 2] {:height 1 :paths #{[0 1]}}
          [1 2] {:height 26 :paths #{[0 2] [2 2] [1 1]}}
          [2 2] {:height 3 :paths #{[2 1]}}} {:start [0 0] :end [1 2]}])))
