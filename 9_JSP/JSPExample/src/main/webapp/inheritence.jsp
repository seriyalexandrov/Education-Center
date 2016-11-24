<%@ page import="com.aleksandrov.jsf.example.BasePage" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Inheritence</title>
</head>
<body>
<%
    class ThisPage extends BasePage {

        private String parameter;

        @Override
        protected void parseParams() {

            parameter = request.getParameter("parameter");
        }

        @Override
        protected void printWindowContent() throws Exception {

            super.printWindowContent();

            out.print(String.format("<h2>Parameter is: %s<h2>", parameter));

            out.print("<span>");
                out.print("New parameter value: ");
                out.print("<input type=\"text\" name=\"parameter\"/>");
            out.print("</span>");

            out.print("<input type=\"submit\" value=\"Submit\">");
        }
    }
%>

<%
    try {
        ThisPage thisPage = new ThisPage();
        thisPage.setPageContext(pageContext);
        thisPage.startService();
    } catch (Exception e) {
        e.printStackTrace();
    }
%>
</body>
</html>
