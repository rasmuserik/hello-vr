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
         (.remove js/scene o)))))
(clear-scene)
(js/setInterval #(.play js/video) 2000)

;; code for setting up the model

;var cube = new THREE.Mesh(
;                      new THREE.BoxGeometry(0.5, 0.5, 0.5),
;                      new THREE.MeshNormalMaterial()
;                      )

(let [canvas (doto (js/document.createElement "canvas")
               (aset "width" 256)
               (aset "height" 128))
      frames #js []
      context (doto (.getContext canvas "2d")
                (aset "fillStyle" "blue")
                (.fillRect 20,20,100,60)
                (aset "fillStyle" "red")
                (aset "font" "30px sans-serif")
                (.fillText "loading..." 10 30)
                (.drawImage js/video 0 0 256 128))
      texture (doto (js/THREE.Texture. canvas)
                (aset "needsUpdate" true))
      material (js/THREE.MeshBasicMaterial. #js {:map texture
                                                 :side js/THREE.DoubleSide})
      tv (js/THREE.Mesh.
          (js/THREE.PlaneGeometry. (.-width canvas) (.-height canvas))
          material)
      cube (js/THREE.Mesh.
            (js/THREE.BoxGeometry. 0.5 0.5 0.5)
           ; (js/THREE.MeshNormalMaterial.)
            material)]
  (aset js/window "m" material)
  (.set (.-position tv) 0 0 -200)
  (aset (.-position cube) "z" -0.5)
  (.add js/scene tv)

; Emily wrote it
(let [canvas (doto (js/document.createElement "canvas")
      (aset "width" 400)
      (aset "height" 128))
    context (doto (.getContext canvas "2d")
              (aset "fillStyle" "white")
              (aset "font" "12px sans-serif")
              (.fillText "Developers: Emily Wu, RasmusErik Voel Jensen" 20 20))
    texture (doto (js/THREE.Texture. canvas)
              (aset "needsUpdate" true))
     material (js/THREE.MeshBasicMaterial. #js {:map texture
                                                 :side js/THREE.DoubleSide})
      credit (js/THREE.Mesh.
          (js/THREE.PlaneGeometry. (.-width canvas) (.-height canvas))
          material)
          
] 
(.set (.-position credit) 0 0 100 0)
 (.add js/scene credit)
)
; Emily wrote it
  ;(.add js/scene cube)
  (aset js/window "animate"
        (fn []
          (.dispose material)
          (.drawImage context js/video 0 0 256 128)
          (aset material "map"
                (doto (js/THREE.Texture. canvas) (aset "needsUpdate" true)))
          (aset material "needsUpdate" true)
          (aset tv "needsUpdate" true)
          (let [timepos (bit-or 0 (* 100 (/ js/video.currentTime (* .1 js/video.duration))))]
            (when (not (aget frames timepos))
              (let [canvas (doto (js/document.createElement "canvas")
                             (aset "width" 256)
                             (aset "height" 128))
                    context (doto (.getContext canvas "2d")
                              (.drawImage js/video 0 0 256 128))
                    texture (doto (js/THREE.Texture. canvas)
                              (aset "needsUpdate" true))
                    material (js/THREE.MeshBasicMaterial. #js {:map texture
                                                               :side js/THREE.DoubleSide})
                    frame (js/THREE.Mesh.
                           (js/THREE.PlaneGeometry. (.-width canvas) (.-height canvas))
                           material)]
                (aset frames timepos true)
                (aset js/window "m" material)
                (.set (.-position frame) 
                  (* 256 (- (rem timepos 10) 5)) 
                  (* 128 (- 5 (quot timepos 10))) 
                  -1000)
                (.add js/scene frame)))
  ;          (js/console.log timepos))
))))

(js/console.log "helo")
;; misc

(.resetPose js/vrDisplay)

(defn testFunc
  [para1]
  (js/console.log "test" para1)) (testFunc 123)

