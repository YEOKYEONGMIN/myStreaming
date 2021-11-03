<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<% session.setAttribute("id", "id"); %>--%>


<jsp:include page="/WEB-INF/views/layout/modal/loginModal.jsp"/>
<jsp:include page="/WEB-INF/views/layout/modal/searchModal.jsp"/>

<link href="/resources/css/modal.css" rel="stylesheet"/>
<link href="/resources/css/navbar.css" rel="stylesheet"/>
<%--구글 버튼--%>
<link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet" type="text/css">

<%--<nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">--%>
<%--    <a class="navbar-brand" href="#"><img--%>
<%--            src="/resources/images/logo_transparent.png" alt="Logo"--%>
<%--            style="width: 100px;"></a>--%>


<%--    <!-- Topbar Search -->--%>
<%--    <div class="my-8 my-lg-0">--%>

<%--    </div>--%>

<%--    <!-- Topbar Navbar -->--%>
<%--    <ul class="navbar-nav ml-md-5">--%>


<%--    </ul>--%>
<%--</nav>--%>
<%--===============================네브바 수정===============================--%>
<div class="p-lg-2 p-md-3 p-sm-3 p-1 navbar-header ">
    <div class="col-2 logo">
        <img src="/resources/images/tempLogo2.png" alt="로고">
        <span>myStreaming</span>
    </div>
    <div class="navbar-header-search col-xl-4 col-lg-4 col-md-5 col-sm-7 col-9 mx-auto">
                <div class="input-icon ">
                    <input type="text" class="form-control" id="navbarSearch" placeholder="어떤 방송을 찾고계신가요?" name="search">
                    <span class="append-icon"><i class="bi bi-search"></i></span>
                </div>
    </div>
    <div class="navbar-header-btn">
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
                        <!-- Nav Item - Alerts -->
<%--                        <li class="nav-item dropdown no-arrow mx-1"><a--%>
<%--                                class="nav-link dropdown-toggle" href="#" id="alertsDropdown"--%>
<%--                                role="button" data-toggle="dropdown" aria-haspopup="true"--%>
<%--                                aria-expanded="false"> <i class="fas fa-bell fa-fw"></i> <!-- Counter - Alerts -->--%>
<%--                            <span class="badge badge-danger badge-counter">3+</span>--%>
<%--                        </a> <!-- Dropdown - Alerts -->--%>
<%--                            <div--%>
<%--                                    class="dropdown-list dropdown-menu dropdown-menu-right shadow animated--grow-in"--%>
<%--                                    aria-labelledby="alertsDropdown">--%>
<%--                                <h6 class="dropdown-header">Alerts Center</h6>--%>
<%--                                <a class="dropdown-item d-flex align-items-center" href="#">--%>
<%--                                    <div class="mr-3">--%>
<%--                                        <div class="icon-circle bg-primary">--%>
<%--                                            <i class="fas fa-file-alt text-white"></i>--%>
<%--                                        </div>--%>
<%--                                    </div>--%>
<%--                                    <div>--%>
<%--                                        <div class="small text-gray-500">December 12, 2019</div>--%>
<%--                                        <span class="font-weight-bold">A new monthly report is--%>
<%--        									ready to download!</span>--%>
<%--                                    </div>--%>
<%--                                </a> <a class="dropdown-item d-flex align-items-center" href="#">--%>
<%--                                <div class="mr-3">--%>
<%--                                    <div class="icon-circle bg-success">--%>
<%--                                        <i class="fas fa-donate text-white"></i>--%>
<%--                                    </div>--%>
<%--                                </div>--%>
<%--                                <div>--%>
<%--                                    <div class="small text-gray-500">December 7, 2019</div>--%>
<%--                                    $290.29 has been deposited into your account!--%>
<%--                                </div>--%>
<%--                            </a> <a class="dropdown-item d-flex align-items-center" href="#">--%>
<%--                                <div class="mr-3">--%>
<%--                                    <div class="icon-circle bg-warning">--%>
<%--                                        <i class="fas fa-exclamation-triangle text-white"></i>--%>
<%--                                    </div>--%>
<%--                                </div>--%>
<%--                                <div>--%>
<%--                                    <div class="small text-gray-500">December 2, 2019</div>--%>
<%--                                    Spending Alert: We've noticed unusually high spending for your--%>
<%--                                    account.--%>
<%--                                </div>--%>
<%--                            </a> <a class="dropdown-item text-center small text-gray-500" href="#">Show--%>
<%--                                All Alerts</a>--%>
<%--                            </div>--%>
<%--                        </li>--%>

