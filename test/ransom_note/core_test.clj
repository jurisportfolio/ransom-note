(ns ransom-note.core-test
  (:require [clojure.test :refer [deftest testing is run-tests]]
            [ransom-note.core :refer [can-make-ransom-note]]))


(deftest test-can-make-ransom-note
  (testing "Ransom note creation"
    (is (can-make-ransom-note "a" "a"))                     ; single character
    (is (can-make-ransom-note "aa" "aab"))                  ; enough characters
    (is (not (can-make-ransom-note "aa" "ab")))             ; not enough characters
    (is (not (can-make-ransom-note "abc" "def")))           ; completely different characters
    (is (can-make-ransom-note "" "any string"))             ; empty message
    (is (not (can-make-ransom-note "message" "")))          ; empty magazine
    (is (can-make-ransom-note "racecar" "racecar"))         ; same characters
    (is (not (can-make-ransom-note "racecar" "racec")))     ; missing one character
    (is (can-make-ransom-note "hello" "ollheh"))))          ; characters present but reordered


;; Run tests
(run-tests)