package com.example.myjavawebsite;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "calcServlet", value = "/calc-servlet")
public class CalcServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/calc.jsp");
        try {
            requestDispatcher.forward(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/calc.jsp");

        Double n1 = Double.parseDouble(req.getParameter("num1"));
        Double n2 = Double.parseDouble(req.getParameter("num2"));
        Double res = 0.0;
        switch (req.getParameter("radio"))
        {
            case "add":
                res = n1 + n2;
                break;
            case "min":
                res = n1 - n2;
                break;
            case "mul":
                res = n1*n2;
                break;
            case "div":
                res = n1 / n2;
                break;
            case "pov":
                res = Math.pow(Double.valueOf(n1),n2);
                break;
            case "per":
                res = n2 * n1 / 100;
                break;
        }

        req.setAttribute("res", res);

        try {
            requestDispatcher.forward(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }
}
