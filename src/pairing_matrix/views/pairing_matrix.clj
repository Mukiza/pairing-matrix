(ns pairing-matrix.views.pairing-matrix
  (:require [net.cgrand.enlive-html :as h])
  (:use [pairing-matrix.views.common :exclude [layout nav *template-dir*]]
        pairing-matrix.utils))

(defpage show "pairing-matrix/show.html"
  [params errors])
