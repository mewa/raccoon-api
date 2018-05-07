(ns raccoon.api.attach
  (:require [aleph.http :as http]
            [kubernetes.core :as core]
            [manifold.stream :as s]
            [clojure.core.async :refer [go <! >! <!! >!! chan thread]]))

(defn ws-k8s-read [val]
  (let [[type & msg] (seq val)
        type (cond
               (= 0 type) :stdin
               (= 1 type) :stdout
               (= 2 type) :stderr)]
    {:type type :msg (apply str (map char msg))}))

(defn ws-k8s-write [val]
  (let [msg (cons 0 (seq val))
        bytes (bytes (byte-array (map byte msg)))]
    bytes))

(defn attach
  "Attaches to POD and returns channels for communication"
  [namespace pod]
  (let [auth (get-in ["BearerToken" :auths] core/*api-context*)
        ws @(http/websocket-client
             {:headers {"Authorization" auth}
              :sub-protocols "channel.k8s.io"})
        podIn (chan)
        podOut (chan)]
    (go
      (thread
        (loop [val (<!! podIn nil)]
          (when (not (nil? val))
            (s/put! ws (ws-k8s-write val))
            (recur (<!! podIn nil)))))
      (thread
        (loop [val @(s/take! ws nil)]
          (when (not (nil? val))
            (>!! podOut val)
            (recur @(s/take! ws nil))))))
    {:in podIn :out podOut}))
