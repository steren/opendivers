$(document).ready(function() {

	// buttons
	$( "button.button, input:submit, a.button").button();

	// make info disappear
	$("#actionFeedbackMessage").delay(5000).fadeOut();
	
	// Focus on search field
	if (!("autofocus" in document.createElement("input"))) {
		$("#searchInput").focus();
	}

	//////////////////////
	// Login and signup
	//////////////////////
	$("#loginLink").click(function() {
		var box = $("#loginBox");
		if (box.is(":visible")) {
			box.fadeOut();
		} else {
			box.fadeIn();
		}
		return false;
	});
	
	$("#registerForm").validate({
		rules: {
			email: {
				required: true,
				email: true
			},
			username: {
				required: true,
				minlength: 3
			},
			password: {
				required: true,
				minlength: 5
			},
			passwordconfirm: {
				required: true,
				minlength: 5,
				equalTo: "#registerPassword"
			}
		}
	});

	//////////////////////
	// Invitation System
	//////////////////////
    $('#emailInvite').focus(function() { $('#inviteMessage').slideDown('normal');});
    
    $('#inviteForm').submit(function() {
        $.getJSON(inviteAction(), $(this).serialize(), function() { 
        	$('#inviteconfirm').slideDown('normal');
        	$('#inviteTextArea').val('');
        	$('#emailInvite').val('');
            }
        );
        return false;
    });
	
	//////////////////////
	// Fish Library
	//////////////////////
	
	var libraryPage = 1;
	var searchQuery;
	
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
		
		// clone this object
		item.clone()
		.find('a.removefish')
			.show()
		.end()
		.find('a.addfish')
			.hide()
		.end()
		.appendTo( $('#fishNetContent') ).fadeIn();
		
		addDraggableBehaviorToFishResults();
	}
	function putFishInLibrary( item ) {
		// remove fish from the list
		var index = fishIds.indexOf(item.attr('id'))
		if(index != -1) {
			// remove from the fishIds list
			fishIds.splice(index, 1);
		}
		writeFishesToInputField();
		
		// move HTML element
		item.fadeOut('fast');
	}
	
	function addDraggableBehaviorToFishResults() {
		$( ".fishResult" ).draggable({
			revert: "invalid",
			cursor: "move",
			helper: "clone",
			opacity: 0.7,
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
	}
	
	function getFishesResultsSuccess(data) {
		$( "#fishResults" ).html(data);
		addDraggableBehaviorToFishResults();
		if( $(data).length < 12 ) {
            $('#nextFishes').fadeTo('fast', 0);
		} else {
			$('#nextFishes').fadeTo('fast', 1);
		}
		if( libraryPage > 1 ) {
			$('#previousFishes').fadeTo('fast', 1);
		} else {
			$('#previousFishes').fadeTo('fast', 0);
		}
	}
	
	function getFishes() {
    	$.get(getFishesAction(), {pageNumber: libraryPage, searchQuery:$('#fishSearch-input').val()}, getFishesResultsSuccess);
	}
	
	getFishes();
	
	$('#nextFishes').click(function() {
		libraryPage++;
		getFishes();
		return false;
	});
	$('#previousFishes').click(function() {
		libraryPage--;
		getFishes();
		return false;
	});
		
	$('#fishSearch-input').keyup(function() {
		getFishes();
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
	
	
	//////////////////////
	// Fish from Wikipedia
	//////////////////////
	
	var wikipediaFishResult = {};
	var redirectOnce = false;

	function wikipediaHTMLResult(data) {
	    var readData = $('<div>' + data.parse.text.* + '</div>');

	    // check if article exists:
	    var exists = readData.find('#noarticletext');
	    if(exists.length != 0) {
	    	$('#wikipediaResults').html('<p>Sorry this wikipedia page is not an article</p>');
	    	return;
	    }
	    
	    // handle redirects
	    var redirect = readData.find('li:contains("REDIRECT") a, li:contains("redirect") a').text();
	    if(redirect != '') {
	    	// do not allow more than one redirect
	    	if(redirectOnce == false) {
	    		redirectOnce = true;
		    	callWikipediaAPI(redirect);
	    	} else {
	    		$('#wikipediaResults').html('<p>Sorry this wikipedia redirects to an invalid page</p>');
	    	}
	        return;
	    }
	    
	    var box = readData.find('.infobox');
	    
	    if(box != null) {
		    wikipediaFishResult.binomial    = box.find('.binomial').text();
		    // the common name of the fish, "th" can contain other info than the name, take the first text node
		    wikipediaFishResult.name        = box.find('th').first().contents().filter(function() { return this.nodeType == Node.TEXT_NODE;}).text();
		    wikipediaFishResult.wikipediaImageURL        = null;
	
		    // Check if page has images
		    if(data.parse.images.length >= 1) {
		    	wikipediaFishResult.wikipediaImageURL  = box.find('img').first().attr('src');
		    }
		    $('#wikipediaResults').html('<div class="fishIcon ui-widget-content middle"><img src="'+ wikipediaFishResult.wikipediaImageURL + '"/> <h5>' + wikipediaFishResult.name +'</h15</div>');
	    } else {
	    	$('#wikipediaResults').html('<p>Sorry data do not contain an infobox</p>');
	    }
	};

	function callWikipediaAPI(wikipediaPage) {
		// see http://www.mediawiki.org/wiki/API:Parsing_wikitext#parse
	    $.getJSON('http://en.wikipedia.org/w/api.php?action=parse&format=json&callback=?', {page:wikipediaPage, prop:'text|images', uselang:'en'}, wikipediaHTMLResult);
	}

	/** receive html of the new fish to add data from the server */
	function marineLifeCreationSuccess(data) {
		putFishInNet($(data));
	}
	
	$('#readWikipediaURL').button().click( function() {
		    wikipediaFishResult.wikipediaURL = $('#wikipediaURL').val();
		    redirectOnce = false;
		    
			var wikipediaPageArray = wikipediaFishResult.wikipediaURL.split('wikipedia.org/wiki/');
			if(wikipediaPageArray[1] != null && wikipediaPageArray[1] != "") {
				if(wikipediaPageArray[0].split('.')[0] == 'http://en') {
					callWikipediaAPI(wikipediaPageArray[1])
				} else {
					$('#wikipediaResults').html('<p>This URL is not from the English version of Wikipedia</p>');
				}
			} else {
				$('#wikipediaResults').html('<p>Not a valid wikipedia URL</p>');
			}
			return false;
	    }
	);
	        
	$( '#addFish-form' ).dialog({
	    autoOpen: false,
	    height: 280,
	    width: 500,
	    resizable: false,
	    modal: true,
	    buttons: {
	        "Import": function() {
	        	$.get(createFishAction(), wikipediaFishResult, marineLifeCreationSuccess);
	        	$( this ).dialog( "close" );
	        },
	        Cancel: function() {
	            $( this ).dialog( "close" );
	        }
	    },
	    close: function() {
	    }
	});

	$( '#addFish' ).click(function() {
	        $( '#addFish-form' ).dialog( 'open' );
	});
	
});

//////////////////////
// Google Maps
//////////////////////
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