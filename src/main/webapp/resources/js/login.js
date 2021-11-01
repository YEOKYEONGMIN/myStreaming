
$('#btnLogin').on('click', function () {
		

		var obj = $('form#loginForm').serializeObject();
		console.log(obj);
		console.log(typeof obj);
		
		var strJson = JSON.stringify(obj);
		console.log(strJson);
		console.log(typeof strJson);
		
		
		 // ajax 함수 호출
		 $.ajax({
			url: '/api/member/login',
			method: 'POST',
			data: strJson,
			contentType: 'application/json; charset=UTF-8',
			success: function (data) {
				console.log(data);
				alert(data.msg);
				if(data.msg =='로그인 성공.'){
					location.reload();
				}
			}
		}); 
	});