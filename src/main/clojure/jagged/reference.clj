(ns jagged.reference
  (:require [jagged.core :as c])
  (:import (org.libgit2.jagged Repository)
           (org.libgit2.jagged.core NativeMethods)))

;; In the namespace so we don't accidentally overwirte clojure's
(defn resolve
  "Resolve a reference to direct"
  [^Repository repo ref]
  (cond
   (instance? String ref) (c/ref->clj (NativeMethods/referenceResolve repo ref))
   (c/symbolic? ref) (resolve repo (:target ref))
   :else ref))
