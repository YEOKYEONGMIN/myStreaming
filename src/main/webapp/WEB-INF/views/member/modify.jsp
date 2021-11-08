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
    <title>회원수정</title>
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
                <h3>회원수정</h3>
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

            <form action="/member/modify" method="POST" enctype="multipart/form-data">
            	<fieldset> 
            	<legend>필수 입력란</legend>

                <div class="form-group">
                    <label for="modifyId">아이디</label>
                    <input type="text" class="form-control" id="modifyId" name="id" value="${ member.id }" readonly>
                    <small id="idHelp" class="form-text"></small>
                </div>

                <div class="form-group">
                    <label for="modifyPassword">비밀번호</label>
                    <input type="password" class="form-control" id="modifyPassword" name="passwd">
                    <small id="passwdHelp" class="form-text"></small>
                </div>

                <div class="form-group">
                    <div class="row">
                        <div class="col-10">
                            <label for="modifyEmail">이메일</label>
                            <input type="email" class="form-control" id="modifyEmail" placeholder="example@naver.com" name="email" value="${ member.email }">
                        </div>
                     </div>
                </div>
				</fieldset>
                 <button type="button" class="btn btn-primary" id="btn_Optional" onclick="option();">선택사항</button>
                <br>
				
				
				<fieldset id="Optional"> 
            	<legend>선택 입력란</legend>
            	<div class="form-group">
                    <c:choose>
	                  	<c:when test="${ not empty profilepic }">
	           				<li>
	               				<a href="/imgView?fileName=profilepic/${ profilepic.uploadpath }/${ profilepic.uuid }_${ profilepic.filename }">
	               					<img src="/imgView?fileName=profilepic/${ profilepic.uploadpath }/s_${ profilepic.uuid }_${ profilepic.filename }">
	               				</a>
	               			</li>
	           			</c:when>
	           			<c:otherwise>
	           				프로필 사진 없음
	           			</c:otherwise>
           			</c:choose>
           			<input type="file" name="file">
                </div>
            	
            	
                <div class="form-group">
                    <label for="modifyName">이름</label>
                    <input type="text" class="form-control" id="modifyName" name="name" value="${ member.name }">
                </div>
                
                <div class="form-group">
                    <label for="modifyNick">닉네임</label>
                    <input type="text" class="form-control" id="modifyNick" name="nickname" value="${ member.nickname }">
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
                </fieldset>
                <button type="submit" class="btn btn-primary" id="btnSubmit">modify</button>
                
            </form>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>

<script>
    $(function () {
        $('[data-toggle="tooltip"]').tooltip()
    })

    $('.carousel').carousel({
        interval: 3000
    })
    
	function option() {
		$('#Optional').show();
		$('#btn_Optional').hide();
		//     	document.querySelector('#Optional').style.display = "block";
		//         document.querySelector('#btn_Optional').style.display = "none";
	}
</script>

</body>
</html>
