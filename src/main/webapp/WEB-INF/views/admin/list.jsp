<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>회원 목록</title>
    <jsp:include page="/WEB-INF/views/layout/Header.jsp"/>
    <link href="../resources/css/admin.css" type="text/css" rel="stylesheet" media="screen,projection"/>
</head>
<body>
	<jsp:include page="/WEB-INF/views/layout/sidebar.jsp" />
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
							<th scope="col"><input type="checkbox"
								aria-label="Checkbox for following text input" id="allCheck"
								name="allCheck">전체선택</th>
						</tr>
					</thead>
					<tbody>
						<c:set var="memberCount" value="${0}" />
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
								<td class="d-flex justify-content-center"><input
									type="checkbox" name="chk"
									aria-label="Checkbox for following text input"
									value="${member.id}"></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>

				 <%--페이지 --%>
            <nav aria-label="Page navigation example">
              <ul class="pagination justify-content-center">

              <%-- 이전 --%>
              <li class="page-item ${ (pageMaker.prev) ? '' : 'disabled' }">
                 <a class="page-link" href="${ (pageMaker.prev) ? '/admin/list?pageNum=' += (pageMaker.startPage - 1) : '' }#member">이전</a>
              </li>

              <%-- 시작페이지 번호 ~ 끝페이지 번호 --%>
              <c:forEach var="i" begin="${ pageMaker.startPage }" end="${ pageMaker.endPage }" step="1"><%--step = 1은 증가 --%>
                 <li class="page-item ${ (pageMaker.cri.pageNum eq i) ? 'active' : '' }">
                    <a class="page-link " href="/admin/list?pageNum=${ i }#member">${ i }</a>
                 </li>
              </c:forEach>

              <%-- 다음 --%>
              <li class="page-item ${ (pageMaker.next) ? '' : 'disabled' }">
                 <a class="page-link " href="${ (pageMaker.next) ? '/admin/list?pageNum=' += (pageMaker.endPage + 1) : '' }#member">다음</a>
              </li>

              </ul>
            </nav>



				<div class="memberCheck">
					<button class="btn btn-primary member-delete" id="admin-delete">회원삭제
					</button>
				</div>
			</div>
		</form>

</div>


<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
</body>
</html>

<script>


	$(function() {//체크박스 참고 홈페이지:https://kcmschool.com/73

		var $checkHead = $("#checkBoard thead th input[type='checkbox']"); // table에 아이디 checkBoard로 선언 thead에서 체크박스인 input을 찾는다.
		var $checkBody = $("#checkBoard tbody td input[type='checkbox']"); // tbody에서 체크박스인 input을 찾는다.

		//전체선택
		$checkHead.click(function() {
			var $bodyPutCk = $checkHead.is(":checked");//th에서 체크가 된것을 bodyPutCk변수에 저장

			if ($bodyPutCk == true) {
				$checkBody.attr("checked", true);//attr:속성값 가져오기
				$checkBody.prop("checked", true);//prop:실제적인상태
			} else {
				$checkBody.attr("checked", false);
				$checkBody.prop("checked", false);
			}
		});

		//하위 전체 선택시 전체버튼 선택
		$checkBody.click(function() {//데이터 있는곳
					var tdInput_Length = $checkBody.length; // td 에 있는 input 갯수
					var tdInput_Check_Length = $("#checkBoard tbody td input[type='checkbox']:checked").length; // td 에 있는 선택된 input 갯수


					if (tdInput_Length == tdInput_Check_Length) {
						//$checkHead.addClass("active");
						$checkHead.attr("checked", true);
						$checkHead.prop("checked", true);
					} else {
						$checkHead.removeClass("active");
						$checkHead.attr("checked", false);
						$checkHead.prop("checked", false);
					}
				});

	})

	//삭제

	$(function(){
			var chkObj = document.getElementsByName("chk");//td:id를 chk라 설정
			var rowCnt = chkObj.length;

			$("input[name='allCheck']").click(function(){//th:id를 allCheck라 설정
				var chk_listArr = $("input[name='chk']");
				for (var i=0; i<chk_listArr.length; i++){
					chk_listArr[i].checked = this.checked;
				}
			});
			$("input[name='chk']").click(function(){
				if($("input[name='chk']:checked").length == rowCnt){
					$("input[name='allCheck']")[0].checked = true;
				}
				else{
					$("input[name='allCheck']")[0].checked = false;
				}
			});
		});

	$('#admin-delete').on("click",function (e) {
		e.preventDefault();
		let list = $("input[name='chk']");

		if (list.length == 0){
			alert("선택된 글이 없습니다.");
		}
		else{
			let check = confirm("정말 삭제하시겠습니까?");
			if(check){
				$('#frm').submit();
			}

		}
	})

    </script>
