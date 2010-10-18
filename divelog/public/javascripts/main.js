$(document).ready(function() {

	// Focus on search field
	if (!("autofocus" in document.createElement("input"))) {
	      $("#searchInput").focus();
	    }
	
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
	
	// make info disappear
	$("#actionFeedbackMessage").delay(5000).fadeOut();

});