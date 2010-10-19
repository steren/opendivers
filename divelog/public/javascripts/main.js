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

// Google Maps
function initialize() {
    var mapDiv = document.getElementById('map-canvas');
    var myLatlng = new google.maps.LatLng(0, 0);
    var myOptions = {
      zoom: 1,
      center: myLatlng,
      mapTypeId: google.maps.MapTypeId.SATELLITE,
      disableDefaultUI: true,
      navigationControl: true
    };
    
    var map = new google.maps.Map(mapDiv, myOptions);

    function setLatLngInputValues(location) {
  	  $("#spotLatitude").val(location.lat());
	  $("#spotLongitude").val(location.lng());
    }
    
    function placeMarker(location) {
  	  var marker = new google.maps.Marker({
  		  map: map,
  	      position: location, 
          draggable: true
  	  });

      google.maps.event.addListener(marker, 'dragend', function(mouseEvent) {
    	  setLatLngInputValues(mouseEvent.latLng);
    	  map.panTo(mouseEvent.latLng);
      });
  	  
	  setLatLngInputValues(location);
      map.panTo(location);
    }
    
    google.maps.event.addListenerOnce(map, 'click', function(event) {
        placeMarker(event.latLng);
    });
    
  }

  google.maps.event.addDomListener(window, 'load', initialize);
