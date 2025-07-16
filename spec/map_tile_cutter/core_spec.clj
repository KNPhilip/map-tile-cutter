(ns map-tile-cutter.core-spec
  (:require [speclj.core :refer :all]
            [map-tile-cutter.core :refer :all]))

(describe "Main function"
  (it "should return hello world."
    (should= "Hello World" (-main))))
