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
    <meta content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
          integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
            crossorigin="anonymous"></script>
    <style>
        body {
            padding: 30px 100px;
            text-align: center;
            background-color: #D78B93;
        }
    </style>
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
