(ns contacts-app-2.app.content.contacts
  (:require [clojure.string :as str]
            [contacts-app-2.shared :as sh]
            [phosphor.icons :as icons]))

;;;; Private event handlers.

(defn- handle-delete-contact!
  [contact]
  (swap!
   sh/state
   (fn [prev]
     {:selected-contact contact
      :status (sh/statuses :deleting)
      :widget-states (mapv
                      #(assoc % :visible? true)
                      (prev :widget-states))})))

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
  [{{:keys [id name phone email groups] :as contact} :contact
    :keys [expanded? highlighted?]}]
  [:div.contact-widget
   [:div
    (if expanded?
      [:button.secondary-iconic-btn
       {:type "button"
        :title "Collapse"
        :on-click (fn [] (toggle-widget-expanded! id))}
       (-> (icons/icon :phosphor.regular/caret-up)
           (icons/render (sh/icon-widths :small)))]
      [:button.secondary-iconic-btn
       {:type "button"
        :title "Expand"
        :on-click (fn [] (toggle-widget-expanded! id))}
       (-> (icons/icon :phosphor.regular/caret-down)
           (icons/render (sh/icon-widths :small)))])]
   [:div {:class (if highlighted? "flex-column-yellow" "flex-column")}
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
        [:button.secondary-iconic-btn
         {:type "button"
          :title "Update"
          :on-click (fn [] (handle-update-contact! contact))}
         (-> (icons/icon :phosphor.regular/pencil-simple)
             (icons/render (sh/icon-widths :medium)))]
        [:button.secondary-iconic-btn
         {:type "button"
          :title "Delete"
          :on-click (fn [] (handle-delete-contact! contact))}
         (-> (icons/icon :phosphor.regular/trash)
             (icons/render (sh/icon-widths :medium)))]]])]])

(defn contacts
  []
  ;; selected-contact may be nil. Embracing nil punning.
  (let [{:keys [selected-contact widget-states]} @sh/state
        visible-wss (filterv :visible? widget-states)]
    (if (empty? visible-wss)
      [:div.field-group "No contacts found"]
      [:div.flex-column
       (for [{:keys [contact expanded?]} visible-wss]
         ^{:key (contact :id)}
         [contact-widget
          {:contact contact
           :expanded? expanded?
           :highlighted? (= (contact :id) (:id selected-contact))}])])))
