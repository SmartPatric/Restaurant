<%--
  Created by IntelliJ IDEA.
  User: Марія
  Date: 11.02.2021
  Time: 15:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/fmt" prefix = "fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${param.lang}" />
<fmt:setBundle basename="MyResources"/>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<h1><fmt:message key="h.registration"/></h1>
<span class="lang">
        <form>
            <select name="lang" onchange="submit()">
                <option value=""><fmt:message key="language.change"/></option>
                <option value="en" ${language == 'en'}><fmt:message key="language.en"/></option>
                <option value="uk" ${language == 'uk'}><fmt:message key="language.ua"/></option>
            </select>
        </form>
    </span>
<a href="/"><fmt:message key="h.mainPage"/></a>
<div class="form">
    <form name="form" autocomplete="off"
          action="/registration" method="post">
        <label text="Email" for="email"></label><br>
        <input type="text"
               required
               name="email"
               id="email"
               placeholder="Email"/>
        <br>
        <label text="password" for="password"></label><br>
        <input type="password"
               id="password"
               name="password"
               required
               placeholder="Password"/>
        <input class="sbm-btn" type="submit" th:attr="value = #{submit}"/>
    </form>
</div>
</body>
</html>
