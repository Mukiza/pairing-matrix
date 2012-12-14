(ns pairing-matrix.controllers.pairing-matrix
  (:require [ring.util.response :as res]
            [net.cgrand.enlive-html :as h]
            [cheshire.core :refer :all]
            [pairing-matrix.views.pairing-matrix :as view]
            [pairing-matrix.data-mappers.pair-count :as pair-count]
            [pairing-matrix.data-mappers.person :as person])

  (:use [pairing-matrix.controllers.common :only (if-valid view)]
        pairing-matrix.controllers.common.content
        pairing-matrix.utils))

;; This should probably be in another controller. Its purpose is to
;; show the angular app, so it's not directly related to pairing-matrix
(defn show
  [params]
  (view
   view/show))

;; TODO somehow make use of ring-json-response
(defn show-json
  [params]
  {:status 200
   :headers {"Content-Type" "application/json"
             "Cache-Control" "no-cache"
             "Expires" "Thu, 01 Dec 1994 16:00:00 GMT"}
   :body (generate-string
          {:people (sort-by :name (person/all))
           :pairCounts (or (merge-seq-of-maps (map #(dissoc % :_id) (pair-count/all))) [])})})