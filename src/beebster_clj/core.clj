(ns beebster-clj.core
  (:require [compojure.route :as route]
            [compojure.handler :as handler]
            [beebster-clj.views :as views])
  (:use [ring.adapter.jetty :only [run-jetty]]
        [clojure.java.shell :only [sh]]
        [clojure.string :as str :only [split-lines ]]
        hiccup.core hiccup.util hiccup.page compojure.core))


(def iplayer-command
  '("get_iplayer" "--nocopyright" "--limitmatches" "50" "--listformat" "<index> <pid> <thumbnail> <name> <episode>"))

(defn iplayer-search
  "run external command with given args list."
  [search-title]
  (split-lines (:out (apply sh (flatten
                                (conj (list search-title) iplayer-command))))))

(def iplayer-info
  '("get_iplayer" "-i"))

(defn old-recordings? 
  "Does get-iplayer complain about recorded programmes > 30 days?"
  [str]
  (remove nil? (map #(re-find #"These programmes should be deleted:" %) str)))

(defn get-info
  "get-iplayer info for entered index"
  [index]
  (split-lines (:out (apply sh (flatten (conj (list index) iplayer-info))))))

(defn get-thumb-from-search
  "return list of thumbnail for searchterm."
  [s]
  (remove nil? (map #(re-find #"http.*jpg" %) s)))

(defn get-title-and-episode
  "return list of titles from search-iplayer string."
  [s]
  (map #(re-find #"[A-Z0-9].*" %)
       (remove nil? (map #(re-find #"jpg.*" %) (map #(str/replace % #"-" "") s)))))

(defn get-index-from-search
  "return list of indexes from search-iplayer string."
  [s]
  (remove #(= "" %) (map #(re-find #"^[0-9]*" %) s)))


(defn iplayer-download-command
  "concatenate index and mode to download command"
   [index mode]
  (apply str  "get_iplayer " mode "1" " -g  --nocopyright --output=\"$HOME/Videos\"" " " index)) 
 

(defn search-categories
  "search iplayer for a given category"
  [cat]
  (split-lines (:out (apply sh (flatten
                                (conj (list "--category" cat)
                                      iplayer-command))))))








