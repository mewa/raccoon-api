(ns kubernetes.api.batch-v-beta-
  (:require [kubernetes.core :refer [call-api check-required-params with-collection-format]])
  (:import (java.io File)))

(defn create-batch-v1beta1-namespaced-cron-job-with-http-info
  "
  create a CronJob"
  ([namespace body ] (create-batch-v1beta1-namespaced-cron-job-with-http-info namespace body nil))
  ([namespace body {:keys [pretty ]}]
   (check-required-params namespace body)
   (call-api "/apis/batch/v1beta1/namespaces/{namespace}/cronjobs" :post
             {:path-params   {"namespace" namespace }
              :header-params {}
              :query-params  {"pretty" pretty }
              :form-params   {}
              :body-param    body
              :content-types ["*/*"]
              :accepts       ["application/json" "application/yaml" "application/vnd.kubernetes.protobuf"]
              :auth-names    ["BearerToken"]})))

(defn create-batch-v1beta1-namespaced-cron-job
  "
  create a CronJob"
  ([namespace body ] (create-batch-v1beta1-namespaced-cron-job namespace body nil))
  ([namespace body optional-params]
   (:data (create-batch-v1beta1-namespaced-cron-job-with-http-info namespace body optional-params))))

(defn delete-batch-v1beta1-collection-namespaced-cron-job-with-http-info
  "
  delete collection of CronJob"
  ([namespace ] (delete-batch-v1beta1-collection-namespaced-cron-job-with-http-info namespace nil))
  ([namespace {:keys [pretty continue field-selector include-uninitialized label-selector limit resource-version timeout-seconds watch ]}]
   (check-required-params namespace)
   (call-api "/apis/batch/v1beta1/namespaces/{namespace}/cronjobs" :delete
             {:path-params   {"namespace" namespace }
              :header-params {}
              :query-params  {"pretty" pretty "continue" continue "fieldSelector" field-selector "includeUninitialized" include-uninitialized "labelSelector" label-selector "limit" limit "resourceVersion" resource-version "timeoutSeconds" timeout-seconds "watch" watch }
              :form-params   {}
              :content-types ["*/*"]
              :accepts       ["application/json" "application/yaml" "application/vnd.kubernetes.protobuf"]
              :auth-names    ["BearerToken"]})))

(defn delete-batch-v1beta1-collection-namespaced-cron-job
  "
  delete collection of CronJob"
  ([namespace ] (delete-batch-v1beta1-collection-namespaced-cron-job namespace nil))
  ([namespace optional-params]
   (:data (delete-batch-v1beta1-collection-namespaced-cron-job-with-http-info namespace optional-params))))

(defn delete-batch-v1beta1-namespaced-cron-job-with-http-info
  "
  delete a CronJob"
  ([name namespace body ] (delete-batch-v1beta1-namespaced-cron-job-with-http-info name namespace body nil))
  ([name namespace body {:keys [pretty grace-period-seconds orphan-dependents propagation-policy ]}]
   (check-required-params name namespace body)
   (call-api "/apis/batch/v1beta1/namespaces/{namespace}/cronjobs/{name}" :delete
             {:path-params   {"name" name "namespace" namespace }
              :header-params {}
              :query-params  {"pretty" pretty "gracePeriodSeconds" grace-period-seconds "orphanDependents" orphan-dependents "propagationPolicy" propagation-policy }
              :form-params   {}
              :body-param    body
              :content-types ["*/*"]
              :accepts       ["application/json" "application/yaml" "application/vnd.kubernetes.protobuf"]
              :auth-names    ["BearerToken"]})))

(defn delete-batch-v1beta1-namespaced-cron-job
  "
  delete a CronJob"
  ([name namespace body ] (delete-batch-v1beta1-namespaced-cron-job name namespace body nil))
  ([name namespace body optional-params]
   (:data (delete-batch-v1beta1-namespaced-cron-job-with-http-info name namespace body optional-params))))

(defn get-batch-v1beta1-api-resources-with-http-info
  "
  get available resources"
  []
  (call-api "/apis/batch/v1beta1/" :get
            {:path-params   {}
             :header-params {}
             :query-params  {}
             :form-params   {}
             :content-types ["application/json" "application/yaml" "application/vnd.kubernetes.protobuf"]
             :accepts       ["application/json" "application/yaml" "application/vnd.kubernetes.protobuf"]
             :auth-names    ["BearerToken"]}))

(defn get-batch-v1beta1-api-resources
  "
  get available resources"
  []
  (:data (get-batch-v1beta1-api-resources-with-http-info)))

