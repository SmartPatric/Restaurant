<%--
  Created by IntelliJ IDEA.
  User: Марія
  Date: 16.02.2021
  Time: 16:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${param.lang}"/>
<fmt:setBundle basename="MyResources"/>
<html lang="en">
<head>
    <title>Restaurant</title>
    <meta content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css" type="text/css"/>
</head>
<body>
<h1><fmt:message key="h.restaurant"/></h1>
<div class="navigation">
    <div class="reg">
        <%--        <a sec:authorize="!isAuthenticated()" th:href="@{/registration}" th:text="#{titleReg}"></a>
                <a sec:authorize="!isAuthenticated()" th:href="@{/login}" th:text="#{logIn}"></a>
                <a sec:authorize="isAuthenticated()" th:href="@{/logout}" th:text="#{logout}"></a>
                <a sec:authorize="hasAuthority('ADMIN')" th:href="@{/adminPage}" th:text="#{page.admin}"></a>
                <a sec:authorize="hasAuthority('USER')" th:href="@{/userCabinet}" th:text="#{cabinet}"></a>--%>
    </div>
    <div class="lang">
        <form>
            <select name="lang" onchange="submit()">
                <option value=""><fmt:message key="language.change"/></option>
                <option value="en" ${language == 'en'}><fmt:message key="language.en"/></option>
                <option value="uk" ${language == 'uk'}><fmt:message key="language.ua"/></option>
            </select>
        </form>
    </div>
</div>

<div class="menu">
    <c:forEach var="dish" items="${dishes}">
        <div class="dish">
            <img src="${pageContext.request.contextPath}/images/${dish.image}" alt="dish img"/>
            <h3>${dish.name}</h3>
            <p>${dish.price}</p>
            <div class="order-btn">
                <form action="/userCabinet" method="post">
                    <input type='hidden' id='pay' name='pay' value='false'>
                    <input type='hidden' id='DishId' name='DishId' value='${dish.id}'>
                    <button type="submit">Buy</button>
                </form>
            </div>
        </div>
    </c:forEach>
</div>

</body>
</html>
