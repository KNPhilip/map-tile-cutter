(ns map-tile-cutter.widgets
  (:import [javax.swing Box])
  (:use (seesaw [core] [font])))

(defn headline [title]
  (horizontal-panel
    :items [(label :text title
                   :font (font :name "SansSerif"
                               :style :bold
                               :size 15))]))

(defn horizontal-strut [n]
  (Box/createHorizontalStrut n))

(defn glue []
  (Box/createVerticalGlue))

(defn glued-section [section]
  (let [top-glue (glue)
        bottom-glue (glue)]
    (vertical-panel
      :items [top-glue
              section
              bottom-glue])))
