<%@ page import="com.my.restaurant.models.Cart" %><%--
  Created by IntelliJ IDEA.
  User: Марія
  Date: 11.02.2021
  Time: 12:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Show Cart</title>
</head>
<body>
    <% Cart cart = (Cart)session.getAttribute("cart"); %>

    <h1>Cart</h1>
    <p>Name: </p><%= cart.getName()%>
    <p>Quantity: </p><%= cart.getQuantity()%>
</body>
</html>
