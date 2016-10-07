<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Log in with your account</title>

</head>

<body>

   <c:if test="${error_message}">
       <h2 class="error">${error_message}</h2>
   </c:if>

<div class="container">

   <c:url value="/dologin" var="loginUrl"/>
   <form method="post" enctype="multipart/form-data" action="${loginUrl}">
       Enter Username : <input type="text" name="username" value="${username}"/><br>
       Enter Password : <input type="password" name="password" value="${password}"/><br>
       <button type="submit" class="button1">Login</button>
   </form>


</div>

</body>
</html>