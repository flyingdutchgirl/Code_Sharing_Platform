<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Latest</title>

    <link rel="stylesheet" href="../static/manySnippets.css">

    <link rel="stylesheet"
          href="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/styles/default.min.css">
    <script src="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/highlight.min.js" type="application/javascript"></script>
    <script type="text/javascript">hljs.initHighlightingOnLoad();</script>
</head>


<body>

<#list snippets as snip>
    <span class="date1"> ${snip.stringDate} </span>
    <br>
    <pre class="code"> <code>${snip.code}</code> </pre>

</#list>

</body>
</html>


