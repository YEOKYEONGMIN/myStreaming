<%--
  Created by IntelliJ IDEA.
  User: JU
  Date: 2021-10-30
  Time: 오전 4:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>회원탈퇴</title>
    <jsp:include page="/WEB-INF/views/layout/Header.jsp"/>
    <jsp:include page="/WEB-INF/views/layout/modal/loginModal.jsp"/>
    <link href="../resources/css/register.css" type="text/css" rel="stylesheet" media="screen,projection"/>
</head>
<body id="body-pd">
<jsp:include page="/WEB-INF/views/layout/sidebar.jsp"/>
<div class="join-outline">
    <div class="join-container">

        <div class="join-header ">
            <div class="col-6">
                <h3>회원탈퇴</h3>
            </div>


            <div class="carousel slide join-carousel col-6" data-ride="carousel">
                <div class="carousel-inner">
                    <div class="carousel-item active">
                        <p>원하는 방송을 한곳에서</p>
                    </div>
                    <div class="carousel-item">
                        <p>myStream에서 경험해보세요</p>
                    </div>
                </div>
            </div>


        </div>

        <div class="join-body">

            <form action="/member/remove" method="POST">
            	<fieldset> 
            	<legend>필수 입력란</legend>

                <div class="form-group">
                    <label for="modifyId">아이디</label>
                    <input type="text" class="form-control" id="removeId" name="id" value="${ member.id }" readonly>
                    <small id="idHelp" class="form-text"></small>
                </div>

                <div class="form-group">
                    <label for="modifyPassword">비밀번호</label>
                    <input type="password" class="form-control" id="removePassword" name="passwd">
                    <small id="passwdHelp" class="form-text"></small>
                </div>
                
				</fieldset>
                <button type="submit" class="btn btn-primary" id="btnRemove">회원탈퇴</button>
                
            </form>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>

<script>
    
    $('#btnRemove').on('click', function(event) {
    	if (!confirm('회원탈퇴 하시겠습니까?')) {
    		event.preventDefault();
    	}
    });
</script>

</body>
</html>
