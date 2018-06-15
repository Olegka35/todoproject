<%--
  Created by IntelliJ IDEA.
  User: Олег
  Date: 05.04.2017
  Time: 16:57
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
    <title>Login Page</title>
</head>

<body>

<div class="container" style="width: 300px;">
    <c:url value="/j_spring_security_check" var="loginUrl" />
    <form action="${loginUrl}" method="post">
        <h2>Enter login and password to authorize:</h2>
        <input type="text" name="j_username" placeholder="Login" required autofocus>
        <input type="password" class="form-control" name="j_password" placeholder="Password" required>
        <button type="submit">Sign in</button>
    </form>
</div>

<h2>Need an account? <a href="/reg">Sign up</a></h2>

</body>
</html>