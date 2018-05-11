(ns raccoon.api.job
  (:require [clojure.string :as s]
            [kubernetes.api.core :as k8score]
            [kubernetes.api.batch-v- :as k8sbatch]
            [kubernetes.api.core-v- :as k8scorev]
            [cheshire.core :refer :all]))

(defn executor-spec [steps]
  {:image "raccoonci/executor:latest"
   :name "raccoon-executor"
   :imagePullPolicy "Always"
   :env [{:name "KUBERNETES_URI" :value "https://kubernetes.default.svc"}
         {:name "RACCOON_AUTH" :value "/var/run/secrets/kubernetes.io/serviceaccount/token"}
         {:name "RACCOON_EXECUTOR_STEPS" :value (pr-str steps)}
         {:name "RACCOON_POD_NAME" :valueFrom {:fieldRef {:fieldPath "metadata.name"}}}]})

(defn create-job
  "Create job which executes CMD"
  [namespace spec]
  (let [job-name (str (java.util.UUID/randomUUID))
        steps (:steps spec)]
    (k8sbatch/create-batch-v1-namespaced-job
     namespace
     {:metadata {:name job-name}
      :spec {:template {:spec {:containers [{:image (or (:image spec) "ubuntu")
                                             :name "job"
                                             :stdin true
                                             :command ["sh"]}
                                            (executor-spec steps)]
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
            {:log (k8scorev/read-core-v1-namespaced-pod-log pod namespace {:container "job"})}))))

(defn get-job
  "Get information for job with given ID"
  [namespace id]
  (let [pods (job-pods namespace id)]
    (doall (map (partial pod-logs namespace) pods))))
