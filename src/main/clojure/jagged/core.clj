(ns jagged.core
  (:import (org.libgit2.jagged Repository Reference
                               ObjectId ObjectType GitObject)
           (org.libgit2.jagged.core NativeMethods)))

(defn ref->clj
  "Convert a jagged java reference to a clojure map"
  [^Reference ref]
  {:name   (.getCanonicalName ref)
   :target (.getTarget ref)})

(defn bare?
  "Returns whether a repository is bare"
  [^Repository repo]
  (.isBare repo))

(defn dispose
  "Dispose the repository and it associated resources"
  [^Repository repo]
  (.dispose repo))

(defn repository
  "Open a repository at the given path"
  [^String path]
  (Repository. path))

(defn repository-init
  "Initialize a new repository at the given path"
  [^String path ^Boolean bare]
  (Repository/init path bare))

(defn bare?
  "Return whether the repository is bare"
  [^Repository repo]
  (.isBare repo))

(defn references
  "Get the list of references of a repository"
  [^Repository repo]
  (map ref->clj (-> repo .getReferences .iterator iterator-seq)))

(defn reference
  "Look up a reference"
  [^Repository repo ^String name]
  (-> repo (NativeMethods/referenceLookup name) ref->clj))

(defn HEAD
  "Get the current branch"
  [^Repository repo]
  (.getHead repo))

(defn object
  "Look up an object from the repository"
  ([^Repository repo ^ObjectId id]
     (.lookup repo id))
  ([^Repository repo ^ObjectId id ^ObjectType type]
     (.lookup repo id type)))

(defn commit
  "Look up a commit"
  [^Repository repo ^ObjectId id]
  (object repo id ObjectType/COMMIT))

(defn tree
  "Look up a tree"
  [^Repository repo ^ObjectId id]
  (object repo id ObjectType/TREE))

(defn blob
  "Look up a blob"
  [^Repository repo ^ObjectId id]
  (object repo id ObjectType/BLOB))

(defn tag
  "Look up a tag"
  [^Repository repo ^ObjectId id]
  (object repo id ObjectType/TAG))

(defn symbolic?
  [{:keys [target]}]
  (instance? String target))

(defn id
  "Get an object's id"
  [^GitObject obj]
  (.getId obj))
