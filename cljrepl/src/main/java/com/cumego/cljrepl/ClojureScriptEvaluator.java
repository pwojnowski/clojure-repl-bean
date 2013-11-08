package com.cumego.cljrepl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptException;

/**
 * Evaluator of Clojure code.
 */
@Component
public class ClojureScriptEvaluator extends ScriptEvaluator {

    private static final Logger log = LoggerFactory.getLogger(ClojureScriptEvaluator.class);

    private static final String NREPL_INIT_CODE
            = "(use '[clojure.tools.nrepl.server :only (start-server stop-server)])";
    private static final String NREPL_START_SERVER
            = "(start-server :port %d)";

    @Value("#{systemProperties['cljrepl.start']?:false}")
    private boolean autostart;

    @Value("#{systemProperties['cljrepl.port']?:4242}")
    private int port;

    @Resource
    private ApplicationContext context;

    public ClojureScriptEvaluator() {
        super("Clojure");
    }

    @PostConstruct
    public final void init() throws ScriptException {
        Bindings bindings = getEngine().createBindings();
        bindings.put("*context*", context);
        getEngine().setBindings(bindings, ScriptContext.ENGINE_SCOPE);
        log.debug("Loading nrepl...");
        eval(NREPL_INIT_CODE);
        if (autostart) {
            log.debug("Starting nrepl on port {}", port);
            eval(String.format(NREPL_START_SERVER, port));
        }
    }
}
