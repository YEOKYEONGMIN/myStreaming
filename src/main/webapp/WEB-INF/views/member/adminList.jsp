<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<html>
<head>
	<title>회원정보목록 </title>
	<jsp:include page="/WEB-INF/views/layout/Header.jsp"/>
	<jsp:include page="/WEB-INF/views/layout/modal/loginModal.jsp"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/layout/sidebar.jsp" />
<div id = "memberTotal">
<h3>회원조회 및 관리</h3>

	<table border="1" cellspacing="0" width="800" align="center">
         <tr>번호</tr>
         <tr>아이디</tr>
         <tr>비밀번호</tr>
         
         <core:forEach items = "${getMembes} var = list">
         	<tr>
         	
         		<td>${list.num}</td><!-- 번호 -->
         		<td>${list.id}</td><!-- 아이디  -->
         		<td><a href = 'adminDetail.memberVO?id=${list.id}'>${list.name}</td><!--  -->
         		<td>${list.eamil}</td><!-- 이메일  -->
         	</tr>
         
         </core:forEach>
  
	</table>
	
	<div class="btnSet">
		<a class="btn-fill" href="index">홈으로</a>
	</div>
	 <jsp:include page="/WEB-INF/views/layout/footer.jsp"/>    
 </div>  
 


</body>
</html>