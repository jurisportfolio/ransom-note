(ns ransom-note.utils
  (:require [clojure.java.io :as io]
            [ransom-note.config :as config])
  (:import (java.io FileNotFoundException)))


(defn file-size
  "Returns the size of the file at the specified file path in bytes.

  If the file exists, the length is returned.
  If the file does not exist, a FileNotFoundException is thrown with a descriptive message.

  Parameters:
  - file-path: A string representing the path to the file."

  [file-path]
  (let [file (io/file file-path)]
    (if (.exists file)
      (.length file)  ;; Get file length if it exists
      (throw (FileNotFoundException.
               (str file-path " (No such file or directory)"))))))  ;; Throw exception if file doesn't exist))


(defn read-message
  "Reads and returns the content of the specified message file.

  If the file size exceeds the maximum allowed size defined in config, an exception is thrown.
  Otherwise, the content is read using slurp and returned as a string.

  Parameters:
  - message-file-name: A string representing the path to the message file."

  [message-file-name]
  (if (> (file-size message-file-name) config/MAX_MESSAGE_FILE_SIZE_MB)
    (throw (ex-info "Message file is too big" {:file message-file-name}))
    (slurp message-file-name)))


(defn count-non-whitespace-chars
  "Counts the frequency of non-whitespace characters in the provided text.

  The function filters out any whitespace characters and computes the frequency of each remaining character.
  It returns a map where keys are the characters and values are their counts.

  Parameters:
  - text: A string to be analyzed for non-whitespace characters."

  [text]
  (frequencies (filter #(not (Character/isWhitespace %)) text)))


(defn enough-letters
  "Determines if the magazine contains enough letters to construct the specified message.

  It compares the character counts from the message and the magazine.
  Returns true if all letters needed for the message are present in sufficient quantities in the magazine.

  Parameters:
  - message-count: A map containing character counts from the message.
  - magazine-count: A map containing character counts from the magazine."

  [message-count magazine-count]
  (every? (fn [[letter count]]
            (>= (get magazine-count letter 0) count))
          message-count))


(defn read-from-file
  "Reads a chunk of data from the provided reader into the specified buffer.

  The number of bytes read is stored in the provided atom, which is updated by this function.
  Returns true if at least one byte was read, false otherwise.

  Parameters:
  - bytes-read: An atom that keeps track of the number of bytes read.
  - reader: A reader object from which to read data.
  - buffer: A mutable character array where data is read into."

  [bytes-read reader buffer]
  (pos? (reset! bytes-read (.read reader buffer))))
