(ns aoc-2022.day7
  (:gen-class)
  (:require [aoc-2022.utils :as utils]))

(defn split-space-second [line]
  (second (utils/split-space line)))

; building filesystem
(defmulti exec-cmd
          (fn [_ _ lines]
            (split-space-second (first lines))))

; cd
(defn new-path [current new]
  (case new
    "/" ["/"]
    ".." (vec (drop-last current))
    (conj current new)))

(defmethod exec-cmd "cd" [fs path lines]
  (let [cmd-line (first lines)
        cd-arg (subs cmd-line 5)
        rest (rest lines)
        new-path (new-path path cd-arg)]
    [fs new-path rest]))

; ls
(defn not-command? [line]
  (not (clojure.string/starts-with? line "$")))

(defn update-fs [path fs file-line]
  (if (clojure.string/starts-with? file-line "dir")
    (let [dir (split-space-second file-line)
          dir-path (conj path dir)]
      (if (contains? fs dir-path)
        fs
        (assoc-in fs dir-path {})))
    (let [[size name] (utils/split-space file-line)]
      (assoc-in fs (conj path name) (Integer/parseInt size)))))

(defmethod exec-cmd "ls" [fs path lines]
  (let [without-ls (rest lines)
        [files rest] (split-with not-command? without-ls)
        new-fs (reduce #(update-fs path %1 %2) fs files)]
    [new-fs path rest]))

(defn build-filesystem [fs path lines]
  (let [[new-fs pwd rest-of-lines] (exec-cmd fs path lines)]
    (if (empty? rest-of-lines)
      new-fs
      (recur new-fs pwd rest-of-lines))))

;compute sizes
(defn sum-sub-sizes [subdirectories-sizes subdirectories path]
  (let [first-level (into #{} (map #(str path % "/") subdirectories))
        only-first-level (filter (fn [x] (contains? first-level (first x))) subdirectories-sizes)]
    (reduce (fn [acc dir-with-size] (+ acc (second dir-with-size))) 0 only-first-level)))


(defn directory-size [path content]
  (let [all-files (keys content)
        files (filter (fn [k] (number? (get content k))) all-files)
        size-of-files (reduce + 0 (map #(get content %) files))
        subdirectories (filter (fn [k] (map? (get content k))) all-files)
        subdirectories-sizes (apply concat (map (fn [k] (directory-size (str path k "/") (get content k))) subdirectories))
        subs-size (sum-sub-sizes subdirectories-sizes subdirectories path)]
    (conj subdirectories-sizes [path (+ subs-size size-of-files)])))

(defn directories-sizes [fs]
  (directory-size "/" (get fs "/")))

; solutions
(defn only-sizes [directories]
  (map #(second %) directories))

(defn sizes [directories threshold-func threshold]
  (filter #(threshold-func threshold %) (only-sizes directories)))

(defn fs [lines]
  (build-filesystem {} [] lines))

(defn part-1 [lines]
  (let [fs (fs lines)
        directories (directories-sizes fs)]
    (reduce + 0 (sizes directories >= 100000))))

(defn part-2 [lines]
  (let [fs (fs lines)
        directories (directories-sizes fs)
        unused-space (- 70000000 (second (first directories)))
        threshold (- 30000000 unused-space)]
    (first (sort (sizes directories <= threshold)))))

(defn -main
  "Day 5"
  [& _]
  (let [lines (utils/file-lines "./resources/day7/input")]
    (println (str "part1: " (part-1 lines)))
    (println (str "part2: " (part-2 lines)))))