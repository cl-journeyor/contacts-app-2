(ns contacts-app-2.app.footer.reading
  (:require [contacts-app-2.vars :as vars]
            [phosphor.icons :as icons]))

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
   [:button.iconic-btn {:type "button"}
    (-> (icons/icon :phosphor.regular/arrow-counter-clockwise)
        (icons/render (vars/icon-widths :large)))]
   [:button.iconic-btn {:type "button"}
    (-> (icons/icon :phosphor.regular/user-plus)
        (icons/render (vars/icon-widths :large)))]
   [search-by-btn
    (-> (icons/icon :phosphor.regular/user)
        (icons/render (vars/icon-widths :small)))
    identity]]) ; FIXME
