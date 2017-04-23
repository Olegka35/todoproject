<%--
  Created by IntelliJ IDEA.
  User: Олег
  Date: 05.03.2017
  Time: 23:19
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/mystyle.css" /> " />
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js"></script>
    <script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script type="text/javascript" src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script type="text/javascript" src="/resources/js/myjs.js"></script>
    <%--<script type="text/javascript" src="jquery-latest.js"></script>--%>
    <script type="text/javascript" src="/resources/js/jquery.tablesorter.js"></script>

    <title>ToDoList Page</title>
</head>

<body>
<security:authorize access="isAuthenticated()">
    <a href="<c:url value="/logout"/>">Logout</a>
</security:authorize>
<security:authorize access="hasRole('ROLE_ADMIN')">
    | <a href="<c:url value="/admin/"/>">Admin Page</a>
</security:authorize>
<p class="header">Задачи пользователя ${taskList.login}</p>
<button class="addbutton" name="addtask" onclick="showInsert(); return false;">Добавить</button>

<table>
    <tr>
        <td>
            <table id="results" class="lux">
                <thead><tr><th>ID</th><th>Название</th><th>Описание</th><th>Приоритет</th><th>Статус</th><th></th><th></th></tr></thead>
                <tbody class="stripy">
                <c:forEach items="${taskList.taskList}" var="task">
                    <tr id="${task.id}">
                        <td>${task.id}</td>
                        <td id="toDo-${task.id}-name">${task.name}</td>
                        <td id="todo-${task.id}-desc">${task.description}</td>
                        <td id="todo-${task.id}-prior"><script language="JavaScript" type="text/javascript">document.getElementById("todo-${task.id}-prior").innerHTML = getPriority(${task.priority});</script></td>
                        <td id="todo-${task.id}-status"><script language="JavaScript" type="text/javascript">document.getElementById("todo-${task.id}-status").innerHTML = getStatus(${task.status});</script></td>
                        <td><a class="show_popup" rel="edit" onclick="showEdit(${task.id}, '${task.name}', '${task.description}', '${task.priority}', '${task.status}'); return false;"><span class="icon">✍</span></a></td>
                        <td><a href="delete/${task.id}"><span class="icon">✖</span></a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </td>
        <td style="vertical-align:top;padding:10px;">
            <label for="filter">Статус:</label>
            <select name="filter" id="filter" size="1" required>
                <option value="1">Все</option>
                <option value="2">В ожидании</option>
                <option value="3">В работе</option>
                <option value="4">Завершено</option>
            </select>
            <button onclick="filterTable($('#filter').find('option:selected').val());return false;">Фильтр</button>
        </td>
    </tr>
</table>

<div class="popup edit">
    <a class="close" href="#">✖</a>
    <h2>Изменение задачи</h2>
    <form method="post" action="edit/">
        <label for="ID">ID:</label>
        <input type="text" name="ID" id="ID" readonly />
        <label for="name">Название:</label>
        <input type="text" name="name" id="name" required/>
        <label for="descr">Описание:</label>
        <input type="text" name="descr" id="descr" required/>
        <label for="priority">Приоритет:</label>
        <select name="priority" id="priority" size="1" required>
            <option value="1">Низкий</option>
            <option value="2">Средний</option>
            <option value="3">Высокий</option>
            <option value="4">Срочный</option>
        </select><br/>
        <label for="status">Статус:</label>
        <select name="status" id="status" size="1" required>
            <option value="1">В ожидании</option>
            <option value="2">В работе</option>
            <option value="3">Завершено</option>
        </select>
        <input type="submit" value="Обновить" />
    </form>
</div>

<div class="popup add">
    <a class="close" href="#">✖</a>
    <h2>Добавление задачи</h2>
    <form method="post" action="add/">
        <label for="addname">Название:</label>
        <input type="text" name="addname" id="addname" required/>
        <label for="adddescr">Описание:</label>
        <input type="text" name="adddescr" id="adddescr" required/>
        <input type="submit" value="Добавить" />
        <label for="addpriority">Приоритет:</label>
        <select name="addpriority" id="addpriority" size="1" required>
            <option value="1">Низкий</option>
            <option value="2">Средний</option>
            <option value="3">Высокий</option>
            <option value="4">Срочный</option>
        </select><br/>
        <label for="addstatus">Статус:</label>
        <select name="addstatus" id="addstatus" size="1" required>
            <option value="1">В ожидании</option>
            <option value="2">В работе</option>
            <option value="3">Завершено</option>
        </select>
    </form>
</div>

</body>
</html>