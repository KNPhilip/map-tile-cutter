(defproject map-tile-cutter "0.1.0-SNAPSHOT"
  :description "Simple tool for cutting a map/image out into tiles written in Clojure."
  :url "https://github.com/KNPhilip/map-tile-cutter"
  :main map-tile-cutter.presenter
  :aot [map-tile-cutter.presenter]
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [seesaw "1.5.0"]]
  :profiles {:dev {:dependencies [[speclj "3.3.2"]]}
             :uberjar {:aot :all}}
  :plugins [[speclj "3.3.2"]]
  :test-paths ["spec"])
