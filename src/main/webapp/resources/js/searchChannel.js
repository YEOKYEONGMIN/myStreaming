
$( document ).ready(function() {
	
	let name = $('#keyword').val();
	console.log(name);
	$.ajax({
    	url: '/Streamer/'+name,
    	dataType:"JSON",
    	method: 'GET',
    	success: function (data) {
        	searchStreamer(data.streamerList[0]);
        	searchYoutuber(name);
    	}
	});
});
function searchStreamer(streamerId){
	
	
	$.ajax({
        url: '/api/twitch/user/' + streamerId,
        dataType: "JSON",
        method: 'GET',
        success: function (data) {
            getFollow(streamerId, data.data[0].profile_image_url, data.data[0].display_name, data.data[0].description);
           
        }
    });
}
function getFollow(streamerId,url,name,description){
	let follow ="";
	$.ajax({
        url: '/api/twitch/follow/' + streamerId,
        dataType: "JSON",
        method: 'GET',
        success: function (data) {
            let str = `
            <div class="channel-img">
                <img src="${url}">
            </div>
            <div class="mx-4" style="display: flex;flex-direction: column">
                <div class="channel-title">
                    <span>
                        ${name}
                    </span>
                </div>
                <div class="my-3">
                    <span>
                        구독자수 : ${data.total}명
                    </span>
                </div>

                <div class="channel-description my-3">
                    <span>
                        ${description}
                    </span>
                </div>
            </div>
            `
            $('#twitchChannel').append(str);
            
        }
    });
}
function searchYoutuber(searchName){
	$.ajax({
    url: '/api/youtube/search/'+searchName,
    dataType:"JSON",
    method: 'GET',
    success: function (data) {
        getSubCount(data.items[0].id.channelId, data.items[0].snippet.thumbnails.medium.url,
         data.items[0].snippet.channelTitle, data.items[0].snippet.description);
         
        }
	});
}

function getSubCount(id, url, title, description){
	$.ajax({
    url: '/api/youtube/subCount/'+id,
    dataType:"JSON",
    method: 'GET',
    success: function (data) {
        let str = `
            <div class="channel-img">
                <img src="${url}">
            </div>
            <div class="mx-4" style="display: flex;flex-direction: column">
                <div class="channel-title">
                    <span>
                        ${title}
                    </span>
                </div>
                 <div class="my-3">
                    <span>
                        구독자수 : ${data.items[0].statistics.subscriberCount}명
                    </span>
                </div>
                <div class="channel-description my-3">
                    <span>
                        ${description}
                    </span>
                </div>
            </div>
            `
            $('#youtubeChannel').append(str);
        }
	});
}