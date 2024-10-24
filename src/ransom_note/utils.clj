(ns ransom-note.utils
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [ransom-note.config :as config])
  (:import (java.io FileNotFoundException IOException)))


(defn file-size
  "Returns the size of the file at the specified file path in bytes.

  Parameters:
  - file-path: A string representing the path to the file.

  Throws:
  - FileNotFoundException if the file does not exist.
  - IllegalArgumentException if the file is not a valid text file (not .txt).

  Returns:
  - The size of the file in bytes if it exists and is a valid text file."

  [file-path]
  (let [file (io/file file-path)]
    (cond
      ;; Throw exception if file not in txt format
      (not (= (last (str/split file #"\.")) "txt"))
      (throw (ex-info "File is not a valid text file. Use txt file." {:file-path file-path}))
      ;; Throw exception if file doesn't exist
      (not (.exists file))
      (throw (FileNotFoundException.
               (str file-path " (No such file or directory)")))
      ;; Returns file length if it exists
      :else (.length file))))



(defn read-message
  "Reads and returns the content of the specified message file.

  Parameters:
  - message-file-name: A string representing the path to the message file.

  Throws:
  - IllegalArgumentException if the file is empty.
  - IllegalArgumentException if the file size exceeds the maximum allowed size.

  Returns:
  - A string containing the content of the message file if valid."

  [message-file-name]
  (let [size (file-size message-file-name)]
    (cond
      (zero? size)
      (throw (ex-info "File is empty" {:file message-file-name}))

      (> size config/MAX_MESSAGE_FILE_SIZE_MB)
      (throw (ex-info "Message file is too big" {:file message-file-name}))

      :else
      (try
        (slurp message-file-name)
        (catch IOException e
          (throw (ex-info "Error reading file. The file may be corrupted or invalid."
                          {:file message-file-name
                           :error (.getMessage e)})))))))


(defn count-non-whitespace-chars
  "Counts the frequency of non-whitespace characters in the provided text.

  Parameters:
  - text: A string to be analyzed for non-whitespace characters."

  [text]
  (frequencies (filter #(not (Character/isWhitespace %)) text)))


(defn enough-letters
  "Determines if the magazine contains enough letters to construct the specified message.

  Parameters:
  - message-count: A map containing character counts from the message.
  - magazine-count: A map containing character counts from the magazine."

  [message-count magazine-count]
  (every? (fn [[letter count]]
            (>= (get magazine-count letter 0) count))
          message-count))


(defn read-from-file
  "Reads a chunk of data from the provided reader into the specified buffer.

  Parameters:
  - bytes-read: An atom that keeps track of the number of bytes read.
  - reader: A reader object from which to read data.
  - buffer: A mutable character array where data is read into."

  [bytes-read reader buffer]
  (pos? (reset! bytes-read (.read reader buffer))))
