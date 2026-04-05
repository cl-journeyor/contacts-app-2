(ns contacts-app-2.shared
  (:require [misc.core :as misc]
            [reagent.core :as r]))

;;;; Functions.

(defn contact->widget-state
  [contact]
  {:contact contact
   :expanded? false})

(defn read-contacts!
  []
  (let [contacts-maybe (-> js/localStorage
                           (.getItem "contacts")
                           misc/try-parse)]
    (if (vector? contacts-maybe)
      contacts-maybe
      (do
        (.setItem js/localStorage "contacts" "[]")
        []))))

(defn write-contacts!
  [contacts]
  (.setItem js/localStorage "contacts" (str contacts)))


;;;; Vars.

(def icon-widths {:small {:width "1em"}
                  :medium {:width "1.5em"}
                  :large {:width "2em"}})

(def statuses #{:reading
                :updating
                :deleting
                :creating
                :searching-by-name
                :searching-by-group})

(def state (r/atom {:status (statuses :reading)
                    :widget-states (mapv
                                    contact->widget-state
                                    (read-contacts!))}))