<%--                        <!-- Nav Item - Messages -->--%>
<%--                        <li class="nav-item dropdown no-arrow mx-1"><a--%>
<%--                                class="nav-link dropdown-toggle" href="#" id="messagesDropdown"--%>
<%--                                role="button" data-toggle="dropdown" aria-haspopup="true"--%>
<%--                                aria-expanded="false"> <i class="fas fa-envelope fa-fw"></i> <!-- Counter - Messages -->--%>
<%--                            <span class="badge badge-danger badge-counter">7</span>--%>
<%--                        </a> <!-- Dropdown - Messages -->--%>
<%--                            <div--%>
<%--                                    class="dropdown-list dropdown-menu dropdown-menu-right shadow animated--grow-in"--%>
<%--                                    aria-labelledby="messagesDropdown">--%>
<%--                                <h6 class="dropdown-header">Message Center</h6>--%>
<%--                                <a class="dropdown-item d-flex align-items-center" href="#">--%>
<%--                                    <div class="dropdown-list-image mr-3">--%>
<%--                                        <img class="rounded-circle" src=""--%>
<%--                                             alt="...">--%>
<%--                                        <div class="status-indicator bg-success"></div>--%>
<%--                                    </div>--%>
<%--                                    <div class="font-weight-bold">--%>
<%--                                        <div class="text-truncate">Hi there! I am wondering if you--%>
<%--                                            can help me with a problem I've been having.--%>
<%--                                        </div>--%>
<%--                                        <div class="small text-gray-500">Emily Fowler · 58m</div>--%>
<%--                                    </div>--%>
<%--                                </a> <a class="dropdown-item d-flex align-items-center" href="#">--%>
<%--                                <div class="dropdown-list-image mr-3">--%>
<%--                                    <img class="rounded-circle" src=""--%>
<%--                                         alt="...">--%>
<%--                                    <div class="status-indicator"></div>--%>
<%--                                </div>--%>
<%--                                <div>--%>
<%--                                    <div class="text-truncate">I have the photos that you--%>
<%--                                        ordered last month, how would you like them sent to you?--%>
<%--                                    </div>--%>
<%--                                    <div class="small text-gray-500">Jae Chun · 1d</div>--%>
<%--                                </div>--%>
<%--                            </a> <a class="dropdown-item d-flex align-items-center" href="#">--%>
<%--                                <div class="dropdown-list-image mr-3">--%>
<%--                                    <img class="rounded-circle" src=""--%>
<%--                                         alt="...">--%>
<%--                                    <div class="status-indicator bg-warning"></div>--%>
<%--                                </div>--%>
<%--                                <div>--%>
<%--                                    <div class="text-truncate">Last month's report looks--%>
<%--                                        great, I am very happy with the progress so far, keep up the--%>
<%--                                        good work!--%>
<%--                                    </div>--%>
<%--                                    <div class="small text-gray-500">Morgan Alvarez · 2d</div>--%>
<%--                                </div>--%>
<%--                            </a> <a class="dropdown-item d-flex align-items-center" href="#">--%>
<%--                                <div class="dropdown-list-image mr-3">--%>
<%--                                    <img class="rounded-circle"--%>
<%--                                         src="https://source.unsplash.com/Mv9hjnEUHR4/60x60" alt="...">--%>
<%--                                    <div class="status-indicator bg-success"></div>--%>
<%--                                </div>--%>
<%--                                <div>--%>
<%--                                    <div class="text-truncate">Am I a good boy? The reason I--%>
<%--                                        ask is because someone told me that people say this to all--%>
<%--                                        dogs, even if they aren't good...--%>
<%--                                    </div>--%>
<%--                                    <div class="small text-gray-500">Chicken the Dog · 2w</div>--%>
<%--                                </div>--%>
<%--                            </a> <a class="dropdown-item text-center small text-gray-500" href="#">Read--%>
<%--                                More Messages</a>--%>
<%--                            </div>--%>
<%--                        </li>--%>

