<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>회원 목록</title>
    <jsp:include page="/WEB-INF/views/layout/Header.jsp"/>
    <jsp:include page="/WEB-INF/views/layout/modal/loginModal.jsp"/>
    <link href="../resources/css/admin.css" type="text/css" rel="stylesheet" media="screen,projection"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/layout/sidebar.jsp"/>
<div class="container-lg">
    <div class=" d-flex justify-content-center">
        <div class="admin-title">
            <h2>회원목록</h2>
        </div>
    </div>

    <div class="table-responsive">
        <table class="table">
            <thead class="thead-dark">
            <tr>
                <th scope="col">#</th>
                <th scope="col">아이디</th>
                <th scope="col">이름(닉네임)</th>
                <th scope="col">이메일</th>
                <th scope="col">생일</th>
                <th scope="col">성별</th>
                <th scope="col">가입날짜</th>
                <th scope="col">선택</th>
            </tr>
            </thead>
            <tbody>
            <c:set var="memberCount" value="${0}"/>
            <c:forEach var="member" items="${memberList}">
                <tr>
                    <th scope="row">${memberCount=memberCount+1}</th>
                    <td>${member.id}</td>
                    <td>${member.name}</td>
                    <td>${member.email}</td>
                    <td>${member.birthday}</td>
                    <td>${member.gender}</td>
                    <td>${member.regDate}</td>
                    <td class="d-flex justify-content-center">
                        <input type="checkbox" aria-label="Checkbox for following text input">
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

</div>


<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
</body>
</html>
