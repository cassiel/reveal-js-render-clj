(ns eu.cassiel.eval-test
  (:require (eu.cassiel [junk :as junk])))

(defn read-from-file-with-trusted-contents [filename]
  (with-open [r (java.io.PushbackReader.
                 (clojure.java.io/reader filename))]
    (binding [*read-eval* false]
      (read r))))

(-> "junk/the-junk"
    (java.io.StringReader.)
    (java.io.PushbackReader.)
    read
    eval)
