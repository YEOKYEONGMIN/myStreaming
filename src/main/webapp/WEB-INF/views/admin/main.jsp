<%--
  Created by IntelliJ IDEA.
  User: JU
  Date: 2021-11-01
  Time: 오후 9:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>관리자 페이지</title>
    <jsp:include page="/WEB-INF/views/layout/Header.jsp"/>
    <jsp:include page="/WEB-INF/views/layout/modal/loginModal.jsp"/>
    <link href="../resources/css/admin.css" type="text/css" rel="stylesheet" media="screen,projection"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/layout/sidebar.jsp" />

<div class=" d-flex justify-content-center">
    <div class="admin-title">
        <h2>회원 관리</h2>
    </div>
</div>
<div class="admin-menu d-flex justify-content-center">
    <a class="btn btn-primary" href="/admin/list">회원 전체 조회</a>
</div>
<div class="admin-menu d-flex justify-content-center">
    <a class="btn btn-primary" href="">회원에게 메일 보내기</a>
</div>






<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
</body>
</html>
