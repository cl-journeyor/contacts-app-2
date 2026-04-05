(ns contacts-app-2.app.content
  (:require [contacts-app-2.app.content.contact-form :refer [contact-form]]
            [contacts-app-2.app.content.contacts :refer [contacts]]
            [contacts-app-2.shared :as sh]))

(defn content
  []
  (let [status (@sh/state :status)]
    [:div.content
     (if (or (= status :updating) (= status :creating))
       [contact-form]
       [contacts])]))
