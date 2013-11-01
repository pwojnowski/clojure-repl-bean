<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Clojure REPL</title>
    <script type="text/javascript" src="http://code.jquery.com/jquery-2.0.2.js"></script>
    <script type="text/javascript" src="repl.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            repl.setupKeys();
            repl.clear();
            repl.println('Hack and be marry!');
            repl.prompt();
        })
    </script>
</head>

<body>
<h3>Clojure REPL</h3>
<div style="display: inline-block; width: 100%">
    <textarea id="repl" rows="20" cols="150" readonly style="border: 1px solid black"></textarea>
</div>
</body>
</html>
