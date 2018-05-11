(ns raccoon.api.job-test
  (:require [clojure.test :refer :all]
            [kubernetes.core :as core]
            [raccoon.core :as raccoon]
            [raccoon.api :as api]
            [raccoon.api.job :as job]))

(defn test-config
  [f]
  (raccoon/in-k8s
    (f)))

(use-fixtures :each test-config)

(def spec {:image "alpine:latest"
           :steps ["echo success" "date" "apk add --no-cache curl" "curl google.com" "echo x" "exit 1"]})

(deftest test-create
  (testing "Creating job"
    (let [job (job/create-job "default" spec)]
      (is (contains? job :name)))))
