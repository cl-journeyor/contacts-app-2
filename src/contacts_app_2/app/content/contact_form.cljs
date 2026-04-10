(ns contacts-app-2.app.content.contact-form
  (:require [clojure.string :as str]
            [contacts-app-2.shared :as sh]
            [misc.core :as misc]
            [reagent.core :as r]))

(defn contact-form
  []
  (let [selected-contact-maybe
        (@sh/state :selected-contact)

        form
        (r/atom {:valid-name-on-blur? true
                 :fields (if selected-contact-maybe
                           (update
                            selected-contact-maybe
                            :groups
                            #(str/join "\n" %))
                           {:name ""
                            :phone ""
                            :email ""
                            :groups ""})})
        
        handle-name-field-blur!
        (fn [e]
          (when (-> e .-target .-value misc/trim-empty?)
            (swap!
             form
             #(assoc % :valid-name-on-blur? false))))

        handle-name-field-focus!
        (fn []
          (swap!
           form
           #(assoc % :valid-name-on-blur? true)))

        handle-input-change!
        (fn [e]
          (swap! form (fn [prev]
                        (assoc-in
                         prev
                         [:fields (-> e .-target .-name keyword)]
                         (-> e .-target .-value)))))

        create!
        (fn [e]
          (.preventDefault e)
          (let [contacts (mapv :contact (@sh/state :widget-states))
                next-id (-> (if (empty? contacts)
                              0
                              (apply max (map :id contacts)))
                            inc)
                new-contact (-> @form
                                :fields
                                (update :groups #(str/split % #"\n"))
                                (assoc :id next-id))]
            (sh/write-contacts! (conj contacts new-contact))
            (reset! sh/state (sh/get-init-state!))))
        
        update!
        (fn [e]
          (.preventDefault e)
          (let [selected-contact-id (selected-contact-maybe :id)
                contacts (mapv :contact (@sh/state :widget-states))
                updated-contact (-> @form
                                    :fields
                                    (update :groups #(str/split % #"\n"))
                                    (assoc :id selected-contact-id))]
            (sh/write-contacts! (mapv
                                 (fn [c]
                                   (if (= (c :id) selected-contact-id)
                                     updated-contact
                                     c))
                                 contacts))
            (reset! sh/state (sh/get-init-state!))))]
    (fn []
      [:form {:on-submit (if selected-contact-maybe update! create!)}
       [:div.field-group
        (if (@form :valid-name-on-blur?)
          [:label.form-label {:for "name-field"}
           "Name (required)"]
          [:label.form-label-yellow {:for "name-field"}
           "Please enter a name"])
        [:input.text-field {:id "name-field"
                            :name "name"
                            :type "text"
                            :required true
                            :on-change handle-input-change!
                            :on-focus handle-name-field-focus!
                            :on-blur handle-name-field-blur!
                            :value (-> @form :fields :name)
                            :auto-focus true}]]
       [:div.field-group
        [:label.form-label {:for "phone-field"}
         "Phone number"]
        [:input.text-field {:id "phone-field"
                            :name "phone"
                            :type "tel"
                            :on-change handle-input-change!
                            :value (-> @form :fields :phone)}]]
       [:div.field-group
        [:label.form-label {:for "email-field"}
         "Email address"]
        [:input.text-field {:id "email-field"
                            :name "email"
                            :type "email"
                            :on-change handle-input-change!
                            :value (-> @form :fields :email)}]]
       [:div.field-group
        [:label.form-label {:for "groups-field"}
         "Groups, one per line"]
        [:textarea.text-field {:id "groups-field"
                               :name "groups"
                               :rows 3
                               :on-change handle-input-change!
                               :placeholder "For ex.: family"
                               :value (-> @form :fields :groups)}]]
       [:div.submit-button-container
        [:button.primary-btn
         (if selected-contact-maybe "Update" "Create")]]])))
