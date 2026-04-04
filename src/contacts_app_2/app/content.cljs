(ns contacts-app-2.app.content
  (:require [contacts-app-2.app.content.contact-form :refer [contact-form]]
            [contacts-app-2.app.content.contacts :refer [contacts]]
            [contacts-app-2.vars :as vars]))

(defn content
  []
  (let [status (@vars/state :status)]
    [:div.content
     (if (or (= status :updating) (= status :creating))
       [contact-form]
       [contacts])]))
