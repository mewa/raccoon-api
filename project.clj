(defproject raccoon "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/core.async "0.4.474"]
                 [clj-http "2.0.0"]
                 [compojure "1.6.1"]
                 [cheshire "5.5.0"]
                 [ring "1.6.3"]
                 [aleph "0.4.4"]]
  :plugins [[lein-auto "0.1.3"]]
  :auto {:default {:file-pattern #"\.(clj|cljs|cljx|cljc|edn)$"}}
  :main ^:skip-aot raccoon.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}
             :executor {:main raccoon.executor.core}
             :e2e {:test-paths ["e2e-test"]}})
