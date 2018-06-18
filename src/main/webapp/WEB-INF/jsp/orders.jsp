<%--
  Created by IntelliJ IDEA.
  User: Олег
  Date: 17.06.2018
  Time: 14:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<html>
<head>
    <title>Order list</title>
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
            <table id="orders" class="lux">
                <thead><tr>
                    <th onclick="sort('object_id');" width="20px">Article</th>
                    <th onclick="sort('Type');" width="125px">Name</th>
                    <th onclick="sort('Price');" width="30px">Price</th>
                    <th onclick="sort('Num');" width="20px">On stock</th>
                </tr></thead>
                <tbody id="table_body" class="stripy">
                <c:forEach items="${articleList.articleList}" var="article">
                    <tr id="${article.id}" class="article_row">
                        <td>${article.id}</td>
                        <td class="wordwrap">${article.type}</td>
                        <td class="wordwrap">${article.price}$</td>
                        <td class="wordwrap">${article.num}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <br/>
            <button id="PrevPage"><<< BACK</button>
            <div id="PageCounter">PAGE ${articleList.page} OF ${articleList.pageNum}</div>
            <button id="NextPage">NEXT >>></button>
        </div>
        <div class = "col-md-5">
            <td style="vertical-align:top;padding:10px;">
                <input type="text" id="search"/>
                <button onclick="searchArticle(); return false;">Search</button>
                <br/><br/><br/>
                <div class="card bg-light mb-3" style="max-width: 18rem;">
                    <div id="description" class="card-body">
                        Select an article from the list.
                    </div>
                </div>
            </td>
            <script language="JavaScript" type="text/javascript">
                $('#search').val('${articleList.search}');
            </script>
        </div>
</body>
</html>
