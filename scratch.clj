(ns user
  (:require (eu.cassiel [reveal-js-render-clj :as renderer])
            [clojure.edn :as edn])
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

(.getCanonicalFile (File. "yes/../x.tmp"))
