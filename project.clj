(defproject beebster-clj "0.1.0-SNAPSHOT"
  :description "a gui for get-iplayer"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [ring "1.2.1"]
                 [compojure "1.1.6"]
                 [hiccup "1.0.4"]
                 [net.cgrand/moustache "1.1.0"]]
  :plugins [[lein-ring "0.8.8"]]
  :ring {:handler beebster-clj.routes/app}
  ;; {:production
  ;;  {:ring
  ;;   {:open-browser? true}}}
  :aot
  :all
  :dev-dependencies [[lein-ring "0.8.8"]])




