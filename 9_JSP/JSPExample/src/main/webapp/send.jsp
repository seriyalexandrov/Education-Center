<html>
<head>
    <title>Echoing HTML Request Parameters</title>
</head>
<body>
<h3>Choose an author:</h3>
<form method="post">
    <input type="checkbox" name="author" value="Tan Ah Teck">Tan
    <input type="checkbox" name="author" value="Mohd Ali">Ali
    <input type="checkbox" name="author" value="Kumar">Kumar

    <input type="text" name="text"/>test
    <input type="submit" value="Query">
</form>

<%
    String text = request.getParameter("text");
    String[] authors = request.getParameterValues("author");
    if (authors != null) {
%>
<h3>You have selected author(s):</h3>
<ul>
    <%
        for (int i = 0; i < authors.length; ++i) {
    %>
    <li><%= authors[i] %></li>
    <%
        }
    %>
</ul>
<a href="<%= request.getRequestURI() %>">BACK</a>
<p><%=text%></p>
<%
    }
%>
</body>
</html>