<%--
  Created by IntelliJ IDEA.
  User: Nuril
  Date: 25.01.2022
  Time: 01:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form method="post">
    <label>Введите строку:
        <input name="string" type="text" value="<% out.println(request.getParameter("string")); %>">
    </label>
    <button type="submit">Обработать</button>
</form>
<%
    if(request.getAttribute("znc") != null) {
        out.println("Согласных: " + request.getAttribute("soc") + " Гласных: " + request.getAttribute("glc") + " Знаков: " + request.getAttribute("znc"));
        out.println("Согласные: " + request.getAttribute("rso"));
        out.println("Гласные: " + request.getAttribute("rgl"));
    }
%>
</body>
</html>
