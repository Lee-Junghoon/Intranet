$(function(){
	$("#btnSavePw").click(function(){
		var currentPassword = $("#current_password").val();
		var newPassword = $("#new_password").val();
		var newPassword2 = $("#new_password2").val();
		
		if(currentPassword == ""){
			alert("현재 패스워드를 입력하세요.");
			return;
		}
		
		if(newPassword==""){alert("새로운 비밀번호를 입력하세요."); return;}
		if(newPassword2==""){alert("새로운 비밀번호를 한번 더 입력하세요."); return;}
		
		if(newPassword!=newPassword2){
			alert("패스워드와 패스워드 확인이 똑같아야 됩니다.");
			return;
		}
		
		
		$.post(context + "settings/changePassword",{currentPassword:currentPassword,newPassword:newPassword},	function(data){
			if(data=="true"){
				alert("변경되었습니다.");
			}else{
				alert("기존 패스워드가 틀렸습니다. 다시 입력하세요.");
			}
		});
	});
});