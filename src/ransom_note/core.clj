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
  ;; Count the non-whitespace characters in the message
  ;; This creates a map where keys are characters and values are their counts
  (let [message-count (utils/count-non-whitespace-chars (utils/read-message message-file-name))
        ;; Create a buffer to read chunks of characters from the magazine file
        buffer (char-array config/CHUNK_SIZE)]
    ;;(println {:message message-count})
    ;; Open the magazine file for reading
    (with-open [reader (io/reader magazine-file-name)]
      ;; Initialize an accumulator to store character counts from the magazine
      ;; and an atom to track the number of bytes read in each iteration
      (loop [acc {}
             bytes-read-atom (atom 0)]
        ;; Read a chunk of data from the magazine file into the buffer
        (when (utils/read-from-file bytes-read-atom reader buffer)
          ;; Convert the buffer into a string for processing
          (let [chunk (String. buffer 0 @bytes-read-atom)
                ;; Count the non-whitespace characters in the chunk
                chunk-count (utils/count-non-whitespace-chars chunk)
                ;; Update the accumulated character counts from the magazine
                updated-acc (merge-with + acc chunk-count)]
            ;; Check if accumulated counts are enough to construct the message
            ;;(println {:magazine updated-acc})
            (if (utils/enough-letters message-count updated-acc)
              true ;; Stop processing and return true
              (recur updated-acc bytes-read-atom))))))))


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
