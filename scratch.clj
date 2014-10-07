(ns user
  (:require (eu.cassiel [reveal-js-render-clj :as renderer])
            [clojure.edn :as edn]))

(renderer/render-from-edn "[:h1 [:a.foo {:href \"/x.html\"} \"My Link\"]]")

(renderer/render-from-edn "{:title \"My Presentation\" :author \"Me\" :slides [] } ")

(renderer/assemble-edn "{:title \"My Presentation\" :author \"Me\" :slides [] } ")


(edn/read-string "a b c")
