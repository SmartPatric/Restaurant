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

<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="MyResources"/>
<html lang="en">
<head>
    <title>Restaurant</title>
    <meta content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
          integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
            crossorigin="anonymous"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
</head>
<body>
<h1><fmt:message key="h.restaurant"/></h1>
<nav class="nav">
   <c:if test="${role!='USER' && role!='ADMIN'}">
    <a class="nav-link" href="${pageContext.request.contextPath}/restaurant/login"><fmt:message key="h.login"/></a>
    <a class="nav-link" href="${pageContext.request.contextPath}/restaurant/registration"><fmt:message key="h.registration"/></a>
   </c:if>
    <c:if test="${role=='USER' || role=='ADMIN'}">
        <a class="nav-link" href="${pageContext.request.contextPath}/restaurant/logout"><fmt:message key="h.logout"/></a>
    </c:if>
    <c:if test="${role=='USER'}">
        <a class="nav-link" href="${pageContext.request.contextPath}/restaurant/userCabinet"><fmt:message key="h.userCabinet"/></a>
    </c:if>
    <c:if test="${role=='ADMIN'}">
        <a class="nav-link" href="${pageContext.request.contextPath}/restaurant/admin"><fmt:message key="h.adminPage"/></a>
    </c:if>
    <div class="lang">
        <form>
            <select name="sessionLocale" onchange="submit()">
                <option value=""><fmt:message key="language.change"/></option>
                <option value="en"><fmt:message key="language.en"/></option>
                <option value="uk"><fmt:message key="language.ua"/></option>
            </select>
        </form>
    </div>
    <span><fmt:message key="sort"/>:</span>
    <a href="${pageContext.request.contextPath}/restaurant/main?page=${currentPage}&sortField=price&sortDir=${sortDirReversed}&choose=${choose}"><fmt:message key="sort.price"/></a>
    <a href="${pageContext.request.contextPath}/restaurant/main?page=${currentPage}&sortField=name&sortDir=${sortDirReversed}&choose=${choose}"><fmt:message key="sort.name"/></a>
    <br>
    <a href="${pageContext.request.contextPath}/restaurant/main?page=1&sortField=${sortField}&sortDir=${sortDir}&choose=all"><fmt:message key="sort.all"/></a>
    <a href="${pageContext.request.contextPath}/restaurant/main?page=1&sortField=${sortField}&sortDir=${sortDir}&choose=pizza"><fmt:message key="sort.pizza"/></a>
    <a href="${pageContext.request.contextPath}/restaurant/main?page=1&sortField=${sortField}&sortDir=${sortDir}&choose=drink"><fmt:message key="sort.drink"/></a>
    <a href="${pageContext.request.contextPath}/restaurant/main?page=1&sortField=${sortField}&sortDir=${sortDir}&choose=dessert"><fmt:message key="sort.dessert"/></a>
    <a href="${pageContext.request.contextPath}/restaurant/main?page=1&sortField=${sortField}&sortDir=${sortDir}&choose=salad"><fmt:message key="sort.salad"/></a>
</nav>

<div class="menu">
    <c:forEach var="dish" items="${dishesList}">
        <div class="dish col-lg-4">
            <img src="/images/${dish.image}" alt="dish img"/>
            <h3>${dish.name}</h3>
            <fmt:message key="list.dishes.currency_course" var="currency_course"/>
            <p><fmt:formatNumber value="${dish.price/currency_course}" maxFractionDigits="2"/> <fmt:message key="currency"/></p>
            <div class="order-btn">
                <form action="${pageContext.request.contextPath}/restaurant/userCabinet/Post" method="post">
                    <input type='hidden' id='DishId' name='DishId' value='${dish.id}'>
                    <button type="submit"><fmt:message key="but.buy"/></button>
                </form>
            </div>
        </div>
    </c:forEach>
</div>

<c:if test="${noOfPages > 1}">
    <div class="col-lg-12">
            <%--For displaying Previous link except for the 1st page --%>
        <c:if test="${currentPage != 1}">
            <span><a href="${pageContext.request.contextPath}/restaurant/main?page=${currentPage - 1}&sortField=${sortField}&sortDir=${sortDir}&choose=${choose}"><fmt:message key="page.previous"/></a></span>
        </c:if>
            <%--For displaying Page numbers.
            The when condition does not display a link for the current page--%>
        <c:forEach begin="1" end="${noOfPages}" var="i">
            <c:choose>
                <c:when test="${currentPage == i}">
                    <span class="disabled">${i}</span>
                </c:when>
                <c:otherwise>
                    <span><a href="${pageContext.request.contextPath}/restaurant/main?page=${i}&sortField=${sortField}&sortDir=${sortDir}&choose=${choose}">${i}</a></span>
                </c:otherwise>
            </c:choose>
        </c:forEach>
            <%--For displaying Next link --%>
        <c:if test="${currentPage < noOfPages}">
            <span><a href="${pageContext.request.contextPath}/restaurant/main?page=${currentPage + 1}&sortField=${sortField}&sortDir=${sortDir}&choose=${choose}"><fmt:message key="page.next"/></a></span>
        </c:if>
    </div>
</c:if>
</body>
</html>