<%--                        <div class="topbar-divider d-none d-sm-block"></div>--%>

<%--                        <!-- Nav Item - User Information -->--%>
<%--                        <li class="nav-item dropdown no-arrow"><a--%>
<%--                                class="nav-link dropdown-toggle" href="#" id="userDropdown"--%>
<%--                                role="button" data-toggle="dropdown" aria-haspopup="true"--%>
<%--                                aria-expanded="false"> <img class="img-profile rounded-circle" src="img/undraw_profile.svg">--%>
<%--                            <span class="mr-2 d-none d-lg-inline text-gray-600 small">Douglas McGee</span>--%>
<%--                        </a> <!-- Dropdown - User Information -->--%>
<%--                            <div--%>
<%--                                    class="dropdown-menu dropdown-menu-right shadow animated--grow-in"--%>
<%--                                    aria-labelledby="userDropdown">--%>
<%--                                <a class="dropdown-item" href="#"> <i--%>
<%--                                        class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i> Profile--%>
<%--                                </a> <a class="dropdown-item" href="#"> <i--%>
<%--                                    class="fas fa-cogs fa-sm fa-fw mr-2 text-gray-400"></i> Settings--%>
<%--                            </a> <a class="dropdown-item" href="#"> <i--%>
<%--                                    class="fas fa-list fa-sm fa-fw mr-2 text-gray-400"></i> Activity--%>
<%--                                Log--%>
<%--                            </a>--%>
<%--                                <div class="dropdown-divider"></div>--%>
<%--                                <a class="dropdown-item" href="#" data-toggle="modal"--%>
<%--                                   data-target="#logoutModal"> <i--%>
<%--                                        class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>--%>
<%--                                    Logout--%>
<%--                                </a>--%>
<%--                            </div>--%>
<%--                        </li>--%>
                    </c:otherwise>
                </c:choose>
    </div>

</div>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarScroll" aria-controls="navbarScroll" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarScroll">

        <ul class="navbar-nav mr-auto my-2 my-lg-0 navbar-nav-scroll" style="max-height: 100px;">

            <li class="nav-item dropdown navbarMemberMenu">
                <a class="nav-link dropdown-toggle" href="#" id="navbarMemberMenu" role="button" data-toggle="dropdown" aria-expanded="false">
                    회원관련(이름짓기)
                </a>
                <ul class="dropdown-menu" aria-labelledby="navbarMemberMenu">
                    <li><a class="dropdown-item" onclick="">로그인</a></li>
                    <li><hr class="dropdown-divider"></li>
                    <li><a class="dropdown-item" href="/member/register">회원가입</a></li>
                </ul>
            </li>


            <li class="nav-item active">
                <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">Link</a>
            </li>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarScrollingDropdown" role="button" data-toggle="dropdown" aria-expanded="false">
                    Link
                </a>
                <ul class="dropdown-menu" aria-labelledby="navbarScrollingDropdown">
                    <li><a class="dropdown-item" href="#">Action</a></li>
                    <li><a class="dropdown-item" href="#">Another action</a></li>
                    <li><hr class="dropdown-divider"></li>
                    <li><a class="dropdown-item" href="#">Something else here</a></li>
                </ul>
            </li>
            <li class="nav-item">
                <a class="nav-link disabled">Link</a>
            </li>
        </ul>

    </div>
</nav>




<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="../../../resources/js/modal.js"></script>