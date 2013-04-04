(ns weekly-algorithm-challenge.week6)

(declare walk)

(def test-strings
  ["+ + + 1 2 + 3 4 5"
   "+ + 12 16 * 10 4"
   "+ 1 2"])

(defn str->tokens
  [str]
  (if (re-find #"\d" str)
    (Integer. str)
    (resolve (symbol str))))


(defn tokens->ast
  ([tokens]
     (tokens->ast [(first tokens)] (rest tokens)))
  ([node tokens]
     (let [ncount (count node)
           head (first tokens)
           tail (rest tokens)]
       (cond
        (= 1 ncount)
        (if (ifn? head)
          (let [[arg remaining] (tokens->ast [head] tail)]
            (tokens->ast (conj node arg) remaining))
          (tokens->ast (conj node head) tail))
        (= 2 ncount)
        (if (ifn? head)
          (let [[arg remaining] (tokens->ast [head] tail)]
            [(conj node arg) remaining])
          [(conj node head) tail])))))

(defn descend
  [x]
  (if (vector? x)
    (walk x)
    x))

(defn walk
  [ast]
  (let [[f a1 a2] ast]
    (f (descend a1) (descend a2))))

(defn polish
  [str]
  (-> (map str->tokens (clojure.string/split str #" "))
      tokens->ast
      first
      walk))