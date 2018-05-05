(ns raccoon.api
  (:require
   [compojure.core :refer :all]
   [compojure.route :as route]
   [cheshire.core :as json]
   [raccoon.api.job :as job]))

(defn str->spec
  "Creates spec map from string S"
  [spec]
  (json/decode spec true))

(defn create-job
  [spec]
  (let [spec (str->spec spec)]
    (json/encode (job/create-job "default" spec))))

(defn get-job [id]
  (json/encode (job/get-job "default" id)))

(defroutes route-handler
  (POST "/job" {body :body} (create-job (slurp body)))
  (GET "/job/:id" [id] (get-job id))
  (route/not-found (json/encode {:error "not found"})))
