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

<%@ taglib uri="/WEB-INF/welcome.tld" prefix="wel" %>

<%@ page isELIgnored="false" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="MyResources"/>
<html>
<head>
    <title>User Cabinet</title>
    <meta content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/user.css"/>
</head>
<h1><fmt:message key="h.userCabinet"/></h1>
<a href="${pageContext.request.contextPath}/restaurant/main"><fmt:message key="h.mainPage"/></a>
<wel:greeting  name="${name}"/>

<c:if test="${dishes!=null}">
    <fmt:message key="list.dishes.currency_course" var="currency_course"/>
    <c:forEach var="dish" items="${dishes}">
        <div class="dish-cab col-lg-12">
            <div class="dish-body">
                <span><img src="/images/${dish.image}" alt="dish img"/></span>
                <span>${dish.name}</span>
                <span><fmt:formatNumber value="${dish.price/currency_course}" maxFractionDigits="2"/> <fmt:message key="currency"/></span>
                <span>${dish.amount}</span>
                <span>${dish.totalPrice/currency_course} <fmt:message key="currency"/></span>
            </div>
            <c:if test="${orderStatus=='MAKING'}">
                <div class="dish-nav">
                    <form class="dish-cab-form" action="${pageContext.request.contextPath}/restaurant/amountChange"
                          method="post">
                        <input type='hidden' id='amountIncrease' name='amountChange' value='plus'>
                        <input type='hidden' id='dishId1' name='dishId' value='${dish.id}'>
                        <input type='hidden' id='dishPrice1' name='dishPrice' value='${dish.price}'>
                        <input type='hidden' id='orderId1' name='orderId' value='${orderId}'>
                        <button type="submit">+</button>
                    </form>
                    <form class="dish-cab-form" action="${pageContext.request.contextPath}/restaurant/amountChange"
                          method="post">
                        <input type='hidden' id='amountDecrease' name='amountChange' value='minus'>
                        <input type='hidden' id='dishId2' name='dishId' value='${dish.id}'>
                        <input type='hidden' id='dishPrice2' name='dishPrice' value='${dish.price}'>
                        <input type='hidden' id='orderId2' name='orderId' value='${orderId}'>
                        <input type='hidden' id='dishAmount' name='dishAmount' value='${dish.amount}'>
                        <button type="submit">-</button>
                    </form>
                    <form class="dish-cab-form" action="${pageContext.request.contextPath}/restaurant/amountChange"
                          method="post">
                        <input type='hidden' id='RemoveDish' name='amountChange' value='remove'>
                        <input type='hidden' id='dishId3' name='dishId' value='${dish.id}'>
                        <input type='hidden' id='dishPrice3' name='dishPrice' value='${dish.totalPrice}'>
                        <input type='hidden' id='orderId3' name='orderId' value='${orderId}'>
                        <button type="submit"><p><i class="fa fa-trash" aria-hidden="true"></i></p></button>
                    </form>
                </div>
            </c:if>
        </div>
    </c:forEach>
    <c:if test="${dishes.size() > 0}">

        <h3><fmt:message key="label.total"/>: <fmt:formatNumber value="${totalPrice/currency_course}" maxFractionDigits="2"/> <fmt:message key="currency"/></h3>
        <c:if test="${orderStatus=='MAKING'}">
            <form action="${pageContext.request.contextPath}/restaurant/userCabinet/Post" method="post">
                <input type='hidden' id='pay' name='pay' value='true'>
                <button type="submit"><fmt:message key="but.pay"/></button>
            </form>
        </c:if>
    </c:if>
    <c:if test="${orderStatus=='APPROVING'}">
        <form action="${pageContext.request.contextPath}/restaurant/userCancelOrder" method="post">
            <input type='hidden' id='order' name='orderId' value='${orderId}'>
            <button type="submit"><fmt:message key="but.cancel"/></button>
        </form>
    </c:if>
</c:if>
</body>
</html>
