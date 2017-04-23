<%--
  Created by IntelliJ IDEA.
  User: Олег
  Date: 23.04.2017
  Time: 8:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Register Page</title>
</head>

<body>

<div class="container" style="width: 300px;">
    <form action="/reg" method="post">
        <h2>Введите логин и пароль:</h2>
        <input type="text" name="login" placeholder="Login" required autofocus>
        <input type="password" name="password" placeholder="Password" required>
        <button type="submit">Зарегистрироваться</button>
    </form>
</div>

<h2>Уже зарегистрированы? <a href="/login">Авторизуйтесь</a></h2>

</body>
</html>