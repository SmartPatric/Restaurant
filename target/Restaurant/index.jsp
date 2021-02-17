<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/fmt" prefix = "fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${param.lang}" />
<fmt:setBundle basename="MyResources"/>

<html lang="${param.lang}">
<head>
    <title>Restaurant</title>
</head>
<body>
    <span class="lang">
        <form>
            <select name="lang" onchange="submit()">
                <option value=""><fmt:message key="language.change"/></option>
                <option value="en" ${language == 'en'}><fmt:message key="language.en"/></option>
                <option value="uk" ${language == 'uk'}><fmt:message key="language.ua"/></option>
            </select>
        </form>
    </span>
    <h2>Hello, <%= session.getAttribute("name")%></h2>
<%--    <fmt:setLocale value="uk"/>
    <fmt:setLocale value="en"/>--%>
    <a href="${pageContext.request.contextPath}/login"><fmt:message key="h.login"/></a>
    <a href="${pageContext.request.contextPath}/registration"><fmt:message key="h.registration"/></a>
    <a href="${pageContext.request.contextPath}/logout"><fmt:message key="h.logout"/></a>
    <a href="${pageContext.request.contextPath}/main">Main</a>
    <c:if test="${role=='USER'}">
    <a href="${pageContext.request.contextPath}/userCabinet">User Cabinet</a>
    </c:if>
    <c:if test="${role=='ADMIN'}">
    <a href="${pageContext.request.contextPath}/admin">Admin</a>
    </c:if>


</body>
</html>