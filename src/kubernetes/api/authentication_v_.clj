(ns kubernetes.api.authentication-v-
  (:require [kubernetes.core :refer [call-api check-required-params with-collection-format]])
  (:import (java.io File)))

(defn create-authentication-v1-token-review-with-http-info
  "
  create a TokenReview"
  ([body ] (create-authentication-v1-token-review-with-http-info body nil))
  ([body {:keys [pretty ]}]
   (check-required-params body)
   (call-api "/apis/authentication.k8s.io/v1/tokenreviews" :post
             {:path-params   {}
              :header-params {}
              :query-params  {"pretty" pretty }
              :form-params   {}
              :body-param    body
              :content-types ["*/*"]
              :accepts       ["application/json" "application/yaml" "application/vnd.kubernetes.protobuf"]
              :auth-names    ["BearerToken"]})))

(defn create-authentication-v1-token-review
  "
  create a TokenReview"
  ([body ] (create-authentication-v1-token-review body nil))
  ([body optional-params]
   (:data (create-authentication-v1-token-review-with-http-info body optional-params))))

(defn get-authentication-v1-api-resources-with-http-info
  "
  get available resources"
  []
  (call-api "/apis/authentication.k8s.io/v1/" :get
            {:path-params   {}
             :header-params {}
             :query-params  {}
             :form-params   {}
             :content-types ["application/json" "application/yaml" "application/vnd.kubernetes.protobuf"]
             :accepts       ["application/json" "application/yaml" "application/vnd.kubernetes.protobuf"]
             :auth-names    ["BearerToken"]}))

(defn get-authentication-v1-api-resources
  "
  get available resources"
  []
  (:data (get-authentication-v1-api-resources-with-http-info)))

