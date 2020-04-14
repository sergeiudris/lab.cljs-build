(ns hw.core
  (:require
   [clojure.core.async :as a :refer [chan go go-loop <! >!  take! put! offer! poll! alt! alts! close!
                                     pub sub unsub mult tap untap mix admix unmix
                                     timeout to-chan  sliding-buffer dropping-buffer
                                     pipeline pipeline-async]]
   [goog.string :refer [format]]
   [cljs.reader :refer [read-string]]
   [clojure.pprint :refer [pprint]]

   [goog.dom :as gdom]
   [goog.events :as events]
   [goog.object :as gobj]
   [cljs.js :as cljs]
   [cljs.analyzer :as ana]
   [cljs.tools.reader :as rdr]
   [cljs.tools.reader.reader-types :refer [string-push-back-reader]]
   [cljs.tagged-literals :as tags]))

(enable-console-print!)

(defn -main
  []
  (println "Hello world!"))

(-main)

(def out (atom {}))

(def st (cljs/empty-state))

(def ex0-src
  (str "(defn foo [a b]\n"
       "  (interleave (repeat a) (repeat b)))\n"
       "\n"
       "(take 5 (foo :read :eval))"))

(defn test1 []
  (cljs/eval-str st ex0-src 'ex0.core
                 {:eval cljs/js-eval
                  :source-map true}
                 (fn [{:keys [error value]}]
                   (if-not error
                     (do
                       (reset! out value)
                       (prn value))

                     (do
                       (reset! out error)
                       (.error js/console error))))))


(let [eval cljs.core/*eval*]
  (set! cljs.core/*eval*
        (fn [form]
          (binding [cljs.env/*compiler* st
                    *ns* 'cljs.user #_(find-ns cljs.analyzer/*cljs-ns*)
                    cljs.js/*eval-fn* cljs.js/js-eval]
            (eval form)))))

(def form1 '(let [x (fn [] 3)
                  o (x)]
              (prn o)
              o))

(def form2 '(fn [file-uri] (cljs.core/re-matches #".+\.cljs" file-uri)))

(prn
 [(eval '(cljs.core.async/chan 10))
  (eval form1)
  (let [f (eval form2)]
    (f "abc.cljs"))
  (eval '(re-matches #".+clj" "abc.clj"))])

(comment
  
  (eval '(cljs.core.async/chan 10)) ; => chan,  works
  (eval form1) ; => 3
  
  ; works:
  (eval form2) 
  (def f (eval form2))
  (f "abc.cljs") ; => "abc.cljs"
  
  (eval '(re-matches #".+clj" "abc.clj")) ; => "abc.clj"
  
  )

(defn test-21
  []
  (eval '(cljs.core/+ 1 1)))

(defn test-22
  []
  (eval '(+ 1 1)))



(defn foo [c|]
  (go-loop []
    (let [v (<! c|)]
      (prn v)
      (recur))))

(defn evalform [x]
  (cljs/eval-str st (str x) 'ex0.core
                 {:eval cljs/js-eval
                  :source-map true}
                 (fn [{:keys [error value]}]
                   (if-not error
                     (do
                       (reset! out value)
                       (prn "value")
                       (prn value))
                     (do
                       (reset! out error)
                       (prn "error")
                       (prn error)
                       (.error js/console error))))))

