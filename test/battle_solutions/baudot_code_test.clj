(ns battle-solutions.baudot-code-test
  (:require [clojure.test :refer :all]
            [battle-asserts.test-helper :refer [assert-equal]]))

(defn baudot-code
  [code]
  (let [dict {".o.o." ["R" "4"]
              ".o.oo" ["J" ""]
              ".ooo." ["C" ":"]
              "oo.o." ["G" ""]
              ".o..o" ["D" ""]
              ".oooo" ["K" "("]
              ".oo.." ["N" ","]
              "...oo" ["A" "-"]
              "....." [" " " "]
              "oo.oo" ["" ""]
              "..oo." ["I" "8"]
              "oo..." ["O" "9"]
              "oooo." ["V" "="]
              "o..o." ["L" ")"]
              ".oo.o" ["F" ""]
              "....o" ["E" "3"]
              "..ooo" ["U" "7"]
              "oo..o" ["B" "?"]
              "..o.." [" " " "]
              "ooooo" ["" ""]
              "ooo.." ["M" ":"]
              "o..oo" ["W" "2"]
              "o.oo." ["P" "0"]
              "..o.o" ["S" ","]
              "ooo.o" ["X" "/"]
              "o...o" ["Z" "+"]
              "o.ooo" ["Q" "1"]
              "o.o.." ["H" ""]
              "o.o.o" ["Y" "6"]
              "o...." ["T" "5"]}
        state (atom 1)]
    (letfn [(change-state
              [word]
              (cond
                (= "ooooo" word) (reset! state 1)
                (= "oo.oo" word) (reset! state 2)))
            (decryptor
              [word]
              (change-state word)
              (cond
                (= @state 1) (first (dict word))
                (= @state 2) (second (dict word))))]
      (->> code
           (partition-all 5)
           (map #(apply str %))
           (map #(decryptor %))
           (clojure.string/join)))))

(deftest test-asserts
  (let [input "oo.ooo.o.o.o.o.o.oo.ooooo.oooo..o......o.oo..oo.....ooooo.o.o.o.."
        output "640K ENOUGH"]
    (assert-equal output (baudot-code input)))
  (let [input "oo.ooo.oooooooo.oooooo..o..o..ooooooo.oooooo...o..o.oooo.oo.o.oo.o.oo.ooooooo..oo.o.oo........o..o.o"
        output "1KB = 1000BYTES"]
    (assert-equal output (baudot-code input)))
  (let [input "oo.ooo.oooooooo.oooo..oo.oo..o..o..oo.oooooo.ooooo..o..oo.ooo.oooo.oo.o..oo.o.o.ooooooo..oo.o.oo........o..o.o"
        output "1KIB = 1024BYTES"]
    (assert-equal output (baudot-code input))))
