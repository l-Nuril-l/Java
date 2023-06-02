<%--
  Created by IntelliJ IDEA.
  User: Nuril
  Date: 28.01.2022
  Time: 23:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form method="post">
    <h2>Конструктор</h2>
    <label>
        <input type="radio" name="size" value="medium" checked>
    </label>Средняя
    <label>
        <input type="radio" name="size" value="large">
    </label>Большая
    <label>
        <input type="radio" name="size" value="small">
    </label>Маленькая


    <div>
        <div>
            <label>Имя:
                <input type="text" name="name">
            </label>
        </div>
        <div>
            <label>Почта:
                <input type="email" name="email">
            </label>
        </div>
        <div>
            <label>Адрес доставки:
                <input type="text" name="address">
            </label>
        </div>
        <div>
            <label>Номер:
                <input type="text" name="number">
            </label>
        </div>
    </div>
    Ингридиенты:
    <div>
        <label>Курятина:
            <input type="number" name="chicken" value="0">
        </label>
        <label>Свинина:
            <input type="number" name="pig" value="0">
        </label>
        <label>Ветчина:
            <input type="number" name="vetchina" value="0">
        </label>
        <label>Бекон:
            <input type="number" name="bacon" value="0">
        </label>
    </div>
    Добавки:
    <div>
        <label>Ананас:
            <input type="number" name="pineapple" value="0">
        </label>
        <label>Оливки:
            <input type="number" name="olives" value="0">
        </label>
        <label>Каперсы:
            <input type="number" name="capers" value="0">
        </label>
        <label>Сыр:
            <input type="number" name="cheese" value="0">
        </label>
    </div>
    <div>
        <a href="pizza-maker-servlet">Создать свою пиццу!</a>
    </div>

    </div>
    <iframe src="https://www.google.com/maps/d/u/0/embed?mid=106vq9yaRgZRlat3JPNMAOSN4GZpWYhwi&ehbc=2E312F" style="width: 100%" width="640" height="480"></iframe>
    <button>Заказать</button>
</form>
</body>
</html>
