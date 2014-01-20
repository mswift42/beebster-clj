(ns beebster-clj.core
  (:import [java.lang.ProcessBuilder])
  (:require [compojure.route :as route]
            [compojure.handler :as handler]
            
            ;; [beebster-clj.views :as views]
            )
  (:use [ring.adapter.jetty :only [run-jetty]]
        [clojure.java.shell :only [sh]]
        [clojure.string :as str :only [split-lines ]]
        hiccup.core hiccup.util hiccup.page compojure.core ))


(def iplayer-command
  '("get_iplayer" "--nocopyright" "--limitmatches" "50" "--listformat" "<index> <pid> <thumbnail> <name> <episode>"))



(def iplayer-info
  '("get_iplayer" "-i"))

(def delete-string
  "These programmes should be deleted:")


(defn get-info
  "get-iplayer info for entered index"
  [index]
  (split-lines (:out (apply sh (flatten (conj (list index) iplayer-info))))))

(defn get-download-modes
  "build list of possible download-modes for a given index."
  [lst]
  (let [modes (first (filter #(re-find #"modes.*" %) lst))]
    (remove nil? (list (re-find #"flashhigh" modes)
                       (re-find #"flashvhigh" modes)
                       (re-find #"flashhd" modes)
                       (re-find #"flashlow" modes)))))

(defn load-thumbandinfo-for-index
  "grep url for thumbnail size44, title and description
   for entered index."
  [index]
  (let [ind (get-info index)]
    (list
     (map #(re-find #"htt.*" %) (filter #(re-find #"thumbnail4.*" %) ind))
     (map #(re-find #"[A-Z].*" %) (filter #(re-find #"desc:.*" %) ind))
     (map #(re-find #"[A-Z0-9].*" %) (filter #(re-find #"title:.*" %) ind))
     (get-download-modes ind))))

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

(defn old-recordings? 
  "Does get-iplayer complain about recorded programmes > 30 days?"
  [lst]
  (seq (remove nil?
               (map #(re-find #"These programmes should be deleted:" %) lst))))


(defn iplayer-download-command
  "concatenate index and mode to download command"
   [index mode]
   (list "get-iplayer" (apply str "modes=" mode "1\"")
         "output=\"$HOME/Videos\"" "-g" index)) 

(defn get-url
  "return /info url string concatenated with index"
  [index]
  (apply str "/info?index=" index))

(defn iplayer-search-resultstring
  "run external command with given args list."
  [search-title]
  (:out (apply sh (flatten
                   (conj (list search-title) iplayer-command)))))

(defn iplayer-search
  "If old-recording? display a list of all programmes,
   that have to be deleted, otherwise, return a list of 
   all matching programmes for search input."
  [search-title]
  (let [result (iplayer-search-resultstring search-title)]
    (if (old-recordings? (split-lines result))
      (split-lines (subs result (.indexOf result delete-string)))
      (split-lines result))))

(defn search-categories
  "search iplayer for a given category"
  [cat]
  (drop 1 (butlast
           (split-lines (:out (apply sh (flatten
                                         (conj (list "--category" cat)
                                               iplayer-command))))))))








