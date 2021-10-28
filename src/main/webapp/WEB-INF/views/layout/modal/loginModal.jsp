<%--
  Created by IntelliJ IDEA.
  User: JU
  Date: 2021-10-28
  Time: 오후 4:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% session.setAttribute("id", "tel");%>
<link href="/resources/css/modal.css" rel="stylesheet"/>

<!-- Modal -->
<div class="modal fade" id="loginModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">로그인</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">

                <!-- ID input -->
                <form>

                    <div class="form-group col-12">
                        <input type="email" class="form-control" id="loginId" placeholder="아이디">
                    </div>

                    <div class="form-group col-12">
                        <input type="password" class="form-control" id="loginPassword" placeholder="비밀번호">
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

                    <button type="submit" class="btn btn-primary col-12">로그인</button>
                    <a class="btn btn-primary col-12" role="button" href="/" >회원가입</a>
                </form>

            </div>
            <div class="modal-footer">
<%--                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>--%>
            </div>
        </div>
    </div>
</div>
