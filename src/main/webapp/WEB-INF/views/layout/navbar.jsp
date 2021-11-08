<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<% session.setAttribute("id", "id"); %>--%>


<jsp:include page="/WEB-INF/views/layout/modal/loginModal.jsp"/>
<jsp:include page="/WEB-INF/views/layout/modal/searchModal.jsp"/>

<link href="/resources/css/modal.css" rel="stylesheet"/>
<link href="/resources/css/navbar.css" rel="stylesheet"/>
<%--구글 버튼--%>
<link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet" type="text/css">

<div class="p-lg-2 p-md-3 p-sm-3 p-1 navbar-header ">
    <div class="col-2 logo">
        <a class="btn" href="/" style="color: black">
            <img src="/resources/images/tempLogo2.png" alt="로고" >
            <span>myStreaming</span>
        </a>

    </div>
    <div class="navbar-header-search col-xl-4 col-lg-4 col-md-5 col-sm-7 col-9 mx-auto">
                <div class="input-icon ">
                    <input type="text" class="form-control" id="navbarSearch" placeholder="어떤 방송을 찾고계신가요?" name="search">
                    <span class="append-icon"><i class="bi bi-search"></i></span>
                </div>
    </div>
    <div class="navbar-header-btn col-2">
                <c:if test="${sessionScope.id eq 'admin'}">
                    <div>
                        <a class="btn btn-primary" href="/admin/main">관리자 페이지</a>
                    </div>
                </c:if>
                <c:choose>
                    <c:when test="${ sessionScope.id eq null || sessionScope.id eq 'null'}">
                        <button class="btn btn-primary" type="button" data-toggle="modal" data-target="#loginModal">로그인</button>
                        &nbsp; &nbsp;
                        <a class="btn btn-primary" href="/member/register">회원가입</a>
                    </c:when>
                    <c:otherwise>
                        <div class="dropdown">
                            <div class="round-img dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-expanded="false">
                            	<c:choose>
                            		<c:when test="${ not empty profilepic }">
                            			<img src="/imgView?fileName=profilepic/${ profilepic.uploadpath }/s_${ profilepic.uuid }_${ profilepic.filename }" height="50px">
                            		</c:when>
                            		<c:otherwise>
                            			<img src="../../../resources/images/defaultProfile.png" height="50px">
                            		</c:otherwise>
                            	</c:choose>
                            </div>
                            <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                <a class="dropdown-item" href="/member/modify">
                                    <i class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i> 회원정보 수정
                                </a>
                                <a class="dropdown-item" href="/member/logout">
                                    <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
                                    로그아웃
                                </a>
                            </div>
                        </div>
                    </c:otherwise>
                </c:choose>
    </div>

</div>
<%--<nav class="navbar navbar-expand-lg navbar-light bg-light">--%>
<%--    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarScroll" aria-controls="navbarScroll" aria-expanded="false" aria-label="Toggle navigation">--%>
<%--        <span class="navbar-toggler-icon"></span>--%>
<%--    </button>--%>
<%--    <div class="collapse navbar-collapse" id="navbarScroll">--%>

<%--        <ul class="navbar-nav mr-auto my-2 my-lg-0 navbar-nav-scroll" style="max-height: 100px;">--%>

<%--            <li class="nav-item dropdown navbarMemberMenu">--%>
<%--                <a class="nav-link dropdown-toggle" href="#" id="navbarMemberMenu" role="button" data-toggle="dropdown" aria-expanded="false">--%>
<%--                    회원관련(이름짓기)--%>
<%--                </a>--%>
<%--                <ul class="dropdown-menu" aria-labelledby="navbarMemberMenu">--%>
<%--                    <li><a class="dropdown-item" onclick="">로그인</a></li>--%>
<%--                    <li><hr class="dropdown-divider"></li>--%>
<%--                    <li><a class="dropdown-item" href="/member/register">회원가입</a></li>--%>
<%--                </ul>--%>
<%--            </li>--%>


<%--            <li class="nav-item active">--%>
<%--                <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>--%>
<%--            </li>--%>
<%--            <li class="nav-item">--%>
<%--                <a class="nav-link" href="#">Link</a>--%>
<%--            </li>--%>
<%--            <li class="nav-item dropdown">--%>
<%--                <a class="nav-link dropdown-toggle" href="#" id="navbarScrollingDropdown" role="button" data-toggle="dropdown" aria-expanded="false">--%>
<%--                    Link--%>
<%--                </a>--%>
<%--                <ul class="dropdown-menu" aria-labelledby="navbarScrollingDropdown">--%>
<%--                    <li><a class="dropdown-item" href="#">Action</a></li>--%>
<%--                    <li><a class="dropdown-item" href="#">Another action</a></li>--%>
<%--                    <li><hr class="dropdown-divider"></li>--%>
<%--                    <li><a class="dropdown-item" href="#">Something else here</a></li>--%>
<%--                </ul>--%>
<%--            </li>--%>
<%--            <li class="nav-item">--%>
<%--                <a class="nav-link disabled">Link</a>--%>
<%--            </li>--%>
<%--        </ul>--%>

<%--    </div>--%>
<%--</nav>--%>




<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="../../../resources/js/modal.js"></script>