<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>QnA글쓰기</title>
<jsp:include page="/WEB-INF/views/layout/Header.jsp" />
</head>
<body>
   <jsp:include page="/WEB-INF/views/layout/sidebar.jsp" />
   
   <%--새글쓰기 --%>
   <hr>
   <div class="container">
      <h2>새글쓰기</h2>
      <hr class="featurette-divider">
      <form action="/board/write" method="POST" enctype="multipart/form-data">
         <div class="form-group">
            <label for="id">작성자:</label> 
             <input type="text" class="form-control" id="id" aria-describedby="idHelp" name="mid" value="${ sessionScope.id }" readonly>
         </div>
         <div class="form-group">
            <label for="subject">제목:</label>
             <input type="text" class="form-control" id="subject" name="subject" autofocus>
         </div>
      
      
         <div class="form-group">
            <label for="comment">내용:</label>
            <textarea class="form-control" id="content" name="content" rows="15" id="cotent"></textarea>
         </div>
     
      <br>
      <div class="form-group form-check">
    		<input type="checkbox" class="form-check-input" id="secret" name="secret1">
    		<label class="form-check-label" for="secret">비밀글</label>
 	 </div>
      <div>
         <button type="button" class="btn my-2 background-purple text-white btnAddFile" id="btnAddFile">파일추가</button>
      </div>
      <div><span>첨부 파일</span></div>
           <div id="fileBox">
           
              <div class="my-2">
                  <input type="file" name="files" multiple>
                  <button type="button" class=" btn btn-sm delete-file background-purple text-white my-1 x">
                     <i class="bi bi-calendar2-x"></i>
                     <span class="align-middle">삭제</span>
                  </button>
                </div>
              
           </div>
    

              <div class="my-4 text-center">
                <button type="submit" class="btn ml-3 background-purple text-white">
                    <i class="bi bi-arrow-up-square"></i>
                  <span class="align-middle">새글등록</span>
                </button>
                <button type="reset" class="btn ml-3 background-purple text-white">
                  <i class="bi bi-x-octagon-fill"></i>
                  <span class="align-middle">초기화</span>
                </button>
                 <button type="button" class="btn ml-3 background-purple text-white" onclick="location.href = '/board/list?pageNum=${ pageNum }';">
                   <i class="bi bi-card-checklist"></i>
                  <span class="align-middle">글목록</span>
                </button>
      
      
      
      
      
      </div>
      </form>
      </div>

    
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
  
    <script>

        var fileCount = 0;

        

        $('button.btnAddFile').on('click', function(){

            if(fileCount >= 5){
                    alert('첨부파일은 최대 5개까지만 첨부할 수 있습니다.');
                    return;
                }

                var str = `
                <div class="my-2">
                  <input type="file" name="files" multiple>
                  <button type="button" class="btn  btn-sm delete-file background-purple text-white">
                     <i class="bi bi-calendar2-x"></i>
                     <span class="align-middle">삭제</span>
                  </button>
                </div>
       
       `;

          $('div#fileBox').append(str);

          fileCount++;

        });

        //삭제

        $('div#fileBox').on('click','button.delete-file',function(){

            $(this).parent().remove();
       
       fileCount--;

        });


     







    </script>
    </body>
</html>