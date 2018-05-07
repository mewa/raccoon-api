(ns raccoon.api.attach
  (:require [kubernetes.core :as core]
            [clojure.string :as s]
            [raccoon.ws.k8s.channel :as ws]
            [clojure.core.async :refer [go <! >! <!! >!! chan thread]]))

(defn attach
  "Attaches to POD and returns channels for communication"
  [namespace pod]
  (let [uri (str (s/replace-first (:base-url core/*api-context*) #"^http" "ws")
                 "/api/v1/namespaces/"
                 namespace
                 "/pods/"
                 pod
                 "/attach?stdout=1&stderr=1&stdin=1")]
    (ws/connect uri)))
