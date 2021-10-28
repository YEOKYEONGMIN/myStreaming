
//구글로 로그인 버튼 만들기
document.write("<script src='https://apis.google.com/js/api:client.js' async defer></script>");
document.write("<script src='https://apis.google.com/js/platform.js?onload=renderButton' async defer></script>");


    var googleUser = {};
    var startApp = function() {
    gapi.load('auth2', function(){
        // Retrieve the singleton for the GoogleAuth library and set up the client.
        auth2 = gapi.auth2.init({
            client_id: 'YOUR_CLIENT_ID.apps.googleusercontent.com',
            cookiepolicy: 'single_host_origin',
            // Request scopes in addition to 'profile' and 'email'
            //scope: 'additional_scope'
        });
        attachSignin(document.getElementById('customBtn'));
    });
};

    function attachSignin(element) {
    console.log(element.id);
    auth2.attachClickHandler(element, {},
    function(googleUser) {
    document.getElementById('name').innerText = "Signed in: " +
    googleUser.getBasicProfile().getName();
}, function(error) {
    alert(JSON.stringify(error, undefined, 2));
});
}




