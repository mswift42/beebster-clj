#!/usr/bin/sh
java -jar target/beebster-clj-0.1.0-SNAPSHOT-standalone.jar &
get_iplayer --refresh 
firefox localhost:3000