(defn list-batch-v1beta1-cron-job-for-all-namespaces-with-http-info
  "
  list or watch objects of kind CronJob"
  ([] (list-batch-v1beta1-cron-job-for-all-namespaces-with-http-info nil))
  ([{:keys [continue field-selector include-uninitialized label-selector limit pretty resource-version timeout-seconds watch ]}]
   (call-api "/apis/batch/v1beta1/cronjobs" :get
             {:path-params   {}
              :header-params {}
              :query-params  {"continue" continue "fieldSelector" field-selector "includeUninitialized" include-uninitialized "labelSelector" label-selector "limit" limit "pretty" pretty "resourceVersion" resource-version "timeoutSeconds" timeout-seconds "watch" watch }
              :form-params   {}
              :content-types ["*/*"]
              :accepts       ["application/json" "application/yaml" "application/vnd.kubernetes.protobuf" "application/json;stream=watch" "application/vnd.kubernetes.protobuf;stream=watch"]
              :auth-names    ["BearerToken"]})))

(defn list-batch-v1beta1-cron-job-for-all-namespaces
  "
  list or watch objects of kind CronJob"
  ([] (list-batch-v1beta1-cron-job-for-all-namespaces nil))
  ([optional-params]
   (:data (list-batch-v1beta1-cron-job-for-all-namespaces-with-http-info optional-params))))

(defn list-batch-v1beta1-namespaced-cron-job-with-http-info
  "
  list or watch objects of kind CronJob"
  ([namespace ] (list-batch-v1beta1-namespaced-cron-job-with-http-info namespace nil))
  ([namespace {:keys [pretty continue field-selector include-uninitialized label-selector limit resource-version timeout-seconds watch ]}]
   (check-required-params namespace)
   (call-api "/apis/batch/v1beta1/namespaces/{namespace}/cronjobs" :get
             {:path-params   {"namespace" namespace }
              :header-params {}
              :query-params  {"pretty" pretty "continue" continue "fieldSelector" field-selector "includeUninitialized" include-uninitialized "labelSelector" label-selector "limit" limit "resourceVersion" resource-version "timeoutSeconds" timeout-seconds "watch" watch }
              :form-params   {}
              :content-types ["*/*"]
              :accepts       ["application/json" "application/yaml" "application/vnd.kubernetes.protobuf" "application/json;stream=watch" "application/vnd.kubernetes.protobuf;stream=watch"]
              :auth-names    ["BearerToken"]})))

(defn list-batch-v1beta1-namespaced-cron-job
  "
  list or watch objects of kind CronJob"
  ([namespace ] (list-batch-v1beta1-namespaced-cron-job namespace nil))
  ([namespace optional-params]
   (:data (list-batch-v1beta1-namespaced-cron-job-with-http-info namespace optional-params))))

(defn patch-batch-v1beta1-namespaced-cron-job-with-http-info
  "
  partially update the specified CronJob"
  ([name namespace body ] (patch-batch-v1beta1-namespaced-cron-job-with-http-info name namespace body nil))
  ([name namespace body {:keys [pretty ]}]
   (check-required-params name namespace body)
   (call-api "/apis/batch/v1beta1/namespaces/{namespace}/cronjobs/{name}" :patch
             {:path-params   {"name" name "namespace" namespace }
              :header-params {}
              :query-params  {"pretty" pretty }
              :form-params   {}
              :body-param    body
              :content-types ["application/json-patch+json" "application/merge-patch+json" "application/strategic-merge-patch+json"]
              :accepts       ["application/json" "application/yaml" "application/vnd.kubernetes.protobuf"]
              :auth-names    ["BearerToken"]})))

(defn patch-batch-v1beta1-namespaced-cron-job
  "
  partially update the specified CronJob"
  ([name namespace body ] (patch-batch-v1beta1-namespaced-cron-job name namespace body nil))
  ([name namespace body optional-params]
   (:data (patch-batch-v1beta1-namespaced-cron-job-with-http-info name namespace body optional-params))))

(defn patch-batch-v1beta1-namespaced-cron-job-status-with-http-info
  "
  partially update status of the specified CronJob"
  ([name namespace body ] (patch-batch-v1beta1-namespaced-cron-job-status-with-http-info name namespace body nil))
  ([name namespace body {:keys [pretty ]}]
   (check-required-params name namespace body)
   (call-api "/apis/batch/v1beta1/namespaces/{namespace}/cronjobs/{name}/status" :patch
             {:path-params   {"name" name "namespace" namespace }
              :header-params {}
              :query-params  {"pretty" pretty }
              :form-params   {}
              :body-param    body
              :content-types ["application/json-patch+json" "application/merge-patch+json" "application/strategic-merge-patch+json"]
              :accepts       ["application/json" "application/yaml" "application/vnd.kubernetes.protobuf"]
              :auth-names    ["BearerToken"]})))

