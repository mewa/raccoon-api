(ns raccoon.api
  (:require
   [compojure.core :refer :all]
   [compojure.route :as route]
   [cheshire.core :refer [generate-string]]
   [raccoon.api.job :as job]))

(defn create-job [spec]
  (println "sepc: " spec)
  (generate-string (job/new-job spec)))

(defn get-job [id]
  (generate-string (job/get-job id)))

(defroutes route-handler
  (POST "/job" {body :body} (create-job (slurp body)))
  (GET "/job/:id" [id] (get-job id))
  (route/not-found (generate-string {:error "not found"})))
