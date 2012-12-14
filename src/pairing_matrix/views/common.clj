(ns pairing-matrix.views.common
  (:require [net.cgrand.enlive-html :as h]
            markdown)
  (use pairing-matrix.utils))

(defonce template-dir "pairing_matrix/templates/")

(h/deftemplate layout (str template-dir "index.html")
  [html]
  [:html] (h/substitute html))

;; Need to come up with better name
;; Bundles together some defsnippet commonalities for user with the
;; layout template
;;
;; TODO destructuring doesn't work in argnames
(defmacro defpage
  [name file [& argnames] & body]
  `(do
     (h/defsnippet ~(symbol (str name "*")) (str template-dir ~file) [:html]
       [~@argnames]
       ~@body)
     (defn ~name
       [{:keys [~@argnames]}]
       (layout (~(symbol (str name "*")) ~@argnames)))))

;; Path stuff
(defn path
  [record url-string prefix & suffixes]
  (str "/"
       (apply str
              (interpose
               "/"
               (into [prefix (or (url-string record) record)] suffixes)))))

(defmacro create-path-fns
  [record-type url-id & suffixes]
  `(do
     ~@(map
        (fn [suffix]
          (let [fn-name-suffix (if suffix
                                 (str "-" suffix "-path")
                                 "-path")
                x (gensym)]
            `(defn ~(symbol (str record-type fn-name-suffix))
               [~x]
               ;; TODO is there a briefer way of doing this?
               ~(if suffix
                  `(path ~x ~url-id ~(str record-type "s") ~suffix)
                  `(path ~x ~url-id ~(str record-type "s"))))))
        (conj suffixes nil))))

(create-path-fns "user" :username "edit")
(create-path-fns "pairing-matrix" :id "edit" "destroy")

(defn set-path
  [x fun]
  (h/set-attr :href (fun x)))

(defn md-content
  [content]
  (let [content (or (:content content) content)]
    (h/html-content (markdown/md-to-html-string content))))

(defn format-error-messages
  [errors]
  (str "<ul>" (apply str (map #(str "<li>" % "</li>") errors)) "</ul>"))

(defn error-content
  [errors, key]
  (if-let [messages (get errors key)]
    (h/html-content (format-error-messages messages))))

(defmacro keep-when
  [condition]
  `(when ~condition
     #(identity %)))

