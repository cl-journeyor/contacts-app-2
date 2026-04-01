(ns contacts-app-2.core
  (:require
   [contacts-app-2.app :refer [app]]
   [reagent.dom :as d]))

;; -------------------------
;; Initialize app

(defn mount-root []
  (d/render [app] (.getElementById js/document "app")))

(defn ^:export init! []
  (mount-root))
