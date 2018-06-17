(ns raccoon.api.register
  (:require [clojure.string :as s]
            [kubernetes.api.core :as k8score]
            [kubernetes.api.core-v- :as k8scorev]
            [cheshire.core :refer :all]))

(defn create-user [name]
  (k8scorev/create-core-v1-namespace
   {:metadata {:name name}}))
