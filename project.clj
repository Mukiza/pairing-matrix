(defproject pairing-matrix "0.1.0-SNAPSHOT"
  :description "an angular app"
  :url "http://pairing-matrix.heroku.com"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [ring "1.1.6"]
                 [compojure "1.1.3"]
                 [enlive/enlive "1.0.1"]
                 [lein-ring "0.7.5"]
                 [markdown-clj "0.9.9"]
                 [cheshire "5.0.1"]
                 [ring/ring-json "0.1.2"]
                 [com.novemberain/monger "1.4.1"]]
  
  :main pairing-matrix.server)


