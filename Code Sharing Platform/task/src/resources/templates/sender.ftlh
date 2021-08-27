<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <title>Create</title>
</head>

<body>
<form action="localhost:8889/api/code/new" method="post">
    <label for="code_snippet"></label><textarea id="code_snippet" cols="30" rows="20">  </textarea>
</form>
Time limit (seconds): <input id="time_restriction" type="text"/>
View limit: <input id="views_restriction" type="text"/>

<button id="send_snippet" type="submit" onclick="send()">Submit</button>

<script type="application/javascript">
    function send() {
        let object = {
            "code": document.getElementById("code_snippet").value,
            "time": parseInt(document.getElementById("time_restriction").value),
            "views": parseInt(document.getElementById("views_restriction").value)
        };

        let json = JSON.stringify(object);

        let xhr = new XMLHttpRequest();
        //xhr.open("POST", 'localhost:8889/api/code/new', false)
        xhr.open("POST", '/api/code/new', false);
        xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
        xhr.send(json);

        if (xhr.status == 200) {
            alert("Success!");
        } else {
            alert(xhr.status)
        }
    }
</script>
</body>
</html>