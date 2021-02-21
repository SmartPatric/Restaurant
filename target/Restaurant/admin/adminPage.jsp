<%--
  Created by IntelliJ IDEA.
  User: Марія
  Date: 11.02.2021
  Time: 23:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="MyResources"/>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css" type="text/css"/>
    <title><fmt:message key="h.adminPage"/></title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
          integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
            crossorigin="anonymous"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css"/>
</head>
<body>
<h1><fmt:message key="h.adminPage"/></h1>
<a href="${pageContext.request.contextPath}/restaurant/main"><fmt:message key="h.mainPage"/></a>
<ul class="nav nav-tabs">
    <li><a class="nav-link active" data-toggle="tab" href="#app"><fmt:message key="status.app"/></a></li>
    <li><a class="nav-link" data-toggle="tab" href="#cook"><fmt:message key="status.cook"/></a></li>
    <li><a class="nav-link" data-toggle="tab" href="#del"><fmt:message key="status.del"/></a></li>
    <li><a class="nav-link" data-toggle="tab" href="#clo"><fmt:message key="status.clo"/></a></li>
    <li><a class="nav-link" data-toggle="tab" href="#can"><fmt:message key="status.can"/></a></li>
</ul>
<div class="tab-content col-lg-12">
    <div id="app" class="tab-pane fade selected">
        <table class="table">
            <thead>
            <tr>
                <th>ID</th>
                <th>Status</th>
                <th>User</th>
                <th>Creation</th>
                <th>Last Update</th>
                <th>Next</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="order" items="${orders_app}">
                <tr>
                    <td>${order.id}</td>
                    <td>${order.status}</td>
                    <td>${order.userId}</td>
                    <td>${order.creationDate}</td>
                    <td>${order.updateDate}</td>
                    <td>
                        <form method="post" action="/restaurant/admin/Post">
                            <input type='hidden' id='userId' name='userId' value=${order.userId}>
                            <input type="submit" value="Next">
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <div id="cook" class="tab-pane fade">
        <table class="table">
            <thead>
            <tr>
                <th>ID</th>
                <th>Status</th>
                <th>User</th>
                <th>Creation</th>
                <th>Last Update</th>
                <th>Next</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="order" items="${orders_cook}">
                <tr>
                    <td>${order.id}</td>
                    <td>${order.status}</td>
                    <td>${order.userId}</td>
                    <td>${order.creationDate}</td>
                    <td>${order.updateDate}</td>
                    <td>
                        <form method="post" action="/restaurant/admin/Post">
                            <input type='hidden' id='userId2' name='userId' value=${order.userId}>
                            <input type="submit" value="Next">
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <div id="del" class="tab-pane fade">
        <table class="table">
            <thead>
            <tr>
                <th>ID</th>
                <th>Status</th>
                <th>User</th>
                <th>Creation</th>
                <th>Last Update</th>
                <th>Next</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="order" items="${orders_dell}">
                <tr>
                    <td>${order.id}</td>
                    <td>${order.status}</td>
                    <td>${order.userId}</td>
                    <td>${order.creationDate}</td>
                    <td>${order.updateDate}</td>
                    <td>
                        <form method="post" action="/restaurant/admin/Post">
                            <input type='hidden' id='userId3' name='userId' value=${order.userId}>
                            <input type="submit" value="Next">
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <div id="clo" class="tab-pane fade">
        <table class="table">
            <thead>
            <tr>
                <th>ID</th>
                <th>Status</th>
                <th>User</th>
                <th>Creation</th>
                <th>Last Update</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="order" items="${orders_closed}">
                <tr>
                    <td>${order.id}</td>
                    <td>${order.status}</td>
                    <td>${order.userId}</td>
                    <td>${order.creationDate}</td>
                    <td>${order.updateDate}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <div id="can" class="tab-pane fade">
        <table class="table">
            <thead>
            <tr>
                <th>ID</th>
                <th>Status</th>
                <th>User</th>
                <th>Creation</th>
                <th>Last Update</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="order" items="${orders_can}">
                <tr>
                    <td>${order.id}</td>
                    <td>${order.status}</td>
                    <td>${order.userId}</td>
                    <td>${order.creationDate}</td>
                    <td>${order.updateDate}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

</div>
</body>
</html>
