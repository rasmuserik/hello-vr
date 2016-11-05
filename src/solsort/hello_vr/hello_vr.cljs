(ns solsort.hello-vr.hello-vr
  (:require-macros
   [cljs.core.async.macros :refer [go go-loop alt!]]
   [reagent.ratom :as ratom :refer  [reaction]])
  (:require
   [cljs.reader]
   [solsort.toolbox.setup]
   [solsort.toolbox.appdb :refer [db db! db-async!]]
   [solsort.toolbox.ui :refer [input select]]
   [solsort.util
    :refer
    [<ajax <seq<! js-seq load-style! put!close!
     parse-json-or-nil log page-ready render dom->clj]]
   [reagent.core :as reagent :refer []]
   [clojure.string :as string :refer [replace split blank?]]
   [cljs.core.async :refer [>! <! chan put! take! timeout close! pipe]]))

;; code for taking down current model

(defn clear-scene []
(doall
 (for [o js/scene.children]
   (do (-> o (.-material) (.dispose))
       (-> o (.-geometry) (.dispose))
       (.remove js/scene o))
   )))
(clear-scene)

;; code for setting up the model

(let [cube (js/THREE.Mesh.
            (js/THREE.BoxGeometry. 0.5 0.5 0.5)
            (js/THREE.MeshNormalMaterial.))]
  (aset (.-position cube) "z" -1)
  (.add js/scene cube)
  )

;; misc

(.resetPose js/vrDisplay)
