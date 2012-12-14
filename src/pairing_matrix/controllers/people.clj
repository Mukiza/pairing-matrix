(ns pairing-matrix.controllers.people
  (:require [pairing-matrix.data-mappers.person :as person]))

(defn create
  [params]
  (person/create params)
  {:status 200})