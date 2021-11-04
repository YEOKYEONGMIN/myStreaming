$(document).ready(function () {
    $('.pass_show').append('<span class="ptxt"><i class="bi bi-eye"></i></span>');
});


$(document).on('click', '.pass_show .ptxt', function () {
    let showIcon = `<i class="bi bi-eye"></i>`;
    let hideIcon = `<i class="bi bi-eye-slash"></i>`;

    $(this).html($(this).html() === showIcon ? hideIcon : showIcon);
    $(this).prev().attr('type', function (index, attr) {
        return attr == 'password' ? 'text' : 'password';
    });

});

/*=================================검색 모달 시작=================================*/


let navbarSearch = $('#navbarSearch');
let searchModalInput = $('#searchModalInput');

navbarSearch.on("focus", function () {
    console.log("클릭")
    $('#searchModal').modal('show')
    searchModalInput.focus();
    let time = getTime();

    $('.search-time').empty();
    $('.search-time').append(time);

    let searchList = returnJsonSearchList();
    recentSearch(searchList);
    console.log(searchList);
})

function getTime() {
    let today = new Date();
    let hours = today.getHours();
    let minutes = today.getMinutes();
    minutes = Math.floor(minutes / 10) * 10;
    return hours + ":" + minutes;
}

//===================검색 히스토리 구현


$('#search-button').on("click", function (e) {
    let searchList = returnJsonSearchList();
    recentSearch(searchList);
    console.log(searchList);
});

function returnJsonSearchList() {
    let jsonSearchList;
    let strSearchList = localStorage.getItem("searchListJson");
    let searchValue = $('#searchModalInput').val();
    if(strSearchList != null){
        jsonSearchList = JSON.parse(strSearchList);
        let index = Object.keys(jsonSearchList).length + 1;
        if(searchValue !== ""){
            jsonSearchList[index] = searchValue;
        }
    }else {
        jsonSearchList = JSON.parse(JSON.stringify({1: searchValue}));
    }
    strSearchList = JSON.stringify(jsonSearchList);
    localStorage.setItem("searchListJson",strSearchList);
    return jsonSearchList;
}

function recentSearch(jsonSearchList) {
    $('.search-results').empty();
    let count = Object.keys(jsonSearchList).length;

    for(let i=count;i>count-3;i--){
        let tag = `       <div class="search-result-one">
                            <span class="search-value">${jsonSearchList[i]}</span>
                            <span class="delete-span"><i class="bi bi-x"></i></span>
                        </div>`;
        $('.search-results').append(tag);
    }


}



