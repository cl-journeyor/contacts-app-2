(ns contacts-app-2.app.footer.searching-by-name
  (:require [clojure.string :as str]
            [contacts-app-2.shared :as sh]
            [phosphor.icons :as icons]))

(defn- cancel!
  []
  (swap!
   sh/state
   (fn [prev]
     {:selected-contact nil
      :status (sh/statuses :reading)
      :widget-states (mapv
                      #(assoc % :visible? true)
                      (prev :widget-states))})))

(defn- search!
  [e]
  (let [search-text (-> e .-target .-value .toLocaleLowerCase)
        visible-widget-mapper (fn [widget-state]
                                (assoc
                                 widget-state
                                 :visible?
                                 (str/includes?
                                  (-> widget-state
                                      :contact
                                      :name
                                      .toLocaleLowerCase)
                                  search-text)))]
    (swap!
     sh/state
     (fn [prev]
       (update prev :widget-states #(mapv visible-widget-mapper %))))))

(defn searching-by-name
  []
  [:<>
   [:button.secondary-iconic-btn {:type "button"
                                  :title "Cancel"
                                  :on-click cancel!}
    (-> (icons/icon :phosphor.regular/arrow-left)
        (icons/render (sh/icon-widths :large)))]
   [:div.footer-field-group
    [:label.footer-label {:for "search-by-name-field"}
     "Search by name"]
    [:input.text-field {:id "search-by-name-field"
                        :type "text"
                        :on-change search!
                        :auto-focus true}]]])
