(ns map-tile-cutter.widgets
  (:import [javax.swing Box])
  (:use [seesaw.core]))

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
