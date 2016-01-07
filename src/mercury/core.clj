(ns mercury.core)

(defn deliver [client service-name payload]
  (let [adapter (:adapter client)
        func    (:deliver adapter)]
    (func client service-name payload)))

(defn deliver-with-response [client service-name payload]
  (let [adapter (:adapter client)
        func    (:deliver-with-response adapter)]
    (func client service-name payload)))
