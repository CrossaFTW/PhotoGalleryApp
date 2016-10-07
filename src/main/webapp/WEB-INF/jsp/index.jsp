<%@page contentType="text/html" pageEncoding="UTF-8" session="true" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>

<html>

    <head>
        <title>PhotoGallery WebApp</title>
        <meta charset="UTF-8"/>
        <link href="<c:url value="/css/a.css"/>" rel="stylesheet">
    </head>

    <body>
        <h1>${title}</h1>
        <h2>Welcome</h2>
        <c:if test="${pageContext.request.remoteUser == 'admin'}">
            <p class="welcome"> Welcome ${pageContext.request.remoteUser} </p>
        </c:if>
        <c:if test="${counter == 1}">
            <p class="counter">You are ${counter}st Visitor</p>
        </c:if>
        <c:if test="${counter == 2}">
            <p class="counter">You are ${counter}nd Visitor</p>
        </c:if>
        <c:if test="${counter == 3}">
            <p class="counter">You are ${counter}rd Visitor</p>
        </c:if>
        <c:if test="${counter >= 4}">
            <p class="counter">You are ${counter}th Visitor</p>
        </c:if>

        <div class="contents">
            <c:forEach items="${items}" var="thumbnail" varStatus="loop">
            <c:if test="${loop.count <= limit}">
                <div class="photo card-1">
                <a class="imglink" href="<c:url value="/photos/large/${thumbnail}"/>">
                    <img src="<c:url value="/photos/thumb/${thumbnail}"/>" class="thumbnail" />
                </a>
                <a class="namafile">
                    <p> ${fn:substringBefore(thumbnail,".")} </p>
                    <c:if test="${pageContext.request.remoteUser == 'admin'}">
                        <c:url value="/dodelete" var="deleteUrl"/>
                        <form method="post" enctype="multipart/form-data" action="${deleteUrl}">
                            <button type="submit" name="thumbnail" value="${thumbnail}" class="button1">Delete This Photo</button>
                        </form>
                    </c:if>

                </a>
                </div>
            </c:if>

            <%-- this will only display next page if the count is limit+1, so on the last page it will not show --%>
            <c:if test="${loop.count == limit+1}">
                <c:url value="/?page=${page+1}" var="nextUrl"/>
                <div class="center">
                    <a id="next" href="${nextUrl}" class="nextButton"> NEXT </a>
                </div>
            </c:if>
            </c:forEach>
        </div>

        <div class = "upload">
            <p>
            <c:url value="/upload" var="uploadUrl"/>
            <a href="${uploadUrl}" class="button">Upload Foto</a>
            </p>
        </div>

        <div class = "login">
            <p>
            <c:url value="/login" var="loginUrl"/>
            <a href="${loginUrl}" class="button">Login</a>
            </p>
        </div>

    <!-- we load the jquery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>

    <script>
    //same like window.onload, run a function
    $(function() {
        function ajaxNext() {
       //on link with id=”next” click, do the function below
            $("#next").click(function() {
           //run an ajax get with url is the link href=”...”, on ajax get, do the following
                $.get( $(this).prop('href'), function(r) {
                    console.log(r);

                    //remove the div center
                    $("#next").parent().remove();

                    // add the aja result content into the contents
                    var asd = $('<div/>').append(r).find('.contents').html()
                    console.log(asd);

                    $(".contents").append(asd);
                    // because of the next is removed and added again, we need to attach a new event. Call the ajaxNext again.
                    ajaxNext();
           })
           return false;
       })
   }
   //execute the function
   ajaxNext();
})

</script>

</body>
</html>
