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
    <a class="nav-link" href="${pageContext.request.contextPath}/restaurant/login"><fmt:message key="h.login"/></a>
    <a class="nav-link" href="${pageContext.request.contextPath}/restaurant/registration"><fmt:message key="h.registration"/></a>
    <a class="nav-link" href="${pageContext.request.contextPath}/restaurant/logout"><fmt:message key="h.logout"/></a>
    <c:if test="${role=='USER'}">
        <a class="nav-link" href="${pageContext.request.contextPath}/restaurant/userCabinet">User Cabinet</a>
    </c:if>
    <c:if test="${role=='ADMIN'}">
        <a class="nav-link" href="${pageContext.request.contextPath}/restaurant/admin">Admin</a>
    </c:if>
    <div class="lang">
        <form>
            <select name="lang" onchange="submit()">
                <option value=""><fmt:message key="language.change"/></option>
                <option value="en" ${language == 'en'}><fmt:message key="language.en"/></option>
                <option value="uk" ${language == 'uk'}><fmt:message key="language.ua"/></option>
            </select>
        </form>
    </div>
    <a href="/restaurant/main?page=${currentPage}&sortField=price&sortDir=${sortDirReversed}&choose=${choose}">Sort by price</a>
    <a href="/restaurant/main?page=${currentPage}&sortField=name&sortDir=${sortDirReversed}&choose=${choose}">Sort by name</a>
    <br>
    <a href="/restaurant/main?page=1&sortField=${sortField}&sortDir=${sortDir}&choose=all">All</a>
    <a href="/restaurant/main?page=1&sortField=${sortField}&sortDir=${sortDir}&choose=pizza">Pizza</a>
    <a href="/restaurant/main?page=1&sortField=${sortField}&sortDir=${sortDir}&choose=drink">Drink</a>
    <a href="/restaurant/main?page=1&sortField=${sortField}&sortDir=${sortDir}&choose=dessert">Dessert</a>
    <a href="/restaurant/main?page=1&sortField=${sortField}&sortDir=${sortDir}&choose=salad">Salad</a>
</nav>

<div class="menu">
    <c:forEach var="dish" items="${dishesList}">
        <div class="dish col-lg-4">
            <img src="/images/${dish.image}" alt="dish img"/>
            <h3>${dish.name}</h3>
            <p>${dish.price}</p>
            <div class="order-btn">
                <form action="/restaurant/userCabinet/Post" method="post">
                    <input type='hidden' id='pay' name='pay' value='false'>
                    <input type='hidden' id='DishId' name='DishId' value='${dish.id}'>
                    <button type="submit">Buy</button>
                </form>
            </div>
        </div>
    </c:forEach>
</div>

<c:if test="${noOfPages > 1}">
    <div class="col-lg-12">
            <%--For displaying Previous link except for the 1st page --%>
        <c:if test="${currentPage != 1}">
            <span><a href="/restaurant/main?page=${currentPage - 1}&sortField=${sortField}&sortDir=${sortDir}&choose=${choose}">Previous</a></span>
        </c:if>

            <%--For displaying Page numbers.
            The when condition does not display a link for the current page--%>
        <c:forEach begin="1" end="${noOfPages}" var="i">
            <c:choose>
                <c:when test="${currentPage == i}">
                    <span class="disabled">${i}</span>
                </c:when>
                <c:otherwise>
                    <span><a href="/restaurant/main?page=${i}&sortField=${sortField}&sortDir=${sortDir}&choose=${choose}">${i}</a></span>
                </c:otherwise>
            </c:choose>
        </c:forEach>

            <%--For displaying Next link --%>
        <c:if test="${currentPage < noOfPages}">
            <span><a href="/restaurant/main?page=${currentPage + 1}&sortField=${sortField}&sortDir=${sortDir}&choose=${choose}">Next</a></span>
        </c:if>
    </div>
</c:if>
</body>
</html>
