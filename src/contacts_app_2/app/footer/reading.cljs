(ns contacts-app-2.app.footer.reading
  (:require [contacts-app-2.vars :as vars]
            [phosphor.icons :as icons]))

(defn reading
  []
  [:<>
   [:button.iconic-btn
    (-> (icons/icon :phosphor.regular/arrow-counter-clockwise)
        (icons/render (vars/icon-widths :large)))]
   [:button.iconic-btn
    (-> (icons/icon :phosphor.regular/user-plus)
        (icons/render (vars/icon-widths :large)))]])
