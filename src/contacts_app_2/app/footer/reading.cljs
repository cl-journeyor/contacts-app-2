(ns contacts-app-2.app.footer.reading
  (:require [contacts-app-2.vars :as vars]
            [phosphor.icons :as icons]))

(defn- iconic-btn
  [child handler]
  [:button.iconic-btn {:type "button"
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
       (icons/render (vars/icon-widths :small)))])

(defn reading
  []
  [:<>
   [iconic-btn
    (-> (icons/icon :phosphor.regular/arrow-counter-clockwise)
        (icons/render (vars/icon-widths :large)))
    identity]
   [iconic-btn
    (-> (icons/icon :phosphor.regular/user-plus)
        (icons/render (vars/icon-widths :large)))
    identity]
   [search-by-btn
    (-> (icons/icon :phosphor.regular/user)
        (icons/render (vars/icon-widths :small)))
    identity]
   [search-by-btn
    (-> (icons/icon :phosphor.regular/users-three)
        (icons/render (vars/icon-widths :small)))
    identity]
   [iconic-btn
    (-> (icons/icon :phosphor.regular/sort-ascending)
        (icons/render (vars/icon-widths :large)))
    identity]
   [iconic-btn
    (-> (icons/icon :phosphor.regular/upload-simple)
        (icons/render (vars/icon-widths :large)))
    identity]
   [iconic-btn
    (-> (icons/icon :phosphor.regular/download-simple)
        (icons/render (vars/icon-widths :large)))
    identity]])
