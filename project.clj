(defproject beebster-clj "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [enlive "1.1.1"]
                 [ring "1.2.0"]
                 [compojure "1.1.5"]
                 [net.cgrand/moustache "1.1.0"]]
  :plugins [[lein-ring "0.8.8"]]
  :ring {:handler beebster-clj.routes/app}
  :dev-dependencies [[lein-ring "0.4.5"]])


