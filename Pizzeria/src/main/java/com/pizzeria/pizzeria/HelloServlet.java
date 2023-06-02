package com.pizzeria.pizzeria;

import java.io.*;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.net.ssl.SSLSocketFactory;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.swing.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher d =  request.getRequestDispatcher("views/main.jsp");
        d.forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher d = req.getRequestDispatcher("views/main.jsp");
        d.forward(req,resp);
        req.setCharacterEncoding("utf-8");
        Mail.send(req.getParameter("email"),"Ваш заказ MIYULI PIZZA",
                "Hi " + req.getParameter("name") + '\n'
                + req.getParameter("address") + '\n'
                + req.getParameter("number") + '\n'
                + req.getParameter("pizza") + '\n'
                + req.getParameter("size") + '\n'
                + (!req.getParameter("pineapple").equals("0") ? "Pineapples: " + req.getParameter("pineapple") +'\n' : "")
                + (!req.getParameter("capers").equals("0") ? "Capers: " + req.getParameter("capers") +'\n' : "")
                + (!req.getParameter("cheese").equals("0") ? "Cheese: " + req.getParameter("cheese") +'\n' : "")
                + (!req.getParameter("olives").equals("0") ? "Olives: " + req.getParameter("olives") +'\n' : ""));
    }

    public void destroy() {
    }
}