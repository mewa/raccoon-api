(ns raccoon.ws.k8s.channel-test
  (:require [clojure.test :refer :all]
            [aleph.http :as http]
            [manifold.stream :as s]
            [clojure.core.async :refer [go <! >! <!! >!! chan thread close!]]
            [raccoon.ws.k8s.channel :as channel]))

(defn make-ws-handler [serve]
  (fn [req]
    (if-let [socket (try @(http/websocket-connection
                           req
                           {:headers {"Sec-WebSocket-Protocol" "channel.k8s.io"}})
                         (catch Exception e nil))]
      (serve socket))))

(defn ws-test [serverFn clientFn]
  (let [server (http/start-server (make-ws-handler serverFn)
                                  {:port 6000})]
    (<!! (thread
           (if-let [[in out] (channel/connect "ws://localhost:6000")]
             (do
               (clientFn in out)
               (close! in)
               (close! out)))))
    (.close server)))

(deftest test-channel-packers
  (testing "channel.k8s.io packers"

    (testing "pack"
      (is (=
           [0 65 66 67]
           (seq (channel/pack {:type :stdin :msg "ABC"}))))
      (is (=
           [1 65 66 67]
           (seq (channel/pack {:type :stdout :msg "ABC"})))))

    (testing "identity"
      (let [val {:type :stdin :msg "test123"}]
        (is (= val (channel/unpack (channel/pack val))))))))

(deftest test-channel-websockets
  (testing "channel.k8s.io WebSockets"

    (testing "channel-k8s-write reads correctly"
      (ws-test
       (fn [socket]
         @(s/put! socket (channel/pack {:type :stdout :msg "test"})))
       (fn [in out]
         (is (= (<!! out) {:type :stdout :msg "test"})))))

    (testing "channel-k8s-write writes correctly"
      (let [t {:type :stdout :msg "test"}
            end (chan)]
        (ws-test
         (fn [socket]
           (is (= (channel/unpack @(s/take! socket nil)) t))
           (close! end))
         (fn [in out]
           (>!! in t)
           (<!! end)))))))
