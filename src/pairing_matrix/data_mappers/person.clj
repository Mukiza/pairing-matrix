(ns pairing-matrix.data-mappers.person
  (:require [pairing-matrix.data-mappers.db :as db]))

(db/add-db-reqs)
(let [collection-name "person"]
  (db/add-db-fns collection-name)
  (db/add-finder-fns))

(defn- create-input->db-fields
  [input]
  (let [object-id (ObjectId.)]
    (merge input
           {:_id object-id})))

(defn create
  [input]
  (let [db-fields (create-input->db-fields input)]
    (db-insert db-fields)
    db-fields))

(defn destroy
  [_id]
  (db-destroy (ObjectId. _id)))

(defn update [_id, input]
  (db-update-by-id (ObjectId. _id)
                   {$set (dissoc input :_id)}))
