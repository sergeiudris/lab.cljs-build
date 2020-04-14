(ns abc.app1
  (:require
   [clojure.core.async :as async :refer [<! >!  chan go alt! take! put!  alts! pub sub]]
   [nrepl.server :refer [start-server stop-server]]
   [cider.nrepl :refer [cider-nrepl-handler]]
   [abc.core]))


(defn start-nrepl-server [host port]
  (println (str "; starting nREPL server on " host ":" port))
  (start-server
   :bind host
   :port port
   :handler cider-nrepl-handler #_(nrepl-handler)
   :middleware '[]))


(defn -main [& args]
  (start-nrepl-server "0.0.0.0" 7788))

(comment

  (abc.core/foo)

  ;;
  )

