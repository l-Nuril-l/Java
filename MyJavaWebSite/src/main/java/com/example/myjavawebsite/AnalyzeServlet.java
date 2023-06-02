package com.example.myjavawebsite;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "analyzeServlet", value = "/analyze-servlet")
public class AnalyzeServlet extends HttpServlet {

    public void init() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/analyze.jsp");
        try {
            requestDispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/analyze.jsp");
        String str = req.getParameter("string");
        String gl = "АОИЕЁЭЫУЮЯAEIOUY";
        gl = gl + gl.toLowerCase();
        String zn = ",.!?";
        String so = "БВГДЖЗЙКЛМНПРСТФХЦЧШЩBCDFGHJKLMNPQRSTVWXYZ";
        so = so + so.toLowerCase();

        String rgl = "";
        String rso = "";
        int soc , glc, znc;
        soc = glc = znc = 0;
        for (char c: str.toCharArray()
             ) {
            if (gl.contains(String.valueOf(c)))
            {
                glc++;
                rgl  = rgl.concat(String.valueOf(c));
            }
            else if (zn.contains(String.valueOf(c)))
            {
                znc++;
            }
            else if (so.contains(String.valueOf(c)))
            {
                soc++;
                rso  = rso.concat(String.valueOf(c));
            }
        }


        req.setAttribute("soc",soc);
        req.setAttribute("glc",glc);
        req.setAttribute("znc",znc);
        req.setAttribute("rso",rso);
        req.setAttribute("rgl",rgl);
        try {
            requestDispatcher.forward(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
    }
}