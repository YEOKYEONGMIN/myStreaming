<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>회원정보수정하기 </title>
	<jsp:include page="/WEB-INF/views/layout/Header.jsp"/>
	<jsp:include page="/WEB-INF/views/layout/modal/loginModal.jsp"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/layout/sidebar.jsp" />
	<h3>[ ${memberVO.name} ]고객 정보 수정</h3>
	<form action="update.cu" method="post">
		<input type="hidden" name="id" value="${memberVO.id}" /> 
		<!-- 수정할 고객을 특정하기 위해 id가 필요한데 수정 내용에는 id가 없으므로 hidden 속성으로 추가 -->
		<table class="w-pct60">
			<tr>
				<th>닉네임</th>
				<td><input type="text" name="nickname" value="${memberVO.nickname}"/></td>
			</tr>
			<tr>
				<th class="w-px160">성별</th>
				<td>
					<label><input type="radio" name="gender" value="남" ${memberVO.gender eq '남' ? "checked" : ''}/>남</label>
					<label><input type="radio" name="gender" value="여" ${memberVO.gender eq '여' ? "checked" : ''}/>여</label>
				</td>
			</tr>
		
			<tr>
				<th>이메일</th>
				<td><input type="text" name="email" value="${memberVO.email}" /></td>
			</tr>
			<tr>
				<th>생일번호</th>
				<td><input type="text" name="birthday" value="${memberVO.birthday}"/></td>
			</tr>
		</table>
	</form>
	<div class='btnSet'>
		<a class="btn-fill" onclick="$('form').submit()">저장</a>
		<a class="btn-empty" href="detail.cu?id=${memberVO.id}">취소</a>
	</div>
</div>
<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>

</body>
</html>