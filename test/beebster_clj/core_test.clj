(ns beebster-clj.core-test
  (:require [clojure.test :refer :all]
            [beebster-clj.core :refer :all]))


(def test-list-1
  ["Matches:" "915 b03bncr5 http://www.bbc.co.uk/iplayer/images/episode/b03bncr5_150_84.jpg The Bridge: Series 2 Episode 1" "916 b03bx7kw http://www.bbc.co.uk/iplayer/images/episode/b03bx7kw_150_84.jpg The Bridge: Series 2 Episode 2" "917 b03cd8cz http://www.bbc.co.uk/iplayer/images/episode/b03cd8cz_150_84.jpg The Bridge: Series 2 Episode 3" "918 b03dxccc http://www.bbc.co.uk/iplayer/images/episode/b03dxccc_150_84.jpg The Bridge: Series 2 Episode 4" "919 b03f2bn1 http://www.bbc.co.uk/iplayer/images/episode/b03f2bn1_150_84.jpg The Bridge: Series 2 Episode 5" "920 b03fqbkf http://www.bbc.co.uk/iplayer/images/episode/b03fqbkf_150_84.jpg The Bridge: Series 2 Episode 6" "" "INFO: 6 Matching Programmes"])


(def test-info-1
  "Matches:
703:	Reading and Leeds Festival - 2013: 15. Don Broco - Reading Festival highlights, BBC Three, Guidance,Music,Rock & Indie,TV, default
INFO: File name prefix = Reading_and_Leeds_Festival_-_2013_15._Don_Broco_-_Reading_Festival_highlights_p01fqbwj_default                 

available:      Unknown
categories:     Music,Rock & Indie
channel:        BBC Three
desc:           Extended highlights of Don Broco's set at Reading Festival 2013
descmedium:     Extended highlights of Don Broco's set at Reading Festival 2013
descshort:      Extended highlights of Don Broco's set at Reading Festival 2013
dir:            /home/martin/quicklisp/local-projects/beebster
dldate:         2013-09-07
dltime:         14:40:45
duration:       2028
durations:      default: 2028
episode:        2013: 15. Don Broco - Reading Festival highlights
episodenum:     15
episodeshort:   2013: Don Broco - Reading Festival highlights
expiry:         2013-09-24T18:01:00Z
expiryrel:      in 17 days 4 hours
ext:            EXT
filename:       /home/martin/quicklisp/local-projects/beebster/Reading_and_Leeds_Festival_-_2013_15._Don_Broco_-_Reading_Festival_highlights_p01fqbwj_default.EXT
filepart:       /home/martin/quicklisp/local-projects/beebster/Reading_and_Leeds_Festival_-_2013_15._Don_Broco_-_Reading_Festival_highlights_p01fqbwj_default.partial.EXT
fileprefix:     Reading_and_Leeds_Festival_-_2013_15._Don_Broco_-_Reading_Festival_highlights_p01fqbwj_default
firstbcast:     default: 2013-08-25T19:01:00+01:00
firstbcastrel:  default: 12 days 19 hours ago
guidance:       adult
index:          703
lastbcast:      default: 2013-08-25T19:01:00+01:00
lastbcastrel:   default: 12 days 19 hours ago
longname:       Reading and Leeds Festival
modes:          default: flashhigh1,flashhigh2,flashlow1,flashlow2,flashstd1,flashstd2,flashvhigh1,flashvhigh2,rtsphigh1,rtsphigh2,rtsplow1,rtsplow2,rtspstd1,rtspstd2,rtspvhigh1,rtspvhigh2
modesizes:      default: flashhigh1=197MB,flashhigh2=197MB,flashlow1=98MB,flashlow2=98MB,flashstd1=119MB,flashstd2=119MB,flashvhigh1=371MB,flashvhigh2=371MB,rtsphigh1=197MB,rtsphigh2=197MB,rtsplow1=98MB,rtsplow2=98MB,rtspstd1=119MB,rtspstd2=119MB,rtspvhigh1=371MB,rtspvhigh2=371MB
name:           Reading and Leeds Festival
nameshort:      Reading and Leeds Festival
pid:            p01fqbwj
player:         http://www.bbc.co.uk/iplayer/episode/p01fqbwj/Reading_and_Leeds_Festival_2013_Don_Broco_Reading_Festival_highlights/
senum:          s00e15
thumbfile:      /home/martin/quicklisp/local-projects/beebster/Reading_and_Leeds_Festival_-_2013_15._Don_Broco_-_Reading_Festival_highlights_p01fqbwj_default.jpg
thumbnail:      http://www.bbc.co.uk/iplayer/images/episode/p01fqbwj_150_84.jpg
thumbnail1:     http://ichef.bbci.co.uk/programmeimages/p01fqbvz/p01fqbwj_86_48.jpg
thumbnail2:     http://ichef.bbci.co.uk/programmeimages/p01fqbvz/p01fqbwj_150_84.jpg
thumbnail3:     http://ichef.bbci.co.uk/programmeimages/p01fqbvz/p01fqbwj_178_100.jpg
thumbnail4:     http://ichef.bbci.co.uk/programmeimages/p01fqbvz/p01fqbwj_512_288.jpg
thumbnail5:     http://ichef.bbci.co.uk/programmeimages/p01fqbvz/p01fqbwj_528_297.jpg
thumbnail6:     http://ichef.bbci.co.uk/programmeimages/p01fqbvz/p01fqbwj_640_360.jpg
timeadded:      12 days 16 hours ago (1377464262)
title:          Reading and Leeds Festival: 2013: Don Broco - Reading Festival highlights
type:           tv
verpids:        default: p01fqbwn
version:        default
versions:       default
web:            http://www.bbc.co.uk/programmes/p01fqbwj.html


INFO: 1 Matching Programmes
")

(deftest test-thumbs-from-string
  (testing "get thumbnail from string"
    (is (= "http://www.bbc.co.uk/iplayer/images/episode/b03bncr5_150_84.jpg"
           (first (get-thumb-from-search test-list-1))))
    (is (= "http://www.bbc.co.uk/iplayer/images/episode/b03bx7kw_150_84.jpg"
           (second (get-thumb-from-search test-list-1))))
    (is (= "http://www.bbc.co.uk/iplayer/images/episode/b03fqbkf_150_84.jpg"
           (last (get-thumb-from-search test-list-1))))))


(deftest test-title-and-episode
  (testing "return title and episode from searchresult"
    (is (= "The Bridge: Series 2 Episode 1"
           (first (get-title-and-episode test-list-1))))
    (is (= "The Bridge: Series 2 Episode 2"
           (second (get-title-and-episode test-list-1))))))

(deftest test-get-index-from-search
  (testing "return index from searchresult"
    (is (= "915"
           (first (get-index-from-search test-list-1))))
    (is (= "920"
           (last (get-index-from-search test-list-1))))))

(deftest test-get-download-modes
  (testing "get-download-modes with test-info-1"
    (is (= '("flashhigh" "flashvhigh" "flashlow")
           (get-download-modes (list test-info-1))))))

(deftest test-get-url
  (testing "get-url "
    (is (= "/info?index=333"
           (get-url "333")))
    (is (= "/info?index=1"
           (get-url "1")))))

(deftest test-search-wiki
  (testing "get wikipedia url for search queries."
    (is (= "http://en.wikipedia.org/w/index.php?search=pramface"
           (wiki-url "pramface")))))

(deftest test-search-imdb
  (testing "get imdb url for search queries."
    (is (= "http://imdb.com/find?q=pramface"
           (imdb-url "pramface")))))





