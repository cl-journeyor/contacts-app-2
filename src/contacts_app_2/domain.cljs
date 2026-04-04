(ns contacts-app-2.domain
  (:require [contacts-app-2.utils :as u]))

(defn read-contacts!
  []
  (let [contacts-maybe (-> js/localStorage
                           (.getItem "contacts")
                           u/try-parse)]
    (if (vector? contacts-maybe)
      contacts-maybe
      (do
        (.setItem js/localStorage "contacts" "[]")
        []))))

(defn write-contacts!
  [contacts]
  (.setItem js/localStorage "contacts" (str contacts)))
