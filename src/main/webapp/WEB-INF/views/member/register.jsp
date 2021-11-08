<%--
  Created by IntelliJ IDEA.
  User: JU
  Date: 2021-10-30
  Time: 오전 4:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>회원가입</title>
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
                <h3>회원가입</h3>
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

            <form action="/member/register" method="POST">
            	<fieldset> 
            	<legend>필수 입력란</legend>

                <div class="form-group">
                    <label for="joinId">아이디</label>
                    <input type="text" class="form-control" id="joinId" name="id">
                    <small id="idHelp" class="form-text"></small>
                </div>

                <div class="form-group">
                    <label for="joinPassword">비밀번호</label>
                    <input type="password" class="form-control" id="joinPassword" name="passwd">
                    <small id="passwdHelp" class="form-text"></small>
                </div>

                <div class="form-group">
                    <label for="joinPassword2">비밀번호 확인</label>
                    <input type="password" class="form-control" id="joinPassword2">
                    <small id="passwdHelp2" class="form-text"></small>
                </div>

                <div class="form-group">
                    <div class="row">
                        <div class="col-10">
                            <label for="joinEmail">이메일</label>
                            <input type="email" class="form-control" id="joinEmail" placeholder="example@naver.com" name="email">
                        </div>
                        <div class="col-2 d-flex align-items-end" id="btnEmailChk">
                            <button type="button" class="btn btn-primary mb-2">본인확인</button>
                        </div>
                     </div>
                </div>   
                   
                <div class="form-group" id="emailCodeCheckDiv">
                     <div class="row">
                        <div class="col-10">
                            <label for="joinEmail">인증번호</label>
                            <input type="text" class="form-control" id="EmailCodeCheck" >
                        </div>
                        <div class="col-2 d-flex align-items-end" id="btnEmailCodeCheck">
                            <button type="button" class="btn btn-primary mb-2">확인</button>
                        </div>
                    </div>
                     <small id="emailHelp" class="form-text"></small>
                </div>
				</fieldset>
                 <button type="button" class="btn btn-primary" id="btn_Optional" onclick="option();">선택사항</button>
                <br>
				
				
				<fieldset id="Optional"> 
            	<legend>선택 입력란</legend>
                <div class="form-group">
                    <label for="joinName">이름</label>
                    <input type="text" class="form-control" id="joinName" name="name">
                </div>
                
                <div class="form-group">
                    <label for="modifyNick">닉네임</label>
                    <input type="text" class="form-control" id="joinNick" name="nickname">
                </div>
                
                <div class="form-group">
                    <label for="modifyBirth">생일</label>
                    <input type="text" class="form-control" id="joinBirth" name="birthday" placeholder="YYYY-MM-DD">
                </div>

                <div class="form-group">
                    <label for="joinGender">성별</label>
                    <select id="joinGender" class="form-control" name="gender">
                        <option value="N" selected>선택안함</option>
                        <option value="M">남자</option>
                        <option value="W">여자</option>
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
                <button type="submit" class="btn btn-primary" id="btnSubmit">Sign in</button>
                
            </form>


        </div>


    </div>
</div>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>

