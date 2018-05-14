(ns raccoon.api.attach-test
  (:require [clojure.test :refer :all]
            [clojure.core.async :refer [<!! >!!]]
            [kubernetes.core :as core]
            [kubernetes.api.core-v- :as k8scorev]
            [raccoon.api.job :as job]
            [raccoon.core :as raccoon]
            [raccoon.api.attach :as attach]))

(defn new-podspec [name]
  {:metadata {:name name}
   :spec {:containers [{:name "job"
                        :image "alpine:latest"
                        :stdin true}]
          :restartPolicy "Never"}})

(def ^:dynamic *pod-name* nil)

(defn with-pod [f]
  (binding [*pod-name* (str "test-pod-" (java.util.UUID/randomUUID))]
    (core/with-api-context {:base-url "http://localhost:8001"}
      (println "Creating pod: " *pod-name*)
      (k8scorev/create-core-v1-namespaced-pod "default" (new-podspec *pod-name*))
      (f)
      (println "Deleting pod: " *pod-name*)
      (k8scorev/delete-core-v1-namespaced-pod *pod-name* "default" {}))))

(use-fixtures :each with-pod)

(deftest attach-test
  (testing "API attach"
    (let [[in out] (attach/attach "default" *pod-name* "job")]
      (>!! in "echo -n success")
      (is
       (= (<!! out) {:type :stdout :msg "success"}))
      (>!! in "exit 0")
      (let [success (<!! out)]
        (is (attach/successful? success))))))
