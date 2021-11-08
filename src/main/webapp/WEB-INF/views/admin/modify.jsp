<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<title>회원정보수정하기 </title>
	<jsp:include page="/WEB-INF/views/layout/Header.jsp"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/layout/sidebar.jsp" />
	<h3>[${member.name}]고객 정보 수정</h3>
	<form action="/admin/modify" method="POST" id="frm" >
		<input type="hidden" name="id" value="${member.id}"/>
		<table class="w-pct60">
			<tr>
				<th>이름</th>
				<td><input type="text" name="name" value="${member.name}"/></td>
			</tr>
			<tr>
				<th>닉네임</th>
				<td><input type="text" name="nickname" value="${member.nickname}"/></td>
			</tr>
			<tr>
				<th class="w-px160">성별</th>
				<td>
					<label><input type="radio" name="gender" value="남" ${member.gender eq '남' ? "checked" : ''}/>남</label>
					<label><input type="radio" name="gender" value="여" ${member.gender eq '여' ? "checked" : ''}/>여</label>
				</td>
			</tr>
		
			<tr>
				<th>이메일</th>
				<td><input id="email" type="email" class="validate" name="email" value="${ member.email }"></td>
			</tr>
			<tr>
				<th>생일번호</th>
				<td><input type="text" name="birthday" value="${member.birthday}"/></td>
			</tr>
		</table>
	</form>
	<div class='btnSet'>
		<a class="btn-fill" onclick="$('form').submit()">저장</a>
		<a class="btn-empty" href="/admin/list">취소</a>
	</div>
<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>

</body>
</html>