(ns ransom-note.core
  (:require [clojure.java.io :as io]
            [ransom-note.utils :as utils]
            [ransom-note.config :as config]))


(defn can-make-ransom-note
  [message-file-name magazine-file-name]
  (let [message-count (utils/count-non-whitespace-chars (utils/read-message message-file-name))]
    (with-open [reader (io/reader magazine-file-name)]
      (loop [acc {}
             bytes-read (atom 0)]
        (let [buffer (char-array config/CHUNK_SIZE)]
          (when (pos? (do (reset! bytes-read (.read reader buffer)) @bytes-read))
            (let [chunk (String. buffer 0 @bytes-read)
                  chunk-count (utils/count-non-whitespace-chars chunk)
                  updated-acc (merge-with + acc chunk-count)]
              ;; Check if accumulated counts are enough to construct the message
              (if (every? (fn [[letter count]]
                            (>= (get updated-acc letter 0) count))
                          message-count)
                true ;; Stop processing and return true
                (recur updated-acc bytes-read)))))))))


(defn -main
  "Entering point"
  [& args]
  (let [message-file-name (first args)
        magazine-file-name (second args)]
    (if (can-make-ransom-note message-file-name magazine-file-name)
      (print "true")
      (print "false"))))
