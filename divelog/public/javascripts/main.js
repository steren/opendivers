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

	// buttons
	$( "button.button, input:submit, a.button").button();

	
	// make info disappear
	$("#actionFeedbackMessage").delay(5000).fadeOut();

	// Drag & Drop for fishes
	
	var fishLibrary = $( '#fishLibrary' );
	var fishNet 	= $( '#fishNet' );
	var fishResults = $( '#fishResults' );
	var fishIds 	= [];
	
	// store here the ids of the fishes
	if($('#fishIds').size() > 0) {
		var val = $('#fishIds').val();
		if(val !== "") {
			fishIds = val.split(',');
		}
	}
	
	function writeFishesToInputField() {
		$('#fishIds').val(fishIds.join(','));
	}
	function putFishInNet( item ) {
		// add fish to the list
		fishIds.push(item.attr('id'));
		writeFishesToInputField();
		
		// move HTML element
		item.fadeOut("fast", function() { 
			item.find('a.removefish')
					.show()
				.end()
				.find('a.addfish')
					.hide()
				.end()
				.appendTo( fishNet ).fadeIn();
		} );
	}
	function putFishInLibrary( item ) {
		// remove fish from the list
		var index = fishIds.indexOf(item.attr('id'))
		if(index != -1) {
			fishIds.splice(index, 1);
		}
		writeFishesToInputField();
		
		// move HTML element
		item.fadeOut('fast', function() { 
			item.find('a.addfish')
					.show()
				.end()
				.find('a.removefish')
					.hide()
				.end()
				.prependTo( fishResults ).fadeIn();
		} );
	}
	
	$( ".fishResult" ).draggable({
		revert: "invalid",
		cursor: "move",
		helper: "clone",
		containment: "#fishDragAndDrop"
	}).click(function( event ) {
			var $item = $( this ),
			$target = $( event.target );
	
		if ( $target.is( "a.removefish" ) ) {
			putFishInLibrary( $item );
		} else if ( $target.is( "a.addfish" ) ) {
			putFishInNet( $item );
		} 
		return false;
	});
	
	fishNet.droppable({
		accept: "#fishLibrary .fishResult",
		activeClass: 'active',
		drop: function( event, ui ) {
			putFishInNet(ui.draggable); //$( this ).addClass( "dropped" );
		}
	});
	fishLibrary.droppable({
		accept: "#fishNet .fishResult",
		activeClass: 'active',
		drop: function( event, ui ) {
			putFishInLibrary(ui.draggable);
		}
	});

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
		var marker = new google.maps.Marker( {
			map : map,
			title : exploreLocations[i].title,
			position : exploreLocations[i].position
		});
		google.maps.event.addListener(marker, 'click', ( function(thisMarker, thisLocation) {
				return function() {
					var content = '<h3><a href="' + navigateToSpotAction({'id': thisLocation.id}) + '">' + thisLocation.title + '</a></h3>';
					var infowindow = new google.maps.InfoWindow({
					    content: content
					});
					infowindow.open(map, thisMarker);
				};
			}(marker, exploreLocations[i]) ));
		
	} 
}

var selectionMap;
var selectionMarker

function attachSelectionMap(mapDiv) {
	var myOptions = {
		zoom : displayZoom,
		center : displayLocation,
		mapTypeId : google.maps.MapTypeId.SATELLITE,
		disableDefaultUI : true,
		navigationControl : true
	};

	selectionMap = new google.maps.Map(mapDiv, myOptions);
	
	if(displayMarker) {
		placeMarkerOnSelectionMap(displayLocation);
	}

	if(!displayMarker && !selectionMarker) { // if the marker hasn't already been set
		google.maps.event.addListenerOnce(selectionMap, 'click', function(event) {
			placeMarkerOnSelectionMap(event.latLng);
		});
	}
}

function setLatLngInputValues(location) {
	$("#spotLatitude").val(location.lat());
	$("#spotLongitude").val(location.lng());
}

function getValueFromCountryInput() {
    var country = "";
    var option = $("#country-select option:selected").get(0);
    if(option.value != "") { // this line prevent from using the "Country" option
    	country = option.text;	
    }
    return country;
}

/**
 * 
 * @param address: the address of the location to move to (words)
 */
function moveSelectionMapToAddress(address) {
	// if a marker exists, do something clever (selectionMarker) 
	if(address != "") {
		var geocoder = new google.maps.Geocoder();
	
	    geocoder.geocode( { 'address': address}, function(results, status) {
	      if (status == google.maps.GeocoderStatus.OK) {
	        selectionMap.fitBounds(results[0].geometry.viewport);
	      }
	    });
    }
}

/**
 * 
 * @param location: the LatLng of the position
 */
function placeMarkerOnSelectionMap(location) {
	selectionMarker = new google.maps.Marker( {
		map : selectionMap,
		position : location,
		draggable : true
	});

	google.maps.event.addListener(selectionMarker, 'dragend', function(mouseEvent) {
		setLatLngInputValues(mouseEvent.latLng);
		selectionMap.panTo(mouseEvent.latLng);
	});

	setLatLngInputValues(location);
	selectionMap.panTo(location);
}