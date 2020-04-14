(ns nodecljs.main
  (:require
   [clojure.core.async :as a :refer [chan go go-loop <! >!  take! put! offer! poll! alt! alts! close!
                                     pub sub unsub mult tap untap mix admix unmix
                                     timeout to-chan  sliding-buffer dropping-buffer
                                     pipeline pipeline-async]]
   [goog.string :refer [format]]
   [clojure.string :as string]
   [cljs.reader :refer [read-string]]
   [clojure.pprint :refer [pprint]]
   [nodecljs.test]))



(defn anchor []
  (let [http (js/require "http")
        handler (fn [req res] (.end res "nodecljs.main"))
        server (.createServer http handler)]
    (.listen server 1337)))

(defn main [& args]
  (println "; main")
  (prn (nodecljs.test/test1))
  (anchor))


(main)