(ns contacts-app-2.app.footer.searching-by-group
  (:require [clojure.string :as str]
            [contacts-app-2.shared :as sh]
            [phosphor.icons :as icons]
            [reagent.core :as r]))

(def ^:private search-text (r/atom ""))

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

(defn- handle-input-change!
  [e]
  (reset! search-text (-> e .-target .-value)))

(defn- search!
  [e]
  (.preventDefault e)
  (let [search-txt
        (.toLocaleLowerCase @search-text)

        visible-widget-mapper
        (fn [widget-state]
          (assoc
           widget-state
           :visible?
           (some
            #(str/includes? (.toLocaleLowerCase %) search-txt)
            (-> widget-state :contact :groups))))]
    (swap!
     sh/state
     (fn [prev]
       (update prev :widget-states #(mapv visible-widget-mapper %))))))

(defn searching-by-group
  []
  [:<>
   [:button.secondary-iconic-btn {:type "button"
                                  :on-click cancel!}
    (-> (icons/icon :phosphor.regular/arrow-left)
        (icons/render (sh/icon-widths :large)))]
   [:form.footer-field-group {:id "search-by-group-form"
                              :on-submit search!}
    [:label.footer-label {:for "search-by-group-field"}
     "Search by group"]
    [:input.text-field {:id "search-by-group-field"
                        :name "search-text"
                        :type "text"
                        :on-change handle-input-change!
                        :auto-focus true}]]
   [:button.primary-iconic-btn {:form "search-by-group-form"}
    (-> (icons/icon :phosphor.regular/magnifying-glass)
        (icons/render (sh/icon-widths :large)))]])
