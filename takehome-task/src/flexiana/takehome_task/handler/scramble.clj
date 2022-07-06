(ns flexiana.takehome-task.handler.scramble
  (:require [compojure.core :refer [context GET]]
            [compojure.route :as route]
            [clojure.spec.alpha :as s]
            [ring.util.response :refer [response]]
            [integrant.core :as ig]))

(defn no-negatives?
  "If there are any negative numbers in the coll, return false"
  [coll]
  (not (some neg? (vals coll))))

(s/fdef scramble?
  :args (s/cat :source-str string? :target-str string?)
  :ret boolean?)

(defn scramble?
  "Extracts the relevant frequencies decrementing for every character found in the `str2`. If any character has a negative score, then it means the `str2` word can't be made with the `str1` characters."
  [str1 str2]
  (let [relevant-char-freq (select-keys (frequencies str1) (seq str2))]
    (->> str2
         (reduce (fn [char-freq-acc character-k]
                   (update char-freq-acc character-k (fn [qnt]
                                                       (if qnt (dec qnt) -1)))) relevant-char-freq)
         no-negatives?)))

(defn wrap-cors
  "Wrap the server response in a Control-Allow-Origin Header to
  allow connections from the web app."
  [handler]
  (fn [request]
    (let [response (handler request)]
      (-> response
          (assoc-in [:headers "Access-Control-Allow-Origin"] "*")
          (assoc-in [:headers "Access-Control-Allow-Headers"] "x-requested-with")
          (assoc-in [:headers "Access-Control-Allow-Methods"] "*")))))

(defmethod ig/init-key ::scramble [_ _]
  (->
   (context "/" []
     (GET "/scramble" [source-str target-str]
       (response {:answer (scramble? source-str target-str)}))
     (route/not-found "Not Found"))
   wrap-cors))
