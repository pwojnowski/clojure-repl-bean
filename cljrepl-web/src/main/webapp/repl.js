var keys = {
    ARROW: 0,
    BACKSPACE: 8,
    ENTER: 13
}
var repl = {
    id: '#repl',
    expr: '',
    clear: function() {
        $(repl.id).val('');
    },
    print: function(text) {
        $(repl.id).val( function(i, content) { return content + text; } );
    },
    println: function(text) {
        repl.print(text + '\n');
    },
    printError: function(req, status, err ) {
        console.log('Error evaluating expression: ' + req, status, err);
        repl.println('Error evaluating expression: ' + req + ':\n' + err);
    },
    printResult: function(resp) {
        console.log('Received response: ' + resp)
        repl.println(resp);
        repl.prompt();
    },
    prompt: function() {
        repl.print('> ');
        $(repl.id).focus();
    },
    eval: function(expression) {
        console.log("Evaluating expr: \"" + expression + "\"");
        $.ajax({
            type: 'POST',
            data: ({expr: expression}),
            dataType: 'text',
            success: repl.printResult,
            error: repl.printError
        });
    },
    setupKeys: function() {
        $(repl.id).keypress( function(event) {
            var key = String.fromCharCode(event.which);
            if (event.ctrlKey) {
                console.log('Pressed: C-' + key);
            } else {
                console.log('Pressed: ' + key + '(' + event.which + ')');
            }

            if (event.which == keys.BACKSPACE) {
                repl.expr = repl.expr.substr(0,repl.expr.length-1);
                $(repl.id).val(function(i, content) {
                    return content.substr(0, content.length-1);
                });
            } else if (event.which == keys.ENTER && event.ctrlKey) {
                if (repl.expr.trim().length > 0) {
                    repl.print(key);
                    var exp = repl.expr;
                    repl.expr = '';
                    repl.eval(exp);
                }
            } else if (!event.ctrlKey && event.which != keys.ARROW) {
                repl.expr += key;
                repl.print(key);
            } else {
                console.log("Unknown sequence: C-" + event.which);
            }
        })
    }
}