(ns flexiana.takehome-task.handler.scramble-test
  (:require [clojure.test :refer :all]
            [integrant.core :as ig]
            [ring.mock.request :as mock]))

(deftest scramble-handler-test
  (testing "Endpoint correctly answers if a first word's characters can form the second word."
    (let [handler       (ig/init-key :flexiana.takehome-task.handler.scramble/scramble {})
          input-outputs {["rekqodlw" "world"]             true
                         ["cedewaraaossoqqyt" "codewars"] true
                         ["katas"  "steak"]               false
                         ["z" "zeca"]                     false}]
      (run!
       (fn [[[source target] output]]
         (let [response (handler (-> (mock/request :get "/scramble")
                                     (mock/query-string {:source-str source
                                                         :target-str target})))]
           (is  (= 200 (:status response)) (= (:answer (:body response)) output))))
       input-outputs))))
