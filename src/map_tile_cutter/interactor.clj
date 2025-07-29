(ns map-tile-cutter.interactor
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [map-tile-cutter.widgets :refer :all])
  (:import [javax.imageio ImageIO])
  (:use seesaw.core))

(defn read-file [path]
  (ImageIO/read (io/file path)))

(defn get-format-without-extension [format]
  (let [dot-idx (.lastIndexOf format ".")]
    (if (and (pos? dot-idx) (< dot-idx (count format)))
      (subs format 0 dot-idx)
      format)))

(defn format-path [format z x y extension]
  (let [extension (str/lower-case extension)
        format (get-format-without-extension format)]
    (case format
      "z_x_y" (str z "_" x "_" y "." extension)
      "z/x_y" (str z "/" x "_" y "." extension)
      "z/x/y" (str z "/" x "/" y "." extension)
      (str z "_" x "_" y "." extension))))

(defn cut-tiles-spec [img-path tile-size cuts bg-color extension format exp-path]
  (let [cuts (Integer/parseInt cuts)
        extension (str/lower-case extension)
        img (read-file img-path)
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
              relative-path (format-path format cuts col row extension)
              output-file (io/file exp-path relative-path)]
          (io/make-parents output-file)
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
  (let [img-path (get-widget-text frame :#image-path)
        tile-size (get-widget-text frame :#tile-size)
        cuts (get-widget-text frame :#cuts)
        bg-color (get-widget-text frame :#bg-color)
        extension (get-widget-text frame :#extension)
        format (get-widget-text frame :#format)
        exp-path (get-widget-text frame :#export-path)]
    (cut-tiles img-path tile-size cuts bg-color extension format exp-path)))
