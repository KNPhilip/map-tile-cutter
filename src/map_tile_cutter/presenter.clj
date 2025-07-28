(ns map-tile-cutter.presenter
  (:use [seesaw.core]))

(defn input-section []
  (vertical-panel
    :items [(horizontal-panel
              :items [(label :text "Image path:")
                      (text)]
            )]))

(defn cutting-options-section []
  (vertical-panel
    :items [(horizontal-panel
              :items [(label    :text "Tile size:")
                      (text     :text "256")
                      (label    :text "Cuts:")
                      (text     :text "1")])
            (horizontal-panel
              :items [(label    :text "Background color:")
                      (text     :text "#00FFFFF")
                      (label    :text "Extension:")
                      (combobox :model ["PNG"
                                        "JPEG"
                                        "GIF"])])
            ]))

(defn submit-button []
  (horizontal-panel
    :items [(button :text "Submit")]))

(defn export-section []
  (vertical-panel
    :items [(horizontal-panel
              :items [(label    :text "Format:")
                      (combobox :model ["z/x_y.png"
                                        "z_x_y.png"
                                        "z/x/y.png"])])
            (horizontal-panel
              :items [(label :text "Export path:")
                      (text)])
            (submit-button)]))

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
