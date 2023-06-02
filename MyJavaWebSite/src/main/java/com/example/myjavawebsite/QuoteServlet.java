package com.example.myjavawebsite;

import java.io.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "quoteServlet", value = "/quote-servlet")
public class QuoteServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Bad programmers worry about the code. Good programmers worry about data structures and their relationships";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }

    public void destroy() {
    }
}