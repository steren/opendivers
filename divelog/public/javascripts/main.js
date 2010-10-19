$(document).ready(function() {

	// Focus on search field
	if (!("autofocus" in document.createElement("input"))) {
		$("#searchInput").focus();
	}

	$("#signupLink").click(function() {
		$("#loginBox").hide();
		var box = $("#signupBox");
		if (box.is(":visible")) {
			box.fadeOut();
		} else {
			box.fadeIn();
		}
	});

	$("#loginLink").click(function() {
		$("#signupBox").hide();
		var box = $("#loginBox");
		if (box.is(":visible")) {
			box.fadeOut();
		} else {
			box.fadeIn();
		}
	});

	// make info disappear
	$("#actionFeedbackMessage").delay(5000).fadeOut();

	// Display Maps
	var mapSelectDiv = $("#mapSelectLocation").get(0);
	if(mapSelectDiv) {
		attachSelectionMap(mapSelectDiv);
	}
	var mapDisplayDiv = $("#mapDisplayLocation").get(0);
	if(mapDisplayDiv) {
		attachDisplayMap(mapDisplayDiv);
	}
});

// Google Maps
function attachDisplayMap(mapDiv) {
	var myOptions = {
		zoom : 14,
		center : displayLocation,
		mapTypeId : google.maps.MapTypeId.SATELLITE,
		disableDefaultUI : true,
		navigationControl : true
	};

	var map = new google.maps.Map(mapDiv, myOptions);
	
	var marker = new google.maps.Marker( {
		map : map,
		position : displayLocation
	});
}


function attachSelectionMap(mapDiv) {
	var myOptions = {
		zoom : displayZoom,
		center : displayLocation,
		mapTypeId : google.maps.MapTypeId.SATELLITE,
		disableDefaultUI : true,
		navigationControl : true
	};

	var map = new google.maps.Map(mapDiv, myOptions);
	
	if(displayMarker) {
		placeMarker(displayLocation);
	}

	function setLatLngInputValues(location) {
		$("#spotLatitude").val(location.lat());
		$("#spotLongitude").val(location.lng());
	}

	function placeMarker(location) {
		var marker = new google.maps.Marker( {
			map : map,
			position : location,
			draggable : true
		});

		google.maps.event.addListener(marker, 'dragend', function(mouseEvent) {
			setLatLngInputValues(mouseEvent.latLng);
			map.panTo(mouseEvent.latLng);
		});

		setLatLngInputValues(location);
		map.panTo(location);
	}
	
	if(!displayMarker) {
		google.maps.event.addListenerOnce(map, 'click', function(event) {
			placeMarker(event.latLng);
		});
	}
}