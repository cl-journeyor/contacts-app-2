(ns contacts-app-2.vars
  (:require [contacts-app-2.domain :as domain]
            [reagent.core :as r]))

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
