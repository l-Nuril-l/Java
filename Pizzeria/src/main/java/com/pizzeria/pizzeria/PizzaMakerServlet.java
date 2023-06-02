package com.pizzeria.pizzeria;

import java.io.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "pizzaMakerServlet", value = "/pizza-maker-servlet")
public class PizzaMakerServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher d =  request.getRequestDispatcher("views/pizzaMaker.jsp");
        d.forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Mail.send(req.getParameter("email"),"Ваш заказ MIYULI PIZZA", req.getParameter("address"));
    }

    public void destroy() {
    }
}