(ns map-tile-cutter.interactor
  (:require [clojure.java.io :as io]
            [clojure.string :as str])
  (:import [javax.imageio ImageIO])
  (:use seesaw.core))

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

(defn read-file [path]
  (ImageIO/read (io/file path)))

(defn resize-img [img target-width target-height]
  (let [scaled-img (java.awt.image.BufferedImage. target-width target-height java.awt.image.BufferedImage/TYPE_INT_RGB)]
    (let [g (.createGraphics scaled-img)]
      (.drawImage g img 0 0 target-width target-height nil)
      (.dispose g))
    scaled-img))

(defn create-bg-img [width height bg-color]
  (let [bg-img (java.awt.image.BufferedImage. width height java.awt.image.BufferedImage/TYPE_INT_RGB)
        g (.createGraphics bg-img)]
    (.setColor g (java.awt.Color. (Integer/parseInt (str/replace bg-color "#" "") 16)))
    (.fillRect g 0 0 width height)
    (.dispose g)
    bg-img))

(defn num-tiles-from-cuts [cuts]
  (int (Math/pow 4 cuts)))

(defn tiles-dimensions [cuts]
  (let [tiles (num-tiles-from-cuts cuts)]
    [(int (Math/sqrt tiles)) (int (Math/sqrt tiles))]))

(defn cut-tiles [rows cols tile-size resized-img extension exp-path cuts format]
  (dotimes [row rows]
    (dotimes [col cols]
      (let [x (* col tile-size)
            y (* row tile-size)
            sub-img (.getSubimage resized-img x y tile-size tile-size)
            relative-path (format-path format cuts col row extension)
            output-file (io/file exp-path relative-path)]
        (io/make-parents output-file)
        (ImageIO/write sub-img extension output-file))))
  (alert (str "All tiles were cut out and exported to \"" exp-path "\"")))

(defn cut-non-square-image [img tile-size cuts bg-color extension format exp-path]
  (let [width (.getWidth img)
        height (.getHeight img)
        max-dimension (max width height)
        new-dimension (int (Math/ceil (/ max-dimension tile-size)))
        new-width (* new-dimension tile-size)
        new-height (* new-dimension tile-size)
        bg-img (create-bg-img new-width new-height bg-color)
        g (.createGraphics bg-img)]
    (.drawImage g img 0 0 width height nil)
    (.dispose g)
    (let [resized-img (resize-img bg-img new-width new-height)
          [rows cols] (tiles-dimensions cuts)]
      (cut-tiles rows cols tile-size resized-img extension exp-path cuts format))))

(defn cut-square-image [img tile-size cuts extension format exp-path]
  (let [width (int (* (Math/pow 2 cuts) tile-size))
        height width
        resized-img (resize-img img width height)
        [rows cols] (tiles-dimensions cuts)]
    (cut-tiles rows cols tile-size resized-img extension exp-path cuts format)))

(defn cut-image [img-path tile-size cuts bg-color extension format exp-path]
  (let [tile-size (Integer/parseInt tile-size)
        cuts (Integer/parseInt cuts)
        img (read-file img-path)
        width (.getWidth img)
        height (.getHeight img)
        is-square? (= width height)]
    (if is-square?
      (cut-square-image img tile-size cuts extension format exp-path)
      (cut-non-square-image img tile-size cuts bg-color extension format exp-path))))
