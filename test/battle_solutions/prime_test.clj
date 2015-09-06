(ns battle-solutions.prime-test
  (:require [clojure.test :refer :all]
            [battle-asserts.test-helper :refer [assert-equal]]))

(defn prime [y]
  (letfn [(prime? [num]
            (when (> num 1)
              (every? (fn [x]
                        (not (zero? (rem num x))))
                      (range 2 (inc (int (Math/sqrt num)))))))]
    (->> (range (+ y 1))
         (filter #(prime? %)))))

(deftest test-asserts
  (assert-equal '(2 3 5)  (prime 5))
  (assert-equal '(2 3 5 7 11)  (prime 12))
  (assert-equal '(2 3 5 7 11 13 17) (prime 18))
  (assert-equal '(2 3 5 7 11 13 17 19 23) (prime 25)))

