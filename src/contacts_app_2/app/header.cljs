(ns contacts-app-2.app.header
  (:require [contacts-app-2.vars :as vars]))

(defn header
  []
  [:div.header (case (@vars/state :status)
                 :updating "Update contact"
                 :creating "Create contact"
                 "Contacts")])
