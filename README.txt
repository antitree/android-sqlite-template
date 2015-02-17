Here's a template that should be pretty simple to use. Just drop the helper library
into the app and then call it from your Activity or View or whatever. I've created 
an example Activity to test adding content to it and I extended the helper class
to let you update/delete/retrieve values for when you get to that point. I'm 
pretty sure I write in some old school style so sorry if there are compatibility
issues. :) 

The database right now looks like:
 * id: index autoincrements (do not supply)
 * ssid: text
 * bssid: text
 * lat: text
 * lon: text
(feel free to change the types but that should suffice)

It's pretty easy to extend the schema if this doesn't work. Just look through
the helper class and add extra variables everywhere.

Mark Manning