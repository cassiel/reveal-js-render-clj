(ns eu.cassiel.reveal-js-render-clj
  (:require [clojure.tools.cli :refer [parse-opts]]
            [clojure.edn :as edn]
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
      ;; The Conch machinery seems to be tokenising properly, so we're OK with filenames containing spaces.
      (when (.exists dest) (rm "-r" (str dest)))
      (cp "-r"
          (str (File. reveal-js-root dir))
          (str dest)))))

(defn render-main
  "All arguments expected to be canonical files."
  [in-file out-file reveal-js-root]
  (let [{:keys [head-part body-part]} (render-from-clj in-file)
        template (slurp (File. reveal-js-root  "index-ssi.shtml"))
        out-root (.getParent out-file)]
    (spit out-file (-> template
                       (clojure.string/replace HEAD-TAG head-part)
                       (clojure.string/replace CONTENT-TAG body-part)))
    (doseq [d ["lib" "js" "css" "plugin"]]
      (println "copying: " d)
      (copy-over d reveal-js-root out-root))))

(def HOME (System/getProperty "user.home"))

(def cli-options
  [[nil "--reveal.js DIR" "The root of the reveal.js source tree"
    :parse-fn #(.getCanonicalFile (File. %))
    :id :reveal-js
    :default (str (-> HOME
                      (File. "GITHUB")
                      (File. "cassiel")
                      (File. "reveal.js")))]
   [nil "--input FILE" "Input file (Clojure source)"
    :parse-fn #(.getCanonicalFile (File. %))]

   [nil "--output FILE" "Output file (HTML)"
    :parse-fn #(.getCanonicalFile (File. %))]])

(defn -main
  [& args]
  (let [parsed (parse-opts args cli-options)
        {:keys [input output reveal-js]} (:options parsed)]
    (if (and input output)
      (do
        (println "   " input)
        (println "-> " output)
        (render-main input output reveal-js)
        (println "done"))
      (println "Usage:\n" (:summary parsed)))))
