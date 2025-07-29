(ns map-tile-cutter.interactor-spec
  (:require [speclj.core :refer :all]
            [map-tile-cutter.interactor :refer :all]))

;; Ran this locally to test the cutting functionality.
;; Seems to work for this simple case.

;; (describe "cut-tiles"
;;   (it "can successfully cut tiles."
;;     (should= "Tiles have been created and saved."
;;              (cut-tiles-spec
;;                "/Users/dkphkrni/Pictures/summer.jpg"
;;                "256"
;;                "1"
;;                "#00FFFFF"
;;                "JPG"
;;                "z/x/y.png"
;;                "/Users/dkphkrni/Pictures"))))

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
