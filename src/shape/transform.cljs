(ns shape.transform
  "Pure, domain-agnostic data morphisms. No I/O, no domain policy.")

(defn kebab->title
  "'hello-world' → 'Hello World'"
  [s]
  (->> (clojure.string/split s #"-")
       (map clojure.string/capitalize)
       (clojure.string/join " ")))

(defn map-keys
  "Apply `f` to every key in map `m`."
  [f m]
  (into {} (map (fn [[k v]] [(f k) v])) m))

(defn deep-merge
  "Recursively merge maps `a` and `b`."
  [a b]
  (if (and (map? a) (map? b))
    (merge-with deep-merge a b)
    b))
