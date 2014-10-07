(defproject eu.cassiel/reveal-js-render-clj "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/tools.cli "0.3.1"]
                 [hiccup "1.0.5"]
                 [org.clojure/clojure "1.6.0"]]
  :plugins [[cider/cider-nrepl "0.7.0"]]
  :main ^:skip-aot eu.cassiel.reveal-js-render-clj
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
