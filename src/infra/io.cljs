(ns infra.io
  "Effectful I/O layer: file system, stdout/stderr, fetch.
   No domain policy belongs here."
  (:require ["fs/promises" :as fsp]
            ["path" :as path]
            [shadow.cljs.modern :refer [js-await]]))

(defn ^:async read-text-file
  "Read file at `p` as a UTF-8 string."
  [p]
  (js-await (fsp/readFile p #js {:encoding "utf8"})))

(defn ^:async write-text-file!
  "Write `content` to `p`, creating parent directories as needed."
  [p content]
  (js-await (fsp/mkdir (path/dirname p) #js {:recursive true}))
  (js-await (fsp/writeFile p content #js {:encoding "utf8"})))

(defn ^:async fetch-json
  "Fetch `url` and parse body as JSON. Returns a Clojure map."
  [url]
  (let [resp (js-await (js/fetch url))]
    (-> (js-await (.json resp))
        (js->clj :keywordize-keys true))))
