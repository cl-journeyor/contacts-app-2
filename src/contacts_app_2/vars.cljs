(ns contacts-app-2.vars
  (:require [contacts-app-2.domain :as domain]
            [reagent.core :as r]))

(def icon-widths {:small {:width "1em"}
                  :medium {:width "1.5em"}
                  :large {:width "2em"}})

(def statuses #{:reading
              :updating
              :deleting
              :creating
              :searching-by-name
              :searching-by-group})

(defn- contact->widget-state
  [contact]
  {:contact contact
   :expanded? false})

(def state (r/atom {:status (statuses :reading)
                    :widget-states (mapv
                                    contact->widget-state
                                    (domain/read-contacts!))}))
