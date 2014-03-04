(ns jagged.core-test
  (:require [clojure.test :refer :all]
            [jagged.core :refer :all]
            [clojure.java.io :as io])

  (:import (java.io File)
           (org.apache.commons.io FileUtils)
           (org.libgit2.jagged Repository)))

(def base-dir (System/getProperty "java.io.tmpdir"))
(def path (atom nil))

(defn rand-dir
  []
  (io/file base-dir
           (str "jagged" (Math/random))))

(defn with-path
  "Set the path to be a random path which we can use to init a
  repository"
  [f]
  (let [dir (rand-dir)]
    (reset! path (.getPath dir))
    (f)
    (FileUtils/deleteDirectory dir)))

(defn init-open-repo []
  (let [repo (repository-init (deref path) true)
        repo2 (repository (deref path))]
    (is (instance? Repository repo))
    (is (instance? Repository repo))
    (is (bare? repo))
    (is (bare? repo2))))

(deftest repo-sanity
  (with-path init-open-repo))
