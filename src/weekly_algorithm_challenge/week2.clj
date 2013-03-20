(ns weekly-algorithm-challenge.week2
  (:require [clojure.contrib.math :as math]))

(defn splits
  [n]
  (loop [splitting n
         splits 0]
    (if (= (int splitting) 1)
      splits
      (recur (math/ceil (/ splitting 2)) (inc splits)))))

(defn min-cuts
  [m, n]
  (+ (splits m) (splits n)))