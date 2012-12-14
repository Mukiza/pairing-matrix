(ns pairing-matrix.server
  (:use [ring.adapter.jetty :only (run-jetty)]
        ring.middleware.params
        ring.middleware.keyword-params
        ring.middleware.nested-params
        ring.middleware.session
        ring.middleware.json
        [pairing-matrix.middleware.routes :only (routes)]))

; The ring app
(def app
  (-> routes
      wrap-keyword-params
      wrap-json-params
      wrap-nested-params
      wrap-params))

(defn -main
  "Start the jetty server"
  []
  (let [uri (get (System/getenv) "MONGOLAB_URI" "mongodb://127.0.0.1/pairing-matrix-dev")]
    (monger.core/connect-via-uri! uri))
  (run-jetty #'app {:port (Integer. (get (System/getenv) "PORT" 8080)) :join? false}))
