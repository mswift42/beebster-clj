;; routes.clj


(ns beebster-clj.routes
  (:use beebster-clj.core beebster-clj.views compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]))

(defroutes app-routes
  (route/resources "/")
  (GET "/" [] (index-page))
  (GET "/about" [] (about-page))
  (GET "/categories" [category] (category-page category)))


(def app
  (handler/site app-routes))


