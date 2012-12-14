(ns pairing-matrix.data-mappers.pair-count
  (:require [pairing-matrix.data-mappers.db :as db]))

(db/add-db-reqs)
(let [collection-name "pair-count"]
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


(defn inc-count
  [p1, p2]
  (let [person1 (if (> 0 (compare p1 p2)) p2 p1)
        person2 (if (> 0 (compare p1 p2)) p1 p2)
        key (str  p1 "." p2)]
    
    (db-update {key {$exists true}}
               {$inc {key 1}}
               :upsert true
               :multi false)))

(defn update
  [_id, input]
  (db-update-by-id (ObjectId. _id)
                   {$set (dissoc input :_id)}))


