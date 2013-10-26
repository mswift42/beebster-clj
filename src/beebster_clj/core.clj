(ns beebster-clj.core
  (:require [compojure.route :as route])
  (:use [ring.adapter.jetty :only [run-jetty]]
        [net.cgrand.enlive-html :as html]
        [clojure.java.shell :only [sh]]
        [compojure.core]
        [clojure.string :only [split-lines]]))


(def iplayer-command
  '("get_iplayer" "--nocopyright" "--limitmatches" "50" "--listformat" "<index> <pid> <thumbnail> <name> <episode>"))

(defn iplayer-search
  "run external command with given args list."
  [search-title]
  (split-lines (:out (apply sh (flatten (conj (list search-title) iplayer-command))))))

(def iplayer-info
  '("get_iplayer" "-i"))

(defn get-info
  "get-iplayer info for entered index"
  [index]
  (split-lines (:out (apply sh (flatten (conj (list index) iplayer-info))))))

(defn get-thumb-from-search
  "find thumbnail-url in string."
  [str]
  (re-find #"http.*jpg" str))

















