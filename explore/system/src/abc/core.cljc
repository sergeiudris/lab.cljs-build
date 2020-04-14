(ns abc.core
  (:require
   [clojure.string :as str]))

(defn foo
  "Returns 'bar"
  {:arglists '([] [val])}
  ([] 'bar)
  ([x] ['bar x]))

(comment

  (foo)

  ;;
  )