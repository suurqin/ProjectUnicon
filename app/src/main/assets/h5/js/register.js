var BASE_URL = "http://39.106.138.102:8088";
function sendCode(){
	
		var phone = $("#username").val();
		var params = {"phone":$.base64.encode(phone),"type":$.base64.encode(0)}
		$.ajax({
				type : 'post',
				url : BASE_URL + '/api/common/P001',
				data : params,
				dataType : 'json',
				timeout : 10000,
				beforeSend: function(request) {
     			request.setRequestHeader("secret", 0);
				},
				success : function(data) {
					if (data.status == 0) {
					
					} else {
					
						alert(data.msg);
					}
					return false;
				}
			});
		
		}
	function register(){
		
		var username = $("#username").val();
		var code = $("#code").val();
		var password = $("#password").val();
		var mobile = $("#mobile").val();
		var params = {"username":$.base64.encode(username),"code":$.base64.encode(code),"password":$.base64.encode(password),"mobile":$.base64.encode(mobile)};
		$.ajax({
				type : 'post',
				url : BASE_URL + '/api/user/R001',
				data : params,
				dataType : 'json',
				timeout : 10000,
				beforeSend: function(request) {
     			request.setRequestHeader("secret", 0);
				},
				success : function(data) {
					if (data.status == 0) {
					
					} else {
						alert(data.msg);
					}
					return false;
				}
			});
	}
function GetQueryString(name)
{
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); return null;
}