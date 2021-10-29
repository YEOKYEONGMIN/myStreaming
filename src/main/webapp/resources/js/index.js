// 방송 리스트

$.ajax({
    url: '/api/twitch/liveList',
    dataType:"JSON",
    method: 'GET',
    success: function (data) {
        console.log("데이터")
        console.log(data);
    }
});