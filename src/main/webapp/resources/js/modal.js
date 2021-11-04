
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


let navbarSearch= $('#navbarSearch');
let searchModalInput = $('#searchModalInput');

navbarSearch.on("focus", function () {
    console.log("클릭")
    $('#searchModal').modal('show')
    searchModalInput.focus();
    let time = getTime();

    $('.search-time').empty();
    $('.search-time').append(time);
})

function getTime() {
    let today = new Date();
    let hours = today.getHours();
    let minutes = today.getMinutes();
    minutes = Math.floor(minutes/10)*10;
    return hours + ":" + minutes;
}





