<%--
  Created by IntelliJ IDEA.
  User: Nuril
  Date: 24.01.2022
  Time: 16:11
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
        <label>Number 3:
            <input name="num3" type="number">
        </label>
        <label>Sum
            <input type="radio" name="radio" value="sum" checked>
        </label>
        <label>Avg
            <input type="radio" name="radio" value="avg">
        </label>
        <label>Min
            <input type="radio" name="radio" value="min">
        </label>
        <label>Max
            <input type="radio" name="radio" value="max">
        </label>
        <button type="submit">Сумма</button>
    </form>
</body>
</html>
