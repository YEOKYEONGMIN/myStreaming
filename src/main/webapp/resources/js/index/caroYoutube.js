
// 유튜브 방송리스트
// $.ajax({
//     url: '/api/youtube/liveList',
//     dataType:"JSON",
//     method: 'GET',
//     success: function (data) {
//         console.log("youtube 데이터")
//         console.log(data);
//
//         createYoutubeCard(data);
//
//     }
// });

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
