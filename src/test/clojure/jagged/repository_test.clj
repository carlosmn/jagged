(ns jagged.repository-test
  (:require [clojure.test :refer :all]
            [jagged.core :refer :all]
            [clojure.java.io :as io])
  (:import (java.io File)
          (org.apache.commons.io FileUtils)
          (org.libgit2.jagged Repository ObjectId)))

(def base-dir (System/getProperty "java.io.tmpdir"))
(def orig-repo-dir (io/file "src" "test" "resources" "testrepo"))
(def path (atom nil))

(defn rand-dir
  []
  (io/file base-dir
           (str "jagged" (Math/random))))

(defn with-repo
  "Set 'path' to the path of the repository we just copied from the
  resources"
  [f]
  (let [dir      (rand-dir)
        dir-path (.getPath dir)]
    (reset! path dir-path)
    ;; copy the test repo and rename .gitted to .git so it's a repo again
    (FileUtils/copyDirectory orig-repo-dir dir)
    (.renameTo (io/file dir-path ".gitted") (io/file dir-path ".git"))
    (f)
    (FileUtils/deleteDirectory dir)))

(defn open-existing-repo []
  (let [repo (repository (deref path))]
    (is (instance? Repository repo))
    (is (not (bare? repo)))))

(deftest open-repo
  (with-repo open-existing-repo))

(defn test-list-references []
  (let [repo (repository (deref path))
        refs (references repo)]
    (is (= 1 (count refs)))))

(defn test-master-id []
  (let [repo (repository (deref path))
        refs (references repo)
        master (nth refs 0)]
    (is (= (:target master) (ObjectId. "5eab02d63a3676df528bcd878ac935ec0c4d5bdc")))
    (is (not ( symbolic? master)))))

(deftest list-references
  (with-repo test-list-references))
(deftest master-id
  (with-repo test-master-id))
