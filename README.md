Beebster
========

Webgui for [get-iplayer](http://www.infradead.org/get_iplayer/html/get_iplayer.html)

Introduction
============

get-iplayer is a wonderful, wonderful program. However, it can be a bit tedious to constantly type <code>get-iplayer --category crime</code>, or <code>get-iplayer -i 345 | grep desc:</code>.

Thus beebster was born. You can search for a programme, or pick a category (e.g. crime) from the navbar.

For downloading a programme, you can pick the desired quality,
available modes are `flashhd` (highest, but only available for some programmes),
`flashvhigh`, `flashhigh` to `flashlow` with the lowest quality.


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

Configuration
-------------

Adapt file `config.clj` to your needs.
By default it uses gnome-terminal, and as Download destination $HOME/Videos.


Screenshots
------------

![category](https://github.com/mswift42/beebster-clj/raw/master/Screenshot-cat.png)
![info](https://github.com/mswift42/beebster-clj/raw/master/Screenshot-info.png)


