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

	// Drag & Drop for fishes
	$( "#fishLibrary .fishBadge" ).draggable({
		revert: "invalid",
		helper: "clone",
		containment: "#fishDragAndDrop" // stick to demo-frame if present
	});
	$( "#fishNet" ).droppable({
		activeClass: 'active',
		drop: function( event, ui ) {
			putFishInNet(ui.draggable); //$( this ).addClass( "dropped" );
		}
	});

	function putFishInNet( item ) {
		item.fadeOut("fast", function() { item.appendTo( $("#fishNet") ).fadeIn();} );
	}
	
	
	
	// Display Maps
	var mapSelectDiv = $("#mapSelectLocation").get(0);
	if(mapSelectDiv) {
		attachSelectionMap(mapSelectDiv);
	}
	var mapDisplayDiv = $("#mapDisplayLocation").get(0);
	if(mapDisplayDiv) {
		attachDisplayMap(mapDisplayDiv);
	}
	var mapExploreDiv = $("#mapExplore").get(0);
	if(mapExploreDiv) {
		attachExploreMap(mapExploreDiv);
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

function attachExploreMap(mapDiv) {
	var myOptions = {
		zoom : 2,
		center : new google.maps.LatLng(0, 0),
		mapTypeId : google.maps.MapTypeId.SATELLITE,
		disableDefaultUI : true,
		navigationControl : true
	};

	var map = new google.maps.Map(mapDiv, myOptions);
	
	for (var i in exploreLocations) {
		new google.maps.Marker( {
			map : map,
			position : exploreLocations[i]
		});
		
	} 
	
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