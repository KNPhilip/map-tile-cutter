(ns map-tile-cutter.widgets
  (:import [javax.swing Box])
  (:use (seesaw [core] [font])))

(defn get-widget-text [frame id]
  (partial (config
    (select frame [id]) :text)))

(defn headline [title]
  (horizontal-panel
    :items [(label :text title
                   :font (font :name "SansSerif"
                               :style :bold
                               :size 15))]))

(defn browse-file [frame text-field]
  (let [file-chooser (javax.swing.JFileChooser.)]
    (when (= (.showOpenDialog file-chooser frame) javax.swing.JFileChooser/APPROVE_OPTION)
      (let [selected-file (.getSelectedFile file-chooser)]
        (config! text-field :text (.getAbsolutePath selected-file))))))

(defn browse-file-btn [frame field]
  (button :text "Browse"
          :listen [:action (fn [_]
                    (browse-file frame field))]))

(defn browse-directory [frame text-field]
  (let [dir-chooser (javax.swing.JFileChooser.)]
    (.setFileSelectionMode dir-chooser javax.swing.JFileChooser/DIRECTORIES_ONLY)
    (when (= (.showOpenDialog dir-chooser frame) javax.swing.JFileChooser/APPROVE_OPTION)
      (let [selected-dir (.getSelectedFile dir-chooser)]
        (config! text-field :text (.getAbsolutePath selected-dir))))))

(defn browse-dir-btn [frame field]
  (button :text "Browse"
          :listen [:action (fn [_]
                    (browse-directory frame field))]))

(defn choose-color [frame text-field]
  (let [color (javax.swing.JColorChooser/showDialog frame "Choose Background Color" java.awt.Color/WHITE)]
    (when color
      (config! text-field :text (format "#%06X" (bit-and (.getRGB color) 0xFFFFFF))))))

(defn color-chooser-btn [frame field]
  (button :text "Choose Color"
          :listen [:action (fn [_]
                    (choose-color frame field))]))

(defn horizontal-strut [n]
  (Box/createHorizontalStrut n))

(defn glue []
  (Box/createVerticalGlue))

(defn glued-section [section]
  (let [top-glue (glue)
        bottom-glue (glue)]
    (vertical-panel
      :items [top-glue
              section
              bottom-glue])))
