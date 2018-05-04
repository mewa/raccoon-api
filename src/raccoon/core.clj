(ns raccoon.core
  (:gen-class)
  (:require [kubernetes.core :as core]
            [clojure.string :as s]
            [raccoon.api :as api]
            [ring.adapter.jetty :as jetty]))

(defn incluster-config []
  {:base-url "https://kubernetes.default.svc"
   :debug true
   :auths {"BearerToken" (str "Bearer " (slurp "/var/run/secrets/kubernetes.io/serviceaccount/token"))}})

(defn test-config []
  {:base-url "http://localhost:8001"})

(defn run-k8s [f & args]
  (core/with-api-context (test-config)
    (try (apply f args)
         (catch Exception e (println {:error (or (ex-data e) e)})))))

(defn -main
  [& args]
  (jetty/run-jetty (partial run-k8s api/route-handler) {:port 4000}))
