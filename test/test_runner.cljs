(ns test-runner
  (:require [cljs.test :as t]
            [app.core-test]))

(defn -main [& _]
  (t/run-tests 'app.core-test))
