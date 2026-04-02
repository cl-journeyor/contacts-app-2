(ns contacts-app-2.vars
  (:require [reagent.core :as r]))

(def statuses #{:reading
              :updating
              :deleting
              :creating
              :searching-by-name
              :searching-by-group})

(def state (r/atom {:status (statuses :reading)}))
