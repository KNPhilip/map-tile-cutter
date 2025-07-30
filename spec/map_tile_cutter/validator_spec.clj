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
    (should-not-be-nil (validate-img-path "unsupported-extension.exe"))
    (should-not-be-nil (validate-img-path nil)))
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
    (should-not-be-nil (validate-tile-size "0"))
    (should-not-be-nil (validate-tile-size nil)))
  (it "returns nil for valid integer string."
    (should-be-nil (validate-tile-size "256"))
    (should-be-nil (validate-tile-size "34"))))

(describe "Validate cuts"
  (it "returns error message for invalid inputs."
    (should-not-be-nil (validate-cuts ""))
    (should-not-be-nil (validate-cuts "not-a-number"))
    (should-not-be-nil (validate-cuts "-3"))
    (should-not-be-nil (validate-cuts "0"))
    (should-not-be-nil (validate-cuts nil)))
  (it "returns nil for valid integer string"
    (should-be-nil (validate-cuts "2"))
    (should-be-nil (validate-cuts "7"))))

(describe "Validate bg-color"
  (it "returns error message for missing or blank value."
    (should-not-be-nil (validate-bg-color ""))
    (should-not-be-nil (validate-bg-color nil)))
  (it "returns error message for invalid hex formats."
    (should-not-be-nil (validate-bg-color "000000"))
    (should-not-be-nil (validate-bg-color "#FFF"))
    (should-not-be-nil (validate-bg-color "#12345g"))
    (should-not-be-nil (validate-bg-color "#1234567")))
  (it "returns nil for valid hex colors."
    (should-be-nil (validate-bg-color "#000000"))
    (should-be-nil (validate-bg-color "#ffffff"))
    (should-be-nil (validate-bg-color "#ABCDEF"))))

(describe "Validate export path"
  (it "returns error message for blank or nil."
    (should-not-be-nil (validate-export-path ""))
    (should-not-be-nil (validate-export-path nil)))
  (it "returns error message if path does not exist."
    (should-not-be-nil (validate-export-path "/non/existing/directory")))
  (it "returns error message if path is a file, not a directory."
    (let [tmp-file (File/createTempFile "not-a-dir" ".txt")]
      (try
        (should-not-be-nil (validate-export-path (.getAbsolutePath tmp-file)))
        (finally (.delete tmp-file)))
    ))
  (it "returns nil for a valid directory."
    (let [tmp-dir (doto (File/createTempFile "test-dir" "")
                    (.delete)
                    (.mkdir))]
      (try
        (should-be-nil (validate-export-path (.getAbsolutePath tmp-dir)))
        (finally (.delete tmp-dir)))
    )))
