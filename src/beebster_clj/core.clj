(ns beebster-clj.core
  (:require [compojure.route :as route]
            [compojure.handler :as handler]
            [beebster-clj.config :as config])
  (:use [ring.adapter.jetty :only [run-jetty]]
        [clojure.java.shell :only [sh]]
        [clojure.string :as str :only [split-lines]]
        hiccup.core hiccup.util hiccup.page compojure.core))

;; iplayer-command to use for searches. The use of --listformat reduces 
;; get_iplayer's output just to the desired information, in this case
;; index, the url of the programmes thumbnail, and the programmes title,
;; and in case of series, the episode number.
(def iplayer-command
  ["get_iplayer" "--nocopyright" "--limitmatches" "50" "--listformat" "<index> <thumbnail> <name> <episode>"])


(def iplayer-info
  ["get_iplayer" "-i"])


;; keeping downloaded programmes longer than 30 days on your harddisk is not
;; cool. Thus, get_iplayer asks you to delete old recordings. 
(def delete-string
  "These programmes should be deleted:")

(defn get-info
  "get-iplayer info for entered index"
  [index]
  (split-lines (:out (apply sh (conj iplayer-info index)))))

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
  "Get values of terminal and download-destination from beebster-clj.config.clj.
   By default, it uses gnome-terminal and $HOME/Videos. Then build a list 
   of commands for 'sh' to work on."
   [index mode]
   (let [term (get config/config :terminal)
         dir  (get config/config :download-folder)]
     (list term
           dir
           "-e"  (apply str "get_iplayer" " --modes=" mode "1" " -g " index)))) 


(defn get-url
  "return /info url string concatenated with index"
  [index]
  (apply str "/info?index=" index))

(defn iplayer-search-resultstring
  "run external command with given args list."
  [search-title]
  (:out (apply sh (conj iplayer-command search-title ))))

(defn iplayer-category-resultstring
  "similar as search-resultstring but search for 
   category"
  [cat]
  (:out (apply sh (conj iplayer-command "--category" cat))))

(defn iplayer-search
  "If old-recording? display a list of all programmes,
   that have to be deleted, otherwise, return a list of 
   all matching programmes for search input."
  [search-title]
  (let [result (iplayer-search-resultstring search-title )]
    (if (old-recordings? (split-lines result))
      (reverse (drop 2 (reverse
                        (split-lines
                         (subs result (.indexOf result delete-string))))))
      (split-lines result))))

(defn search-categories
  "search iplayer for a given category"
  [cat]
  (let [result (iplayer-category-resultstring cat)]
    (if (old-recordings? (split-lines result))
      (reverse (drop 2 (reverse
                        (split-lines
                         (subs result (.indexOf result delete-string))))))
      (split-lines result))))


(defn string-to-url-sanitizer
  "remove ':' from string replace space with '_' or '+'"
  [string delimiter]
  (let [sanstring (str/replace string #":" "")]
    (str/replace sanstring #" " delimiter)))

(def wiki-search-string
  "http://en.wikipedia.org/w/index.php?search=")

(defn wiki-url
  "return wikipedia url for search queries."
  [searchterm]
  (let [term (str/replace searchterm #" " "_")]
    (apply str wiki-search-string (str/replace term #":" "") )))

(def imdb-search-string
  "http://imdb.com/find?q=")

(defn imdb-url
  "return imdb url for search queries."
  [searchterm]
  (let [term (str/replace searchterm #" " "+")]
    (apply str imdb-search-string term)))










