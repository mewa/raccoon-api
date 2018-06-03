(ns raccoon.util
  (:require [clojure.core.async :refer [<!!]]))


(defn exhaust
  "Takes function FN which is applied to messages from channel CHAN until it's closed"
  [chan fn]
  (doseq [msg (take-while #(not (nil? %)) (repeatedly #(<!! chan)))]
    (fn msg)))
