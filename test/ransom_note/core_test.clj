(ns ransom-note.core-test
  (:require [clojure.test :refer [deftest testing is run-tests]]
            [ransom-note.core :as core]))


(deftest test-can-make-ransom-note
  (testing "Ransom note creation"
    (is (not (core/can-make-ransom-note "magazine.txt" "message.txt")))))


;; Run tests
(run-tests)