(defproject map-tile-cutter "0.1.0-SNAPSHOT"
  :description "Simple tool for cutting a map/image out into tiles written in Clojure."
  :url "https://github.com/KNPhilip/map-tile-cutter"
  :main map-tile-cutter.core
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :profiles {:dev {:dependencies [[speclj "3.3.2"]]}}
  :plugins [[speclj "3.3.2"]]
  :test-paths ["spec"])
