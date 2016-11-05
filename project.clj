(defproject solsort.hello-vr/hello-vr "0.0.1-SNAPSHOT"
  :license "MIT"

  :dependencies
  [[org.clojure/clojure "1.8.0"]
   [org.clojure/clojurescript "1.8.51"]
   [org.clojure/core.async "0.2.374"]
   [reagent "0.6.0-rc"]
   [binaryage/devtools "0.6.1"]]

  :plugins
  [[lein-cljsbuild "1.1.3"]
   [lein-ancient "0.6.8"]
   [lein-figwheel "0.5.0-2"]
   [lein-bikeshed "0.2.0"]
   [lein-kibit "0.1.2"]]

  :source-paths ["src/" "util/src/"]

  :clean-targets ^{:protect false}
  ["resources/public/out"
   "resources/public/index.js"
   "resources/public/tests.js"
   "resources/public/out-tests"
   "figwheel_server.log"
   "out/"
   "target/"]

  :profiles
  {:dev
   {:dependencies
    [[figwheel-sidecar "0.5.4-3"]
     [com.cemerick/piggieback "0.2.1"]]
    :plugins
    [[lein-figwheel "0.5.4-3"]
     [cider/cider-nrepl "0.13.0-SNAPSHOT"]]
    }}

  :cljsbuild
  {:builds
   [

    {:id "dev"
      :source-paths ["src/" "util/src/"]
      :figwheel
      {:websocket-host :js-client-host}
      :compiler
      {:main solsort.hello-vr.hello-vr
       :asset-path "out"
       :output-to "resources/public/index.js"
       :output-dir "resources/public/out"
       :source-map-timestamp true }}
    {:id "dist"
     :source-paths ["src" "util/src/"]
     :compiler
     {:output-to "index.js"
      :main solsort.hello-vr.hello-vr
      :externs ["util/externs.js"]
      :optimizations :advanced
      :pretty-print false}}
    ]}
)
