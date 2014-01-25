;; config file for beebster.
;; Replace gnome-terminal with whatever terminal program you prefer to use.
;; However, make sure to adapt the working-directory argument.
;; Beebster will work without it, but all downloaded programmes will then be
;; dumped into the beebster-clj folder.

(ns beebster-clj.config)

(def config {:terminal "gnome-terminal"
             :download-folder
             (apply str "--working-directory=" (System/getenv "HOME") "/Videos")})






