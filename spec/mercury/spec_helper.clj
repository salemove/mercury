(ns mercury.spec-helper
  (:require [langohr.core      :as rmq]
            [langohr.channel   :as lch]
            [langohr.queue     :as lq]
            [langohr.consumers :as lc]
            [langohr.basic     :as lb]))

(def ^{:const true}
  default-exchange-name "mercury")

(def connection (rmq/connect))
(def channel (lch/open connection))

(def result (atom nil))

(defn message-handler
  [ch {:keys [content-type delivery-tag type] :as meta} ^bytes payload]
  (reset! result payload))

(defn get-message [service-name]
  (lc/subscribe channel service-name message-handler {:auto-ack true})
  @result)
