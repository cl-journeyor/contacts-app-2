(ns contacts-app-2.app
  (:require [contacts-app-2.components.contact-widget :refer [contact-widget]]))

(defn app
  []
  [:div.contacts-view {:style {:background "#9a4949"}}
   [contact-widget {:id 1
                    :name "Alex"
                    :phone "+1 654 123 2830"
                    :email "alex32210@gmail.com"
                    :groups "college, friends"}]])
