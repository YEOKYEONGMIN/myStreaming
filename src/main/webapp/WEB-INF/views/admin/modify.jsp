<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<title>회원정보 수정하기</title>
	<jsp:include page="/WEB-INF/views/layout/Header.jsp" />
	<jsp:include page="/WEB-INF/views/layout/modal/loginModal.jsp" />
	<link href="../resources/css/register.css" type="text/css" rel="stylesheet" media="screen,projection" />
</head>
<body id="body-pd">
	<jsp:include page="/WEB-INF/views/layout/sidebar.jsp" />
	<div class="join-outline">
		<div class="join-container">
			<div class="join-header ">
				<div class="col-6">
					<h3>[${member.name}]고객 정보 수정</h3>
				</div>
			</div>

			<div class="join-body">

				<form action="/admin/modify" method="POST" id="frm">
					<input type="hidden" name="id" value="${member.id}" />
					<div class="form-group">
	                    <label for="modifyName">이름</label>
	                    <input type="text" class="form-control" id="modifyName" name="name" value="${ member.name }">
	                </div>
	                
	                <div class="form-group">
	                    <label for="modifyNick">닉네임</label>
	                    <input type="text" class="form-control" id="modifyNick" name="nickname" value="${ member.nickname }">
	                </div>
	                
	                <div class="form-group">
	                    <div class="row">
	                        <div class="col-10">
	                            <label for="modifyEmail">이메일</label>
	                            <input type="email" class="form-control" id="modifyEmail" placeholder="example@naver.com" name="email" value="${ member.email }">
	                        </div>
	                     </div>
                	</div>
	                
	                <div class="form-group">
	                    <label for="modifyBirth">생일</label>
	                    <input type="text" class="form-control" id="modifyBirth" name="birthday" value="${ member.birthday }" placeholder="YYYY-MM-DD">
	                </div>
	
	                <div class="form-group">
	                    <label for="modifyGender">성별</label>
	                    <select id="modifyGender" class="form-control" name="gender">
	                    <c:choose>
	                    	<c:when test="${ member.gender eq 'N' }">
		                    	<option value="N" selected>선택안함</option>
		                        <option value="M">남자</option>
		                        <option value="W">여자</option>
	                    	</c:when>
	                    	<c:when test="${ member.gender eq 'M' }">
		                    	<option value="N">선택안함</option>
		                        <option value="M" selected>남자</option>
		                        <option value="W">여자</option>
	                    	</c:when>
	                    	<c:when test="${ member.gender eq 'W' }">
		                    	<option value="N">선택안함</option>
		                        <option value="M">남자</option>
		                        <option value="W" selected>여자</option>
	                    	</c:when>
	                    	<c:otherwise>
	                    		<option value="N">선택안함</option>
		                        <option value="M">남자</option>
		                        <option value="W">여자</option>
	                    	</c:otherwise>
	                    </c:choose>
	                    </select>
	                </div>
	
	                <div class="form-group">
	                    <div>
	                    <span>
	                        알람 수신
	                    </span>
	                    </div>
	                    <div class="form-check">
	                        <div class="custom-control custom-radio custom-control-inline">
	                            <input type="radio" id="alarmYes" name="customRadioInline" class="custom-control-input">
	                            <label class="custom-control-label" for="alarmYes">예</label>
	                        </div>
	                        <div class="custom-control custom-radio custom-control-inline">
	                            <input type="radio" id="alarmNo" name="customRadioInline" class="custom-control-input">
	                            <label class="custom-control-label" for="alarmNo">아니요</label>
	                        </div>
	                    </div>
	                </div>
	                <button type="submit" class="btn btn-primary" id="btnSubmit">저장</button> 
					<button type="button" class="btn btn-primary" id="btn_Optional" onclick="location.href='/admin/list'">취소</button>
				</form>
			</div>
		</div>
	</div>
		
	<jsp:include page="/WEB-INF/views/layout/footer.jsp" />

</body>
</html>