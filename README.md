Beebster
========

Webgui for [get-iplayer](http://www.infradead.org/get_iplayer/html/get_iplayer.html)


Installation:
-------------

Install [get-iplayer](https://github.com/dinkypumpkin/get_iplayer).

Clone this repository

<code>git clone https://github.com/mswift42/beebster-clj.git</code>

Package beebster

<code>cd beebster-clj</code>

<code>lein ring uberjar</code>

This will create a runnable jar which can be started with:

<code>java -jar target/beebster-clj-0.1.0-SNAPSHOT-standalone.jar</code>

If you point a web browser now to localhost:3000/ you should see beebster search page.

You could also create a small shell-script with for example something like this:

```shell
#!/usr/bin/sh
get_iplayer --refresh &
java -jar target/beebster-clj-0.1.0-SNAPSHOT-standalone.jar
firefox -new-tab localhost:3000
```


Screenshots
------------

![category](https://github.com/mswift42/beebster-clj/raw/master/Screenshot-cat.png)
![info](https://github.com/mswift42/beebster-clj/raw/master/Screenshot-info.png)


