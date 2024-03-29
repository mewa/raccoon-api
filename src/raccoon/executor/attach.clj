(ns raccoon.executor.attach
  (:gen-class)
  (:require [kubernetes.core :as core]
            [cheshire.core :refer :all]
            [raccoon.api.attach :as attach]
            [raccoon.core :as raccoon]
            [raccoon.util :refer :all]
            [clojure.core.async :refer [go timeout <! >! <!! >!! thread close! chan]]))

(def eo-pipefail (str "set -o pipefail 2>/dev/null;" "set -e;"))

(defn wrap-shell [cmd]
  (str "sh -c '"
       ;; Enable pipefail if underlying shell supports it
       eo-pipefail
       cmd "'"))

(defn -main
  [& args]
  (println "Starting raccoon-executor")
  (raccoon/in-k8s
   (try
     (let [steps (read-string (System/getenv "RACCOON_EXECUTOR_STEPS"))
           pod (System/getenv "RACCOON_POD_NAME")]
       (println "Performing steps:" steps)
       (let [[in out] (attach/attach "default" pod "job")
             end (chan)]
         (go
           (exhaust! out (fn [msg]
                           (if (= "Success" (msg "status"))
                             (println "Success:" msg))))
           (close! end))
         (doseq [step (take-non-nil steps)]
           (let [step (wrap-shell step)]
             (println "Executing" step (>!! in step))
             (>!! in "LAST_CODE=$? && [ ! \"$LAST_CODE\" -eq 0 ] && echo Process exited with code $LAST_CODE && exit 0"))))
         (>!! in "exit 0")
         (close! in)
         (<!! end)
         (close! out)))
     (catch Exception e (do
                          (println {:error (or (ex-data e) e)})
                          (System/exit 1))))))
