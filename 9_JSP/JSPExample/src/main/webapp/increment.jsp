<%--
  Created by IntelliJ IDEA.
  User: Ser
  Date: 25/11/2016
  Time: 21:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>IncrementComponent</title>
</head>
<body>
<input type="button" value="+" onclick="increment()"/>
<div id="val">0</div>
<input type="button" value="-" onclick="decrement()"/>
</body>

<script>

    function decrement () {
        let value = parseInt($('#val').text());
        if (value > 0) {
            $('#val').text(value-1);
        }
    }

    function increment () {

        let value = parseInt($('#val').text());
        $('#val').text(value+1);

    }

</script>
</html>
