$(document).ready(function() {

	$("#signupLink").click(function() {
		$("#loginBox").hide();
		var box = $("#signupBox");
		if(box.is(":visible")) {
			box.fadeOut();
		} else {
			box.fadeIn();
		}
	});

	$("#loginLink").click(function() {
		$("#signupBox").hide();
		var box = $("#loginBox");
		if(box.is(":visible")) {
			box.fadeOut();
		} else {
			box.fadeIn();
		}
	});

});