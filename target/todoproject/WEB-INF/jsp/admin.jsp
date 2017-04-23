<%--
  Created by IntelliJ IDEA.
  User: Олег
  Date: 07.04.2017
  Time: 22:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
    <title>Admin Page</title>
</head>
<body>
<p>Список пользователей</p>
<table id="results" class="lux">
    <tbody>
    <c:forEach items="${userList}" var="user">
        <tr id="${user.ID}">
            <td>${user.ID}</td>
            <td>${user.login}</td>
            <td><a href="../todolist/${user.ID}"><span class="icon">Показать задачи</span></a></td>
            <td><a href="admin/${user.ID}"><span class="icon">Удалить</span></a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
