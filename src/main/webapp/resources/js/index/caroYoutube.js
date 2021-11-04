
// 유튜브 방송리스트
$.ajax({
    url: '/api/youtube/liveList',
    dataType:"JSON",
    method: 'GET',
    success: function (data) {
        console.log("youtube 데이터")
        console.log(data);

        createYoutubeCard(data);
		bookmarkChk();
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
	                        <div class="card_link">
	                        <a href="https://www.youtube.com/watch?v=${data.items[i].snippet.resourceId.videoId}" id="btn_link" target="_blank">방송보러 가기</a>
	                    	<i class="far fa-star bookmark" id="bookmark${data.items[i].snippet.videoOwnerChannelId}"
	                    		onclick="BookmarkYoutube('${data.items[i].snippet.videoOwnerChannelId}', '${data.items[i].snippet.videoOwnerChannelTitle}');"></i>
	                    	</div>
	                    </div>
	                </div>
	                <div class="card">
	                    <img src="${data.items[i+1].snippet.thumbnails.medium.url}"
	                        class="card-img-top" alt="...">
	                    <span class="live">생방송</span>
	                    <div class="card-body">
	                        <h5 class="card-title">${data.items[i+1].snippet.videoOwnerChannelTitle}</h5>
	                        <p class="card-text">${data.items[i+1].snippet.title}</p>
	                        <div class="card_link">
	                        <a href="https://www.youtube.com/watch?v=${data.items[i+1].snippet.resourceId.videoId}" id="btn_link" target="_blank">방송보러 가기</a>
	                    	<i class="far fa-star bookmark" id="bookmark${data.items[i+1].snippet.videoOwnerChannelId}"
	                    		onclick="BookmarkYoutube('${data.items[i+1].snippet.videoOwnerChannelId}', '${data.items[i+1].snippet.videoOwnerChannelTitle}');"></i>
	                    	</div>
	                    </div>
	                </div>
	                <div class="card">
	                    <img src="${data.items[i+2].snippet.thumbnails.medium.url}"
	                        class="card-img-top" alt="...">
	                    <span class="live">생방송</span>
	                    <div class="card-body">
	                        <h5 class="card-title">${data.items[i+2].snippet.videoOwnerChannelTitle}</h5>
	                        <p class="card-text">${data.items[i+2].snippet.title}</p>
	                        <div class="card_link">
	                        <a href="https://www.youtube.com/watch?v=${data.items[i+2].snippet.resourceId.videoId}" id="btn_link" target="_blank">방송보러 가기</a>
	                    	<i class="far fa-star bookmark" id="bookmark${data.items[i+2].snippet.videoOwnerChannelId}"
	                    		onclick="BookmarkYoutube('${data.items[i+2].snippet.videoOwnerChannelId}', '${data.items[i+2].snippet.videoOwnerChannelTitle}');"></i>
	                    	</div>
	                    </div>
	                </div>
	                <div class="card">
	                    <img src="${data.items[i+3].snippet.thumbnails.medium.url}"
	                        class="card-img-top" alt="...">
	                    <span class="live">생방송</span>
	                    <div class="card-body">
	                        <h5 class="card-title">${data.items[i+3].snippet.videoOwnerChannelTitle}</h5>
	                        <p class="card-text">${data.items[i+3].snippet.title}</p>
	                        <div class="card_link">
	                        <a href="https://www.youtube.com/watch?v=${data.items[i+3].snippet.resourceId.videoId}" id="btn_link" target="_blank">방송보러 가기</a>
	                    	<i class="far fa-star bookmark" id="bookmark${data.items[i+3].snippet.videoOwnerChannelId}"
	                    		onclick="BookmarkYoutube('${data.items[i+3].snippet.videoOwnerChannelId}', '${data.items[i+3].snippet.videoOwnerChannelTitle}');"></i>
	                    	</div>
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
function BookmarkYoutube( youtubeId, youtubeName){
    let $i = $('#bookmark'+youtubeId);
    let $a = $('#nav__follow'+youtubeId);
    
    console.log(youtubeId);
    console.log(youtubeName);
    
    $.ajax({
    url: '/api/youtube/channelId/'+youtubeId,
    dataType:"JSON",
    method: 'GET',
    success: function (data) {
        console.log("youtube 데이터")
        console.log(data);
        addOrDeleteBookmarkYoutube(youtubeId,youtubeName, data.items[0].snippet.thumbnails.medium.url);
        let str = `
		    	<a href="https://www.youtube.com/channel/${youtubeId}" class="nav__follow" id="nav__follow${youtubeId}" target="_blank">
				<img class="nav__img" alt="사진" src="${data.items[0].snippet.thumbnails.medium.url }"> <span
				class="nav_name">${ youtubeName }</span> 
		   	 `
		 if($i.hasClass("far fa-star") === true){
		        $i.removeClass('far fa-star').addClass('fas fa-star');
		        $('.nav__bookmark').append(str);
		    }else{
		        $i.removeClass('fas fa-star').addClass('far fa-star');
		        $a.remove();
		   }
    	}
	});
}

function addOrDeleteBookmarkYoutube(youtubeId, youtubeName, profileImageUrl){
	let $i = $('#bookmark'+youtubeId);
    console.log(youtubeId);
    console.log(youtubeName);
    console.log(profileImageUrl);
    // let profileUrl= encodeURIComponent(profile_image_url);


    let bookmarkValue = JSON.stringify({
        streamerId: youtubeId,
        streamerName: youtubeName,
        profileImageUrl: profileImageUrl
    })


    $.ajax({
        url: '/api/bookmark',
        contentType: 'application/json; charset=UTF-8',
        data:bookmarkValue,
        method: 'POST',
        success: function (data) {
            console.log("북마크 메세지")
            console.log(data);
			if(data.msg == 'failed'){
				$i.removeClass('fas fa-star').addClass('far fa-star');
				$('#loginModal').modal('show');
			}
        }
    });
}

function  bookmarkChk(){
	
	  $.ajax({
        url: '/api/bookmark',
        dataType:"JSON",
    	method: 'GET',
        success: function (data) {
            console.log("겟 데이터")
            console.log(data);
			if(data.msg != 'Id null'){
				for(let i=0; i<data.bookmarkList.length; i++){
					let $i = $('#bookmark'+data.bookmarkList[i].streamerId);
					console.log($i);
					$i.removeClass('far fa-star').addClass('fas fa-star');
				}
			}

        }
    });
}