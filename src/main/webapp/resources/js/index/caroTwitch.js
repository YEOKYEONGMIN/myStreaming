// 방송 리스트

$.ajax({
    url: '/api/twitch/liveList',
    dataType: "JSON",
    method: 'GET',
    success: function (data) {
        console.log("데이터")
        console.log(data);

        createCard(data);
        twitchbookmarkChk();

    }
});

function createCard(data) {
    for (let i = 0; i < 19; i += 4) {
        let str = `
				<div class="carousel-item">
	                <div class="cards-wrapper">
                        <div class="card mt-2 mb-5">
                            <div class="custom-shadow">
                                <a href="https://www.twitch.tv/${data.data[i].user_login}" target="_blank">
                                <img src="${data.data[i].thumbnail_url}"
                                    class="card-img-top" alt="...">
                                    </a>
                                <span class="live">생방송</span>
                                <span class="viewer_count">시청자 ${data.data[i].viewer_count}명</span>
                            </div>
                            <div class="card-body">
                                <div class="my-1"> 
                                     <span class="card-text">${data.data[i].title}</span>
                                </div>
                                <div class="card_link my-1">
                                <div>
                                 <span class="card-title">${data.data[i].user_name}</span>
                                </div>
                                <div>
                                    <i class="far fa-star bookmark" id="bookmark${data.data[i].user_id}"
                                    onclick="Bookmark('${data.data[i].user_id}', '${data.data[i].user_name}', '${data.data[i].user_login}');">
                                    </i>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
	                   <div class="card mt-2 mb-5">
                            <div class="custom-shadow">
                                <a href="https://www.twitch.tv/${data.data[i+1].user_login}" target="_blank">
                                <img src="${data.data[i+1].thumbnail_url}"
                                    class="card-img-top" alt="...">
                                    </a>
                                <span class="live">생방송</span>
                                <span class="viewer_count">시청자 ${data.data[i+1].viewer_count}명</span>
                            </div>
                            <div class="card-body">
                                <div class="my-1"> 
                                     <span class="card-text">${data.data[i+1].title}</span>
                                </div>
                                <div class="card_link my-1">
                                <div>
                                 <span class="card-title">${data.data[i+1].user_name}</span>
                                </div>
                                <div>
                                    <i class="far fa-star bookmark" id="bookmark${data.data[i+1].user_id}"
                                    onclick="Bookmark('${data.data[i+1].user_id}', '${data.data[i+1].user_name}', '${data.data[i+1].user_login}');">
                                    </i>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <div class="card mt-2 mb-5">
                            <div class="custom-shadow">
                                <a href="https://www.twitch.tv/${data.data[i+2].user_login}" target="_blank">
                                <img src="${data.data[i+2].thumbnail_url}"
                                    class="card-img-top" alt="...">
                                    </a>
                                <span class="live">생방송</span>
                                <span class="viewer_count">시청자 ${data.data[i+2].viewer_count}명</span>
                            </div>
                            <div class="card-body">
                                <div class="my-1"> 
                                     <span class="card-text">${data.data[i+2].title}</span>
                                </div>
                                <div class="card_link my-1">
                                <div>
                                 <span class="card-title">${data.data[i+2].user_name}</span>
                                </div>
                                <div>
                                    <i class="far fa-star bookmark" id="bookmark${data.data[i+2].user_id}"
                                    onclick="Bookmark('${data.data[i+2].user_id}', '${data.data[i+2].user_name}', '${data.data[i+2].user_login}');">
                                    </i>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <div class="card mt-2 mb-5">
                            <div class="custom-shadow">
                                <a href="https://www.twitch.tv/${data.data[i+3].user_login}" target="_blank">
                                <img src="${data.data[i+3].thumbnail_url}"
                                    class="card-img-top" alt="...">
                                    </a>
                                <span class="live">생방송</span>
                                <span class="viewer_count">시청자 ${data.data[i+3].viewer_count}명</span>
                            </div>
                            <div class="card-body">
                                <div class="my-1"> 
                                     <span class="card-text">${data.data[i+3].title}</span>
                                </div>
                                <div class="card_link my-1">
                                <div>
                                 <span class="card-title">${data.data[i+3].user_name}</span>
                                </div>
                                <div>
                                    <i class="far fa-star bookmark" id="bookmark${data.data[i+3].user_id}"
                                    onclick="Bookmark('${data.data[i+3].user_id}', '${data.data[i+3].user_name}', '${data.data[i+3].user_login}');">
                                    </i>
                                    </div>
                                </div>
                            </div>
                        </div>
	            </div>
	        </div>
			`
        let thumbnailWidth = str.replaceAll('{width}', 1600);
        let thumbnailHeight = thumbnailWidth.replaceAll('{height}', 900);
        $('#caroTwitch').append(thumbnailHeight);
    }
    let item = $('#caroTwitch').children('div').first();
    item.addClass('active');
}


