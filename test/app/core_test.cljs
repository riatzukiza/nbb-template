(ns app.core-test
  (:require [cljs.test :refer [deftest is testing async]]
            [domain.core :as domain]
            [law.schema :as schema]
            [malli.core :as m]))

(deftest greet-returns-valid-greeting
  (testing "happy path: well-formed name"
    (let [result (domain/greet "Ada")]
      (is (m/validate schema/Greeting result))
      (is (= "Hello, Ada!" (:message result))))))

(deftest greet-rejects-blank-name
  (testing "contract: blank name throws"
    (is (thrown? js/Error (domain/greet "")))))
