(ns nodecljs.test
  (:require
   [clojure.core.async :as a :refer [chan go go-loop <! >!  take! put! offer! poll! alt! alts! close!
                                     pub sub unsub mult tap untap mix admix unmix
                                     timeout to-chan  sliding-buffer dropping-buffer
                                     pipeline pipeline-async]]
   [goog.string :refer [format]]
   [clojure.string :as string]
   [cljs.reader :refer [read-string]]
   [clojure.pprint :refer [pprint]]

   [cljs.js]
   [cljs.analyzer :as ana]
   [cljs.tools.reader :as r]
   [cljs.tools.reader.reader-types :refer [string-push-back-reader]]
   [cljs.tagged-literals :as tags]

   [nodecljs.core :refer [foo]]))

(def st (cljs.js/empty-state))

(let [eval cljs.core/*eval*]
  (set! cljs.core/*eval*
        (fn [form]
          (binding [cljs.env/*compiler* st
                    *ns* (find-ns 'nodecljs.test #_cljs.analyzer/*cljs-ns*)
                    cljs.js/*eval-fn* cljs.js/js-eval]
            (prn "(.-name *ns*) " (.-name *ns*))
            (eval form)))))

(def form1 '(let [x (fn [] 3)
                  o (x)]
              (prn o)
              o))

(def form2 '(fn [file-uri] (cljs.core/re-matches #".+\.cljs" file-uri)))

; https://cljs.github.io/api/cljs.js/eval-str

(defn test1
  []
  [(eval '(cljs.core.async/chan 10))
   (eval form1)
   (let [f (eval form2)]
     (f "abc.cljs"))
   (eval '(re-matches #".+clj" "abc.clj"))
   (apply (eval '(fn abc [a b c] #{a b c})) [1 2 3])
   (eval '(nodecljs.core/foo))
   (cljs.js/eval-str st
                     "['eval-str (map inc [1 2 3])]"
                     ""
                     {:eval cljs.js/js-eval}
                     prn)
   #_(cljs.js/eval-str st
                       "['foo-is (foo)]"
                       ""
                       {:eval cljs.js/js-eval
                        :ns 'nodecljs.test #_(find-ns 'nodecljs.test)}
                       prn)])