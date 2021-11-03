<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>QnA수정하기</title>
<jsp:include page="/WEB-INF/views/layout/Header.jsp" />
<jsp:include page="/WEB-INF/views/layout/modal/loginModal.jsp" />
</head>
<body>
   <jsp:include page="/WEB-INF/views/layout/sidebar.jsp" />
   
   
   <hr>
   <div class="container">
      <h2>QnA수정하기 </h2>
      <hr class="featurette-divider">
      <form action="/board/modify" method="POST" enctype="multipart/form-data">
          <input type="hidden" name="pageNum" value="${ pageNum }">
         <input type="hidden" name="num" value="${ board.num }">
         
         <div class="form-group">
            <label for="id">작성자:</label> 
             <input type="text" class="form-control" id="id" aria-describedby="idHelp" name="mid" value="${ sessionScope.id }" readonly>
         </div>
         <div class="form-group">
            <label for="subject">제목:</label>
             <input type="text" class="form-control" id="subject" name="subject" autofocus>
         </div>
      
      
         <div class="form-group">
            <label for="content">내용:</label>
           <textarea class="form-control" id="content" rows="10" name="content">${ board.content }</textarea>
         </div>
      
      <br>
      <div class="checkbox">
         <label><input type="checkbox" value="false" id="secret" name="secret1"> 비밀글</label>
      </div>
      <div>
            <button type="button" class="btn btn-primary my-3" id="btnAddFile">파일추가</button>
       </div>
      <div><span>첨부 파일</span></div>
           
            <!-- 기존 첨부파일 영역. 삭제할 파일정보 표현 용도 -->
           <div id="oldFileBox">
           
           <!--delete-oldfile 삭제버튼 클릭 시 hidden input 요소의 name 속성을 oldfile → delfile 로 수정-->
           <!-- 서버에서는 oldfile은 찾지않고 delfile만 찾아서 파일 삭제처리 -->
           <c:forEach var="attach" items="${ attachList }">
              <input type="hidden" name="oldfile" value="${ attach.uuid }">
              <div>
                 <span>${ attach.filename }</span>
                 <button type="button" class="btn btn-primary btn-sm delete-oldfile">
                      <i class="material-icons align-middle">clear</i>
                      <span class="align-middle">삭제</span>
                   </button>
              </div>
           </c:forEach>
                
           </div>
              <div class="newFileBox"></div>

              <div class="my-4 text-center">
                <button type="submit" class="btn btn-primary">
                    <i class="bi bi-arrow-up-square"></i>
                  <span class="align-middle">새글등록</span>
                </button>
                <button type="reset" class="btn btn-primary ml-3">
                  <i class="bi bi-x-octagon-fill"></i>
                  <span class="align-middle">초기화</span>
                </button>
                 <button type="button" class="btn btn-primary ml-3" onclick="location.href = '/board/list?pageNum=${ pageNum }'">
                   <i class="bi bi-card-checklist"></i>
                  <span class="align-middle">글목록</span>
                </button>
      
      
      
      
      
      </div>
      </form>
      </div>

    
    <script src="../../../resources/js/jquery-3.6.0.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.min.js" integrity="sha384-+YQ4JLhjyBLPDQt//I+STsc9iw4uQqACwlvpslubQzn4u2UU2UFM80nGisd026JF" crossorigin="anonymous"></script>
   <script src="https://unpkg.com/ionicons@5.2.3/dist/ionicons.js"></script>
   <script src="../resources/js/sidebar.js"></script>
   <script src="../resources/js/init.js"></script>
  
    <script>

   var fileCount = ${ fn:length(attachList) };

        

        $('button#btnAddFile').on('click', function(){

            if(fileCount >= 5){
                    alert('첨부파일은 최대 5개까지만 첨부할 수 있습니다.');
                    return;
                }

                var str = `
                <div class="my-2">
                  <input type="file" name="files" multiple>
                  <button type="button" class="btn btn-primary btn-sm delete-file">
                     <i class="bi bi-calendar2-x"></i>
                     <span class="align-middle">삭제</span>
                  </button>
                </div>
       
       `;

          $('div.newFileBox').append(str);

          fileCount++;

        });

        //삭제

        $('div.newFileBox').on('click','button.delete-file',function(){

            $(this).parent().remove();
       
       fileCount--;

        });
        
        //기존첨부파일삭제
        $('button..delete-oldfile').on('click',function(){
           
           
           $(this).parent().prev().prop('name','delfile');
           $(this).parent().remove();
           
           fileCount--;
           
        });


     







    </script>
    </body>
</html>