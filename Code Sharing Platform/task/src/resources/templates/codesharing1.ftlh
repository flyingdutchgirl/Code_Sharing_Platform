<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <title>Code</title>

    <link rel="stylesheet" href="../static/codesharing1.css">

    <link rel="stylesheet"
          href="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/styles/default.min.css">
    <script src="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/highlight.min.js" type="application/javascript"></script>
    <script type="text/javascript">hljs.initHighlightingOnLoad();</script>

</head>
<body>

<span id="load_date">${snippet.getStringDate()}</span> <br>

<#if snippet.isViewRestriction()>
<span id="views_restriction">${snippet.getNumberOfViews()}</span> more views are allowed<br>
</#if>

<#if snippet.isTimeRestriction()>
    <span id="time_restriction">The code will be available for ${snippet.timeLeft()} seconds</span><br>
</#if>

<pre id="code_snippet"><code>${snippet.getCode()}</code></pre>
</body>
</html>