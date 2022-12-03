(ns aoc-2022.utils
  (:gen-class))

(defn file-lines [input-file]
  (clojure.string/split-lines (slurp input-file)))