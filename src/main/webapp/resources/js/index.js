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
	
	function createCard(data){
		for(let i=0;i<18;i+=3){	
			let str = `
				<div class="carousel-item">
	            <div class="cards-wrapper">
	                <div class="card">
	                    <img src="${data.data[i].thumbnail_url}"
	                        class="card-img-top" alt="...">
	                    <span class="live">생방송</span>
	                    <span class="viewer_count">시청자 ${data.data[i].viewer_count}명</span>
	                    <div class="card-body">
	                        <h5 class="card-title">${data.data[i].user_name}</h5>
	                        <p class="card-text">${data.data[i].title}</p>
	                        <a href="https://www.twitch.tv/${data.data[i].user_login}" id="btn_link" target="_blank">방송보러 가기</a>
	                    </div>
	                </div>
	                <div class="card">
	                    <img src="${data.data[i+1].thumbnail_url}"
	                        class="card-img-top" alt="...">
	                    <span class="live">생방송</span>
	                    <span class="viewer_count">시청자 ${data.data[i+1].viewer_count}명</span>
	                    <div class="card-body">
	                        <h5 class="card-title">${data.data[i+1].user_name}</h5>
	                        <p class="card-text">${data.data[i+1].title}</p>
	                        <a href="https://www.twitch.tv/${data.data[i+1].user_login}" id="btn_link" target="_blank">방송보러 가기</a>
	                    </div>
	                </div>
	                <div class="card">
	                    <img src="${data.data[i+2].thumbnail_url}"
	                        class="card-img-top" alt="...">
	                    <span class="live">생방송</span>
	                    <span class="viewer_count">시청자 ${data.data[i+2].viewer_count}명</span>
	                    <div class="card-body">
	                        <h5 class="card-title">${data.data[i+2].user_name}</h5>
	                        <p class="card-text">${data.data[i+2].title}</p>
	                        <a href="https://www.twitch.tv/${data.data[i+2].user_login}" id="btn_link" target="_blank">방송보러 가기</a>
	                    </div>
	                </div>
	            </div>
	        </div>
			`
			"https://static-cdn.jtvnw.net/previews-ttv/live_user_tmxk319-{width}x{height}.jpg"
			let thumbnailWidth = str.replaceAll('{width}',300);
			let thumbnailHeight = thumbnailWidth.replaceAll('{height}',300);
			$('.carousel-inner').append(thumbnailHeight);
		}
		let item = $('.carousel-inner').children('div').first();
		item.addClass('active');
	}
	
	
