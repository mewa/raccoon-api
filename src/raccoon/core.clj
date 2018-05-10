(ns raccoon.core
  (:gen-class)
  (:require [kubernetes.core :as core]
            [clojure.string :as s]
            [raccoon.api :as api]
            [ring.adapter.jetty :as jetty]))

(defn load-config []
  (merge {:base-url (System/getenv "KUBERNETES_URI")
          :debug (System/getenv "RACCOON_DEBUG_K8S")}
         (if-let [auth (System/getenv "RACCOON_AUTH")]
           {:auths {"BearerToken" (str "Bearer " (slurp auth))}})))

(defn run-k8s [f & args]
  (core/with-api-context (load-config) (apply f args)))

(defmacro in-k8s [body]
  `(core/with-api-context (load-config) ~body))

(defn try-k8s [f & args]
  (in-k8s
    (try (apply f args)
         (catch Exception e (println {:error (or (ex-data e) e)})))))

(defn -main
  [& args]
  (jetty/run-jetty (partial try-k8s api/route-handler) {:port 4000}))
