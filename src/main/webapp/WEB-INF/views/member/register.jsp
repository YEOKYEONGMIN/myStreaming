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

<div class="container">

    <div class="join-header ">
        <div class="col-5">
            <h3>회원가입</h3>
        </div>


        <div class="carousel slide join-carousel col-5" data-ride="carousel">
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

        <form>
            <div class="form-group">
                <label for="joinId">아이디</label>
                <input type="email" class="form-control" id="joinId">
            </div>

            <div class="form-group">
                <label for="joinPassword">비밀번호</label>
                <input type="password" class="form-control" id="joinPassword">
            </div>

            <div class="form-group">
                <label for="joinPassword2">비밀번호 확인</label>
                <input type="password" class="form-control" id="joinPassword2">
            </div>

            <div class="form-group">
                <div class="row">
                    <div class="col-10">
                        <label for="joinEmail">이메일</label>
                        <input type="email" class="form-control" id="joinEmail" placeholder="example@naver.com">
                    </div>
                    <div class="col-2">
                        <button type="submit" class="btn btn-primary mb-2">Confirm identity</button>
                    </div>
                </div>
            </div>


            <div class="form-group">
                <label for="joinGender">성별</label>
                <select id="joinGender" class="form-control">
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
            <button type="submit" class="btn btn-primary">Sign in</button>
        </form>


    </div>


</div>


<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>

<script>
    $(function () {
        $('[data-toggle="tooltip"]').tooltip()
    })


</script>

</body>
</html>
