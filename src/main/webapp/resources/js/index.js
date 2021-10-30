// 방송 리스트




	$.ajax({
	    url: '/api/twitch/liveList',
	    dataType:"JSON",
	    method: 'GET',
	    success: function (data) {
	        console.log("데이터")
	        console.log(data);
	        
	        createCard(data);
	        
	        
	        
	    }
	});
	"https://static-cdn.jtvnw.net/previews-ttv/live_user_pjs9073-{width}x{height}.jpg"
	function createCard(data){
		for(let i=0;i<20;i+3){	
			let str = `
				<div class="carousel-item active">
	            <div class="cards-wrapper">
	                <div class="card">
	                    <img src="${data[i].thumbnail_url}"
	                        class="card-img-top" alt="...">
	                    <span class="live">생방송</span>
	                    <span class="viewer_count">시청자 ${data[i].viewer_count}명</span>
	                    <div class="card-body">
	                        <h5 class="card-title">침착맨</h5>
	                        <p class="card-text">침&펄 침착맨을 위한 지인과 여행 월드컵</p>
	                        <a href="#" id="btn_link">방송보러 가기</a>
	                    </div>
	                </div>
	                
	                <div class="card">
	                    <img src="https://static-cdn.jtvnw.net/previews-ttv/live_user_runner0608-200x200.jpg"
	                        class="card-img-top" alt="...">
	                    <div class="card-body">
	                        <h5 class="card-title">Card title</h5>
	                        <p class="card-text">Some quick example text to build on the card title and make up the bulk
	                            of
	                            the card's content.</p>
	                        <a href="#" id="btn_link">Go somewhere</a>
	                    </div>
	                </div>
	                <div class="card">
	                    <img src="https://static-cdn.jtvnw.net/previews-ttv/live_user_portialyn-200x200.jpg"
	                        class="card-img-top" alt="...">
	                    <div class="card-body">
	                        <h5 class="card-title">Card title</h5>
	                        <p class="card-text">Some quick example text to build on the card title and make up the bulk
	                            of
	                            the card's content.</p>
	                        <a href="#" id="btn_link">Go somewhere</a>
	                    </div>
	                </div>
	            </div>
	        </div>
			`
		}
		console.log(str);
	}
	
	
