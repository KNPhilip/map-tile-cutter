(ns map-tile-cutter.core
  (:require [map-tile-cutter.presenter :as presenter])
  (:use seesaw.core))

(defn -main []
  (native!)
  (presenter/present-mainframe))
