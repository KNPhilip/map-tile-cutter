(ns map-tile-cutter.presenter
  (:require [map-tile-cutter.widgets :refer :all])
  (:require [map-tile-cutter.interactor :as interactor])
  (:use [seesaw.core]))

(defn input-section [frame]
  (let [input-field (text :size [450 :by 30]
                          :id :image-path)]
    (vertical-panel
      :items [(headline "Input")
              (separator)
              (horizontal-panel
                :items [(label :text "Image path:")
                        input-field
                        (browse-file-btn frame input-field)]
              )])))

(defn cutting-options-section [frame]
  (let [color-field (text :text "#00FFFFF"
                          :size [200 :by 30]
                          :id :bg-color)]
    (vertical-panel
      :items [(separator)
              (headline "Cutting Options")
              (separator)
              (glue)
              (horizontal-panel
                :items [(label    :text "Tile size:")
                        (text     :text "256"
                                  :size [200 :by 30]
                                  :id :tile-size)
                        (horizontal-strut 30)
                        (label    :text "Cuts:")
                        (text     :text "1"
                                  :size [200 :by 30]
                                  :id :cuts)])
              (glue)
              (horizontal-panel
                :items [(label    :text "Background color:")
                        color-field
                        (color-chooser-btn frame color-field)
                        (horizontal-strut 30)
                        (label    :text "Extension:")
                        (combobox :size [200 :by 30]
                                  :id :extension
                                  :model ["PNG"
                                          "JPG"
                                          "GIF"])])
              (glue)])))

(defn submit-button [frame]
  (horizontal-panel
    :items [(button :text "Submit"
                    :listen [:action (fn [_]
                              (interactor/validate-and-submit frame))]
            )]))

(defn export-section [frame]
  (let [export-field (text :size [450 :by 30]
                           :id :export-path)]
    (vertical-panel
      :items [(separator)
              (headline "Export")
              (separator)
              (horizontal-panel
                :items [(label    :text "Format:")
                        (combobox :size [200 :by 30]
                                  :id :format
                                  :model ["z/x_y.png"
                                          "z_x_y.png"
                                          "z/x/y.png"])])
              (horizontal-panel
                :items [(label :text "Export path:")
                        export-field
                        (browse-dir-btn frame export-field)])
              (submit-button frame)])))

(defn mainframe-content [mainframe]
  (let [input (input-section mainframe)
        cutting-options (cutting-options-section mainframe)
        export (export-section mainframe)]
    (border-panel
      :north input
      :center (glued-section cutting-options)
      :south export)))

(defn make-mainframe []
  (let [mainframe (frame
                    :title "Map Tile Cutter"
                    :on-close :exit)
        content (mainframe-content mainframe)]
    (config! mainframe :content content)
    mainframe))

(defn present-mainframe []
  (invoke-later
    (-> (make-mainframe)
        pack!
        (config! :size [800 :by 400])
        show!)))
