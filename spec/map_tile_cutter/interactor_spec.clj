(ns map-tile-cutter.interactor-spec
  (:require [speclj.core :refer :all]
            [map-tile-cutter.interactor :refer :all])
  (:import [java.awt.image BufferedImage]))

(describe "Formatting"
  (it "can format path for z_x_y."
    (should= "z_x_y.jpg" (format-path "z_x_y" "z" "x" "y" "jpg"))
    (should= "1_0_0.jpg" (format-path "z_x_y.gif" "1" "0" "0" "JPG"))
    (should= "2_0_1.png" (format-path "z_x_y" "2" "0" "1" "pNg"))
    (should= "3_2_4.gif" (format-path "z_x_y" "3" "2" "4" "giF")))
  (it "can format path for z/x_y."
    (should= "z/x_y.gif" (format-path "z/x_y.png" "z" "x" "y" "gif"))
    (should= "2/2_2.png" (format-path "z/x_y" "2" "2" "2" "PNG"))
    (should= "1/2_0.jpg" (format-path "z/x_y" "1" "2" "0" "jPG"))
    (should= "1/0_0.gif" (format-path "z/x_y.png" "1" "0" "0" "GIF")))
  (it "can format path for z/x/y."
    (should= "z/x/y.png" (format-path "z/x/y" "z" "x" "y" "PNG"))
    (should= "1/1/0.gif" (format-path "z/x/y" "1" "1" "0" "gif"))
    (should= "4/0/0.png" (format-path "z/x/y" "4" "0" "0" "png"))
    (should= "1/0/0.jpg" (format-path "z/x/y.jpg" "1" "0" "0" "jpG"))))

(describe "resize-img"
  (it "should correctly resize image to specified dimensions."
    (let [img (java.awt.image.BufferedImage. 100 100 java.awt.image.BufferedImage/TYPE_INT_RGB)
          resized (resize-img img 50 75)]
      (should= 50 (.getWidth resized))
      (should= 75 (.getHeight resized))
    )))

(describe "create-bg-img"
  (it "creates an image with correct size and background color."
    (let [width 100
          height 50
          hex "#FF0000"
          img (create-bg-img width height hex)
          rgb (.getRGB img 0 0)]
      (should= width (.getWidth img))
      (should= height (.getHeight img))
      (should= (bit-and rgb 0xFFFFFF) 0xFF0000))))

(describe "tiles-dimensions"
  (it "calculates correct number of rows and cols for cuts."
    (should= [2 2] (tiles-dimensions 1))
    (should= [4 4] (tiles-dimensions 2))
    (should= [8 8] (tiles-dimensions 3))))

(describe "Image I/O"
  (it "exports an image file correctly."
    (with-redefs [export-img (fn [exp-path relative-path extension img]
                                (should= "expected-path" exp-path)
                                (should= "expected-image.jpg" relative-path)
                                (should= "jpg" extension)
                                (should= 100 (.getWidth img))
                                (should= 100 (.getHeight img)))]
      (let [img (BufferedImage. 100 100 BufferedImage/TYPE_INT_RGB)]
        (export-img "expected-path" "expected-image.jpg" "jpg" img))))
  (it "handles tile cutting correctly."
    (let [img (BufferedImage. 200 200 BufferedImage/TYPE_INT_RGB)]
      (with-redefs [cut-tiles (fn [rows cols tile-size resized-img extension exp-path cuts format]
                                 (should= 4 rows)
                                 (should= 4 cols)
                                 (should= 50 tile-size))]
        (cut-square-image img 50 2 "jpg" "z_x_y" "dummy-path"))
    )))

(describe "cut-image"
  (it "processes square images correctly."
    (with-redefs [read-file (fn [path] (BufferedImage. 100 100 BufferedImage/TYPE_INT_RGB))
                   cut-square-image (fn [img tile-size cuts extension format exp-path]
                                      (should= 50 tile-size)
                                      (should= 2 cuts))]
      (cut-image "dummy-path" "50" "2" "#FFFFFF" "jpg" "z_x_y" "dummy-path")))
  (it "processes non-square images correctly."
    (with-redefs [read-file (fn [path] (BufferedImage. 100 50 BufferedImage/TYPE_INT_RGB))
                   fill-to-square (fn [img bg-color] (BufferedImage. 100 100 BufferedImage/TYPE_INT_RGB))
                   cut-square-image (fn [img tile-size cuts extension format exp-path]
                                      (should= 50 tile-size)
                                      (should= 2 cuts))]
      (cut-image "dummy-path" "50" "2" "#FFFFFF" "jpg" "z_x_y" "dummy-path"))))
