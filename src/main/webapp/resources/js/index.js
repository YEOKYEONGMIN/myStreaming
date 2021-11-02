$('.carousel').carousel({
	interval: 0
});


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
		for(let i=0;i<19;i+=4){	
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
	                        <div class="card_link">
	                        	<a href="https://www.twitch.tv/${data.data[i].user_login}" id="btn_link" target="_blank">방송보러 가기</a>
	                    		<i class="far fa-star bookmark" id="bookmark${data.data[i].user_id}"
	                    		onclick="Bookmark('${data.data[i].user_id}', '${data.data[i].user_name}');"></i>
                            </div>
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
	                        <div class="card_link">
	                        	<a href="https://www.twitch.tv/${data.data[i+1].user_login}" id="btn_link" target="_blank">방송보러 가기</a>
	                    		<i class="far fa-star bookmark" id="bookmark${data.data[i+1].user_id}"
	                    		onclick="Bookmark('${data.data[i+1].user_id}', '${data.data[i+1].user_name}');"></i>
                            </div>
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
	                        <div class="card_link">
	                        	<a href="https://www.twitch.tv/${data.data[i+2].user_login}" id="btn_link" target="_blank">방송보러 가기</a>
	                    		<i class="far fa-star bookmark" id="bookmark${data.data[i+2].user_id}"
	                    		onclick="Bookmark('${data.data[i+2].user_id}', '${data.data[i+2].user_name}');"></i>
                            </div>
	                    </div>
	                </div>
	                <div class="card">
	                    <img src="${data.data[i+3].thumbnail_url}"
	                        class="card-img-top" alt="...">
	                    <span class="live">생방송</span>
	                    <span class="viewer_count">시청자 ${data.data[i+3].viewer_count}명</span>
	                    <div class="card-body">
	                        <h5 class="card-title">${data.data[i+3].user_name}</h5>
	                        <p class="card-text">${data.data[i+3].title}</p>
	                        <div class="card_link">
	                        	<a href="https://www.twitch.tv/${data.data[i+3].user_login}" id="btn_link" target="_blank">방송보러 가기</a>
	                    		<i class="far fa-star bookmark" id="bookmark${data.data[i+3].user_id}"
	                    		onclick="Bookmark('${data.data[i+3].user_id}', '${data.data[i+3].user_name}');"></i>
                            </div>
	                    </div>
	                </div>
	            </div>
	        </div>
			`
			let thumbnailWidth = str.replaceAll('{width}',1600);
			let thumbnailHeight = thumbnailWidth.replaceAll('{height}',900);
			$('#caroTwitch').append(thumbnailHeight);
		}
		let item = $('#caroTwitch').children('div').first();
		item.addClass('active');
	}


	

	function Bookmark( streamerId, streamerName){
		let $i = $('#bookmark'+streamerId);

		if($i.hasClass("far fa-star") === true){
    		$i.removeClass('far fa-star').addClass('fas fa-star');
    	}else{
    		$i.removeClass('fas fa-star').addClass('far fa-star');
    	}

		$.ajax({
	    url: '/api/twitch/user/'+streamerId,
	    dataType:"JSON",
	    method: 'GET',
	    success: function (data) {
	        console.log("데이터")
	        console.log(data);

	        addOrDeleteBookmark(streamerId,streamerName, data.data[0].profile_image_url);
	    	}
		});

		/*if(mid==null){
			$('#loginModal').modal(show);
			return;
		}*/

	}
	function addOrDeleteBookmark(streamerId, streamerName, profile_image_url){

		console.log(streamerId);
		console.log(streamerName);
		console.log(profile_image_url);
		let profileUrl= encodeURIComponent(profile_image_url);


        let bookmarkValue = JSON.stringify({
            streamerId: streamerId,
            streamerName:streamerName,
            profile_image_url: profileUrl
        })


		$.ajax({
	    url: '/api/bookmark/'+bookmarkValue,
	    contentType: 'application/json; charset=UTF-8',
	    method: 'POST',
	    success: function (data) {
	        console.log("데이터")
	        console.log(data);


	    	}
		});

	}



// 유튜브 방송리스트
$.ajax({
	url: '/api/youtube/liveList',
	dataType:"JSON",
	method: 'GET',
	success: function (data) {
		console.log("youtube 데이터")
		console.log(data);

		createYoutubeCard(data);

	}
});

function createYoutubeCard(data){
	for(let i=0;i<19;i+=4){
		let str = `
				<div class="carousel-item">
	            <div class="cards-wrapper">
	                <div class="card">
	                    <img src="${data.items[i].snippet.thumbnails.medium.url}"
	                        class="card-img-top" alt="...">
	                    <span class="live">생방송</span>
	                    <div class="card-body">
	                        <h5 class="card-title">${data.items[i].snippet.videoOwnerChannelTitle}</h5>
	                        <p class="card-text">${data.items[i].snippet.title}</p>
	                        <a href="https://www.youtube.com/watch?v=${data.items[i].snippet.resourceId.videoId}" id="btn_link" target="_blank">방송보러 가기</a>
	                    </div>
	                </div>
	                <div class="card">
	                    <img src="${data.items[i+1].snippet.thumbnails.medium.url}"
	                        class="card-img-top" alt="...">
	                    <span class="live">생방송</span>
	                    <div class="card-body">
	                        <h5 class="card-title">${data.items[i+1].snippet.videoOwnerChannelTitle}</h5>
	                        <p class="card-text">${data.items[i+1].snippet.title}</p>
	                        <a href="https://www.youtube.com/watch?v=${data.items[i+1].snippet.resourceId.videoId}" id="btn_link" target="_blank">방송보러 가기</a>
	                    </div>
	                </div>
	                <div class="card">
	                    <img src="${data.items[i+2].snippet.thumbnails.medium.url}"
	                        class="card-img-top" alt="...">
	                    <span class="live">생방송</span>
	                    <div class="card-body">
	                        <h5 class="card-title">${data.items[i+2].snippet.videoOwnerChannelTitle}</h5>
	                        <p class="card-text">${data.items[i+2].snippet.title}</p>
	                        <a href="https://www.youtube.com/watch?v=${data.items[i+2].snippet.resourceId.videoId}" id="btn_link" target="_blank">방송보러 가기</a>
	                    </div>
	                </div>
	                <div class="card">
	                    <img src="${data.items[i+3].snippet.thumbnails.medium.url}"
	                        class="card-img-top" alt="...">
	                    <span class="live">생방송</span>
	                    <div class="card-body">
	                        <h5 class="card-title">${data.items[i+3].snippet.videoOwnerChannelTitle}</h5>
	                        <p class="card-text">${data.items[i+3].snippet.title}</p>
	                        <a href="https://www.youtube.com/watch?v=${data.items[i+3].snippet.resourceId.videoId}" id="btn_link" target="_blank">방송보러 가기</a>
	                    </div>
	                </div>
	            </div>
	        </div>
			`
		let thumbnailWidth = str.replaceAll('{width}',300);
		let thumbnailHeight = thumbnailWidth.replaceAll('{height}',300);
		$('#caroYoutube').append(thumbnailHeight);
	}
	let item = $('#caroYoutube').children('div').first();
	item.addClass('active');
}

