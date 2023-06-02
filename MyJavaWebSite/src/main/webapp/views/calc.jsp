<%--
  Created by IntelliJ IDEA.
  User: Nuril
  Date: 25.01.2022
  Time: 01:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form method="post">
    <label>Number 1:
        <input name="num1" type="number">
    </label>
    <label>Number 2:
        <input name="num2" type="number">
    </label>
    <label>Add
        <input type="radio" name="radio" value="add" checked>
    </label>
    <label>Minus
        <input type="radio" name="radio" value="min">
    </label>
    <label>Mul
        <input type="radio" name="radio" value="mul">
    </label>
    <label>Div
        <input type="radio" name="radio" value="div">
    </label>
    <label>Pov
        <input type="radio" name="radio" value="pov">
    </label>
    <label>Percent
        <input type="radio" name="radio" value="per">
    </label>
    <button type="submit">=</button>
</form>
<%
    Double res = (Double) request.getAttribute("res");
    if(res != null) {
        out.println("Результат: " + res);
    }
%>
</body>
</html>
