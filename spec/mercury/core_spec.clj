(ns mercury.core-spec
  (:require [speclj.core :refer :all]
            [mercury.core :as mercury]
            [mercury.spec-helper :as helper]))

(def test-queue (atom {}))

(defn test-adapter [queue]
  {:deliver (fn [client service payload]
              (reset! queue {service payload})
              nil)})


(def test-client {:adapter (test-adapter test-queue)})

(describe "Mercury"
  (it "delivers a message"
      (let [message "random message"
            service :test_service
            resp (mercury/deliver test-client service msg)
            response-message (service @test-queue)]
      (should= message response-message))))
