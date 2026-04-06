(ns contacts-app-2.shared
  (:require [misc.core :as misc]
            [reagent.core :as r]))

(defn load-widget-states!
  []
  (let [contacts-maybe (-> js/localStorage
                           (.getItem "contacts")
                           misc/try-parse)]
    (if (vector? contacts-maybe)
      (mapv (fn [c] {:contact c :expanded? false}) contacts-maybe)
      (do
        (.setItem js/localStorage "contacts" "[]")
        []))))

(defn write-contacts!
  [contacts]
  (.setItem js/localStorage "contacts" (str contacts)))

(def icon-widths {:small {:width "1em"}
                  :medium {:width "1.5em"}
                  :large {:width "2em"}})

(def statuses #{:reading
                :updating
                :deleting
                :creating
                :searching-by-name
                :searching-by-group})

(defn get-init-state!
  []
  {:selected-contact nil
   :status (statuses :reading)
   :widget-states (load-widget-states!)})

(def state (r/atom (get-init-state!)))
