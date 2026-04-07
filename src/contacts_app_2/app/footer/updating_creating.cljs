(ns contacts-app-2.app.footer.updating-creating
  (:require [contacts-app-2.shared :as sh]
            [phosphor.icons :as icons]))

(defn updating-creating
  []
  [:<>
   [:button.secondary-iconic-btn {:type "button"
                                  :on-click (fn []
                                              (reset!
                                               sh/state
                                               (sh/get-init-state!)))}
    (-> (icons/icon :phosphor.regular/arrow-left)
        (icons/render (sh/icon-widths :large)))]])
