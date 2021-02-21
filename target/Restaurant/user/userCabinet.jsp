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

<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="MyResources"/>
<html>
<head>
    <title>User Cabinet</title>
    <meta content="text/html; charset=UTF-8"/>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>
<h1><fmt:message key="h.userCabinet"/></h1>
<a href="${pageContext.request.contextPath}/restaurant/main"><fmt:message key="h.mainPage"/></a>
<c:if test="${dishes!=null}">
    <c:forEach var="dish" items="${dishes}">
        <div class="dish-cab col-lg-12">
            <div class="dish-body">
                <span><img src="/images/${dish.image}" alt="dish img"/></span>
                <span>${dish.name}</span>
                <span>${dish.price}</span>
                <span>${dish.amount}</span>
            </div>
            <c:if test="${orderStatus=='MAKING'}">
                <div class="dish-nav">
                    <form class="dish-cab-form" action="${pageContext.request.contextPath}/restaurant/amountChange"
                          method="post">
                        <input type='hidden' id='amountIncrease' name='amountChange' value='plus'>
                        <input type='hidden' id='dishId1' name='dishId' value='${dish.id}'>
                        <input type='hidden' id='orderId1' name='orderId' value='${orderId}'>
                        <button type="submit">+</button>
                    </form>
                    <form class="dish-cab-form" action="${pageContext.request.contextPath}/restaurant/amountChange"
                          method="post">
                        <input type='hidden' id='amountDecrease' name='amountChange' value='minus'>
                        <input type='hidden' id='dishId2' name='dishId' value='${dish.id}'>
                        <input type='hidden' id='orderId2' name='orderId' value='${orderId}'>
                        <input type='hidden' id='dishAmount' name='dishAmount' value='${dish.amount}'>
                        <button type="submit">-</button>
                    </form>
                    <form class="dish-cab-form" action="${pageContext.request.contextPath}/restaurant/amountChange"
                          method="post">
                        <input type='hidden' id='RemoveDish' name='amountChange' value='remove'>
                        <input type='hidden' id='dishId3' name='dishId' value='${dish.id}'>
                        <input type='hidden' id='orderId3' name='orderId' value='${orderId}'>
                        <button type="submit"><p><i class="fas fa-trash"></i></p></button>
                    </form>
                </div>
            </c:if>
        </div>
    </c:forEach>
    <c:if test="${dishes.size() > 0}">
        <h3><fmt:message key="label.total"/>: ${totalPrice}</h3>
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
