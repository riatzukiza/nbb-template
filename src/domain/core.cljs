(ns domain.core
  "Pure business logic. No I/O, no side effects."
  (:require [law.schema :as schema]
            [malli.core :as m]))

(defn greet
  "Returns a greeting map for `name`. Validates input/output via Malli."
  [name]
  (when-not (m/validate schema/NonBlankStr name)
    (throw (js/Error. (str "greet: invalid name " (pr-str name)))))
  (let [result {:message (str "Hello, " name "!")
                :name    name}]
    (when-not (m/validate schema/Greeting result)
      (throw (js/Error. "greet: postcondition failed")))
    result))
