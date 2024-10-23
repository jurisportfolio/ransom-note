(ns ransom-note.user
  (:require [clojure.java.io :as io]
            [ransom-note.core :as note])
  (:import (java.io FileNotFoundException)
           (java.nio.file Files NoSuchFileException Paths)))




(comment

  (defn get-file-size [file-path]
    (try
      (let [file (io/file file-path)]
        (.length file))  ;; Using the length method from the java.io.File class
      (catch FileNotFoundException e
        (println "File not found:" file-path)
        nil)
      (catch Exception e
        (println "An error occurred:" (.getMessage e))
        nil)))




  (defn is-file-bigger
    [file-path max-size]
    (> (utils/file-size file-path) max-size))


  (is-file-bigger magazine-file-name config/SMALL_MAGAZINE_FILE_SIZE_MB)

  (cond
    (empty? message) true
    (empty? magazine) false
    :else (every? (fn [[char count]]
                    (>= (get count-magazine char 0) count))
                  count-message))


  (deftest test-can-make-ransom-note
           (testing "Ransom note creation"

                             (is (core/can-make-ransom-note "a" "a"))                     ; single character
                             (is (core/can-make-ransom-note "aa" "aab"))                  ; enough characters
                             (is (not (core/can-make-ransom-note "aa" "ab")))             ; not enough characters
                             (is (not (core/can-make-ransom-note "abc" "def")))           ; completely different characters
                             (is (core/can-make-ransom-note "" "any string"))             ; empty message
                             (is (not (core/can-make-ransom-note "message" "")))          ; empty magazine
                             (is (core/can-make-ransom-note "racecar" "racecar"))         ; same characters
                             (is (not (core/can-make-ransom-note "racecar" "racec")))     ; missing one character
                             (is (core/can-make-ransom-note "hello" "ollheh"))))          ; characters present but reordered


  (get-file-size "magazine_false.txt")
  (get-file-size "message.txt"))








