(ns aoc-2022.day11-test
  (:require [aoc-2022.day11 :refer [do-n-rounds init-inspections item-not-broken-relief read-monkeys round turn]]
            [clojure.test :refer :all]))

(deftest parse-input-tests
  (let [monkeys (read-monkeys ["Monkey 0:"
                               "  Starting items: 91, 66"
                               "  Operation: new = old * 13"
                               "  Test: divisible by 19"
                               "    If true: throw to monkey 6"
                               "    If false: throw to monkey 2"])
        monkey (get monkeys 0)]
    (is (= (monkey :items) [91 66]))
    (is (= (monkey true) 6))
    (is (= (monkey false) 2))
    (is (= ((monkey :operation) 10) 130))
    (is (= ((monkey :operation) 1) 13))
    (is (= (monkey :divisible-by)) 19))

  (let [with-plus (read-monkeys ["Monkey 0:"
                                 "  Starting items: 91, 66"
                                 "  Operation: new = old + 13"
                                 "  Test: divisible by 19"
                                 "    If true: throw to monkey 6"
                                 "    If false: throw to monkey 2"])
        monkey (get with-plus 0)]
    (is (= ((monkey :operation) 10) 23))
    (is (= ((monkey :operation) 100) 113))))

(def monkey-zero (get (read-monkeys ["Monkey 0:"
                                     "  Starting items: 79, 98"
                                     "  Operation: new = old * 19"
                                     "  Test: divisible by 23"
                                     "    If true: throw to monkey 2"
                                     "    If false: throw to monkey 3"]) 0))

(deftest turn-tests
  (is (= (turn monkey-zero item-not-broken-relief) [[3 [500 620]]])))

(def monkeys-from-file (read-monkeys (aoc-2022.utils/file-lines "./resources/day11/test_input")))

(defn items [monkeys id]
  ((get monkeys id) :items))

(deftest round-tests
  (let [[monkeys inspections] (round monkeys-from-file (init-inspections monkeys-from-file) item-not-broken-relief)]
    (is (= (items monkeys 0) [20 23 27 26]))
    (is (= (items monkeys 1) [2080 25 167 207 401 1046]))
    (is (= (items monkeys 2) []))
    (is (= (items monkeys 3) []))
    (is (= inspections {0 2 1 4 2 3 3 5}))))

(deftest n-rounds-tests
  (let [[monkeys inspections] (do-n-rounds 20 monkeys-from-file item-not-broken-relief)]
    (is (= (items monkeys 0) [10 12 14 26 34]))
    (is (= (items monkeys 1) [245 93 53 199 115]))
    (is (= (items monkeys 2) []))
    (is (= (items monkeys 3) []))
    (is (= inspections {0 101 1 95 2 7 3 105}))))