(ns contacts-app-2.app.footer.reading
  (:require [contacts-app-2.shared :as sh]
            [misc.core :as misc]
            [phosphor.icons :as icons]))

;;;; Private functions.

(defn- get-contacts-as-uri
  []
  (let [contacts-str (str (mapv :contact (@sh/state :widget-states)))]
    (str
     "data:text/x-clojure;charset=utf-8,"
     (js/encodeURIComponent contacts-str))))


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

(defn- handle-contacts-file-input-change!
  [e]
  (when-let [contacts-file (-> e .-target .-files first)]
    (let [contact->widget-state
          (fn [c] {:contact c :expanded? false :visible? true})

          file-reader
          (js/FileReader.)

          handle-read-file!
          (fn []
            (let [contacts-maybe (-> file-reader .-result misc/try-parse)]
              (set! (-> e .-target .-value) "")
              (if (vector? contacts-maybe)
                (do
                  (sh/write-contacts! contacts-maybe)
                  (swap!
                   sh/state
                   (fn [prev]
                     (assoc
                      prev
                      :widget-states
                      (mapv contact->widget-state contacts-maybe)))))
                (js/alert "Couldn't read contacts from file"))))]
      (set! (.-onload file-reader) handle-read-file!)
      (.readAsText file-reader contacts-file))))

(defn- handle-contacts-file-label-enter!
  [e]
  (when (= (.-key e) "Enter")
    (.click (.-target e))))

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
   [:label.secondary-iconic-btn
    {:for "contacts-file-input"
     :tab-index 0
     :on-key-press handle-contacts-file-label-enter!}
    (-> (icons/icon :phosphor.regular/upload-simple)
        (icons/render (sh/icon-widths :large)))]
   [:input.file-input {:id "contacts-file-input"
                       :type "file"
                       :accept ".edn"
                       :on-change handle-contacts-file-input-change!}]
   [:a.secondary-iconic-btn {:href (get-contacts-as-uri)
                             :download "contacts-app-2.edn"}
    (-> (icons/icon :phosphor.regular/download-simple)
        (icons/render (sh/icon-widths :large)))]])
