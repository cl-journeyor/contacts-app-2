(ns contacts-app-2.app.footer.reading
  (:require [contacts-app-2.shared :as sh]
            [phosphor.icons :as icons]))

;;;; Private functions.

(defn- get-contacts-as-uri*
  []
  (str
   "data:text/x-clojure;charset=utf-8,"
   (js/encodeURIComponent (.getItem js/localStorage "contacts"))))


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

(defn- change-app-status!
  [kw]
  (swap! sh/state (fn [prev] (assoc prev :status kw))))

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
    (fn [] (change-app-status! (sh/statuses :creating)))]
   [search-by-btn
    (-> (icons/icon :phosphor.regular/user)
        (icons/render (sh/icon-widths :small)))
    (fn [] (change-app-status! (sh/statuses :searching-by-name)))]
   [search-by-btn
    (-> (icons/icon :phosphor.regular/users-three)
        (icons/render (sh/icon-widths :small)))
    (fn [] (change-app-status! (sh/statuses :searching-by-group)))]
   [iconic-btn
    (-> (icons/icon :phosphor.regular/sort-ascending)
        (icons/render (sh/icon-widths :large)))
    sort-contacts!]
   [:label.secondary-iconic-btn {:for "contacts-file-input"
                                 :tab-index 0}
    (-> (icons/icon :phosphor.regular/upload-simple)
        (icons/render (sh/icon-widths :large)))]
   [:input.file-input {:id "contacts-file-input"
                       :type "file"
                       :accept ".edn"}]
   [:a.secondary-iconic-btn {:href (get-contacts-as-uri*)
                             :download "contacts-app-2.edn"}
    (-> (icons/icon :phosphor.regular/download-simple)
        (icons/render (sh/icon-widths :large)))]])
