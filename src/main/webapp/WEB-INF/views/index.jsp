<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
	<jsp:include page="/WEB-INF/views/layout/Header.jsp"/>
</head>
<body>
<h1>
	Hello world!  
</h1>
<button type="button" class="btn btn-primary">Primary</button>
<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
</body>
</html>
