(ns contacts-app-2.app
  (:require [contacts-app-2.app.content :refer [content]]
            [contacts-app-2.app.footer :refer [footer]]
            [contacts-app-2.app.header :refer [header]]))

(defn app
  []
  [:div.app-container
   [header]
   [content]
   [footer]])
