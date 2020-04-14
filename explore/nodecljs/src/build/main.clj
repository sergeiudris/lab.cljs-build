(ns build.main
  (:require
   [clojure.core.async :as a :refer [chan go go-loop <! >!  take! put! offer! poll! alt! alts! close!
                                     pub sub unsub mult tap untap mix admix unmix <!!
                                     timeout to-chan  sliding-buffer dropping-buffer
                                     pipeline pipeline-async]]
   [clojure.java.io :as io]
   [clojure.string :as string]
   [clojure.edn :as edn]
   [cljs.util :as util]
   [cljs.env :as env]
   [cljs.analyzer :as analyzer]
   [cljs.analyzer.api :as analyzer.api]
   [cljs.compiler.api :as compiler.api]
   [cljs.build.api :as build.api]
   [cljs.closure :as closure]
   [cljs.repl :as repl]
   [cljs.repl.node :as repl.node]
   [build.impl.nrepl :as nrepl]
   [build.protocols :as p]))

(defonce channels (let [main| (chan 10)
                        main|m (mult main|)]
                    {:main| main|
                     :main|m main|m}))

(declare proc-main)

(defn -main [& args]
  (<!! (proc-main channels {})))


; repl only
(def ^:private proc-main-state (atom {}))

(defn proc-main
  [channels ctx]
  (let [pid [:proc-main]
        {:keys [main| main|m]} channels
        main|t (tap main|m (chan 10))
        nserver-clj (nrepl/nserver-clj {:host "0.0.0.0" :port 7788})
        nserver-cljs (nrepl/nserver-cljs {:host "0.0.0.0" :port 8899})]
    (go (loop [state {}]
          (reset! proc-main-state state)
          (if-let [v (<! main|t)]
            (recur state)))
        (prn "; proc-main exiting"))))





(comment

  (def arg-opts (read-string (slurp "build.edn")))
  (def opts (build.api/add-implicit-options arg-opts))
  (def source nil)

  (def compiler-env (env/default-compiler-env
                      (closure/add-externs-sources (dissoc opts :foreign-libs))))

  (do
    (alter-var-root #'cljs.env/*compiler* (constantly compiler-env))
    nil)

  (time
   (build.api/build
    source
    opts
    (if-not (nil? env/*compiler*)
      env/*compiler*
      compiler-env)))



  (def env (repl.node/repl-env :host "0.0.0.0" :port 8890))
  (repl/repl env)

  ;;
  )