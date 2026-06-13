(ns app.core
  "CLI entry point. Wires domain + infra + law together."
  (:require [babashka.cli :as cli]
            [domain.core :as domain]
            [law.schema :as schema]))

(def ^:private cli-spec
  {:name {:desc    "Your name"
          :alias   :n
          :default "World"}
   :help {:desc   "Print this help message"
          :alias  :h
          :coerce :boolean}})

(defn- print-help []
  (println (cli/format-opts {:spec cli-spec})))

(defn ^:async -main
  "Application entry point. Validates CLI args, then delegates to domain."
  [& args]
  (let [opts (cli/parse-opts args {:spec cli-spec})]
    (schema/validate! schema/CliArgs opts)
    (if (:help opts)
      (print-help)
      (-> opts :name domain/greet :message println))))

;; Guard: only run when loaded as a script, not required by another ns.
(when (= *file* (.-main js/module))
  (apply -main (.-argv js/process)))