<script>
	let emailChk = false;	// 이메일인증 체크여부
	let idChk = false;		// 아이디 중복여부
	let passwdChk = false;	// 비밀번호 정규식 체크여부
	let passwdChk2 = false;	// 비밀번호 비밀번호 확인값 일치여부 확인 
    $(function () {
        $('[data-toggle="tooltip"]').tooltip()
    })

    $('.carousel').carousel({
        interval: 3000
    })
    
    $('input#joinId').on('keyup', function () {
		
		var id = $(this).val();
		if (id.length == 0) {
			return;
		}
		
		var $inputId = $(this);
		var $span = $(this).next();
		
		// ajax 함수 호출
		$.ajax({
			url: '/api/member/' + id,
			method: 'GET',
			success: function (data) {
				console.log(data);
				console.log(data.count);
				console.log(typeof data);
				
				if (data.count == 0) {
					$span.html('사용가능한 아이디 입니다').css('color', 'green');
					idChk = true;
				} else { // data.count == 1
					$span.html('이미 사용중인 아이디 입니다').css('color', 'red');
					idChk = false;
				}
			} // success
		});
	});
    
    
    // 비밀번호 정규식체크
	$('#joinPassword').on("focusout", function() {
		// 비밀번호 확인 정규식
		let passType = /^[a-zA-Z0-9~!@#$%^&*(_+|<>?:{}]{6,16}$/;
		let passType2 = /(.)\1{2,}/
		let passwd = $('#joinPassword').val();
		let passwd2 = $('#joinPassword2').val();
		let $span = $(this).closest('div.form-group').find('small.form-text');
		let $span2 = $('small#passwdHelp2');
		console.log($span2);
		console.log(passwd);
		console.log(passType.test(passwd));
		console.log(passType2.test(passwd));
		 
		if (passwd != '') { // 빈칸 아닐때
			if (passType.test(passwd)) { // 타입 1 통과
				if (passType2.test(passwd)) {
					passwdChk = false;
	
					$span.html('연속된문자를 3자연속 쓰시면 안됩니다.').css('color', 'red');
				} else {
	
						// 성공 표시
					passwdChk = true;
					$span.html('좋은 비밀번호네요').css('color', 'green');
				}
	
			} else { // 타입 1 불통
				passwdChk = false;
				$span.html('비밀번호는 6~16사이로 작성해주세요').css('color', 'red');
			}
			if(passwd != passwd2){
				$span2.html('비밀번호 불일치함').css('color', 'red');
				passwdChk2 = false;
			}
		
	
		} else { // 빈칸일때
			passwdChk = false;
			$span.html('')
		}
	});

	// 비밀번호와 비밀번호 확인 일치체크
	$('input#joinPassword2').on('focusout', function() {
		let passwd = $('input#joinPassword').val();
		let passwd2 = $(this).val();
		console.log(passwd);
		console.log(passwd2);
		let $span = $(this).closest('div.form-group').find('small.form-text');

		if (passwd == passwd2) {
			$span.html('비밀번호 일치함').css('color', 'green');
			passwdChk2 = true;
			if (passwd2 == '') {
				$span.html('');
				passwdChk2 = false;
			}
		} else {
			$span.html('비밀번호 불일치함').css('color', 'red');
			passwdChk2 = false;
		}
	});
	function option() {
		$('#Optional').show();
		$('#btn_Optional').hide();
		//     	document.querySelector('#Optional').style.display = "block";
		//         document.querySelector('#btn_Optional').style.display = "none";
	}
	let email = $('#joinEmail');
	let emailType = /^[0-9a-zA-Z]([-_]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/;

	$('#btnEmailChk').on("click", function() {

		let email = $('#joinEmail').val();

		$.ajax({
			url : '/email/simple-mail',
			method : 'POST',
			data : JSON.stringify({
				email : email
			}),
			contentType : 'application/json; charset=UTF-8',
			success : function(data) {

				if (data.result == "fail") {
					alert("메일 전송이 실패했습니다. 메일을 확인해주세요.")
				} else {
					alert("메일에서 인증번호를 확인해주세요")
					emailChkCode = data.code;
					console.log(emailChkCode);
					$('#emailCodeCheckDiv').show();
				}
			} // success
		});
	});
	$('#btnEmailCodeCheck').on("click", function() {

		let emailCode = $('#EmailCodeCheck').val();
		let $span = $(this).closest('div.form-group').find('small.form-text');

		if (emailChkCode == emailCode) {
			emailChk = true;
			$span.html('이메일 인증성공!!').css('color', 'green');
		} else {
			$span.html('인증코드가 일치하지 않습니다. 다시 확인해주세요.').css('color', 'red');
		}

	});

	$('#btnSubmit').on("click", function() {
		if (!idChk) {
			event.preventDefault();
			alert("아이디를 확인해주세요.");
		} else if (!passwdChk) {
			event.preventDefault();
			alert("비밀번호를 확인해주세요.");
		} else if (!passwdChk2) {
			event.preventDefault();
			alert("비밀번호 확인을 확인해주세요.");
		} else if (!emailChk) {
			event.preventDefault();
			alert("이메일 인증을 확인해주세요.");
		}
	});
</script>

</body>
</html>
