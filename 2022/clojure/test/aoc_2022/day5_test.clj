(ns aoc-2022.day5-test
  (:require [aoc-2022.day5 :refer :all]
            [clojure.test :refer :all]))

(deftest crates-list-tests
  (is (= (crates-from-string "[A]") [\A]))
  (is (= (crates-from-string "[A] [B]") [\A \B]))
  (is (= (crates-from-string "    [B]     [C]") [\space \B \space \C])))

(deftest read-crates-state-tests
  (is (= (parse-crates ["    [B]" "[A] [C]"]) {1 [\A] 2 [\C \B]}))
  (is (=
        (parse-crates ["    [D]    " "[N] [C]    " "[Z] [M] [P]"])
        {1 [\Z \N] 2 [\M \C \D] 3 [\P]})))

(deftest movement-parsing-tests
  (is (= (parse-move "move 1 from 2 to 1") [1 2 1]))
  (is (= (parse-move "move 3 from 1 to 3") [3 1 3]))
  (is (= (parse-move "move 12 from 3 to 2") [12 3 2])))

(deftest movements-parsing-tests
  (is (= (parse-moves ["move 1 from 2 to 1" "move 3 from 2 to 3"]) [[1 2 1] [3 2 3]])))

(deftest movement-apply-tests
  (is (=
        (apply-single-move {1 [\Z \N] 2 [\M \C \D] 3 [\P]} 2 1)
        {1 [\Z \N \D] 2 [\M \C] 3 [\P]})))

(deftest move-n-times-tests
  (is (=
        (crate-mover-9000 {1 [\Z \N] 2 [\M \C \D] 3 [\P]} [3 2 3])
        {1 [\Z \N] 2 [] 3 [\P \D \C \M]})))

(deftest run-crate-tests
  (is (=
        (operate-crane
          (parse-crates ["    [D]    " "[N] [C]    " "[Z] [M] [P]"])
          (parse-moves ["move 1 from 2 to 1"
                        "move 3 from 1 to 3"
                        "move 2 from 2 to 1"])
          crate-mover-9000)
        {1 [\C \M] 2 [] 3 [\P \D \N \Z]})))

(deftest mover-9001-tests
  (is (=
        (crate-mover-9001 {1 [\Z \N] 2 [\M \C \D] 3 [\P]} [3 2 3])
        {1 [\Z \N] 2 [] 3 [\P \M \C \D]})))