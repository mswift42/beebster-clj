(ns beebster-clj.views
  (:use hiccup.core hiccup.util hiccup.page))

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
          [:li.active [:a {:href url} description]])]]]]]))

(defn base-template
  "base html template for all beebster sites."
  [title]
  (html
   [:head
    [:title title]
    (include-css "/css/bootstrap.min.css")]))

(defn index-page
  "html for index page"
  []
  (html
   (base-template "Index")
   (header '(("/about" "About")))))
