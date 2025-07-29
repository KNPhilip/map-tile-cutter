(ns map-tile-cutter.interactor
  (:use seesaw.core))

(defn get-field-text [frame id]
  (partial (config
    (select frame [id]) :text)))

(defn cut-tiles [img-path tile-size cuts bg-color extension format exp-path]
  (alert (str "img-path: " img-path
              "\ntile-size: " tile-size
              "\ncuts: " cuts
              "\nbg-color: " bg-color
              "\nextension: " extension
              "\nformat: " format
              "\nexp-path: " exp-path)))

(defn validate-and-submit [frame]
  (let [img-path (get-field-text frame :#image-path)
        tile-size (get-field-text frame :#tile-size)
        cuts (get-field-text frame :#cuts)
        bg-color (get-field-text frame :#bg-color)
        extension (get-field-text frame :#extension)
        format (get-field-text frame :#format)
        exp-path (get-field-text frame :#export-path)]
    (cut-tiles img-path tile-size cuts bg-color extension format exp-path)))
