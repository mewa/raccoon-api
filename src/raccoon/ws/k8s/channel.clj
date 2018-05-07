(ns raccoon.ws.k8s.channel
  (:require [aleph.http :as http]
            [kubernetes.core :as core]
            [manifold.stream :as s]
            [clojure.core.async :refer [go <! >! <!! >!! chan thread close!]]))

(defn channel-k8s-read [val]
  (let [[type & msg] (seq val)
        type (cond
               (= 0 type) :stdin
               (= 1 type) :stdout
               (= 2 type) :stderr)]
    {:type type :msg (apply str (map char msg))}))

(defn channel-k8s-write [val]
  (let [msg (cons 0 (seq val))
        bytes (bytes (byte-array (map byte msg)))]
    bytes))

(defn connect
  "Connects to URI using WebSockets channel.k8s.io subprotocol and returns
  a VECTOR containing IN and OUT channels"
  [uri]
  (let [auth (get-in ["BearerToken" :auths] core/*api-context*)
        opts {:sub-protocols "channel.k8s.io"}
        ws @(http/websocket-client
             uri
             (if auth
               (assoc opts :headers {"Authorization" auth})
               opts))
        wsIn (chan)
        wsOut (chan)]
    (go
      (loop [val (<! wsIn)]
        (when (not (nil? val))
          (println @(s/put! ws (channel-k8s-write val)))
          (recur (<! wsIn)))))
    (go
      (loop [val @(s/take! ws nil)]
        (when (not (nil? val))
          (>! wsOut (channel-k8s-read val))
          (recur @(s/take! ws nil))))
      (close! wsOut)
      (close! wsIn))
    [wsIn wsOut]))
