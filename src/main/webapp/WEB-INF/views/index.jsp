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



<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>

<script>
	$(function () {
	  $('[data-toggle="tooltip"]').tooltip()
	})


</script>

</body>
</html>
