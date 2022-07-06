(ns flexiana.takehome-task-front.client
  (:require [cljs-http.client :as http]
            [cljs.core.async :refer [<!]])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(goog-define backend-uri "http://localhost:3000")

(defn set-scramble-word-containment [atom source-str target-str]
  (go
    (let [{{:keys [answer]} :body} (<! (http/get (str backend-uri "/scramble")
                                                 {:with-credentials? false
                                                  :query-params {:source-str source-str
                                                                 :target-str target-str}}))]
      (reset! atom answer))))
