(ns contacts-app-2.app.footer.reading
  (:require [contacts-app-2.shared :as sh]
            [phosphor.icons :as icons]))

;;;; Private components.

(defn- iconic-btn
  [child handler]
  [:button.secondary-iconic-btn {:type "button"
                                 :on-click handler}
   child])

(defn- search-by-btn
  [child handler]
  [:button.four-x-four-btn {:type "button"
                            :on-click handler}
   child
   [:span]
   [:span]
   (-> (icons/icon :phosphor.regular/magnifying-glass)
       (icons/render (sh/icon-widths :small)))])


;;;; Private event handlers.

(defn- handle-create-contact!
  []
  (swap! sh/state (fn [prev] (assoc prev :status (sh/statuses :creating)))))

(defn- reset-contacts!
  []
  (reset! sh/state (sh/get-init-state!)))

(defn- sort-contacts!
  []
  (swap!
   sh/state
   (fn [prev]
     (update
      prev
      :widget-states
      (fn [wss]
        (sort-by #(-> % :contact :name) wss))))))


;;;; Main component.

(defn reading
  []
  [:<>
   [iconic-btn
    (-> (icons/icon :phosphor.regular/arrow-counter-clockwise)
        (icons/render (sh/icon-widths :large)))
    reset-contacts!]
   [iconic-btn
    (-> (icons/icon :phosphor.regular/user-plus)
        (icons/render (sh/icon-widths :large)))
    handle-create-contact!]
   [search-by-btn
    (-> (icons/icon :phosphor.regular/user)
        (icons/render (sh/icon-widths :small)))
    identity]
   [search-by-btn
    (-> (icons/icon :phosphor.regular/users-three)
        (icons/render (sh/icon-widths :small)))
    identity]
   [iconic-btn
    (-> (icons/icon :phosphor.regular/sort-ascending)
        (icons/render (sh/icon-widths :large)))
    sort-contacts!]
   [iconic-btn
    (-> (icons/icon :phosphor.regular/upload-simple)
        (icons/render (sh/icon-widths :large)))
    identity]
   [iconic-btn
    (-> (icons/icon :phosphor.regular/download-simple)
        (icons/render (sh/icon-widths :large)))
    identity]])
