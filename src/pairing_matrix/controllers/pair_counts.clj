(ns pairing-matrix.controllers.pair-counts
  (:require [pairing-matrix.data-mappers.pair-count :as pair-count]))

(defn update
  [params]
  (println params)
  (pair-count/inc-count (:p1 params) (:p2 params))
  {:status 200})
