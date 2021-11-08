<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false" %>

<html>
<head>
    <title>myStreaming</title>
    <jsp:include page="/WEB-INF/views/layout/Header.jsp"/>
    <link href="../../resources/css/index.css" type="text/css" rel="stylesheet" media="screen,projection"/>

</head>
<body id="body-pd">
<jsp:include page="/WEB-INF/views/layout/sidebar.jsp"/>

<div class="my-5 mx-5">

    <div class="carousel-container-purple">
        <div class="platform-logo-twitch" style="display:flex; justify-content: center">
            <a href="https://www.twitch.com" target="_blank">
                <img src="https://upload.wikimedia.org/wikipedia/commons/2/26/Twitch_logo.svg" width="auto" height="90">
            </a>
        </div>
        <div>
            <jsp:include page="/WEB-INF/views/layout/twitchCarousel.jsp"/>
        </div>
    </div>


    <div class="carousel-container-red">
        <div class="platform-logo-youtube" style="display:flex; justify-content: center">
            <a href="https://www.youtube.com" target="_blank">
                <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/b/b8/YouTube_Logo_2017.svg/1280px-YouTube_Logo_2017.svg.png" width="auto" height="50">
            </a>
        </div>

        <div>
            <jsp:include page="/WEB-INF/views/layout/youtubeCarousel.jsp"/>
        </div>
    </div>


    <div style="min-height: 500px">

    </div>

</div>
	
<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
<script src="../../resources/js/index/index.js"></script>
	<c:if test="${not empty login }">
		<script>
		 	$('#loginModal').modal('show');
		</script>
	</c:if>

</body>
</html>
