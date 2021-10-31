<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>회원관리 상세보기 </title>
	<jsp:include page="/WEB-INF/views/layout/Header.jsp"/>
	<jsp:include page="/WEB-INF/views/layout/modal/loginModal.jsp"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/layout/sidebar.jsp" />

<div id="detailContent">
	<h3>[ ${memberVO.name} ]고객 정보</h3>
	<table class='w-pct60'>
	
		<tr>
			<th class='w-px160'>닉네임</th>
			<td>${memberVO.nickname}</td>
		</tr>
		<tr>
			<th>생일</th>
			<td>${memberVO.birthday}</td>
		</tr>
		<tr>
			<th class='w-px160'>성별</th>
			<td>${memberVO.gender}</td>
		</tr>
		<tr>
			<th>이메일</th>
			<td>${memberVO.eamil}</td>
		</tr>
		
	</table>
	<div class='btnSet'>
		<a class='btn-fill' href="adminList">고객 목록</a><!-- Mapping 주소 넣기 -->
		<a class='btn-fill' href="adminmodify?id=${memberVO.id }">수정</a>
		<a class='btn-fill' onclick="if( confirm('정말 삭제하시겠습니까?') ){ href='adminDelete?id=${memberVO.id }' }" >삭제</a>
	</div>
</div>
<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>

</body>
</html>