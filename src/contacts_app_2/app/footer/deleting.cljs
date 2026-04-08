(ns contacts-app-2.app.footer.deleting
  (:require [contacts-app-2.shared :as sh]
            [phosphor.icons :as icons]))

(defn- cancel!
  []
  (swap!
   sh/state
   (fn [prev]
     {:selected-contact nil
      :status (sh/statuses :reading)
      :widget-states (prev :widget-states)})))

(defn- delete!
  []
  (let [{:keys [selected-contact widget-states]} @sh/state
        filtered-wss (filterv
                      #(not= (-> % :contact :id) (selected-contact :id))
                      widget-states)]
    (sh/write-contacts! (mapv :contact filtered-wss))
    (reset! sh/state {:selected-contact nil
                      :status (sh/statuses :reading)
                      :widget-states filtered-wss})))

(defn deleting
  []
  [:<>
   [:div.footer-field-group
    "Delete selected contact?"]
   [:button.secondary-iconic-btn {:type "button"
                                  :on-click cancel!}
    (-> (icons/icon :phosphor.regular/x)
        (icons/render (sh/icon-widths :large)))]
   [:button.primary-iconic-btn {:type "button"
                                :on-click delete!}
    (-> (icons/icon :phosphor.regular/check)
        (icons/render (sh/icon-widths :large)))]])
