(ns contacts-app-2.app.content.contact-form
  (:require [clojure.string :as str]
            [contacts-app-2.shared :as sh]
            [reagent.core :as r]))

(defn contact-form
  []
  (let [{:keys [id name phone email groups] ; May be nil.
         :as selected-contact-maybe} (@sh/state :selected-contact)
        form (r/atom {:name name
                      :phone phone
                      :email email
                      :groups (when groups (str/join "\n" groups))})
        handle-input-change! (fn [e]
                               (swap! form #(assoc
                                             %
                                             (-> e .-target .-name keyword)
                                             (-> e .-target .-value))))
        create! (fn [e]
                  (.preventDefault e)
                  (let [contacts (mapv :contact (@sh/state :widget-states))
                        next-id (-> (if (empty? contacts)
                                      0
                                      (apply max (map :id contacts)))
                                    inc)
                        new-contact (-> @form
                                        (update :groups #(str/split % #"\n"))
                                        (assoc :id next-id))]
                    (sh/write-contacts! (conj contacts new-contact))
                    (reset! sh/state (sh/get-init-state!))))
        update! (fn [e]
                  (.preventDefault e)
                  (let [contacts (mapv :contact (@sh/state :widget-states))
                        updated-contact (-> @form
                                            (update :groups #(str/split % #"\n"))
                                            (assoc :id id))]
                    (sh/write-contacts! (mapv
                                         #(if (= (% :id) id) updated-contact %)
                                         contacts))
                    (reset! sh/state (sh/get-init-state!))))]
    (fn []
      [:form {:on-submit (if selected-contact-maybe update! create!)}
       [:div.field-group
        [:label.form-label {:for "name-field"}
         "Name (required)"]
        [:input.text-field {:id "name-field"
                            :name "name"
                            :type "text"
                            :required true
                            :on-change handle-input-change!
                            :value (@form :name)
                            :auto-focus true}]]
       [:div.field-group
        [:label.form-label {:for "phone-field"}
         "Phone number"]
        [:input.text-field {:id "phone-field"
                            :name "phone"
                            :type "tel"
                            :on-change handle-input-change!
                            :value (@form :phone)}]]
       [:div.field-group
        [:label.form-label {:for "email-field"}
         "Email address"]
        [:input.text-field {:id "email-field"
                            :name "email"
                            :type "email"
                            :on-change handle-input-change!
                            :value (@form :email)}]]
       [:div.field-group
        [:label.form-label {:for "groups-field"}
         "Groups, one per line"]
        [:textarea.text-field {:id "groups-field"
                               :name "groups"
                               :rows 3
                               :on-change handle-input-change!
                               :placeholder "For ex.: family"
                               :value (@form :groups)}]]
       [:div.submit-button-container
        [:button.primary-btn
         (if selected-contact-maybe "Update" "Create")]]])))
