<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
	<jsp:include page="/WEB-INF/views/layout/Header.jsp"/>
	<jsp:include page="/WEB-INF/views/layout/modal/loginModal.jsp"/>
</head>
<body id="body-pd">
	<jsp:include page="/WEB-INF/views/layout/sidebar.jsp" />
<h1>
	Hello world!  
</h1>


<!-- Button trigger modal -->
<button id="loginButton" type="button" class="btn btn-primary" data-toggle="modal" data-target="#loginModal">
	로그인
</button>


<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>

<script>

$('#loginButton').on("click",function () {
	console.log("클릭")
})

</script>

</body>
</html>