(defn patch-batch-v1beta1-namespaced-cron-job-status
  "
  partially update status of the specified CronJob"
  ([name namespace body ] (patch-batch-v1beta1-namespaced-cron-job-status name namespace body nil))
  ([name namespace body optional-params]
   (:data (patch-batch-v1beta1-namespaced-cron-job-status-with-http-info name namespace body optional-params))))

(defn read-batch-v1beta1-namespaced-cron-job-with-http-info
  "
  read the specified CronJob"
  ([name namespace ] (read-batch-v1beta1-namespaced-cron-job-with-http-info name namespace nil))
  ([name namespace {:keys [pretty exact export ]}]
   (check-required-params name namespace)
   (call-api "/apis/batch/v1beta1/namespaces/{namespace}/cronjobs/{name}" :get
             {:path-params   {"name" name "namespace" namespace }
              :header-params {}
              :query-params  {"pretty" pretty "exact" exact "export" export }
              :form-params   {}
              :content-types ["*/*"]
              :accepts       ["application/json" "application/yaml" "application/vnd.kubernetes.protobuf"]
              :auth-names    ["BearerToken"]})))

(defn read-batch-v1beta1-namespaced-cron-job
  "
  read the specified CronJob"
  ([name namespace ] (read-batch-v1beta1-namespaced-cron-job name namespace nil))
  ([name namespace optional-params]
   (:data (read-batch-v1beta1-namespaced-cron-job-with-http-info name namespace optional-params))))

(defn read-batch-v1beta1-namespaced-cron-job-status-with-http-info
  "
  read status of the specified CronJob"
  ([name namespace ] (read-batch-v1beta1-namespaced-cron-job-status-with-http-info name namespace nil))
  ([name namespace {:keys [pretty ]}]
   (check-required-params name namespace)
   (call-api "/apis/batch/v1beta1/namespaces/{namespace}/cronjobs/{name}/status" :get
             {:path-params   {"name" name "namespace" namespace }
              :header-params {}
              :query-params  {"pretty" pretty }
              :form-params   {}
              :content-types ["*/*"]
              :accepts       ["application/json" "application/yaml" "application/vnd.kubernetes.protobuf"]
              :auth-names    ["BearerToken"]})))

(defn read-batch-v1beta1-namespaced-cron-job-status
  "
  read status of the specified CronJob"
  ([name namespace ] (read-batch-v1beta1-namespaced-cron-job-status name namespace nil))
  ([name namespace optional-params]
   (:data (read-batch-v1beta1-namespaced-cron-job-status-with-http-info name namespace optional-params))))

(defn replace-batch-v1beta1-namespaced-cron-job-with-http-info
  "
  replace the specified CronJob"
  ([name namespace body ] (replace-batch-v1beta1-namespaced-cron-job-with-http-info name namespace body nil))
  ([name namespace body {:keys [pretty ]}]
   (check-required-params name namespace body)
   (call-api "/apis/batch/v1beta1/namespaces/{namespace}/cronjobs/{name}" :put
             {:path-params   {"name" name "namespace" namespace }
              :header-params {}
              :query-params  {"pretty" pretty }
              :form-params   {}
              :body-param    body
              :content-types ["*/*"]
              :accepts       ["application/json" "application/yaml" "application/vnd.kubernetes.protobuf"]
              :auth-names    ["BearerToken"]})))

(defn replace-batch-v1beta1-namespaced-cron-job
  "
  replace the specified CronJob"
  ([name namespace body ] (replace-batch-v1beta1-namespaced-cron-job name namespace body nil))
  ([name namespace body optional-params]
   (:data (replace-batch-v1beta1-namespaced-cron-job-with-http-info name namespace body optional-params))))

(defn replace-batch-v1beta1-namespaced-cron-job-status-with-http-info
  "
  replace status of the specified CronJob"
  ([name namespace body ] (replace-batch-v1beta1-namespaced-cron-job-status-with-http-info name namespace body nil))
  ([name namespace body {:keys [pretty ]}]
   (check-required-params name namespace body)
   (call-api "/apis/batch/v1beta1/namespaces/{namespace}/cronjobs/{name}/status" :put
             {:path-params   {"name" name "namespace" namespace }
              :header-params {}
              :query-params  {"pretty" pretty }
              :form-params   {}
              :body-param    body
              :content-types ["*/*"]
              :accepts       ["application/json" "application/yaml" "application/vnd.kubernetes.protobuf"]
              :auth-names    ["BearerToken"]})))

