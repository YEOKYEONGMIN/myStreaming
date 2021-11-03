<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="modal" id="searchModal" tabindex="-1" aria-labelledby="searchModal" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">

                <div style="display: flex; width: 100%">
                    <div class="col-2 logo">
                        <img src="/resources/images/tempLogo2.png" alt="로고">
                        <span>myStreaming</span>
                    </div>

                    <div class="offset-2 col-4 search-bar">
                        <div class="input-icon">
                            <input type="text" class="form-control" id="searchModalInput" placeholder="어떤 방송을 찾고계신가요?" name="search">
                            <span class="append-icon"><i class="bi bi-search"></i></span>
                        </div>
                        <div>
                            <span class="modal-close-span" data-dismiss="modal">취소</span>
                        </div>
                    </div>
                </div>





            </div>
            <div class="modal-body">
                <div>
                    최근 검색어
                </div>
                <div>
                    추천 검색어
                </div>
                <div>
                    실시간 검색
                </div>
            </div>
        </div>
    </div>
</div>