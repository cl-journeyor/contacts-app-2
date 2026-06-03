(ns contacts-app-2.core
  (:require [contacts-app-2.app :refer [app]]
            [reagent.dom.client :as rdc]))

(defonce ^:private root (-> (.getElementById js/document "app")
                            rdc/create-root))

(defn mount-root
  []
  (rdc/render root [app]))

(mount-root)
