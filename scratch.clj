(ns user
  (:require (eu.cassiel [reveal-js-render-clj :as renderer])
            [clojure.edn :as edn]
            [me.raynes.conch :refer [with-programs]])

  (:import (java.io File)))

(renderer/render-from-edn "[:h1 [:a.foo {:href \"/x.html\"} \"My Link\"]]")

(renderer/render-from-edn "{:title \"My Presentation\" :author \"Me\" :slides [] } ")

(renderer/assemble-edn "{:title \"My Presentation\" :author \"Me\" :slides [] } ")

(edn/read-string "a b c")

(renderer/render-from-clj "/Users/nick/GITHUB/cassiel/reveal-js-render-clj/resources/test_input.clj")

(in-ns 'eu.cassiel.reveal-js-render-clj)

*ns*

(ns-name *ns*)

(renderer/render-main (File. "/Users/nick/GITHUB/cassiel/reveal-js-render-clj/resources/test_input.clj")
                      (File. "/Users/nick/GITHUB/cassiel/reveal-js-render-clj/resources/test_input.html")
                      (File. "/Users/nick/GITHUB/cassiel/reveal.js"))

(renderer/render-main (File. "/Users/nick/GITHUB/cassiel/reveal-js-render-clj/example/example_slides.clj")
                      (File. "/Users/nick/GITHUB/cassiel/reveal-js-render-clj/example/example_slides.html")
                      (File. "/Users/nick/GITHUB/cassiel/reveal.js"))

(with-programs [ls]
  (ls "-l" "/Users/nick/file with spaces.txt"))

(.getCanonicalFile (File. "yes/../x.tmp"))

(slurp "resources/foo.txt")

(->> "A"
     (File. "B")
     str)

(->> "F"
     (File. "images")
     ;;(File.)
     ;;(File. "../../reveal-media/")
     ;;str
     )

(str (File. "../../reveal-media/" "X"))

(str (File. "A" "B"))


(image-h 480 "X")
