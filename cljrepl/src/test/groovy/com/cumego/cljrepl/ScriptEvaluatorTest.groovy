package com.cumego.cljrepl

import spock.lang.Specification

class ScriptEvaluatorTest extends Specification {

    def ScriptEvaluator engine

    def 'should return list of all available engines'() {
        when:
        def Map<String, List<String>> engines = ScriptEvaluator.listEngines()

        then:
        !engines.isEmpty()
        engines.each { String key, List<String> names ->
            println("$key, engine names: $names")
        }
    }

    def 'should return ScriptEvaluator for Clojure'() {
        expect:
        new ClojureScriptEvaluator() != null
    }

    def 'should evaluate sample script'() {
        given:
        engine = new ClojureScriptEvaluator()

        when:
        def result = engine.eval('(+ 2 2)')

        then:
        result == 4
    }
}
