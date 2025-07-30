(ns map-tile-cutter.validator
  (:require [map-tile-cutter.widgets :refer :all]
            [map-tile-cutter.interactor :as interactor])
  (:use seesaw.core))

(defn validate-img-path [path]
  (if (empty? path)
    "Please fill out the image path."
    nil))

(defn validate-inputs [img-path tile-size cuts bg-color extension format exp-path]
  (or
    (validate-img-path img-path)))

(defn validate-and-submit [frame]
  (let [img-path  (get-widget-text frame :#image-path)
        tile-size (get-widget-text frame :#tile-size)
        cuts      (get-widget-text frame :#cuts)
        bg-color  (get-widget-text frame :#bg-color)
        extension (get-widget-text frame :#extension)
        format    (get-widget-text frame :#format)
        exp-path  (get-widget-text frame :#export-path)
        error     (validate-inputs img-path tile-size cuts bg-color
                                   extension format exp-path)]
    (if error
      (alert error)
      (interactor/cut-image img-path tile-size cuts bg-color
                            extension format exp-path))))
