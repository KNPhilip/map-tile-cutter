(ns map-tile-cutter.core
  (:use seesaw.core))

(defn make-section [name id]
  (let [stat-label (label name)
        stat-data (label :text "" :id id :size [100 :by 20])
        stat-panel (top-bottom-split stat-label stat-data)]
    stat-panel))

(defn -main
  [& args]
  (let [window (frame :title "Map Tile Cutter")
        input-section (make-section "Input" :input-options)
        cutting-options-section (make-section "Cutting Options" :cut-ops)
        export-section (make-section "Export" :export-options)]

    (config! window :content
      (vertical-panel :items
        (concat [input-section
                 cutting-options-section
                 export-section])))

    (pack! window)
    (show! window)
    ) "Hello World")
