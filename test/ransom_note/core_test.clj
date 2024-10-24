(ns ransom-note.core-test
  (:require [clojure.test :refer [deftest testing is run-tests]]
            [ransom-note.core :as core])
  (:import (java.time ZonedDateTime)
           (java.time.format DateTimeFormatter)))


(defn current-time []
  (let [now (ZonedDateTime/now)
        formatter (DateTimeFormatter/ofPattern "yyyy-MM-dd HH:mm:ss.SSS")]
    (.format now formatter)))


(deftest test-can-make-ransom-note-small
  (testing "Ransom note creation - small files"
    (println "Ransom note creation - small files\n")
    (println (current-time) "\n")
    (println "Message is valid; magazine is valid; there are NOT enough chars to make message from magazine")
    (is (not (core/can-make-ransom-note "resources/message.txt" "resources/magazine_false.txt")))
    (println "OK\n")

    (println "Message is valid; magazine is valid; there are enough chars")
    (is (core/can-make-ransom-note "resources/message.txt" "resources/magazine_true.txt"))
    (println "OK\n")

    (println (current-time) "\n")))


#_(deftest test-can-make-ransom-note-big
    (testing "Ransom note creation - big files"
      (println "Ransom note creation - big files\n")
      (println (current-time) "\n")

      (println "Message is valid; magazine is valid, but file is 2MB; there are enough chars")
      (println "It will take some time to finish process")
      (is (core/can-make-ransom-note "resources/message.txt" "resources/magazine_midsize_true.txt"))
      (println "OK\n")

      (println "Message is valid; magazine is valid, but file is 2MB; there are NOT enough chars")
      (println "It will take some time to finish process")
      (is (not (core/can-make-ransom-note "resources/message.txt" "resources/magazine_midsize_false.txt")))
      (println "OK\n")

      (println "Message is valid; magazine is valid, but file is bigger than 20MB; there are enough chars")
      (println "It will take some time to finish process")
      (is (core/can-make-ransom-note "resources/message.txt" "resources/magazine_big.txt"))
      (println "OK\n")

      (println (current-time) "\n")))

(run-tests)