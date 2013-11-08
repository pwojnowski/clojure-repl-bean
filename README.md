Clojure REPL bean with access to Spring context
===============================================

It's a Clojure REPL as Spring bean with Spring application context bound to the <span>*context*</span> var.

To make this bean available in your application just add package scan on: *com.cumego.cljrepl*.

The REPL can be accessed in two ways:
1. Bean can start *nrepl* for remote access:
  - -Dcljrepl.start=true - automatically starts nrepl after the bean has been created
  - -Dcljrepl.port=XXXX - port on which nrepl should be accessible (default: 4242)

   The REPL starts on *localhost*.

2. Access from a web console available in **cljrepl-web** module.
  Use maven overlays to add the WAR to your project.
  * TODO describe maven overlays configuration
