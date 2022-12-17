(ns aoc-2022.day16-test
  (:require [aoc-2022.day16 :refer :all]
            [clojure.test :refer :all]))

(def test-input "./resources/day16/test_input")

(deftest valves-loading-tests
  (is (= (load-valves ["Valve AA has flow rate=0; tunnels lead to valves DD, II, BB"])
         {"AA" {:rate 0 :tunnels ["DD" "II" "BB"]}}))
  (is (= (load-valves ["Valve JJ has flow rate=21; tunnel leads to valve II"])
         {"JJ" {:rate 21 :tunnels ["II"]}})))

(def test-valves (load-valves (aoc-2022.utils/file-lines test-input)))

(deftest path-calculator-tests
  (is (= (shortest-path test-valves "AA" "CC") ["BB" "CC"])))

(deftest shortest-from-node-to-nodes-tests
  (is (= (shortest-paths test-valves "AA" ["BB" "CC" "DD"])
         (into {} [["BB" ["BB"]] ["CC" ["BB" "CC"]] ["DD" ["DD"]]]))))

(deftest part1-tests
  (is (= (part-1 (aoc-2022.utils/file-lines test-input)) 1651)))