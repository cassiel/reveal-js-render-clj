(ns eu.cassiel.reveal-js-render-clj
  (:require [clojure.edn :as edn]
            [hiccup.core :refer :all]
            [hiccup.page :as p]
            [me.raynes.conch :refer [with-programs]])
  (:import (java.io File))

  (:gen-class))

(defn ^:deprecated render-from-edn [str]
  (html (edn/read-string str)) )

(defn ^:deprecated assemble-edn
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

(defn render
  "Called within the form passed to `eval`."
  [& {:keys [theme title author slides]}]
  {:head-part (html [:title title]
                    [:meta {:name "description" :content title}]
                    [:meta {:name "author" :content author}]
                    (p/include-css (format "css/theme/%s.css" (name theme))))
   :body-part (html [:div.reveal
                     (vec (cons :div.slides slides))])})

(def REVEAL-ROOT (File. "/Users/nick/GITHUB/cassiel/reveal.js"))

(def template (slurp "/Users/nick/GITHUB/cassiel/reveal.js/index-ssi.shtml"))

(def HEAD-TAG    #"<!--#include .*/head.html.*-->")
(def CONTENT-TAG #"<!--#include .*/content.html.*-->")

(defn render-from-clj [file]
  (with-open [r (java.io.PushbackReader.
                 (clojure.java.io/reader file))]
    (binding [*read-eval* false]
      (let [enclosing-ns (ns-name *ns*)]
        (try
          (do
            (in-ns 'eu.cassiel.reveal-js-render-clj)
            (eval (read r)))
          (finally (in-ns enclosing-ns)))))))

(defn copy-over [dir reveal-js-root out-root]
  (with-programs [rm cp echo touch]
    (let [dest (File. out-root dir)]
      (when (.exists dest) (rm "-r" (str dest)))
      (cp "-r"
          (str (File. reveal-js-root dir))
          (str dest)))))

(defn render-main [in-file out-file reveal-js-root]
  (let [{:keys [head-part body-part]} (render-from-clj in-file)
        template (slurp (File. reveal-js-root  "index-ssi.shtml"))
        out-root (.getParent out-file)]
    (spit out-file (-> template
                       (clojure.string/replace HEAD-TAG head-part)
                       (clojure.string/replace CONTENT-TAG body-part)))
    (doseq [d ["lib" "js" "css" "plugin"]]
      (copy-over d reveal-js-root out-root))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
