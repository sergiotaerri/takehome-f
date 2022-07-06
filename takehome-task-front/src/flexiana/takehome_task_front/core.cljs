(ns flexiana.takehome-task-front.core
  (:require
   [reagent.core :as r]
   [reagent.dom :as d]
   [tailwind-hiccup.core :refer [tw]]
   [flexiana.takehome-task-front.client :refer [set-scramble-word-containment]]))

(def target-contained? (r/atom :default))

(def source-str (r/atom ""))
(def target-str (r/atom ""))

;; -------------------------
;; Views

(defn containment-status [target-contained?]
  [:div (tw [:h-10 :p-10 :mb-4 :font-bold :whitespace-normal])
   [:h4 (case @target-contained?
          true "The left word CAN be used to spell the right one."
          false "The left word CAN'T be used to spell the right one."
          "Fill in the words to see if the right one can be spelled with the other.")]])

(defn button [button-text]
  [:button
   (tw [:w-40 :my-4 :font-bold :transition :duration-150 :ease-in-out :rounded-full :p-2]
       ["hover:bg-orange" "hover:bg-orange-200" "hover:shadow-lg" "focus:bg-orange-200" "focus:shadow-lg"
        "focus:outline-none" "focus:ring-0" "active:bg-orange-300" "active:shadow-lg"]
       {:type "submit"})
   button-text])

(defn input [value-atom placeholder]
  [:input (tw [:form-control :block :w-full :px-3 :py-1.5 :text-base :font-normal :text-gray-700 :bg-white :bg-clip-padding :border :border-solid
               :border-gray-300 :rounded :transition :ease-in-out :m-0]
              ["focus:text-gray-700" "focus:bg-white focus:border-blue-600" "focus:outline-none"]

              {:type "text"
               :value @value-atom
               :placeholder placeholder
               :on-change #(reset! value-atom (.-value (.-target %)))})])

(defn main-form []
  [:form {:on-submit (fn [e]
                       (.preventDefault e)
                       (set-scramble-word-containment target-contained? @source-str @target-str))}
   [:div (tw [:flex :gap-2])
    (input source-str "Source string")
    (input target-str "Target string")]
   [:div (tw [:flex :flex-row-reverse])
    (button "Check")]])

(defn main-page []
  [:section (tw [:h-screen :flex :items-center :justify-center :bg-gradient-to-b :from-slate-200 :to-yellow-100])
   [:div (tw [:w-max :flex :flex-col :justify-center])
    [:h1 (tw [:text-md :font-semibold :px-32]) "WORD SCRAMBLER CONTAINMENT CHECKER"]
    (containment-status target-contained?)
    (main-form)]])

;; -------------------------
;; Initialize app
(defn mount-root []
  (d/render [main-page] (.getElementById js/document "app")))

(defn ^:export init! []
  (mount-root))
