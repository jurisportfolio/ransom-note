(ns ransom-note.utils
  (:require [clojure.java.io :as io]
            [ransom-note.config :as config])
  (:import (java.io FileNotFoundException)))


(defn file-size [file-path]
  (try
    (let [file (io/file file-path)]
      (.length file))  ;; Using the length method from the java.io.File class
    (catch FileNotFoundException e
      (println "File not found:" file-path)
      nil)
    (catch Exception e
      (println "An error occurred:" (.getMessage e))
      nil)))


(defn read-from-file [file-name]
  (slurp file-name))


(defn read-message
  "Returns str with message from message file,
  or throw exception, if file is bigger than max file size"
  [message-file-name]
  (if (> (file-size message-file-name) config/MAX_MESSAGE_FILE_SIZE_MB)
    (throw (ex-info "Message file is too big" {:file message-file-name}))
    (read-from-file message-file-name)))


(defn count-non-whitespace-chars
  [text]
  (frequencies (filter #(not (Character/isWhitespace %)) text)))


(defn enough-letters
  [message-count magazine-count]
  (every? (fn [[letter count]]
            (>= (get magazine-count letter 0) count))
          message-count))
