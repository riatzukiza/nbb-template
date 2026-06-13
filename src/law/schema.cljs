(ns law.schema
  "Malli schemas — contracts, validators, guards. No I/O."
  (:require [malli.core :as m]))

(def NonBlankStr
  [:and :string [:fn {:error/message "must not be blank"}
                 #(not (clojure.string/blank? %))]])

(def Greeting
  [:map
   [:message NonBlankStr]
   [:name    NonBlankStr]])

(def CliArgs
  [:map
   [:name {:optional true} NonBlankStr]
   [:help {:optional true} :boolean]])

(defn validate!
  "Validate `value` against `schema`. Throws js/Error with explain on failure."
  [schema value]
  (when-not (m/validate schema value)
    (throw (js/Error. (str "Validation failed: "
                           (m/explain schema value)))))
  value)
