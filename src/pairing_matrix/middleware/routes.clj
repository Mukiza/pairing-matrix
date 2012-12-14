(ns pairing-matrix.middleware.routes
  (:require compojure.route
            compojure.handler
            [pairing-matrix.controllers.pairing-matrix :as pairing-matrix]
            [pairing-matrix.controllers.pair-counts :as pair-counts]
            [pairing-matrix.controllers.people :as people])
  (:use [compojure.core :only (GET PUT POST ANY defroutes)]))


(defmacro route
  [method path & handlers]
  `(~method ~path {params# :params}
            (->> params#
                ~@handlers)))

(defroutes routes
  (compojure.route/files "/" {:root "public"})

  ;; pairing matrix
  (route GET "/" pairing-matrix/show)
  (route GET "/pairing-matrix" pairing-matrix/show)
  (route GET "/pairing-matrix.json" pairing-matrix/show-json)

  (route POST "/people" people/create)

  (route POST "/pair-counts" pair-counts/update)

  (compojure.route/not-found "Sorry, there's nothing here."))
