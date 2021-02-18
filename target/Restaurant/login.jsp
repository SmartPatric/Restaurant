<%--
  Created by IntelliJ IDEA.
  User: Марія
  Date: 11.02.2021
  Time: 14:53
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
    <title>Login</title>
    <meta content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
          integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
            crossorigin="anonymous"></script>
</head>
<body>
    <h1><fmt:message key="h.login"/></h1>
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
          action="/login" method="post">
        <label text="Login" for="login"></label><br>
        <input type="text"
               required
               name="login"
               id="login"
               placeholder="Login"/>
        <br>
        <label text="Password" for="password"></label><br>
        <input type="password"
               id="password"
               name="password"
               required
               placeholder="Password"/>
        <input class="sbm-btn" type="submit" value = "Submit"/>
    </form>
</div>
</body>
</html>
