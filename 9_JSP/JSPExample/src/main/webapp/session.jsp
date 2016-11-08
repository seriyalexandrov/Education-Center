<%--
  Created by IntelliJ IDEA.
  User: Ser
  Date: 08/11/2016
  Time: 22:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Increment</title>
</head>
<body>
<form method="post">
    <input type="submit" value="Increment">
</form>

<%
    HttpSession s = request.getSession();
    if (s.getAttribute("count") == null) {
        s.setAttribute("count", new Integer(0));
    }
    s.setAttribute("count", ((Integer) s.getAttribute("count")) + 1);

%>
<%=s.getAttribute("count")%>
</body>
</html>
