(ns beebster-clj.views
  (:use hiccup.core hiccup.element hiccup.util hiccup.page hiccup.form beebster-clj.core [clojure.java.shell :only [sh]]))

(def categories
  '("popular" "highlights" "films" "nature"
    "crime" "sitcom" "sport" "thriller"))

(defn header
  "universal bootstrap header for all beebster sites."
  [links]
  (html
   [:nav.navbar.navbar-inverse.navbar-fixed-top {:role "navigation"}
    [:div.navbar-inner
     [:div.container
      [:ul.nav.navbar-left
       (for [link categories]
         [:li.active.navbar-left
          [:a {:href (apply str "/categories?category=" link)} link]])
       [:ul.nav.navbar-right
        (for [[url description] links]
          [:li.active.navbar-right [:a {:href url} description]])]]]]]))

(defn base-template
  "base html template for all beebster sites."
  [title]
  (html
   [:head
    [:title title]
    (include-css "/css/bootstrap.min.css")
    (include-css "/css/beebster.css")]))


(defmacro category-template 
  "macro for category links."
  [url cat header]
  `(base-template
    (:title ~header)
    (for [i categories]
      (html ([:a.ms {:href (apply str "/" i)} i])))
    (:h3 :id "header" ,header)
    (display-results (search-categories ~header))))


(defn index-page
  "html for index page"
  []
  (html
   (base-template "Search")
   (header '(("/about" "about")))
   [:h2.header "Search"]
   [:div.sform
    [:form {:method "post" :role "form" :action "/results"} 
     [:div.tfield
      [:p "Enter Search term:"]
      [:input.form-control {:type "text" :name "searchvalue" }]]
     [:div.sbut
      [:input {:type "submit" :value "Search" :class "searchbutton"}]]]]))

(defn about-page
  []
  (html
   (base-template "About beebster")
   (header '(("/" "search")))
   [:h2.header "Beebster"]
   [:br]
   [:p "Beebster, a Web Gui for "]
   [:a {:href "http://www.infradead.org/get_iplayer/html/get_iplayer.html"}
    "get-iplayer"]))


(defn combine-list
  "zip thumb, title and index from search into one list."
  [search]
  (map list (get-thumb-from-search search)
       (get-title-and-episode search)
       (get-index-from-search search)))

(defn display-results
  "check if search contaings iplayer's warning notice for 
   expired programmes. If not, and if search is succesful
   loop through list to display thumbnail and title
   in 2 columns."
  [resultlist]
  (cond
   (nil? resultlist)
   (html
    [:p "No matches found."])
   (some #(re-find #"These programmes should" %) resultlist)
   (html
    (for [i resultlist]
      [:p (str i)]))
   :else
   (let [comb (combine-list resultlist)]
     (html
      [:div.rtable
       (for [[img desc ind] comb]
         [:div.table
          [:div.tablecell
           [:div.t1
            [:a {:href (get-url ind) :alt desc}
             [:img.img {:src img}]]]
           [:div.t1
            [:div.imgtitle
             (str desc)]]]])]))))



(defn category-page
  [category]
  (html
   (base-template "Categories")
   (header '(("/" "search") ("/about" "about")))
   [:h2.header (str category)]
   (display-results (search-categories category))))

(defn result-page
  [searchterm]
  (html
   (base-template "Search Results")
   (header '(("/" "search") ("/about" "about")))
   [:h2.header "Search Results"]
   (display-results (iplayer-search searchterm))))


(defn info-page
  [index]
  (let [[thumb desc title modes] (load-thumbandinfo-for-index index)]
    (html
     (base-template "Info")
     (header '(("/" "search") ("/about" "about")))
     [:h2.header "Info"]
     [:div.infotitle
      [:p [:strong (first title)]]]
     [:div.infothumb
      [:img {:src (first thumb)}]]
     [:div.infoform
      [:form.form-inline
       {:role "form" :method "post" :action (apply str "/download?index=" index)}
       [:div.form-group
        [:select.searchbutton {:name "mode" :label.sr-only "download modes"}
         (for [i modes]
           [:option {:value i :selected (= i "mode") } i])]]
       [:div.form-group
        [:input {:type "submit" :value "Download" :class "searchbutton"
                 :label.sr-only "download-modes"}]]]]
     [:div.iplayerinfo
      [:p (first desc)]])))

(defn download-page
  [index mode]
  (let [prog (future (apply sh  (iplayer-download-command index mode)))]
    (html
     (base-template "Download")
     (header '(("/" "search") ("/about" "about")))
     [:div.download
      [:p (apply str "Downloading programme with index: " index )]])))
