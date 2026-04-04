(ns contacts-app-2.app.content.contacts
  (:require [clojure.string :as str]
            [contacts-app-2.vars :as vars]
            [phosphor.icons :as icons]))

(def ^:private em-width {:width "1em"})

(defn- contact-widget
  [{:keys [id name phone email groups]} expanded?]
  [:div.contact-widget
   [:div
    [:button.iconic-btn
     (if expanded?
       (-> (icons/icon :phosphor.regular/caret-up) (icons/render em-width))
       (-> (icons/icon :phosphor.regular/caret-down) (icons/render em-width)))]]
   [:div.flex-column
    [:div.flex-row
     [:div.cell (-> (icons/icon :phosphor.regular/user)
                    (icons/render em-width))]
     [:div.cell name]]
    (when expanded?
      [:div.flex-column
       [:div.flex-row
        [:div.cell (-> (icons/icon :phosphor.regular/phone)
                       (icons/render em-width))]
        [:div.cell phone]]
       [:div.flex-row
        [:div.cell (-> (icons/icon :phosphor.regular/at)
                       (icons/render em-width))]
        [:div.cell email]]
       [:div.flex-row
        [:div.cell (-> (icons/icon :phosphor.regular/users-three)
                       (icons/render em-width))]
        [:div.cell (str/join ", " groups)]]
       [:div.flex-row
        [:button.iconic-btn (-> (icons/icon :phosphor.regular/pencil-simple)
                                (icons/render em-width))]
        [:button.iconic-btn (-> (icons/icon :phosphor.regular/trash)
                                (icons/render em-width))]]])]])

(defn contacts
  []
  [:<>
   (for [{:keys [contact expanded?]} (@vars/state :widget-states)]
     ^{:key (contact :id)} [contact-widget contact expanded?])])
