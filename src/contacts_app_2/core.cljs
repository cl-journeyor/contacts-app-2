(ns contacts-app-2.core
  (:require
   [contacts-app-2.app :refer [app]]
   [reagent.dom.client :as rdc]))

;; -------------------------
;; Initialize app

(defn loadSampleData
  []
  (let [data [{:id 1
               :name "Drake Turing"
               :phone "+1 202-918-2132"
               :email "drturing2339@gmail.com"
               :groups ["family"]}
              {:id 2
               :name "Joana Gómez"
               :phone "+52 237 254 6246"
               :email "joana.gomez2250@gmail.com"
               :groups ["friends"]}
              {:id 3
               :name "Stephanie Wells"
               :phone "+1 424-555-9772"
               :email "stwell55@outlook.com"
               :groups ["work"]}
              {:id 4
               :name "Erick Boor"
               :phone "+1 472-282-0607"
               :email "l20boor@gmail.com"
               :groups ["college", "friends"]}
              {:id 5
               :name "Ricky Paul"
               :phone "+1 505-644-9993"
               :email "ricky21@yahoo.com"
               :groups ["work", "friends"]}
              {:id 6
               :name "Karen Morgan"
               :phone "+1 505-622-0140"
               :email ""
               :groups ["college"]}
              {:id 7
               :name "Elena Rojas"
               :phone "+52 657 154 2389"
               :email ""
               :groups ["work"]}
              {:id 8
               :name "Emmanuel"
               :phone "+1 505-646-9333"
               :email "emmanuel.joel@gmail.com"
               :groups ["friends"]}
              {:id 9
               :name "Peter Brown"
               :phone "+1 505-646-8729"
               :email "fnalobj@yahoo.com"
               :groups ["church"]}]]
    (.setItem js/localStorage "contacts" (str data))))

(defn mount-root []
  (-> (.getElementById js/document "app")
      rdc/create-root
      (rdc/render [app])))

(defn ^:export init! []
  (mount-root))
