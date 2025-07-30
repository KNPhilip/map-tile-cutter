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

(describe "Validate tile-size"
  (it "returns error message for invalid inputs."
    (should-not-be-nil (validate-tile-size ""))
    (should-not-be-nil (validate-tile-size "abc"))
    (should-not-be-nil (validate-tile-size "-10"))
    (should-not-be-nil (validate-tile-size "0")))
  (it "returns nil for valid integer string."
    (should= nil (validate-tile-size "256"))
    (should= nil (validate-tile-size "34"))))

(describe "Validate cuts"
  (it "returns error message for invalid inputs."
    (should-not-be-nil (validate-cuts ""))
    (should-not-be-nil (validate-cuts "not-a-number"))
    (should-not-be-nil (validate-cuts "-3"))
    (should-not-be-nil (validate-cuts "0")))
  (it "returns nil for valid integer string"
    (should= nil (validate-cuts "2"))
    (should= nil (validate-cuts "7"))))