function Bookmark(streamerId, streamerName, streamerLogin) {
    let $i = $('#bookmark' + streamerId);
    let $a = $('#nav__follow' + streamerId);

    $.ajax({
        url: '/api/twitch/user/' + streamerId,
        dataType: "JSON",
        method: 'GET',
        success: function (data) {
            console.log("프로필 데이터")
            console.log(data);

            addOrDeleteBookmark(streamerId, streamerName, streamerLogin, data.data[0].profile_image_url);
            let str = `
		    	<a href="https://www.twitch.tv/${streamerLogin}" class="nav__follow" id="nav__follow${streamerId}" target="_blank">
				<img class="nav__img" alt="사진" src="${data.data[0].profile_image_url}"> <span
				class="nav_name">${streamerName}</span>
				<span class="twitchIs_live" id="isLive${streamerId}"></span>
		   	 `
            if ($i.hasClass("far fa-star") === true) {
                $i.removeClass('far fa-star').addClass('fas fa-star');
                $('.nav__bookmark').append(str);
            } else {
                $i.removeClass('fas fa-star').addClass('far fa-star');
                $a.remove();
            }
            twitchIsLive(streamerId, streamerName);
        }
    });

    /*if(mid==null){
        $('#loginModal').modal(show);
        return;
    }*/

}

function addOrDeleteBookmark(streamerId, streamerName, streamerLogin, profileImageUrl) {
    let $i = $('#bookmark' + streamerId);
    console.log(streamerId);
    console.log(streamerName);
    console.log(profileImageUrl);
    // let profileUrl= encodeURIComponent(profile_image_url);


    let bookmarkValue = JSON.stringify({
        streamerId: streamerId,
        streamerName: streamerName,
        streamerLogin: streamerLogin,
        profileImageUrl: profileImageUrl
    })


    $.ajax({
        url: '/api/bookmark',
        contentType: 'application/json; charset=UTF-8',
        data: bookmarkValue,
        method: 'POST',
        success: function (data) {
            console.log("북마크 메세지")
            console.log(data);
            if (data.msg == 'failed') {
                $i.removeClass('fas fa-star').addClass('far fa-star');
                $('#loginModal').modal('show');
            }

        }
    });

}

function twitchbookmarkChk() {

    $.ajax({
        url: '/api/bookmark',
        dataType: "JSON",
        method: 'GET',
        success: function (data) {
            console.log("겟 데이터")
            console.log(data);
            if (data.msg != 'Id null') {
                for (let i = 0; i < data.bookmarkList.length; i++) {
                    let $i = $('#bookmark' + data.bookmarkList[i].streamerId);
                    console.log($i);
                    $i.removeClass('far fa-star').addClass('fas fa-star');
                    console.log(data.bookmarkList[i].streamerLogin);
                    if (data.bookmarkList[i].streamerLogin != null) {
                        twitchIsLive(data.bookmarkList[i].streamerId, data.bookmarkList[i].streamerName);
                    }
                }
            }

        }
    });
}

function twitchIsLive(streamerId, streamerLogin) {

    $.ajax({
        url: '/api/twitch/isLive/' + streamerLogin,
        dataType: "JSON",
        method: 'GET',
        success: function (data) {
            console.log("라이브 데이터")
            console.log(data);

            let span = $('#isLive' + streamerId);
            console.log(span);
            if (data.data[0].is_live) {
                span.html('live').css('color', 'red');
                span.addClass('liveTrue').removeClass('liveFalse');
            } else {
                span.html('offline').css('color', 'grey');
                span.addClass('liveFalse').removeClass('liveTrue');
            }
        }
    });
}

