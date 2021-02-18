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
    <%--    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css" type="text/css"/>--%>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
          integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
            crossorigin="anonymous"></script>
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Rubik+Mono+One&display=swap');

        body {
            padding: 30px 100px;
            text-align: center;
            background-color: #D78B93;
        }

        button {
            border: none;
            background-color: black;
            width: 150px;
            height: 40px;
            color: white;
            border-radius: 5px;
        }

        button:hover {
            background-color: #282828;
            color: #D78B93;
        }

        h1 {
            font-family: 'Rubik Mono One', sans-serif;
        }

        select {
            border: none;
            background-color: #e8d8ec;
            margin: 10px;
        }

        a {
            color: black;
            text-align: left;
        }

        span {
            font-size: 20px;
            margin: 0px 10px;
        }
    </style>
</head>
<body>
<h1><fmt:message key="h.restaurant"/></h1>

<nav class="nav">
    <a class="nav-link" href="${pageContext.request.contextPath}/login"><fmt:message key="h.login"/></a>
    <a class="nav-link" href="${pageContext.request.contextPath}/registration"><fmt:message key="h.registration"/></a>
    <a class="nav-link" href="${pageContext.request.contextPath}/logout"><fmt:message key="h.logout"/></a>
    <c:if test="${role=='USER'}">
        <a class="nav-link" href="${pageContext.request.contextPath}/userCabinet">User Cabinet</a>
    </c:if>
    <c:if test="${role=='ADMIN'}">
        <a class="nav-link" href="${pageContext.request.contextPath}/admin">Admin</a>
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
    <a href="/main?page=${currentPage}&sortField=price&sortDir=${sortDirReversed}&choose=${choose}">Sort by price</a>
    <a href="/main?page=${currentPage}&sortField=name&sortDir=${sortDirReversed}&choose=${choose}">Sort by name</a>
    <br>
    <a href="/main?page=${currentPage}&sortField=${sortField}&sortDir=${sortDir}&choose=pizza">Pizza</a>
    <a href="/main?page=${currentPage}&sortField=${sortField}&sortDir=${sortDir}&choose=drink">Drink</a>
    <a href="/main?page=${currentPage}&sortField=${sortField}&sortDir=${sortDir}&choose=dessert">Dessert</a>
    <a href="/main?page=${currentPage}&sortField=${sortField}&sortDir=${sortDir}&choose=salad">Salad</a>
</nav>

<div class="menu">
    <c:forEach var="dish" items="${dishesList}">
        <div class="dish col-lg-4">
                <%--<img src="${pageContext.request.contextPath}${dish.image}" alt="dish img"/>--%>
            <h3>${dish.name}</h3>
            <p>${dish.price}</p>
            <div class="order-btn">
                <form action="/userCabinet/Post" method="post">
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
            <span><a href="/main?page=${currentPage - 1}&sortField=${sortField}&sortDir=${sortDir}&choose=${choose}">Previous</a></span>
        </c:if>

            <%--For displaying Page numbers.
            The when condition does not display a link for the current page--%>
        <c:forEach begin="1" end="${noOfPages}" var="i">
            <c:choose>
                <c:when test="${currentPage == i}">
                    <span>${i}</span>
                </c:when>
                <c:otherwise>
                    <span><a href="/main?page=${i}&sortField=${sortField}&sortDir=${sortDir}&choose=${choose}">${i}</a></span>
                </c:otherwise>
            </c:choose>
        </c:forEach>

            <%--For displaying Next link --%>
        <c:if test="${currentPage < noOfPages}">
            <span><a href="/main?page=${currentPage + 1}&sortField=${sortField}&sortDir=${sortDir}&choose=${choose}">Next</a></span>
        </c:if>
    </div>
</c:if>
</body>
</html>
