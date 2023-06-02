package com.example.myjavawebsite;

import java.io.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "mathServlet", value = "/math-servlet")
public class MathServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Bad programmers worry about the code. Good programmers worry about data structures and their relationships";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/math.jsp");
        try {
            requestDispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int n1 = Integer.parseInt(req.getParameter("num1"));
        int n2 = Integer.parseInt(req.getParameter("num2"));
        int n3 = Integer.parseInt(req.getParameter("num3"));
        int res = 0;
        switch (req.getParameter("radio"))
        {
            case "sum":
                res = n1 + n2 + n3;
                break;
            case "avg":
                res = (n1 + n2 + n3) / 3;
                break;
            case "min":
                res = Math.min(Math.min(n1,n2),n3);
                break;
            case "max":
                res = Math.max(Math.max(n1,n2),n3);
                break;
        }
        PrintWriter pw = resp.getWriter();
        pw.println(Integer.toString(n1) +" + "+ Integer.toString(n2) +" + "+ Integer.toString(n3) + " = " + Integer.toString(res));
    }

    public void destroy() {
    }
}