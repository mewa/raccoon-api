(ns raccoon.api.job
  (:require [clojure.string :as s]
            [kubernetes.api.core :as k8score]
            [kubernetes.api.batch-v- :as k8sbatch]
            [kubernetes.api.core-v- :as k8scorev]
            [cheshire.core :refer :all]))

(defn create-job
  "Create job which executes CMD"
  [namespace spec]
  (let [job-name (str (java.util.UUID/randomUUID))
        cmd (s/join ";" (:steps spec))]
    ;; todo: add prefix join between commands
    (k8sbatch/create-batch-v1-namespaced-job
     namespace
     {:metadata {:name job-name}
      :spec {:template {:spec {:containers [{:image (or (:image spec) "ubuntu")
                                             :name "job"
                                             :command ["sh" "-c" cmd]}]
                               :restartPolicy "Never"}}}})
    {:name job-name}))

(defn job-pods
  "Get pods for job ID and return a list of pod info"
  [namespace id]
  (map (fn [v] {:name (get-in v [:metadata :name])
                :status (get-in v [:status :phase])})
       (:items (k8scorev/list-core-v1-namespaced-pod
                namespace
                {:label-selector (str "job-name=" id)}))))

(defn pod-logs
  "Get logs for pod described by PODINFO"
  [namespace podinfo]
  (let [pod (:name podinfo)
        status (:status podinfo)
        pending (= "Pending" status)
        response {:pod pod
                  :status status}]
    (if pending
      response
      (into response
            {:log (k8scorev/read-core-v1-namespaced-pod-log namespace pod)}))))

(defn get-job
  "Get information for job with given ID"
  [namespace id]
  (let [pods (job-pods namespace id)]
    (doall (map (partial pod-logs namespace) pods))))
