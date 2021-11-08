
$( document ).ready(function() {
	
	let name = $('#keyword').val();
	
	$.ajax({
    	url: '/Streamer/'+name,
    	dataType:"JSON",
    	method: 'GET',
    	success: function (data) {
       		console.log("db 서치값")
        	console.log(data);
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
            console.log("프로필 데이터")
            console.log(data);
        }
    });
}
function searchYoutuber(searchName){
	 $.ajax({
    url: '/api/youtube/search/'+searchName,
    dataType:"JSON",
    method: 'GET',
    success: function (data) {
        console.log("youtube 데이터")
        console.log(data);
        
        }
	});
}