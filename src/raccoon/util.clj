(ns raccoon.util
  (:require [clojure.core.async :refer [<!! <!]]))

(defn take-non-nil [seq]
  (take-while #(not (nil? %)) seq))

(defn exhaust!!
  "Takes function FN which is applied to messages from channel CHAN until it's closed"
  [chan fn]
  (doseq [msg (take-non-nil (repeatedly #(<!! chan)))]
    (fn msg)))

(defn exhaust!
  "Takes function FN which is applied to messages from channel CHAN until it's closed"
  [chan fn]
  (doseq [msg (take-non-nil (repeatedly #(<! chan)))]
    (fn msg)))
