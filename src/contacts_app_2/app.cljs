(ns contacts-app-2.app
  (:require [contacts-app-2.app.footer :refer [footer]]
            [contacts-app-2.app.header :refer [header]]))

(defn app
  []
  [:div.app-container {:style {:background "#9a4949"}}
   [header]
   [:div.content]
   [footer]])
