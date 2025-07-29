(ns map-tile-cutter.interactor
  (:require [clojure.java.io :as io]
            [clojure.string :as str])
  (:import [javax.imageio ImageIO])
  (:use seesaw.core))

(defn get-field-text [frame id]
  (partial (config
    (select frame [id]) :text)))

(defn cut-tiles-spec [img-path tile-size cuts bg-color extension format exp-path]
  (let [cuts (Integer/parseInt cuts)
        extension (str/lower-case extension)
        img (ImageIO/read (io/file img-path))
        width (.getWidth img)
        height (.getHeight img)
        num-tiles (int (Math/pow 4 cuts))
        rows (int (Math/sqrt num-tiles))
        cols (int (Math/sqrt num-tiles))
        step-x (int (/ width cols))
        step-y (int (/ height rows))]
    (dotimes [row rows]
      (dotimes [col cols]
        (let [x (* col step-x)
              y (* row step-y)
              w (min step-x (- width x))
              h (min step-y (- height y))
              sub-img (.getSubimage img x y w h)
              output-file (io/file (str exp-path "/" cuts "_" col "_" row "." extension))]
          (ImageIO/write sub-img extension output-file))))
    "Tiles have been created and saved."))

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
