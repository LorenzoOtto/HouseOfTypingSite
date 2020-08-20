$(document).ready(function() {
	var mollieId = getCookie("MollieId");
	if(mollieId != "") {
		$("#processPayment").css("display", "block");
		$("#beginSection").css("display", "none");
		$("#codeSection").css("display", "none");
		$.ajax({
			type : 'POST',
			data : {
				mollieId : mollieId,
				type : "statusPayment",
			},
			url : 'MollieController',
			success : function(result) {
				if(result=="paid") {
					$("#processPayment").css("display", "none");
					$("#paymentSuccess").css("display", "block");
					Cookies.remove('MollieId', { path: '' });
				} else {
					$("#processPayment").css("display", "none");
					$("#paymentFailed").css("display", "block");
					Cookies.remove('MollieId', { path: '' });
				}
			}
		});
	}
});

function getCookie(cname) {
	var name = cname + "=";
	var decodedCookie = decodeURIComponent(document.cookie);
	var ca = decodedCookie.split(';');
	for (var i = 0; i < ca.length; i++) {
		var c = ca[i];
		while (c.charAt(0) == ' ') {
			c = c.substring(1);
		}
		if (c.indexOf(name) == 0) {
			return c.substring(name.length, c.length);
		}
	}
	return "";
}