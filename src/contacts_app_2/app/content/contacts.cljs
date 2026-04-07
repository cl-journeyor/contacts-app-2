(ns contacts-app-2.app.content.contacts
  (:require [clojure.string :as str]
            [contacts-app-2.shared :as sh]
            [phosphor.icons :as icons]))

;;;; Private event handlers.

(defn- handle-update-contact!
  [contact]
  (swap!
   sh/state
   (fn [prev]
     {:selected-contact contact
      :status (sh/statuses :updating)
      :widget-states (prev :widget-states)})))

(defn- toggle-widget-expanded!
  [contact-id]
  (swap!
   sh/state
   (fn [prev]
     (let [new-widget-states (mapv
                              #(if (= (-> % :contact :id) contact-id)
                                 (update % :expanded? not)
                                 %)
                              (prev :widget-states))]
       (assoc prev :widget-states new-widget-states)))))


;;;; Private component.

(defn- contact-widget
  [{:keys [id name phone email groups] :as contact} expanded?]
  [:div.contact-widget
   [:div
    [:button.iconic-btn {:type "button"
                         :on-click (fn [] (toggle-widget-expanded! id))}
     (if expanded?
       (-> (icons/icon :phosphor.regular/caret-up)
           (icons/render (sh/icon-widths :small)))
       (-> (icons/icon :phosphor.regular/caret-down)
           (icons/render (sh/icon-widths :small))))]]
   [:div.flex-column
    [:div.flex-row
     [:div.cell (-> (icons/icon :phosphor.regular/user)
                    (icons/render (sh/icon-widths :small)))]
     [:div.cell name]]
    (when expanded?
      [:div.flex-column
       [:div.flex-row
        [:div.cell (-> (icons/icon :phosphor.regular/phone)
                       (icons/render (sh/icon-widths :small)))]
        [:div.cell phone]]
       [:div.flex-row
        [:div.cell (-> (icons/icon :phosphor.regular/at)
                       (icons/render (sh/icon-widths :small)))]
        [:div.cell email]]
       [:div.flex-row
        [:div.cell (-> (icons/icon :phosphor.regular/users-three)
                       (icons/render (sh/icon-widths :small)))]
        [:div.cell (str/join ", " groups)]]
       [:div.flex-row
        [:button.iconic-btn {:type "button"
                             :on-click (fn [] (handle-update-contact! contact))}
         (-> (icons/icon :phosphor.regular/pencil-simple)
             (icons/render (sh/icon-widths :medium)))]
        [:button.iconic-btn {:type "button"}
         (-> (icons/icon :phosphor.regular/trash)
             (icons/render (sh/icon-widths :medium)))]]])]])

(defn contacts
  []
  (let [widget-states (@sh/state :widget-states)]
    (if (empty? widget-states)
      [:div.field-group "No contacts found"]
      [:<>
       (for [{:keys [contact expanded?]} widget-states]
         ^{:key (contact :id)} [contact-widget contact expanded?])])))
