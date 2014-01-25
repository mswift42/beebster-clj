(ns beebster-clj.config)

(def config {:terminal "gnome-terminal"
             :download-folder (apply str "--working-directory=" (System/getenv "HOME") "/Videos")})




