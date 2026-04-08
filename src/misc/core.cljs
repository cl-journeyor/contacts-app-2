(ns misc.core
  (:require [clojure.edn :as edn]))

(defn separate
  "Returns a map with :true pointing to the items of `coll` that match `pred`
  and :false pointing to the ones that don't."
  [pred coll]
  (reduce
   (fn [acc x] (update acc (boolean (pred x)) #(conj % x)))
   {true [] false []}
   coll))

(defn try-parse
  [s]
  (try
    (edn/read-string s)
    (catch js/Error _ nil)))
