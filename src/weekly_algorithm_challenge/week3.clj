(ns weekly-algorithm-challenge.week3
  (:use faker.name)
  (:import (java.util Random)))

;; The best time to be alive
;; find overlap between ranges
(def r (Random.))

;; TODO make this more general somehow
(defn accumulate
  [col]
  (rest
   (reverse
    (reduce
     (fn [ret x]
       (conj ret (+ x (first ret))))
     '(0)
     col))))

(defn rand-in-range
  [min max]
  (+ min (.nextInt r (- max min))))

(defn random-name
  []
  (first (take 1 (names))))

(defn random-record
  []
  (repeatedly
   (fn []
     {:name (random-name)
      :years (into [] (accumulate (map (partial apply rand-in-range) [[1600 1920] [14 100]])))})))

(defn records
  [num-records]
  (map :years (take num-records (random-record))))


(defn best-year
  [records]
  (loop [records (sort records)
         current-year (ffirst records)
         best-year {:year (ffirst records) :alive 0}]
    (cond (empty? records)
          best-year
          
          (> current-year (second (first records)))
          (recur (rest records) current-year best-year)

          (< current-year (ffirst records))
          (recur records (inc current-year) best-year)

          :else
          (recur records
                 (inc current-year)
                 (let [alive
                       (count
                        (filter
                         #(<= current-year (second %))
                         (take-while
                          #(>= current-year (first %))
                          records)))]
                   (if (> alive (:alive best-year))
                     {:year current-year :alive alive}
                     best-year))))))
