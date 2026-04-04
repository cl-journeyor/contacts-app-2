(ns contacts-app-2.utils
  (:require [clojure.edn :as edn]))

(defn try-parse
  [s]
  (try
    (edn/read-string s)
    (catch js/Error _ nil)))
