(ns abc.app3
  (:require
   [clojure.core.async :as async :refer [<! >!  chan go alt! take! put!  alts! pub sub]]
   [nrepl.server :as nrepl]
   [cider.nrepl :refer [cider-nrepl-handler]]
   [cider.piggieback :as pback]
   [abc.core]))

(defn start-nrepl-server [host port]
  (println (str "; starting nREPL server on " host ":" port))
  (nrepl/start-server
   :bind host
   :port port
   :handler (nrepl/default-handler #'pback/wrap-cljs-repl)
   :middleware '[]))


(defn -main [& args]
  (start-nrepl-server "0.0.0.0" 8899))

(comment

  (abc.core/foo)

  ;;
  )

