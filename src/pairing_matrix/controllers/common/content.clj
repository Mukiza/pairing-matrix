(ns pairing-matrix.controllers.common.content
  (:use pairing-matrix.models.permissions))

(defn updated
  [params]
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body (markdown/md-to-html-string (:content params))})

(defn update-fn
  [finder-fn record-update-fn]
  (fn [params]
    (let [id     (:id params)
          record (finder-fn id)]
      (protect
       (can-modify-record? record)
       (record-update-fn {:id id} params)
       (updated params)))))
