<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false" %>

<html>
<head>
    <title>myStreaming</title>
    <jsp:include page="/WEB-INF/views/layout/Header.jsp"/>
    <link href="../../../resources/css/search.css" type="text/css" rel="stylesheet" media="screen,projection"/>
</head>

<body id="body-pd">
<jsp:include page="/WEB-INF/views/layout/sidebar.jsp"/>

<div class="my-5 mx-5 px-5">
    <%--    <input type="hidden" id="keyword" value="${keyword}">--%>

    <div class="platform-container" style="display: flex;flex-direction: column">

        <div class="platform-title my-5">
            <span> 트위치 검색 결과</span>
        </div>

        <div class="channel-container" style="display: flex;">
            <div class="channel-img">
                <img src="../../../resources/images/defaultProfile.png">
            </div>
            <div class="mx-4" style="display: flex;flex-direction: column">
                <div class="channel-title">
                    <span>
                        풍월량
                    </span>
                </div>
                <div class="channel-description my-3">
                    <span>
                        풍월량 방송
                    </span>
                </div>
            </div>
        </div>
    </div>



        <div class="platform-container" style="display: flex;flex-direction: column">

            <div class="platform-title my-5">
                <span> 유튜브 검색 결과</span>
            </div>

            <div class="channel-container" style="display: flex;">
                <div class="channel-img">
                    <img src="../../../resources/images/defaultProfile.png">
                </div>
                <div class="mx-4" style="display: flex;flex-direction: column">
                    <div class="channel-title">
                    <span>
                        풍월량
                    </span>
                    </div>
                    <div class="channel-description my-3">
                    <span>
                        풍월량 방송
                    </span>
                    </div>
                </div>
            </div>
        </div>



</div>


</div>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
<script src="../../resources/js/index/index.js"></script>


</body>
</html>
