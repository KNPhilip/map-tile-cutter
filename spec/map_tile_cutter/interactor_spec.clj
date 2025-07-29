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
;;                "z_x_y.png"
;;                "/Users/dkphkrni/Pictures"))))
