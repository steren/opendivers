<div id="addFish-form" title="Import a fish from Wikipedia">
    <form>
    <label for="name">Wikipedia URL</label>
    <input type="text" name="wikipediaURL" id="wikipediaURL" class="text ui-widget-content ui-corner-all" /> <a id="readWikipediaURL">Read</a>
    </form>
    
    <div id="insertTest"></div>
</div>


<script>
var wikipediaHTMLResult = function(data) {
    // TODO handle redirect (example:http://en.wikipedia.org/wiki/Amphiprion)

    var box = $('<div>' + data.parse.text.* + '</div>').find('.infobox');
    
    var binomialName    = box.find('.binomial').text();
    var fishName        = box.find('th').first().text();
    var imageURL        = box.find('img').first().attr('src');

    $('#insertTest').append('<div><img src="'+ imageURL + '"/>'+ fishName +' <i>('+ binomialName +')</i></div>');
};

$('#readWikipediaURL').button().click( function() {
	//http://www.mediawiki.org/wiki/API:Parsing_wikitext#parse
	$.getJSON("http://en.wikipedia.org/w/api.php?action=parse&format=json&callback=?", {page:$('#wikipediaURL').val(), prop:"text"}, wikipediaHTMLResult);
    }
);
        
$( "#addFish-form" ).dialog({
    autoOpen: false,
    height: 300,
    width: 350,
    modal: true,
    buttons: {
        "Import fish": function() {
            //$( this ).dialog( "close" );
        },
        Cancel: function() {
            $( this ).dialog( "close" );
        }
    },
    close: function() {

    }
});

$( "#addFish" )
    .button()
    .click(function() {
        $( "#addFish-form" ).dialog( "open" );
    });
</script>

