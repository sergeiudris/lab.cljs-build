 
(defproject dl4j "0.1.0"

  :min-lein-version "2.8.0"

  :plugins [[lein-cljsbuild "1.1.8"]]

  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/clojurescript "1.10.597"]
                 [org.clojure/core.async "1.0.567"]
                 [nrepl/nrepl "0.6.0"]
                 [cider/cider-nrepl "0.24.0"]
                 [cider/piggieback "0.4.2"]]

  :cljsbuild
  {:builds [{:id "dev"
             :source-paths ["src"]
             :compiler {:output-to "out/node-main.js"
                        :output-dir "out"
                        :optimizations :none
                        :cache-analysis true
                        :source-map true}}]}

  :repl-options {:init-ns          hw.core
                 :main             hw.core
                 :host             "0.0.0.0"
                 :port             8899}
  :profiles {:dev  {:main         ^{:skip-aot true}  hw.core
                    :aot          nil #_[dev]
                    :aliases      {"dev" ["trampoline" "run" "-m" "hw.core/-main"]}
                    :dependencies []}

             :prod ^:leaky {:main hw.core
                            :uberjar-name "hw.core-standalone.jar"
                            :jar-name     "hw.core.jar"
                            :aot  [hw.core]}
             :uberjar {:aot :all
                       :native-image {:jvm-opts ["-Dclojure.compiler.direct-linking=true"]}}}

  :main ^:skip-aot hw.core
  :jvm-opts ["-Xms768m" "-Xmx11998m"]

  :source-paths ["src"]
  :java-source-paths ["src"]
  :test-paths ["test"]
  :resource-paths ["resources" "config"]
  :auto-clean false)