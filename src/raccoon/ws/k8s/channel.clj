(ns raccoon.ws.k8s.channel
  (:require [aleph.http :as http]
            [kubernetes.core :as core]
            [manifold.stream :as s]
            [clojure.core.async :refer [go <! >! <!! >!! chan thread close!]]))

(def ^:const stream->channel
  {:stdin 0
   :stdout 1
   :stderr 2})

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
          @(s/put! ws (pack val))
          (recur (<! wsIn)))))
    (go
      (loop [val @(s/take! ws nil)]
        (when (not (nil? val))
          (>! wsOut (unpack val))
          (recur @(s/take! ws nil))))
      (close! wsOut)
      (close! wsIn))
    [wsIn wsOut]))
