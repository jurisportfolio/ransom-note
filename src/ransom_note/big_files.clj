(ns ransom-note.big-files
  (:require [clojure.java.io :as io]))


(defn can-make-ransom-note [message magazine-file chunk-size]
  (let [message-count (frequencies (filter #(not (Character/isWhitespace %)) message))]
    (with-open [reader (io/reader magazine-file)]
      (loop [acc {}
             bytes-read (atom 0)]
        (let [buffer (char-array chunk-size)]
          (when (pos? (do (reset! bytes-read (.read reader buffer)) @bytes-read))
            (let [chunk (String. buffer 0 @bytes-read)
                  filtered-chunk (filter #(not (Character/isWhitespace %)) chunk)
                  chunk-count (frequencies filtered-chunk)
                  updated-acc (merge-with + acc chunk-count)]
              ;; Check if accumulated counts are enough to construct the message
              (if (every? (fn [[letter count]]
                            (>= (get updated-acc letter 0) count))
                          message-count)
                true ;; Stop processing and return true
                (recur updated-acc bytes-read)))))))))