(ns contacts-app-2.app.footer.deleting
  (:require [contacts-app-2.shared :as sh]
            [phosphor.icons :as icons]))

(defn deleting
  []
  [:<>
   [:div.footer-label-container
    "Delete selected contact?"]
   [:button.secondary-iconic-btn {:type "button"}
    (-> (icons/icon :phosphor.regular/x)
        (icons/render (sh/icon-widths :large)))]
   [:button.primary-iconic-btn {:type "button"}
    (-> (icons/icon :phosphor.regular/check)
        (icons/render (sh/icon-widths :large)))]])
