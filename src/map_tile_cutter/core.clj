(ns map-tile-cutter.core
  (:use seesaw.core))

(defn make-section [name id]
  (let [stat-label (label name)
        stat-data (label :text "" :id id :size [100 :by 20])
        stat-panel (top-bottom-split stat-label stat-data)]
    stat-panel))

(defn mainframe-content []
  (let [input (make-section "Input" :input-options)
        cutting-options (make-section "Cutting Options" :cut-ops)
        export (make-section "Export" :export-options)]
    (border-panel
      :north input
      :center cutting-options
      :south export)))

(defn make-mainframe []
  (let [content (mainframe-content)]
    (frame
      :title "Map Tile Cutter"
      :content content
      :on-close :exit)))

(defn -main []
  (native!)
  (invoke-later
    (-> (make-mainframe)
        pack!
        (config! :size [800 :by 400])
        show!)))
