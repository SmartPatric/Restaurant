<%--
  Created by IntelliJ IDEA.
  User: Марія
  Date: 12.02.2021
  Time: 22:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${param.lang}"/>
<fmt:setBundle basename="MyResources"/>
<html>
<head>
    <title>User Cabinet</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cabinet.css" />
</head>
<body>
<h1><fmt:message key="h.userCabinet"/></h1>
<a href="${pageContext.request.contextPath}/restaurant/main"><fmt:message key="h.mainPage"/></a>
<c:if test="${dishes!=null}">
    <div class="table">
        <table class="user_table">
            <thead>
            <tr>
                <th>Name</th>
                <th>Price</th>
                <th>Image</th>
                <th>Amount</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="dish" items="${dishes}">
                <tr>
                    <td>${dish.name}</td>
                    <td>${dish.price}</td>
                    <td>${dish.image}</td>
                    <td>${dish.amount}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <h3>Total: ${totalPrice}</h3>
    <c:if test="${orderStatus=='MAKING'}">
        <form action="/restaurant/userCabinet/Post" method="post">
            <input type='hidden' id='pay' name='pay' value='true'>
            <input type="submit" value="Оплатити"/>
        </form>
    </c:if>
</c:if>
</body>
</html>
