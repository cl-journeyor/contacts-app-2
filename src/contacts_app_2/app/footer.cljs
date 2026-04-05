(ns contacts-app-2.app.footer
  (:require [contacts-app-2.app.footer.deleting :refer [deleting]]
            [contacts-app-2.app.footer.reading :refer [reading]]
            [contacts-app-2.app.footer.searching-by-group :refer [searching-by-group]]
            [contacts-app-2.app.footer.searching-by-name :refer [searching-by-name]]
            [contacts-app-2.app.footer.updating-creating :refer [updating-creating]]
            [contacts-app-2.shared :as sh]))

(defn footer
  []
  [:div.footer (case (@sh/state :status)
                 :updating [updating-creating]
                 :deleting [deleting]
                 :creating [updating-creating]
                 :searching-by-name [searching-by-name]
                 :searching-by-group [searching-by-group]
                 [reading])])
