(ns jagged.core
  (:import (org.libgit2.jagged Signature)
           (org.libgit2.jagged.core NativeMethods)))

(defn sig->clj
  "Convert a signature to a clojure map"
  [^Signature sig]
  {:name  (.getName sig)
   :email (.getEmail sig)})
