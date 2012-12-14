(ns pairing-matrix.utils)

(defn str->int
  ([str]
     (Integer.  str))

  ([m & keys]
     (reduce
      (fn [m k]
        (if-let [val (k m)]
          (assoc m k (str->int val))
          m))
      m keys)))

(defn deserialize
  [m & ks]
  (reduce #(assoc % %2 (read-string (%2 %))) m ks))

(defmacro self-unless-fn
  [self fn otherwise]
  `(let [self# ~self]
     (if (~fn self#)
       ~otherwise
       self#)))

(defn merge-seq-of-maps
  [seq]
  (reduce merge
          ;; iterate over the keys, creating one map for each key
          (map (fn [key]
                 (hash-map key (reduce merge (map (fn [x] (get x key)) seq))))
               ;; get all they keys
               (into #{} (map #(first (keys %)) seq)))))

(def t
  [{:_id "50cb6d7dbc3057124c7ef011"
    "Daniel Higginbotham" {:Anil 3}}
   {:_id "50cb6dcbbc3057124c7ef012",
    "Daniel Higginbotham" {:Seth 1}}])