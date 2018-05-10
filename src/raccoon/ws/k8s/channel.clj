;; This file includes implementation of channel.k8s.io WebSocket subprotocol
;; for mode details regarding the protocol see
;; https://github.com/kubernetes/kubernetes/blob/master/staging/src/k8s.io/apiserver/pkg/util/wsstream/conn.go#L34-L48
(ns raccoon.ws.k8s.channel
  (:require [aleph.http :as http]
            [kubernetes.core :as core]
            [manifold.stream :as s]
            [clojure.core.async :refer [go <! >! <!! >!! chan thread close!]]))

;; For respective channels refer to
;; https://github.com/kubernetes/kubernetes/blob/5e4625cad72e5b9dde2f8a51eed06c59a6d70869/pkg/kubelet/server/remotecommand/websocket.go#L29-L53
(def ^:const stream->channel
  {:stdin 0
   :stdout 1
   :stderr 2
   :error 3
   :resize 4})

(def ^:const channel->stream
  (clojure.set/map-invert stream->channel))

(defn unpack [val]
  (let [[type & msg] (seq val)
        type (channel->stream type)]
    {:type type :msg (apply str (map char msg))}))

(defn pack [{type :type msg :msg}]
  (let [msg (cons (stream->channel type) (seq msg))
        bytes (bytes (byte-array (map byte msg)))]
    bytes))

(defn connect
  "Connects to URI using WebSockets channel.k8s.io subprotocol and returns
  a VECTOR containing IN and OUT channels"
  [uri]
  (let [auth (get-in core/*api-context* [:auths "BearerToken"])
        opts {:sub-protocols "v4.channel.k8s.io"}
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
          @(s/put! ws (pack val))
          (recur (<! wsIn))))
      (close! wsIn))
    (go
      (loop [val @(s/take! ws nil)]
        (when (not (nil? val))
          (>! wsOut (unpack val))
          (recur @(s/take! ws nil))))
      (close! wsOut))
    [wsIn wsOut]))
