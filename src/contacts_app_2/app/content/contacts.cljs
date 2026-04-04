(ns contacts-app-2.app.content.contacts
  (:require [clojure.string :as str]
            [contacts-app-2.vars :as vars]
            [phosphor.icons :as icons]))

(defn- toggle-widget-expanded!
  [contact-id]
  (swap!
   vars/state
   (fn [prev]
     (let [new-widget-states (mapv
                              #(if (= (-> % :contact :id) contact-id)
                                 (update % :expanded? not)
                                 %)
                              (prev :widget-states))]
       (assoc prev :widget-states new-widget-states)))))

(defn- contact-widget
  [{:keys [id name phone email groups]} expanded?]
  [:div.contact-widget
   [:div
    [:button.iconic-btn {:on-click (fn [] (toggle-widget-expanded! id))}
     (if expanded?
       (-> (icons/icon :phosphor.regular/caret-up)
           (icons/render (vars/icon-widths :small)))
       (-> (icons/icon :phosphor.regular/caret-down)
           (icons/render (vars/icon-widths :small))))]]
   [:div.flex-column
    [:div.flex-row
     [:div.cell (-> (icons/icon :phosphor.regular/user)
                    (icons/render (vars/icon-widths :small)))]
     [:div.cell name]]
    (when expanded?
      [:div.flex-column
       [:div.flex-row
        [:div.cell (-> (icons/icon :phosphor.regular/phone)
                       (icons/render (vars/icon-widths :small)))]
        [:div.cell phone]]
       [:div.flex-row
        [:div.cell (-> (icons/icon :phosphor.regular/at)
                       (icons/render (vars/icon-widths :small)))]
        [:div.cell email]]
       [:div.flex-row
        [:div.cell (-> (icons/icon :phosphor.regular/users-three)
                       (icons/render (vars/icon-widths :small)))]
        [:div.cell (str/join ", " groups)]]
       [:div.flex-row
        [:button.iconic-btn (-> (icons/icon :phosphor.regular/pencil-simple)
                                (icons/render (vars/icon-widths :medium)))]
        [:button.iconic-btn (-> (icons/icon :phosphor.regular/trash)
                                (icons/render (vars/icon-widths :medium)))]]])]])

(defn contacts
  []
  [:<>
   (for [{:keys [contact expanded?]} (@vars/state :widget-states)]
     ^{:key (contact :id)} [contact-widget contact expanded?])])
