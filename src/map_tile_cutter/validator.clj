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
