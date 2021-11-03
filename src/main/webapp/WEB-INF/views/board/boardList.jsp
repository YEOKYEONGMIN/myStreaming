<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>QnA게시판</title>
<jsp:include page="/WEB-INF/views/layout/Header.jsp" />
<jsp:include page="/WEB-INF/views/layout/modal/loginModal.jsp" />
</head>
<body>
   <jsp:include page="/WEB-INF/views/layout/sidebar.jsp" />
   <div class="container">

      <h2 class="text-center">Q&A</h2>
      <p class="text-center text-info">CS관련 문의사항을 적어주세요</p>
      <div class="text-left">
         <h5>게시판 글목록 (글개수: ${ pageMaker.totalCount })</h5>

         <%--아이디 세션 필z --%>
      
            <!-- 새글쓰기 버튼 -->
              <button type="button" class="btn btn-primary btn-sm float-right my-3" onclick="location.href = '/board/write?pageNum=${ pageMaker.cri.pageNum }';">
                 <span class="align-middle">새글쓰기</span>
              </button>
         
            
      <div class="clearfix"></div>
   
      
      
       <!-- 글목록 테이블 -->
         <table class="table table-hover" id="board">
            <thead>
               <tr>
                  <th scope="col" class="text-center">번호</th>
                  <th scope="col" class="text-center">제목</th>
                  <th scope="col" class="text-center">작성자</th>
                  <th scope="col" class="text-center">작성일</th>
                  <th scope="col" class="text-center">조회수</th>
                </tr>
             </thead>
             <tbody>
             <c:choose>
                 <c:when test="${ pageMaker.totalCount gt 0 }"><%--gt는 ">" --%>
                    <c:forEach var="board" items="${ boardList }">
                       <tr>
                        <td class="text-center">${ board.num }</td>
                        
                          
                          
                          <c:if test="${sessionScope.id != board.mid  and board.secret eq false}">
                          	<i class="bi bi-shield-lock-fill"></i>
                          <c:choose>
                          	<c:when test="${ sessionScope.id eq board.mid and board.secret eq true }"><%-- 자신의 비밀글일 경우 --%>
                          		<td>
                          			<a class="align-middle" href="/board/content?num=${ board.num }&pageNum=${ pageMaker.cri.pageNum }">${ board.subject }</a>	
                          		</td>
                          		
                          	</c:when>
                          	<c:otherwise>
                          		비밀글은 작성자와 관리자만 볼 수 있습니다.
                          	</c:otherwise>
                          </c:choose>
                          </c:if>
                          <c:if test="${ board.secret  eq false}"><%--비밀글이 아닐때  --%>
                          	<td>
                          <c:if test="${ board.reLev gt 0 }"><%-- 답글이면 --%>
                          	<span>
                          	<i class="bi bi-arrow-return-right"></i>
                          	 <span style="display: inline-block; width: ${ board.reLev * 15 }px"></span>
                          	</span>
                          </c:if>
                          	<a class="align-middle" href="/board/content?num=${ board.num }&pageNum=${ pageMaker.cri.pageNum }">${ board.subject }</a>	
                          </td>
                          
                          	<td class="text-center">${ board.mid }</td>
                        	<td class="text-center"><fmt:formatDate value="${ board.regDate }" pattern="yyyy.MM.dd" /></td>
                       		<td class="text-center"><span><i class="bi bi-eye"></i></span>  ${ board.readcount }</td>
                          </c:if>
                          
                         </tr>
                       
                        
                     
                    </c:forEach>
                 
                 </c:when>
                 <c:otherwise>
                    <tr>
                     <td colspan="5" class="text-center">QnA글이 존재 하지 않습니다.</td>
                   </tr>
                 </c:otherwise>
              </c:choose>
  
              </tbody>
            </table>
         <hr class="featurette-divider">
   
         <%--페이지 --%>
            <nav aria-label="Page navigation example">
              <ul class="pagination justify-content-center my-4">
              
              <%-- 이전 --%>
              <li class="page-item ${ (pageMaker.prev) ? '' : 'disabled' }">
                 <a class="page-link" href="${ (pageMaker.prev) ? '/board/list?pageNum=' += (pageMaker.startPage - 1) += '&type=' += pageMaker.cri.type += '&keyword=' += pageMaker.cri.keyword : '' }#board">이전</a>
              </li>
              
              <%-- 시작페이지 번호 ~ 끝페이지 번호 --%>
              <c:forEach var="i" begin="${ pageMaker.startPage }" end="${ pageMaker.endPage }" step="1"><%--step = 1은 증가 --%>
                 <li class="page-item ${ (pageMaker.cri.pageNum eq i) ? 'active' : '' }">
                    <a class="page-link" href="/board/list?pageNum=${ i }&type=${ pageMaker.cri.type }&keyword=${ pageMaker.cri.keyword }#board">${ i }</a>
                 </li>
              </c:forEach>
              
              <%-- 다음 --%>
              <li class="page-item ${ (pageMaker.next) ? '' : 'disabled' }">
                 <a class="page-link" href="${ (pageMaker.next) ? '/board/list?pageNum=' += (pageMaker.endPage + 1) += '&type=' += pageMaker.cri.type += '&keyword=' += pageMaker.cri.keyword : '' }#board">다음</a>
              </li>

              </ul>
            </nav>
         

            
			<div class="container">
            <form class="form-inline justify-content-center my-4" action="/board/list?#board" method="get" id="frm">
          
                 <div class="form-group mx-3">
                   <label for="searchType">검색 조건</label>
                      <select class="form-control mx-2" id="searchType" name="type">
                          <option value="" disabled selected>--</option>
                            <option value="subject" ${ (pageMaker.cri.type eq 'subject') ? 'selected' : '' }>제목</option>
                          <option value="content" ${ (pageMaker.cri.type eq 'content') ? 'selected' : '' }>내용</option>
                            <option value="mid" ${ (pageMaker.cri.type eq 'mid') ? 'selected' : '' }>작성자</option>
                      </select>
                 </div>

                  <label for="searchKeyword">검색어</label>
                    <input type="text" class="form-control mb-2 mr-sm-2 mx-2" id="searchKeyword" placeholder="검색어" name="keyword" value="${ pageMaker.cri.keyword }">
                      <button type="button" class="btn btn-primary mb-2" id="btnSearch">
  
                         <span class="align-middle">검색</span>
                    </button>
               </form>
         </div>
      </div>
   </div>


   <jsp:include page="/WEB-INF/views/layout/footer.jsp" />
   <script src="../../../resources/js/jquery-3.6.0.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.min.js" integrity="sha384-+YQ4JLhjyBLPDQt//I+STsc9iw4uQqACwlvpslubQzn4u2UU2UFM80nGisd026JF" crossorigin="anonymous"></script>
   <script src="https://unpkg.com/ionicons@5.2.3/dist/ionicons.js"></script>
   <script src="../resources/js/sidebar.js"></script>
   <script src="../resources/js/init.js"></script>
   <script>
   
   $('#btnSearch').on('click',function(){
      
      var query = $('#frm').serialize();
        console.log(query);
        
        location.href = '/board/list?' + query + '#board';
   });
   
   
   
   </script>
  
 
</body>
</html>
  
 

</body>
</html>