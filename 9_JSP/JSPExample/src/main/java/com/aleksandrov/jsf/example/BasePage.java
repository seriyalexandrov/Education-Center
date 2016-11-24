package com.aleksandrov.jsf.example;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import java.io.IOException;

public abstract class BasePage {

    protected PageContext pageContext;
    protected JspWriter out;

    public void setPageContext(PageContext pageContext) {

        this.pageContext = pageContext;
        this.out = pageContext.getOut();
    }

    public void startService() throws Exception {

        out.print("<form method=\"post\">");

        parseParams();
        printWindowContent();

        out.print("</form>");
    }

    protected void parseParams() {}

    protected void printWindowContent() throws Exception {

        printDefaultContent();
    }

    private void printDefaultContent() throws IOException {
        out.print("<h1>This is a default header</h1>");
    }
}
