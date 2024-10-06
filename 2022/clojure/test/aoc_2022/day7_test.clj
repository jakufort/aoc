(ns aoc-2022.day7-test
  (:require [aoc-2022.day7 :refer :all]
            [clojure.test :refer :all]))

(deftest cd-command-tests
  (is (=
        (exec-cmd {} [] ["$ cd /"])
        [{} ["/"] []]))
  (is (=
        (exec-cmd {} ["/" "a" "b" "c"] ["$ cd /"])
        [{} ["/"] []]))
  (is (=
        (exec-cmd {} ["/"] ["$ cd dir"])
        [{} ["/" "dir"] []]))
  (is (=
        (exec-cmd {} ["/" "dir"] ["$ cd .."])
        [{} ["/"] []]))
  (is (=
        (exec-cmd {} ["/"] ["$ cd test" "$ cd /"])
        [{} ["/" "test"] ["$ cd /"]])))

(deftest ls-command-tests
  (is (=
        (exec-cmd {"/" {}} ["/"] ["$ ls" "dir test" "100 foo.txt" "dir bar"])
        [{"/" {"test" {} "bar" {} "foo.txt" 100}} ["/"] []])))

(deftest directory-size-tests
  (is (=
        (directories-sizes {"/" {"test" {"foo.txt" 100} "snd" {"bar.zip" 100 "foo.txt" 200} "some.file" 300}})
        [["/" 700] ["/test/" 100] ["/snd/" 300]]))
  (is (=
        (directories-sizes {"/" {"test" {"bar" {"baz" {"elo" 100} "foo2" 100}} "some.file" 300}})
        [["/" 500] ["/test/" 200] ["/test/bar/" 200] ["/test/bar/baz/" 100]])))

(deftest sizes-under-tests
  (is (= (sizes [["a" 50] ["b" 100] ["c" 200]] >= 100)
         [50 100])))