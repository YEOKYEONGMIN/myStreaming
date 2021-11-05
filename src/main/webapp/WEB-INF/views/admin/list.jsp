<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>회원 목록</title>
    <jsp:include page="/WEB-INF/views/layout/Header.jsp"/>
    <jsp:include page="/WEB-INF/views/layout/modal/loginModal.jsp"/>
    <link href="../resources/css/admin.css" type="text/css" rel="stylesheet" media="screen,projection"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/layout/sidebar.jsp"/>
<div class="container-lg">
    <div class=" d-flex justify-content-center">
        <div class="admin-title">
            <h2>회원목록</h2>
        </div>
    </div>
	<form action="/admin/remove" method="post" id="frm">
    <div class="table-responsive">
        <table class="table" id="checkBoard">
            <thead class="thead-dark">
            <tr>
                <th scope="col">#</th>
                <th scope="col">아이디</th>
                <th scope="col">이름</th>
                <th scope="col">닉네임</th>
                <th scope="col">이메일</th>
                <th scope="col">생일</th>
                <th scope="col">성별</th>
                <th scope="col">가입날짜</th>
                <th scope="col"><input type="checkbox" aria-label="Checkbox for following text input" id="allCheck" name="allCheck">전체선택</th>
            </tr>
            </thead>
            <tbody>
            <c:set var="memberCount" value="${0}"/>
            <c:forEach var="member" items="${memberList}">
                <tr>
                    <th scope="row">${memberCount=memberCount+1}</th>
                    <td><a href="/admin/modify?id=${member.id}">${member.id}</a></td>
                    <td>${member.name}</td>
                    <td>${member.nickname}</td>
                    <td>${member.email}</td>
                    <td>${member.birthday}</td>
                    <td>${member.gender}</td>
                    <td>${member.regDate}</td>
                    <td class="d-flex justify-content-center">
                        <input type="checkbox" name="chk" id="chk"aria-label="Checkbox for following text input" value="${member.id}">
                    </td>
                </tr>
            </c:forEach>
            </tbody>   
        </table>
        <button class="btn btn-primary member-delete" onclick="report()">회원탈퇴
        </button>
    </div>
    </form>

</div>


<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
</body>
</html>

 <script>
        function report() {
            
        }


        $(function(){
        	 
            var $checkHead = $("#checkBoard thead th input[type='checkbox']"); // table에 아이디 checkBoard로 선언 thead에서 체크박스인 input을 찾는다.
            var $checkBody = $("#checkBoard tbody td input[type='checkbox']"); // tbody에서 체크박스인 input을 찾는다.
         
            //전체선택
            $checkHead.click(function(){
                var $bodyPutCk = $checkHead.is(":checked");//th에서 체크가 된것을 bodyPutCk변수에 저장
         
                if ( $bodyPutCk == true ) {
                    $checkBody.attr("checked", true);//attr:
                    $checkBody.prop("checked", true);//prop:
                }else {
                    $checkBody.attr("checked", false);
                    $checkBody.prop("checked", false);
                }
            });
         
            //하위 전체 선택시 전체버튼 선택 
            $checkBody.click(function(){//데이터 있는곳
                var tdInput_Length = $checkBody.length; // td 에 있는 input 갯수
                var tdInput_Check_Length = $("#checkBoard tbody td input[type='checkbox']:checked").length; // td 에 있는 선택된 input 갯수
         
                console.log(tdInput_Length);
                console.log(tdInput_Check_Length);
         
                if ( tdInput_Length == tdInput_Check_Length ) {
                    //$checkHead.addClass("active");
                    $checkHead.attr("checked", true);
                    $checkHead.prop("checked", true);
                }else {
                    //$checkHead.removeClass("active");
                    $checkHead.attr("checked", false);
                    $checkHead.prop("checked", false);
                }
            });
         
        })
          
        
        

    </script>

<!-- 체크박스 전체선택 -->
<!-- <script type="text/javascript">
$(document).ready(function() {
	$("#allCheck").click(function() {
		if ($("#allCheck").is(":checked")) $("input[name=chk]").prop("checked", true);
		else $("input[name=chk]").prop("checked", false);
	});
	
	$("input[name=chk]").click(function() {
		var total = $("input[name=chk]").length;
		var checked = $("input[name=chk]:checked").length;

		if(total != checked) $("#allCheck").prop("checked", false);
		else $("#allCheck").prop("checked", true); 
	});
</script>

<script src="../../../resources/js/jquery-3.6.0.js"></script> -->