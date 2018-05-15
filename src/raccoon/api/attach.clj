(ns raccoon.api.attach
  (:require [cheshire.core :as json]
            [kubernetes.core :as core]
            [kubernetes.api.core-v- :as k8scorev]
            [clojure.string :as s]
            [raccoon.ws.k8s.channel :as ws]
            [clojure.core.async :refer [go <! >! <!! >!! chan close!]]))

(defn job-container-status [pod container]
  (some #(if (= container (:name %)) (:state %))
        (get-in (k8scorev/read-core-v1-namespaced-pod-status pod "default")
                [:status :containerStatuses])))

(defn successful? [{:keys [type msg]}]
  (and
   (= type :error)
   (= "Success" (get (json/decode msg) "status"))))

(defn attach
  "Attaches to POD and returns [IN OUT] channels for communication"
  [namespace pod container]
  (let [uri (str (s/replace-first (:base-url core/*api-context*) #"^http" "ws")
                 "/api/v1/namespaces/"
                 namespace
                 "/pods/"
                 pod
                 "/attach?stdout=1&stderr=1&stdin=1&container="
                 container)]
    (println "Waiting for container to become ready")
    (loop [status (job-container-status pod container)]
      (when (not (:running status))
        (Thread/sleep 100)
        (recur (job-container-status pod container))))
    (println "Container is ready")
    (println "Attaching to container")
    (let [stdin (chan)
          [in out] (ws/connect uri)]
      ;; Initial empty message
      (<!! out)
      (go
        (loop [msg (<! stdin)]
          (when msg
            (>! in {:type :stdin :msg (str msg "\n")})
            (recur (<! stdin))))
        (close! in))
      [stdin out])))
