(ns raccoon.executor.core
  (:gen-class)
  (:require [kubernetes.core :as core]
            [cheshire.core :refer :all]
            [raccoon.api.attach :as attach]
            [raccoon.core :as raccoon]
            [clojure.core.async :refer [go timeout <! >! <!! >!! thread close! alts!!]]))

(defn -main
  [& args]
  (println "Starting raccoon-executor")
  (raccoon/in-k8s
   (try
     (let [steps (read-string (System/getenv "RACCOON_EXECUTOR_STEPS"))
           pod (System/getenv "RACCOON_POD_NAME")]
       (println "Performing steps:" steps)
       (let [[in out] (attach/attach "default" pod "job")]
         (doseq [step steps]
           (println "Executing" step (>!! in step))
           (println "Out" (<!! out)))
         (close! in)
         (close! out)))
     (catch Exception e (do
                          (println {:error (or (ex-data e) e)})
                          (System/exit 1))))))
