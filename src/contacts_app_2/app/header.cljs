(ns contacts-app-2.app.header
  (:require [contacts-app-2.shared :as sh]))

(defn header
  []
  [:div.header (case (@sh/state :status)
                 :updating "Update contact"
                 :creating "Create contact"
                 "Contacts")])
