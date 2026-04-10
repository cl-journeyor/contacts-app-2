(ns misc.core
  (:require [clojure.edn :as edn]
            [clojure.string :as str]))

(defn separate
  "Returns a map with :true pointing to the items of `coll` that match `pred`
  and :false pointing to the ones that don't."
  [pred coll]
  (reduce
   (fn [acc x] (update acc (boolean (pred x)) #(conj % x)))
   {true [] false []}
   coll))

(defn trim-empty?
  "Checks whether `s` is empty when the leading and trailing whitespace is
  removed."
  [s]
  (= (str/trim s) ""))

(defn try-parse
  "Tries to convert `s` to a Clojure object. Returns nil if the parsing fails."
  [s]
  (try
    (edn/read-string s)
    (catch js/Error _ nil)))
