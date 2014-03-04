(ns jagged.oid)
(import (org.libgit2.jagged ObjectId)
        (java.io Writer))

(defmethod print-method ObjectId
  [^ObjectId obj, ^Writer w]
  (.write w (.toString obj)))