(defn replace-batch-v1beta1-namespaced-cron-job-status
  "
  replace status of the specified CronJob"
  ([name namespace body ] (replace-batch-v1beta1-namespaced-cron-job-status name namespace body nil))
  ([name namespace body optional-params]
   (:data (replace-batch-v1beta1-namespaced-cron-job-status-with-http-info name namespace body optional-params))))

(defn watch-batch-v1beta1-cron-job-list-for-all-namespaces-with-http-info
  "
  watch individual changes to a list of CronJob"
  ([] (watch-batch-v1beta1-cron-job-list-for-all-namespaces-with-http-info nil))
  ([{:keys [continue field-selector include-uninitialized label-selector limit pretty resource-version timeout-seconds watch ]}]
   (call-api "/apis/batch/v1beta1/watch/cronjobs" :get
             {:path-params   {}
              :header-params {}
              :query-params  {"continue" continue "fieldSelector" field-selector "includeUninitialized" include-uninitialized "labelSelector" label-selector "limit" limit "pretty" pretty "resourceVersion" resource-version "timeoutSeconds" timeout-seconds "watch" watch }
              :form-params   {}
              :content-types ["*/*"]
              :accepts       ["application/json" "application/yaml" "application/vnd.kubernetes.protobuf" "application/json;stream=watch" "application/vnd.kubernetes.protobuf;stream=watch"]
              :auth-names    ["BearerToken"]})))

(defn watch-batch-v1beta1-cron-job-list-for-all-namespaces
  "
  watch individual changes to a list of CronJob"
  ([] (watch-batch-v1beta1-cron-job-list-for-all-namespaces nil))
  ([optional-params]
   (:data (watch-batch-v1beta1-cron-job-list-for-all-namespaces-with-http-info optional-params))))

(defn watch-batch-v1beta1-namespaced-cron-job-with-http-info
  "
  watch changes to an object of kind CronJob"
  ([name namespace ] (watch-batch-v1beta1-namespaced-cron-job-with-http-info name namespace nil))
  ([name namespace {:keys [continue field-selector include-uninitialized label-selector limit pretty resource-version timeout-seconds watch ]}]
   (check-required-params name namespace)
   (call-api "/apis/batch/v1beta1/watch/namespaces/{namespace}/cronjobs/{name}" :get
             {:path-params   {"name" name "namespace" namespace }
              :header-params {}
              :query-params  {"continue" continue "fieldSelector" field-selector "includeUninitialized" include-uninitialized "labelSelector" label-selector "limit" limit "pretty" pretty "resourceVersion" resource-version "timeoutSeconds" timeout-seconds "watch" watch }
              :form-params   {}
              :content-types ["*/*"]
              :accepts       ["application/json" "application/yaml" "application/vnd.kubernetes.protobuf" "application/json;stream=watch" "application/vnd.kubernetes.protobuf;stream=watch"]
              :auth-names    ["BearerToken"]})))

(defn watch-batch-v1beta1-namespaced-cron-job
  "
  watch changes to an object of kind CronJob"
  ([name namespace ] (watch-batch-v1beta1-namespaced-cron-job name namespace nil))
  ([name namespace optional-params]
   (:data (watch-batch-v1beta1-namespaced-cron-job-with-http-info name namespace optional-params))))

(defn watch-batch-v1beta1-namespaced-cron-job-list-with-http-info
  "
  watch individual changes to a list of CronJob"
  ([namespace ] (watch-batch-v1beta1-namespaced-cron-job-list-with-http-info namespace nil))
  ([namespace {:keys [continue field-selector include-uninitialized label-selector limit pretty resource-version timeout-seconds watch ]}]
   (check-required-params namespace)
   (call-api "/apis/batch/v1beta1/watch/namespaces/{namespace}/cronjobs" :get
             {:path-params   {"namespace" namespace }
              :header-params {}
              :query-params  {"continue" continue "fieldSelector" field-selector "includeUninitialized" include-uninitialized "labelSelector" label-selector "limit" limit "pretty" pretty "resourceVersion" resource-version "timeoutSeconds" timeout-seconds "watch" watch }
              :form-params   {}
              :content-types ["*/*"]
              :accepts       ["application/json" "application/yaml" "application/vnd.kubernetes.protobuf" "application/json;stream=watch" "application/vnd.kubernetes.protobuf;stream=watch"]
              :auth-names    ["BearerToken"]})))

(defn watch-batch-v1beta1-namespaced-cron-job-list
  "
  watch individual changes to a list of CronJob"
  ([namespace ] (watch-batch-v1beta1-namespaced-cron-job-list namespace nil))
  ([namespace optional-params]
   (:data (watch-batch-v1beta1-namespaced-cron-job-list-with-http-info namespace optional-params))))

