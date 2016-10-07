<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

<html>
<head>
   <title>Upload Photo</title>
   <meta charset="UTF-8"/>
   <link href="<c:url value="/css/a.css"/>" rel="stylesheet">
</head>

<body>
   <h1>Upload Foto</h1>



   <!-- debugging purposes -->
   <c:if test="${not empty file_name}">
       NAME = ${file_name}<br>
       TYPE = ${file_type}<br>
       DIR Thumbnail = ${upload_dirT}<br>
       DIR Medium = ${upload_dirM}<br>
       DIR Large = ${upload_dirL}<br>
   </c:if>

   <c:if test="${error_message}">
       <h2 class="error">${error_message}</h2>
   </c:if>

   <c:url value="/doupload" var="uploadUrl"/>
   <form method="post" enctype="multipart/form-data" action="${uploadUrl}">
       <input type="file" name="file"/>
       <br>
       <br>
       <input type="text" name="msg" value="${msg}" placeholder="About the photo" />
       <br>
       <br>
       <button type="submit" class="button1">UPLOAD</button>
   </form>

</body>
</html>
