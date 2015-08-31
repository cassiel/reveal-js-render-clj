(defproject eu.cassiel/reveal-js-render-clj "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/tools.cli "0.3.1"]
                 [hiccup "1.0.5"]
                 [me.raynes/conch "0.8.0"]
                 [org.clojure/clojure "1.7.0"]]
  :plugins [[cider/cider-nrepl "0.9.1"]
            [lein-bin "0.3.4"]
            [refactor-nrepl "1.1.0"]
            ]
  :main ^:skip-aot eu.cassiel.reveal-js-render-clj
  :target-path "target/%s"
  :profiles {:dev {:dependencies [[org.clojure/tools.nrepl "0.2.7"]]}
             :uberjar {:aot :all}})
