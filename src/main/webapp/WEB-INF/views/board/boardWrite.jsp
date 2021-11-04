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
<jsp:include page="/WEB-INF/views/layout/modal/loginModal.jsp" />
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
      <div class="checkbox">
         <label><input type="checkbox" value="false" id="secret" name="secret1"> 비밀글</label>
      </div>
      <div>
         <button type="button" class="btn btn-primary my-3 btnAddFile" id="btnAddFile">파일추가</button>
      </div>
      <div><span>첨부 파일</span></div>
           <div id="fileBox">
           
              <div class="my-2">
                  <input type="file" name="files" multiple>
                  <button type="button" class="btn btn-primary btn-sm delete-file">
                     <i class="bi bi-calendar2-x"></i>
                     <span class="align-middle">삭제</span>
                  </button>
                </div>
              
           </div>
    

              <div class="my-4 text-center">
                <button type="submit" class="btn btn-primary">
                    <i class="bi bi-arrow-up-square"></i>
                  <span class="align-middle">새글등록</span>
                </button>
                <button type="reset" class="btn btn-primary ml-3">
                  <i class="bi bi-x-octagon-fill"></i>
                  <span class="align-middle">초기화</span>
                </button>
                 <button type="button" class="btn btn-primary ml-3" onclick="location.href = '/board/list?pageNum=${ pageNum }';">
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

        var fileCount = 0;

        

        $('button.btnAddFile').on('click', function(){

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