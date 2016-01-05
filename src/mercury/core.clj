(ns mercury.core)

(defn deliver [client service-name payload]
  (let [adapter (:adapter client)
        func    (:deliver adapter)]
    (func client service-name payload)))
