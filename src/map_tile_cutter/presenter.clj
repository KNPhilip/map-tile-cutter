(ns map-tile-cutter.presenter
  (:use [seesaw.core]))

(defn input-section []
  (vertical-panel
    :items [(label :text "Image path:")
            (text)]))

(defn cutting-options-section []
  (vertical-panel
    :items [(label :text "Tile size:")
            (text)
            (label :text "Cuts:")
            (text)
            (label :text "Background color:")
            (text)
            (label :text "Extension:")
            (combobox :model ["PNG"
                              "JPEG"
                              "GIF"])]))

(defn export-section []
  (vertical-panel
    :items [(label    :text "Format:")
            (combobox :model ["z/x_y.png"
                              "z_x_y.png"
                              "z/x/y.png"])
            (label :text "Export path:")
            (text)]))

(defn mainframe-content []
  (let [input (input-section)
        cutting-options (cutting-options-section)
        export (export-section)]
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

(defn present-mainframe []
  (invoke-later
    (-> (make-mainframe)
        pack!
        (config! :size [800 :by 400])
        show!)))
