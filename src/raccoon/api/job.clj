(ns raccoon.api.job
  (:require [kubernetes.api.core :as k8score]
            [kubernetes.api.batch-v- :as k8sbatch]
            [kubernetes.api.core-v- :as k8scorev]
            [cheshire.core :refer :all]))

(defn new-job
  "Create job which executes CMD"
  [cmd]
  (let [job-name (str "k8s-job-" (java.util.UUID/randomUUID))]
    (k8sbatch/create-batch-v1-namespaced-job
     "default"
     {:metadata {:name job-name}
      :spec {:template {:spec {:containers [{:image "alpine"
                                             :name "k8s-job"
                                             :command ["sh" "-c" cmd]}]
                               :restartPolicy "Never"}}}})
    {:name job-name}))

(defn job-pods
  "Get pods for job ID and return a list of pod info"
  [id]
  (map (fn [v] {:name (get-in v [:metadata :name])
                :status (get-in v [:status :phase])})
       (:items (k8scorev/list-core-v1-namespaced-pod
                "default"
                {:label-selector (str "job-name=" id)}))))

(defn pod-logs
  "Get logs for pod described by PODINFO"
  [podinfo]
  (let [pod (:name podinfo)
        status (:status podinfo)]
    {:pod pod
     :status status
     :log (k8scorev/read-core-v1-namespaced-pod-log pod "default")}))

(defn get-job
  "Get information for job with given ID"
  [id]
  (let [pods (job-pods id)]
    (doall (map pod-logs pods))))
