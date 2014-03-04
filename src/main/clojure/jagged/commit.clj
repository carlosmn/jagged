(ns jagged.core
  (:require [jagged.object :as obj])
  (:import (org.libgit2.jagged Repository Commit
                               ObjectId ObjectType)
           (org.libgit2.jagged.core NativeMethods)))

(defn parents
  "Get a commit's parents"
  [^Commit commit]
  (.getParents commit))

(defn tree
  "Get a commit's tree"
  [^Commit commit]
  (.getTree commit))

(defn author
  "Get the commit's author"
  [^Commit commit]
  (-> commit .getAuthor sig->clj))

(defn committer
  "Get the commit's committer"
  [^Commit commit]
  (-> commit .getCommitter sig->clj))
