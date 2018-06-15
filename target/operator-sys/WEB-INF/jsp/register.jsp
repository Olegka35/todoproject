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
    <script type="text/javascript" src="/resources/js/password_check.js"></script>
</head>

<body>

<div class="container" style="width: 300px;">
    <form action="/reg" method="post">
        <h2>Register a new account</h2>
        <input type="text" name="login" placeholder="Login" required autofocus>
        <input type="password" name="password" id="password" placeholder="Password" required>
        <input type="password" name="password2" id="password2" placeholder="Accept Password" required>
        <button type="submit">Sign up</button>
    </form>
</div>

<h2>Already registered? <a href="/login">Sign in</a></h2>

</body>
</html>