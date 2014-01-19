(ns beebster-clj.views
  (:use hiccup.core hiccup.util hiccup.page hiccup.form))

(def categories
  '("search" "popular" "highlights" "films" "nature"
    "crime" "sitcom" "sport" "thriller"))

(defn header
  "universal bootstrap header for all beebster sites."
  [links]
  (html
   [:div.navbar.navbar-inverse.navbar-fixed-top
    [:div.navbar-inner
     [:div.container
      [:div.nav-collapse.collapse
       [:ul.nav
        (for [[url description] links]
          [:li.active [:a {:href url} description]])
        (for [link categories]
          [:li.active
           [:a {:href (apply str "/categories?category=" link)} link]])]]]]]))

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
   (base-template "Index")
   (header '(("/about" "About")))
   [:div.sform
    [:form {:method "post" :role "form" :action "/results"} 
     [:div.form-group
      [:input.form-control {:type "text" :name "searchvalue"}]]
     [:div.form-group
      [:input {:type "submit" :value "Search" :class "btn btn-default"}]]]]))

(defn about-page
  []
  (html
   (base-template "About beebster")
   (header '(("/" "Index")))
   [:h2.header "Beebster"]
   [:br]
   [:p "Beebster, a Web Gui for "]
   [:a {:href "http://www.infradead.org/get_iplayer/html/get_iplayer.html"}
    "get-iplayer"]))

(defn category-page
  [category]
  (html
   (base-template "Categories")
   (header '(("/" "Index") ("/about" "About")))
   [:h2.header (str category)]))

