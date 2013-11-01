package com.cumego.cljrepl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * Evaluates given script in selected ScriptEngine.
 */
public abstract class ScriptEvaluator {

    protected final ScriptEngine engine;

    protected ScriptEvaluator(String engineName) {
        if (engineName == null) {
            throw new IllegalArgumentException("Scripting engine name must not be null!");
        }
        ScriptEngineManager engineManager = new ScriptEngineManager();
        this.engine = engineManager.getEngineByName(engineName);
        if (this.engine == null) {
            throw new IllegalStateException("Scripting engine must not be null!");
        }
    }

    /**
     * Evaluates expression in a scripting engine and returns its result.
     *
     * @param script    Script text to evaluate
     * @return result of evaluation
     * @throws ScriptException
     */
    public Object eval(final String script) throws ScriptException {
        return engine.eval(script);
    }

    /**
     * Returns all available engine names and their descriptions.
     *
     * @return map of engine name to description
     */
    public static Map<String, List<String>> listEngines() {
        Map<String,List<String>> engines = new ConcurrentHashMap<String,List<String>>();
        ScriptEngineManager engineManager = new ScriptEngineManager();
        for (ScriptEngineFactory factory : engineManager.getEngineFactories()) {
            String name = toReadableName(factory);
            if (!engines.containsKey(name)) {
                engines.put(name, new ArrayList<String>());
            }
            engines.get(name).addAll(factory.getNames());
        }
        return engines;
    }

    private static String toReadableName(ScriptEngineFactory factory) {
        return String.format("%s (%s), ver: %s", factory.getLanguageName(),
                factory.getEngineName(), factory.getLanguageVersion());
    }
}
