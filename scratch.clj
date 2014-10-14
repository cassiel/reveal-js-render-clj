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

(in-ns 'user)
render
(inc 1)

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

(let [topic "HTML/05_building_sites_with_Bootstrap"
      dir (File. (format "/Users/nick/CASSIEL/codezoners-2-prep/%s/presentation" topic))
      reveal-js (File. "/Users/nick/GITHUB/cassiel/reveal.js")]
  (renderer/render-main (File. dir "presentation.clj")
                        (File. dir "presentation.html")
                        reveal-js))
