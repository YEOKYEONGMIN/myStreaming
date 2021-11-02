<%--
  Created by IntelliJ IDEA.
  User: JU
  Date: 2021-10-28
  Time: 오후 4:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<meta name="google-signin-client_id"
      content="1061393167955-p6mipsnaoecrmefmsji5tujmh4kad0sd.apps.googleusercontent.com">



<!-- Modal -->
<div class="modal fade" id="loginModal" tabindex="-1" aria-labelledby="loginModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="loginModalLabel">로그인</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">

                <!-- ID input -->
                <form id="loginForm">

                    <div class="form-group col-12">
                        <input type="text" class="form-control" id="loginId" placeholder="아이디" name="id">
                    </div>

                    <div class="form-group pass_show col-12">
                        <input type="password" class="form-control" id="loginPassword" placeholder="비밀번호" name="passwd">
                    </div>


                    <div class="d-flex justify-content-between">
                        <div>
                            <a href="#!" style="color: #283593; font-weight: bold; font-size: 12px">아이디</a>
                            <span style="color: #283593; font-weight: bold">|</span>
                            <a href="#!" style="color: #283593; font-weight: bold; font-size: 12px">비밀번호 찾기</a>
                        </div>

                        <div class="form-group form-check">
                            <input type="checkbox" class="form-check-input" id="loginRememberMe">
                            <label class="form-check-label" for="loginRememberMe">로그인 유지</label>
                        </div>

                    </div>

                    <button type="button" class="btn background-purple text-white col-12" id="btnLogin">로그인</button>
                    <a class="btn background-purple text-white col-12" role="button" href="/">회원가입</a>
                </form>

            </div>
            <div class="modal-footer">


                <div class="row justify-content-between" id="socialLogin">
                    <%-- 트위치 --%>
                    <div>
                        <a href="#!" class="btn" type="submit" id="twitchLoginBtn"
                           style="background-color:#6441a4; color: white">
                            <img class="img" src="/resources/images/twitchSymbol.svg" alt="">
                            <span>로그인</span>
                        </a>
                    </div>
                    <%-- 카카오 --%>
                    <div>
                        <a href="#!" class="btn" type="submit" id="kakaoLoginBtn"
                           style="background-color:#FEE500; color:#191919;">
                            <img class="img" src="/resources/images/kakaoSymbol.svg" alt="">
                            <span>로그인</span>
                        </a>
                    </div>
                    <%-- 구글 --%>
                    <div>
                        <a href="#!;" class="btn" type="submit" id="googleLoginBtn" style="background-color:#FFFFFF; color:black;">
                            <img class="img" src="/resources/images/googleSymbol.png" alt="">
                            <span>로그인</span>
                        </a>

                    </div>
                </div>

            </div>
        </div>
    </div>
</div>


