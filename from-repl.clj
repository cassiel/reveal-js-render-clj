(ns user
  (:require (eu.cassiel [reveal-js-render-clj :as renderer])
            [clojure.edn :as edn]
            [me.raynes.conch :refer [with-programs]])
  (:import (java.io File)))

;; This function works for the Codezoners presentations in a standard location. Edit to taste for other presentations.

(defn render-site [topic]
  (let [dir (File. (format "/Users/nick/CASSIEL/codezoners-2-prep/%s/presentation" topic))
        reveal-js (File. "/Users/nick/GITHUB/cassiel/reveal.js")]
    (renderer/render-main (File. dir "presentation.clj")
                          (File. dir "presentation.html")
                          reveal-js)))

(render-site "HTML/02_forms_controls_inputs")
(render-site "HTML/03_templating_with_Jinja2")
(render-site "HTML/05_styling_sites_with_Bootstrap")
(render-site "React/01_introduction_to_React")
(render-site "React/02_dynamic_sites_with_React")
(render-site "React/03_grids_and_calculation_with_React")
(render-site "AgileDevelopment/01_basic_testing")
(render-site "WebServices/01_running_Flask")
(render-site "WebServices/02_AJAX")
(render-site "WebServices/04_data_visualisation")
(render-site "WebServices/05_MongoDB")

(defn render-workshop [ws]
  (let [dir (File. (format "/Users/nick/CASSIEL/codezoners-workshops/%s/presentation" ws))
        reveal-js (File. "/Users/nick/GITHUB/cassiel/reveal.js")]
    (renderer/render-main (File. dir "presentation.clj")
                          (File. dir "presentation.html")
                          reveal-js)))

(render-workshop "hackney - codezoners taster")

(defn render-cmc [dir]
  (let [home                    (System/getenv "HOME")
        dir (File. (format "%s/CASSIEL/design-coding/%s/presentation"
                           home
                           dir))
        reveal-js (File. (format "%s/GITHUB/cassiel/reveal.js" home))]
    (renderer/render-main (File. dir "presentation.clj")
                          (File. dir "presentation.html")
                          reveal-js)))

(render-cmc "Arduino")
