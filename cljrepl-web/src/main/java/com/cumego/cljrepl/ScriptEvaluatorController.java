package com.cumego.cljrepl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.annotation.Resource;
import javax.script.ScriptException;

@Controller
@RequestMapping("/repl")
public class ScriptEvaluatorController {

    private static final String JSP_NAME = "repl";

    @Resource
    private ClojureScriptEvaluator evaluator;

    @RequestMapping(method = RequestMethod.GET)
    public final String getPrompt() {
        return JSP_NAME;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public final String evaluate(@RequestParam("expr") final String expr) {
        try {
            return evaluator.eval(expr).toString();
        } catch (ScriptException e) {
            return e.getMessage();
        }
    }
}
