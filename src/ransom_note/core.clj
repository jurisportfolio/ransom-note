(ns ransom-note.core)


(defn can-make-ransom-note
  [message magazine]
  (let [count-message (frequencies message)
        count-magazine (frequencies magazine)]
    (every? (fn [[char count]]
              (>= (get count-magazine char 0) count))
            count-message)))


(defn read-from-file [file-name]
  (slurp file-name))

(defn -main
  "Entering point"
  [& args]
  (let [message-file-name (first args)
        magazine-file-name (second args)
        message (read-from-file message-file-name)
        magazine (read-from-file magazine-file-name)]

    (if (can-make-ransom-note message magazine)
      (print "true")
      (print "false"))))
