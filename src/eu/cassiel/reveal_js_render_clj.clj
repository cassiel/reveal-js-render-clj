(ns eu.cassiel.reveal-js-render-clj
  (:require [clojure.tools.cli :refer [parse-opts]]
            [clojure.edn :as edn]
            [hiccup.core :refer :all]
            [hiccup.page :as p]
            [me.raynes.conch :refer [with-programs]])
  (:import (java.io File))
  (:gen-class))

;; FIXME: need to bind ./asset vs. ASSET-ROOT-URL/asset depending on mode.
(def ASSET-ROOT-URL "../reveal-media/")

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

;; Called from the slide contents:

(defn heading [& args]
  (vec (cons :h3 args)))

(defn subheading [& args]
  (vec (cons :h4 args)))

(defn htmlize [text]
  (-> text
      (clojure.string/replace "&" "&amp;")
      (clojure.string/replace "<" "&lt;")
      (clojure.string/replace ">" "&gt;")))

(defn code [& lines]
  [:pre [:code.html (clojure.string/trim (htmlize (clojure.string/join "\n" lines)))]])

(defn tt [text]
  [:span {:style "font-family:monospace; background-color:rgba(150, 150, 150, 0.5); padding: 0px 5px;"} (htmlize text)])

(defn image-h [h f]
  [:img {:height h
         :style "margin:10px; vertical-align:middle"
         :src (->> f
                   ;; (File. "images')
                   (File. ASSET-ROOT-URL)
                   str)}])

(def image (partial image-h 480))

(defn video-h [h f]
  [:video {:height h
           :controls 1
           :data-autoplay 1}
   [:source {:src (->> f
                       (File. ASSET-ROOT-URL)
                       str)
             ;;(str (File. "videos" f))
             }]])

(def video (partial video-h 480))

(defn github [stem text]
  [:a {:href (str "https://github.com/" stem)} text])

(defn link
  "Raw link, URL monospaced - or link with text."
  ([url content] [:a {:href url} content])
  ([url] (link url [:code url])))

(defn youtube-link [id caption]
  [:a {:href (format "http://www.youtube.com/watch?v=%s" id)} caption])

(defn youtube-embed-h [h id]
  [:iframe {:id "ytplayer"
            :type "text/html"
            :height h
            :width (int (* h 16/9))
            :src (format "http://www.youtube.com/embed/%s" id)
            :frameborder 0}])

(def youtube-embed (partial youtube-embed-h 480))

(defn vimeo-link [id caption]
  [:a {:href  (format "http://vimeo.com/%s" id)} caption])

(defn vimeo-embed-h [h id]
  [:iframe {:src (format "http://player.vimeo.com/video/%s" id)
            :height h
            :width (int (* h 16/9))
            :frameborder 0}])

(def vimeo-embed (partial vimeo-embed-h 480))

(defn render
  "Called within the form passed to `eval`."
  [& {:keys [theme title author slides]}]
  {:head-part (html [:title title]
                    [:meta {:name "description" :content title}]
                    [:meta {:name "author" :content author}]
                    (p/include-css (format "css/theme/%s.css" (name theme))))
   :body-part (html [:div.reveal
                     (vec (cons :div.slides slides))])})

;; This machinery is for rendering directly from a project containing a
;; presentation file which we want to be able to evaluate immediately.
;; It renders into our SSI-enabled Apache document area.

(def INCLUDE-ROOT (-> (File. (System/getProperty "user.home"))
                      (File. "Sites")
                      (File. "reveal.js")
                      (File. "_included")))
(defn render1 [& {:keys [head body]}]
  (.mkdir INCLUDE-ROOT)
  (spit (str (File. INCLUDE-ROOT "head.html")) head)
  (spit (str (File. INCLUDE-ROOT "content.html")) body))

(defn render-me [& {:keys [theme title author slides]}]
  (render1
   :head (html [:title title]
               [:meta {:name "description" :content title}]
               [:meta {:name "author" :content author}]
               [:link#theme {:rel "stylesheet"
                             :href (format "css/theme/%s.css" (name theme))}])
   :body (html [:div.reveal
                (vec (cons :div.slides slides))])))

;; This machinery is for "batch" rendering from a .clj to a .html, without
;; needing any server machinery. Upside: result is a single vanilla HTML file
;; (plus copied reveal.js assets). Downside: `render-main` needs to be
;; explicitly called with all file arguments to do the work (see `from-repl.clj`).

(def REVEAL-ROOT (File. "/Users/nick/GITHUB/cassiel/reveal.js"))

(def template (slurp (str REVEAL-ROOT  "/index-ssi.shtml")))

(def HEAD-TAG    #"<!--#include .*/head.html.*-->")
(def CONTENT-TAG #"<!--#include .*/content.html.*-->")

;; Dynamic functions which can be accessed within the presentation:
(declare ^:dynamic include-code)

(defn render-from-clj [file]
  (with-open [r (java.io.PushbackReader.
                 (clojure.java.io/reader file))]
    (binding [*read-eval* false]
      (let [enclosing-ns (ns-name *ns*)]
        (try
          (do
            (in-ns 'eu.cassiel.reveal-js-render-clj)
            (binding [include-code (fn [f] (code (slurp (-> (.getParent file)
                                                           (File. "include")
                                                           (File. f)))))]
              (eval (read r))))
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
    ;; Note the hacks here for armouring any "$" in the fragments:
    (spit out-file (-> template
                       (clojure.string/replace HEAD-TAG (clojure.string/replace head-part "$" "\\$"))
                       (clojure.string/replace CONTENT-TAG (clojure.string/replace body-part "$" "\\$"))))
    (doseq [d ["lib" "js" "css" "plugin"]]
      (println "copying" d)
      (copy-over d reveal-js-root out-root))))

(def HOME (System/getProperty "user.home"))

(def cli-options
  [[nil "--reveal.js"
    :required "[the root of the reveal.js source tree]"
    :parse-fn #(.getCanonicalFile (File. %))
    :validate [#(and (.isDirectory %)
                     (.exists (File. % "index-ssi.shml"))) "reveal.js directory required"]
    :id :reveal-js
    :default (-> HOME
                 (File. "GITHUB")
                 (File. "cassiel")
                 (File. "reveal.js"))]

   [nil "--dir"
    :required "[input (and output) directory]"
    :parse-fn #(when % (.getCanonicalFile (File. %)))
    :validate [#(when % (.isDirectory %)) "must be a directory"]]

   [nil "--input"
    :required "[input file (Clojure source) in DIR]"
    :validate [some? "input file expected"]]

   [nil "--output"
    :required "[output file (HTML) in DIR]"
    :validate [some? "output file expected"]]])

(defn -main
  [& args]
  (let [{:keys [options errors summary]} (parse-opts args cli-options)
        {:keys [input output dir reveal-js]} options]
    (cond errors
          (do
            (dorun (map println errors))
            (println summary)
            (System/exit 1))

          (not (and input output dir))
          (println "usage:\n" summary)

          :else
          (let [in-file (.getCanonicalFile (File. dir input))
                out-file (.getCanonicalFile (File. dir output))]
            (println "       " (str in-file))
            (println "     ->" (str out-file))
            (println "  using" (str reveal-js))
            (render-main in-file
                         out-file
                         reveal-js)
            (println "done")
            (System/exit 0)))))
