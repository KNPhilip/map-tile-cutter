(ns map-tile-cutter.validator
  (:require [map-tile-cutter.widgets :refer :all]
            [map-tile-cutter.interactor :as interactor]
            [clojure.string :as str]
            [clojure.java.io :as io])
  (:use seesaw.core))

(def valid-extensions #{"png" "jpg" "jpeg" "bmp" "gif"})

(defn file-extension [filename]
  (let [parts (str/split filename #"\.")]
    (when (> (count parts) 1)
      (str/lower-case (last parts)))))

(defn validate-img-path [path]
  (cond
    (or (nil? path) (str/blank? path))
    "Please fill out the image path."
    (nil? (file-extension path))
    "Image path must point to a file with an extension (e.g., .png, .jpg)."
    (not (contains? valid-extensions (file-extension path)))
    (str "Image path has an unsupported file extension. Supported types: "
      (clojure.string/join ", " valid-extensions) ".")
    (not (.exists (io/file path)))
    "Image path points to a file that does not exist."
    :else nil))

(defn valid-int-string? [s]
  (try
    (let [parsed (Integer/parseInt s)]
      parsed)
    (catch Exception _ false)))

(defn validate-tile-size [s]
  (cond
    (str/blank? s)
    "Please fill out the tile size."
    (not (valid-int-string? s))
    "Tile size must be an integer."
    (not (pos? (Integer/parseInt s)))
    "Tile size must be a positive integer."
    :else nil))

(defn validate-cuts [s]
  (cond
    (str/blank? s)
    "Please fill out the cuts."
    (not (valid-int-string? s))
    "Cuts must be an integer."
    (neg? (Integer/parseInt s))
    "Cuts cannot be a negative integer."
    :else nil))

(def hex-color-pattern #"^#(?:[0-9a-fA-F]{6})$")

(defn validate-bg-color [color]
  (cond
    (str/blank? color)
    "Please fill out the background color."
    (not (re-matches hex-color-pattern color))
    "Background color must be a valid hex color (e.g., #000000)."
    :else nil))

(defn validate-export-path [path]
  (cond
    (str/blank? path)
    "Please fill out the export path."
    (not (.exists (io/file path)))
    "Export path does not exist."
    (not (.isDirectory (io/file path)))
    "Export path must be a directory, not a file."
    :else nil))

(defn validate-inputs [img-path tile-size cuts bg-color exp-path]
  (or
    (validate-img-path img-path)
    (validate-tile-size tile-size)
    (validate-cuts cuts)
    (validate-bg-color bg-color)
    (validate-export-path exp-path)))

(defn validate-and-submit [frame]
  (let [img-path  (get-widget-text frame :#image-path)
        tile-size (get-widget-text frame :#tile-size)
        cuts      (get-widget-text frame :#cuts)
        bg-color  (get-widget-text frame :#bg-color)
        extension (get-widget-text frame :#extension)
        format    (get-widget-text frame :#format)
        exp-path  (get-widget-text frame :#export-path)
        error     (validate-inputs img-path tile-size cuts bg-color exp-path)]
    (if error
      (alert error)
      (interactor/cut-image img-path tile-size cuts bg-color
                            extension format exp-path))))
