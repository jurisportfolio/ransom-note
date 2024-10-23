(ns ransom-note.core
  (:require [clojure.java.io :as io]
            [ransom-note.utils :as utils]
            [ransom-note.config :as config]))


(defn enough-letters
  [message-count magazine-count]
  (every? (fn [[letter count]]
            (>= (get magazine-count letter 0) count))
          message-count))


(defn can-make-ransom-note
  [message-file-name magazine-file-name]
  (println "Start function: ")
  (let [message-count (utils/count-non-whitespace-chars (utils/read-message message-file-name))]
    ;;(println {:message message-count})
    (with-open [reader (io/reader magazine-file-name)]
      (loop [acc {}
             bytes-read (atom 0)]
        (let [buffer (char-array config/CHUNK_SIZE)]
          (when (pos? (do (reset! bytes-read (.read reader buffer)) @bytes-read))
            (let [chunk (String. buffer 0 @bytes-read)
                  chunk-count (utils/count-non-whitespace-chars chunk)
                  updated-acc (merge-with + acc chunk-count)]
              ;; Check if accumulated counts are enough to construct the message
              ;;(println {:magazine updated-acc})
              (if (enough-letters message-count updated-acc)
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
