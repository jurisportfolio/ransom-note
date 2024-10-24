(ns ransom-note.core-test
  (:require [clojure.test :refer [deftest testing is run-tests]]
            [ransom-note.core :as core]))


(deftest test-can-make-ransom-note
  (testing "Ransom note creation"
    (is (not (core/can-make-ransom-note "message.txt" "magazine_false.txt")))
    (is (core/can-make-ransom-note "message.txt" "magazine_true.txt"))
    #_(is (not (core/can-make-ransom-note "message.txt" "magazine_big.txt")))))


;; Run tests
(run-tests)