(ns aoc-2022.day11
  (:gen-class)
  (:require [aoc-2022.utils :as utils]
            [clojure.string :as str]))

; input parsing
(defn monkey-number [monkey]
  (Integer/parseInt (str (first (second (utils/split-space monkey))))))

(defn items [items]
  (map #(bigint (Integer/parseInt %)) (str/split (str/replace items #"  Starting items: " "") #", ")))

(defn operation-func [operator]
  (case operator
    "*" *
    "+" +))

(defn operation [operation]
  (let [[operator str-number] (utils/split-space (str/replace operation #"  Operation: new = old " ""))
        func (operation-func operator)]
    (if (= str-number "old")
      #(func % %)
      #(func % (Integer/parseInt str-number)))))

(defn divisible-by [test]
  (let [divisor (Integer/parseInt (str/replace test #"  Test: divisible by " ""))]
    divisor))

(defn if-true [if-true]
  (Integer/parseInt (str/replace if-true #"    If true: throw to monkey " "")))

(defn if-false [if-false]
  (Integer/parseInt (str/replace if-false #"    If false: throw to monkey " "")))

(defn parse-monkey [[monkey items-line operation-line test-line if-true-line if-false-line]]
  (let [number (monkey-number monkey)
        items (vec (items items-line))
        operation (operation operation-line)
        divisible-by (divisible-by test-line)
        if-true (if-true if-true-line)
        if-false (if-false if-false-line)]
    [number {:items items :operation operation :divisible-by divisible-by true if-true false if-false}]))

(defn read-monkeys [lines]
  (loop [left-lines lines
         monkeys {}]
    (let [line (first left-lines)]
      (cond
        (nil? line) monkeys
        (empty? line) (recur (rest left-lines) monkeys)
        (str/starts-with? line "Monkey") (let [[number monkey] (parse-monkey (take 6 left-lines))]
                                           (recur (drop 6 left-lines) (assoc monkeys number monkey)))))))

; simulation
(defn process-item [item monkey coping-mechanism]
  (let [after-operation ((monkey :operation) item)
        im-coping (coping-mechanism after-operation)]
    [(get monkey (= 0 (mod im-coping (monkey :divisible-by)))) im-coping]))

(defn turn [monkey coping-mechanism]
  (let [throws (map #(process-item % monkey coping-mechanism) (monkey :items))
        throws-map (reduce (fn [acc [k v]] (update acc k #(if (nil? %) [v] (conj % v)))) {} throws)]
    (seq throws-map)))

(defn change-items [monkeys monkey-id func]
  (update monkeys monkey-id #(update % :items func)))

(defn process-throws [monkeys throws]
  (loop [to-process throws
         new-monkeys monkeys]
    (let [throw (first to-process)]
      (if (nil? throw)
        new-monkeys
        (let [[to-monkey new] throw]
          (recur (rest to-process) (change-items new-monkeys to-monkey #(concat % new))))))))

(defn update-inspections [id monkey inspections]
  (update inspections id (fn [old] (+ old (count (monkey :items))))))

(defn round [monkeys inspections coping-mechanism]
  (let [count (count monkeys)]
    (loop [counter 0
           new-monkeys monkeys
           new-inspections inspections]
      (if (= counter count)
        [new-monkeys new-inspections]
        (let [monkey (get new-monkeys counter)
              throws (turn monkey coping-mechanism)
              with-new-inspections (update-inspections counter monkey new-inspections)
              cleaned-items (change-items new-monkeys counter (fn [_] []))
              processed-throws (process-throws cleaned-items throws)]
          (recur (inc counter) processed-throws with-new-inspections))))))

(defn init-inspections [monkeys]
  (into {} (map (fn [id] [id 0]) (keys monkeys))))

(defn do-n-rounds [n monkeys coping-mechanism]
  (loop [counter 0
         [new-monkeys inspections] [monkeys (init-inspections monkeys)]]
    (if (= counter n)
      [new-monkeys inspections]
      (recur (inc counter) (round new-monkeys inspections coping-mechanism)))))

(defn monkey-business [lines n coping-mechanism]
  (let [init-monkeys (read-monkeys lines)
        [_ inspections] (do-n-rounds n init-monkeys coping-mechanism)]
    (reduce * 1 (map second (take 2 (reverse (sort-by second (seq inspections))))))))

(defn item-not-broken-relief [item]
  (int (Math/floor (/ item 3))))

(defn part-1 [lines]
  (monkey-business lines 20 item-not-broken-relief))

(defn full-copium [least-common-denominator item]
  (mod item least-common-denominator))

(defn part-2 [lines]
  (let [init-monkeys (read-monkeys lines)
        lcd (reduce * 1 (map (fn [[_ v]] (get v :divisible-by)) init-monkeys))
        [_ inspections] (do-n-rounds 10000 init-monkeys (partial full-copium lcd))]
    (reduce * 1 (map second (take 2 (reverse (sort-by second (seq inspections))))))))

(defn -main
  "Day 11"
  [& _]
  (let [lines (utils/file-lines "./resources/day11/input")]
    (println (str "part1: " (part-1 lines)))
    (println (str "part2: " (part-2 lines)))))