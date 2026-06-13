# nbb-template

> Node Babashka (nbb) project template — fast-start ClojureScript scripting on
> Node.js with `^:async` / `js-await` native async, four-layer namespace
> architecture, Malli contracts at every boundary, and clj-kondo enforcement
> from day one.

[![CI](https://github.com/riatzukiza/nbb-template/actions/workflows/ci.yml/badge.svg)](https://github.com/riatzukiza/nbb-template/actions/workflows/ci.yml)

## Architecture

| Layer | Namespace prefix | Rule |
|---|---|---|
| Business logic | `domain.*`  | Pure. No I/O. No side effects. |
| Effectful I/O  | `infra.*`   | fs, fetch, process exec. No domain policy. |
| Data morphisms | `shape.*`   | Pure, domain-agnostic transformations. |
| Contracts      | `law.*`     | Malli schemas and `validate!`. No I/O. |

Every boundary-crossing function calls `law/validate!` before trusting inputs.

## Quick Start

```bash
npm install
npx nbb -m app.core --name Clojure
# => Hello, Clojure!
```

## Scripts

```
npm test        # run all tests via nbb
npm run lint    # clj-kondo
npm run dev     # run with --watch (hot reload on save)
npm run repl    # nbb nREPL on port 1667
```

## Project Structure

```
.
├── package.json              # nbb dep + npm scripts
├── nbb.edn                   # classpath config
├── .clj-kondo/config.edn     # enforced lint rules
├── bin/run.js                # ESM entry shim
├── src/
│   ├── domain/core.cljs      # pure business logic
│   ├── law/schema.cljs       # Malli schemas + validate!
│   ├── shape/transform.cljs  # pure data morphisms
│   ├── infra/io.cljs         # effectful I/O (^:async)
│   └── app/core.cljs         # ^:async -main + CLI dispatch
├── test/
│   ├── app/core_test.cljs
│   └── test_runner.cljs
└── .github/workflows/ci.yml
```

## Async Pattern

All I/O functions use native `^:async` + `js-await` via `shadow.cljs.modern`:

```clojure
(ns infra.io
  (:require [shadow.cljs.modern :refer [js-await]]))

(defn ^:async fetch-json [url]
  (let [resp (js-await (js/fetch url))]
    (-> (js-await (.json resp))
        (js->clj :keywordize-keys true))))
```

No `.then` chains. No `core.async` channels. No Promise wrappers.

## Extending

- **New CLI subcommands** — add entries to `app.core/cli-spec` and dispatch in `-main`
- **New domain models** — add Malli schemas to `law.schema`, logic to `domain.*`
- **npm packages** — `npm install <pkg>` then `(:require ["pkg" :as p])`
- **npm packages in nbb.edn** — use `:require-macros` for compile-time macros

## Lint Rules (enforced)

- `:missing-docstring` — all public vars require docstrings
- `:unused-value` — no silent discards
- `:shadowed-var` — no rebinding of outer names
- `:unsorted-required-namespaces` — canonical `:require` order
- No `(:refer :all)` — explicit imports only

## License

The Unlicense — public domain.
