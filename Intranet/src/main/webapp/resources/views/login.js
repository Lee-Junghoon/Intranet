$(function(){
	$("#btnSignup").click(function(){
		var username = $("#username").val();
		var password = $("#password").val();
		var password2 = $("#password2").val();
		var birth = $("#birth").val();
		
		if(username==""){alert("사번을 입력하세요."); return;}
		if(birth==""){alert("주민등록번호 앞의 6자리 번호를 입력하세요."); return;}
		if(password==""){alert("비밀번호를 입력하세요."); return;}
		if(password2==""){alert("비밀번호를 한번 더 입력하세요."); return;}
		
		if(password!=password2){
			alert("패스워드와 패스워드 확인이 똑같아야 됩니다.");
			return;
		}

		$.post(_context + "signup",{username:username,password:password,birth:birth},	function(data){	
			if(data=="not registered"){
				alert("등록되었습니다.");
				$("#signupModal").modal("toggle");
			}else if(data=="exist username"){
				alert("이미 등록된 사원입니다.");
				$("#signupModal").modal("toggle");
			}else if(data=="unknown username"){
				alert("삼송인이 아닙니다.");
				$("#signupModal").modal("toggle");
			}
		});
	});
	
	$("#btnFindPw").click(function(){
		var username = $("#username2").val();
		var birth = $("#birth2").val();
		
		if(username==""){alert("사번을 입력하세요."); return;}
		if(birth==""){alert("주민등록번호 앞의 6자리 번호를 입력하세요."); return;}
		
		$.post(_context + "findpw",{username:username,birth:birth},	function(data){	
			if(data=="exist username"){
				alert("임시 비밀번호가 발송 되었습니다. 메일을 확인하세요.");
				$("#findPwModal").modal("toggle");
			}else if(data="not registered"){
				alert("등록된 사원이 아닙니다.");
				$("#findPwModal").modal("toggle");
			}else if(data="unknown username"){
				alert("삼송인이 아닙니다.");
				$("#findPwModal").modal("toggle");
			}
		});
	});	
	
	$("#signupModal").on("hidden.bs.modal", function(e){
		$(this).find("input").val("").end();
	});
	$("#signupModal").on("shown.bs.modal", function() {
		  $(this).find("input:first").focus();
	});
	$("#findPwModal").on("hidden.bs.modal", function(e){
		$(this).find("input").val("").end();
	});
	$("#findPwModal").on("shown.bs.modal", function() {
		  $(this).find("input:first").focus();
	});
});