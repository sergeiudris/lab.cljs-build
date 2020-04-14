(ns abc.app2
  (:require
   [clojure.core.async :as async :refer [<! >!  chan go alt! take! put!  alts! pub sub]]
   [abc.core]))

(defn ^:export main
  []
  (println "; started"))

(comment

  (abc.core/foo)

  ;;
  )

