(ns ransom-note.core
  (:require [clojure.java.io :as io]
            [ransom-note.utils :as utils]
            [ransom-note.config :as config]))


(defn can-make-ransom-note
  "Checks if a ransom note can be constructed from the characters in the magazine file.

  It reads the message file, counts non-whitespace characters, and processes the magazine file in chunks.
  The function returns true if all characters needed for the message are available in the magazine,
  false otherwise.

  Parameters:
  - message-file-name: A string representing the path to the message file.
  - magazine-file-name: A string representing the path to the magazine file."

  [message-file-name magazine-file-name]
  (println "Start function: ")
  (let [message-count (utils/count-non-whitespace-chars (utils/read-message message-file-name))]
    ;;(println {:message message-count})
    (with-open [reader (io/reader magazine-file-name)]
      (loop [acc {}
             bytes-read (atom 0)
             buffer (char-array config/CHUNK_SIZE)]
        (when (utils/read-from-file bytes-read reader buffer)
          (let [chunk (String. buffer 0 @bytes-read)
                chunk-count (utils/count-non-whitespace-chars chunk)
                updated-acc (merge-with + acc chunk-count)]
            ;; Check if accumulated counts are enough to construct the message
            ;;(println {:magazine updated-acc})
            (if (utils/enough-letters message-count updated-acc)
              true ;; Stop processing and return true
              (recur updated-acc bytes-read buffer))))))))


(defn -main
  "Entry point of the program, which processes command line arguments for the message and magazine files.

  It checks whether the ransom note can be constructed from the magazine file and prints
  'true' or 'false' accordingly.
  The first argument should be the path to the message file and the second argument should be the path
  to the magazine file.

  Parameters:
  - & args: A sequence of command line arguments."

  [& args]
  (let [message-file-name (first args)
        magazine-file-name (second args)]
    (if (can-make-ransom-note message-file-name magazine-file-name)
      (print "true")
      (print "false"))))
