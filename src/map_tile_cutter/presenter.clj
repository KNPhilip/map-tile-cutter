(ns map-tile-cutter.presenter
  (:require [map-tile-cutter.widgets :refer :all])
  (:require [map-tile-cutter.interactor :as interactor])
  (:use [seesaw.core]))

(defn input-section []
  (vertical-panel
    :items [(headline "Input")
            (separator)
            (horizontal-panel
              :items [(label :text "Image path:")
                      (text  :size [500 :by 30]
                             :id :image-path)]
            )]))

(defn cutting-options-section []
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
                      (text     :text "#00FFFFF"
                                :size [200 :by 30]
                                :id :bg-color)
                      (horizontal-strut 30)
                      (label    :text "Extension:")
                      (combobox :size [200 :by 30]
                                :id :extension
                                :model ["PNG"
                                        "JPEG"
                                        "GIF"])])
            (glue)]))

(defn export-section [submit-btn]
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
                      (text  :size [500 :by 30]
                             :id :export-path)])
            submit-btn]))

(defn submit-button [frame]
  (horizontal-panel
    :items [(button :text "Submit"
                    :listen [:action (fn [_]
                              (interactor/validate-and-submit frame))]
            )]))

(defn mainframe-content [mainframe]
  (let [input (input-section)
        cutting-options (cutting-options-section)
        submit-btn (submit-button mainframe)
        export (export-section submit-btn)]
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
