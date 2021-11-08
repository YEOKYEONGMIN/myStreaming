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

    $('#searchModal').modal('show')
    searchModalInput.focus();
    let time = getTime();

    $('.search-time').empty();
    $('.search-time').append(time);

    if (searchList.length > 0) {
        addRecentSearch();
        searchResultContainer.show();
    }


})

$('.modal-close-span').on("click",function () {
    searchModalInput.val("");
})

function getTime() {
    let today = new Date();
    let hours = today.getHours();
    let minutes = today.getMinutes();
    minutes = Math.floor(minutes / 10) * 10;
    return hours + ":" + minutes;
}

//===================검색 히스토리 구현
let searchList;
let searchResults = $('.search-results');
let searchResultContainer = $('#search-recent-container');
searchResultContainer.hide();
if (localStorage.getItem("recentSearch") != null) {
    searchList = localStorage.getItem("recentSearch").split(",");
} else {
    searchList = [];
}

$('#search-recent-deleteAll').on("click", function () {
    searchResults.empty();
    searchResultContainer.hide();
    localStorage.removeItem("recentSearch");
    searchList = [];
})

$('#search-button').on("click", function (e) {

    let searchWord = $('#searchModalInput').val();
    if (searchWord != "") {
        searchResultContainer.show();
        let dupIndex = dupCheck(searchWord);
        addToList(dupIndex, searchWord);
        let strSearchList = searchList.toString();
        localStorage.setItem("recentSearch", strSearchList);
        addRecentSearch();

    }
    
});

function dupCheck(searchWord) {
    return searchList.indexOf(searchWord);
}

function addToList(dupIndex, searchWord) {
    if (dupIndex == -1) {
        searchList.push(searchWord);
    } else if (dupIndex == 0) {
        searchList.shift();
        searchList.push(searchWord);
    } else {
        searchList.splice(dupIndex, dupIndex);
        searchList.push(searchWord);
    }
}

function addRecentSearch() {
    searchResults.empty();
    let strRecentSearch = localStorage.getItem("recentSearch");
    let arrayRecentSearch = strRecentSearch.split(",");
    let count = arrayRecentSearch.length;

    if (count == 0) {
        return
    } else if (count < 4) {
        for (let i = 0; i < count; i++) {
            let tag = `       <div class="search-result-one" id="${arrayRecentSearch[i]}">
                            <span class="search-value" >${arrayRecentSearch[i]}</span>
                            <span class="delete-span" ><i class="bi bi-x"></i></span>
                        </div>`;
            searchResults.prepend(tag);
            if($('.search-results').children().length>0){
                let id = arrayRecentSearch[i];
                let search_div = $('#'+id);
                let search_value = search_div.children().first();
                let search_delete = search_div.children().last();

                search_value.on("click",function () {
                    $('#searchModalInput').val(id);
                })

                search_delete.on("click", function () {
                    $(this).parent('div').remove();
                    let deleteIndex = dupCheck(arrayRecentSearch[i]);
                    if (deleteIndex == 0) {
                        searchList.shift();
                    } else {
                        searchList.splice(deleteIndex, deleteIndex);
                    }
                    let strSearchList = searchList.toString();
                    localStorage.setItem("recentSearch", strSearchList);
                })
            }
        }
    } else {
        for (let i = count-1; i > count - 4; i--) {
            let tag = `       <div class="search-result-one">
                            <span class="search-value">${arrayRecentSearch[i]}</span>
                            <span class="delete-span" id="${arrayRecentSearch[i]}" ><i class="bi bi-x"></i></span>
                        </div>`;
            searchResults.append(tag);
            if($('.search-results').children().length>0){
                let id = arrayRecentSearch[i];
                let search_div = $('#'+id);
                let search_value = search_div.children().first();
                let search_delete = search_div.children().last();

                search_value.on("click",function () {
                    $('#searchModalInput').val(id);
                })

                search_delete.on("click", function () {
                    $(this).parent('div').remove();
                    let deleteIndex = dupCheck(arrayRecentSearch[i]);
                    if (deleteIndex == 0) {
                        searchList.shift();
                    } else {
                        searchList.splice(deleteIndex, deleteIndex);
                    }
                    let strSearchList = searchList.toString();
                    localStorage.setItem("recentSearch", strSearchList);
                })

            }

        }
    }


}



