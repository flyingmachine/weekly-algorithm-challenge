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

(defn generate-name
  []
  (first (take 1 (names))))

(defn generate-record
  []
  (repeatedly
   (fn []
     {:name (generate-name)
      :years (accumulate (map (partial apply rand-in-range) [[1600 1920] [14 100]]))})))