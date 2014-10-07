(ns eu.cassiel.reveal-js-render-clj
  (:require [clojure.edn :as edn]
            [hiccup.core :refer :all])

  (:gen-class))

(defn render-from-edn [str]
  (html (edn/read-string str)) )

(defn assemble-edn
  "Expects an EDN map with keys `:title`, `:author` and `:slides`. The title and author are baked
   into the head, while the sequence from slides is planted within `:div.reveal`/`:div.slides`.
   (Each slide is a `[:section ...]`.)"
  [str]
  (let [{:keys [title author slides]} (edn/read-string str)]
    {:head-part (html [:title title]
                      [:meta {:name "description" :content title}]
                      [:meta {:name "author" :content author}])
     :body-part (html [:div.reveal
                       (vec (cons :div.slides slides))])}))

(defn render [in-file out-file reveal-js-root]
  )

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
