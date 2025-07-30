(ns map-tile-cutter.validator-spec
  (:require [speclj.core :refer :all]
            [map-tile-cutter.validator :refer :all])
  (:import [java.io File]))

(describe "Validate image path"
  (it "returns error message for invalid input."
    (should-not-be-nil (validate-img-path ""))
    (should-not-be-nil (validate-img-path "dumb-image"))
    (should-not-be-nil (validate-img-path "image/file/with/no/extension"))
    (should-not-be-nil (validate-img-path "unsupported-extension.txt"))
    (should-not-be-nil (validate-img-path "unsupported-extension.clj"))
    (should-not-be-nil (validate-img-path "unsupported-extension.exe")))
  (it "returns error message if file does not exist."
    (should-not-be-nil (validate-img-path "nonexistent.jpg")))
  (it "returns nil for valid existing file."
    (let [tmp-file (File/createTempFile "test-image" ".png")]
      (try
        (should= nil (validate-img-path (.getAbsolutePath tmp-file)))
        (finally (.delete tmp-file)))
    )))
