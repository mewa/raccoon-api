(ns raccoon.core-test
  (:require [clojure.test :refer :all]
            [kubernetes.core :as core]
            [raccoon.api :as api]
            [raccoon.api.job :as job]))

(defn test-config
  [f]
  (core/with-api-context {:base-url "http://localhost:8001"
                          :debug false}
    (f)))

(use-fixtures :each test-config)

(deftest test-create
  (testing "Creating job"
    (let [spec (api/str->spec (slurp "spec.json"))
          job (job/create-job "default" spec)]
      (is (contains? job :name)))))
