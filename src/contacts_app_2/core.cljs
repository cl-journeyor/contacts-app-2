(ns contacts-app-2.core
  (:require
   [contacts-app-2.app :refer [app]]
   [reagent.dom.client :as rdc]))

;; -------------------------
;; Initialize app

(defonce ^:private root (rdc/create-root (.getElementById js/document "app")))

(defn ^:export init! []
  (rdc/render root [app]))
