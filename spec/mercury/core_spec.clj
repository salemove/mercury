(ns mercury.core-spec
  (:require [speclj.core :refer :all]
            [mercury.core :as mercury]
            [mercury.spec-helper :as helper]))

(def test-queue (atom {}))

(def response-message "OK")

(defn test-adapter [queue]
  {:deliver (fn [client service payload]
              (reset! queue {service payload})
              nil)
   :deliver-with-response (fn [client service payload]
                            (reset! queue {service payload})
                            response-message)})


(def test-client {:adapter (test-adapter test-queue)})

(def message "random message")
(def service :test_service)

(describe "Mercury interface"
  (it "delivers a message"
    (let [resp (mercury/deliver test-client service message)
          response-message (service @test-queue)]
      (should= message response-message)))

  (it "delivers a message and waits for response"
    (let [response (mercury/deliver-with-response test-client service message)]
      (should= response response-message))))
