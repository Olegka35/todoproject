<%--
  Created by IntelliJ IDEA.
  User: Олег
  Date: 15.06.2018
  Time: 11:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<html>
<head>
    <title>Basket</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/bootstrap-4.1.1-dist/css/bootstrap.min.css" /> " />
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/mystyle.css" /> " />
    <script type="text/javascript" src="/resources/js/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="/resources/js/jquery-ui.js"></script>
    <script type="text/javascript" src="/resources/js/index.js"></script>
    <link rel="stylesheet" href="http://ajax.aspnetcdn.com/ajax/jquery.ui/1.10.3/themes/sunny/jquery-ui.css">
    <script type="text/javascript" src="/resources/js/jquery.textchange.js"></script>
    <script type="text/javascript" src="/resources/js/moment.js"></script>
    <script type="text/javascript" src="/resources/bootstrap-4.1.1-dist/js/bootstrap.min.js"></script>
    <link href="/resources/msgGrowl/css/msgGrowl.css" rel="stylesheet" />
    <script src="/resources/msgGrowl/msgGrowl.min.js"></script>
</head>
<body>
<div class = "container-fluid">
    <div class = "row">
        <div class = "col-md-7">
        <nav class="nav nav-inline">
            <a class="nav-link active" href="#"><security:authentication property="principal.username" /></a>
            <a class="nav-link" href="/index">Back to shop</a>
            <a class="nav-link" href="<c:url value="/logout"/>">Logout</a>
        </nav>

        <table id="basket_table" class="table">
            <thead class="thead-default"><tr>
                <th>Article Name</th>
                <th>Price</th>
                <th>Num</th>
            </tr></thead>
            <tbody id="table_body">
            <c:forEach items="${basket}" var="basket_item">
                <tr class="bi_row">
                    <td class="wordwrap">${basket_item.article.type}</td>
                    <td class="wordwrap">${basket_item.article.price}$</td>
                    <td class="wordwrap">${basket_item.num}</td>
                    <td class="wordwrap"><button id="delete_basket_${basket_item.id}" type="button" class="btn btn-primary delbasket">Delete</button></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        </div>
    </div>
</div>
</body>
</html>
