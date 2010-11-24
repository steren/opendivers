<div id="addFish-form" title="&{'importfromwikipedia'}">
    <p><a href="http://en.wikipedia.org/wiki/Special:Search" target="_blank">&{'linksearchwikipedia'}</a> or <a href="http://en.wikipedia.org/wiki/List_of_fish_common_names" target="_blank">&{'linkbrowsewikipedia'}</a>&{'englishwikipedia'}</p>
    <form>
    <label for="name">&{'wikipediaurl'}</label>
    <input type="text" name="wikipediaURL" id="wikipediaURL" class="text ui-widget-content ui-corner-all" placeholder="http://en.wikipedia.org/wiki/Red_Sea_Clownfish"/> <a id="readWikipediaURL">&{'readwikipediaurl'}</a>
    </form>
    
    <div id="insertTest"></div>
</div>


<script>

var wikipediaFishResult = {};

function wikipediaHTMLResult(data) {
    var readData = $('<div>' + data.parse.text.* + '</div>');

    // handle redirects
    var redirect = readData.find('li:contains("REDIRECT") a, li:contains("redirect") a').text();
    if(redirect != '') {
    	callWikipediaAPI(redirect);
        return;
    }
    
    var box = readData.find('.infobox');
    
    wikipediaFishResult.binomial    = box.find('.binomial').text();
    wikipediaFishResult.name        = box.find('th').first().text();
    wikipediaFishResult.wikipediaImageURL        = null;

    // Check if page has images
    if(data.parse.images.length >= 1) {
    	wikipediaFishResult.wikipediaImageURL  = box.find('img').first().attr('src');
    }
    
    $('#insertTest').append('<div><img src="'+ wikipediaFishResult.wikipediaImageURL + '"/>'+ wikipediaFishResult.name +' <i>('+ wikipediaFishResult.binomial +')</i></div>');
};

function callWikipediaAPI(wikipediaPage) {
	// see http://www.mediawiki.org/wiki/API:Parsing_wikitext#parse
    $.getJSON('http://en.wikipedia.org/w/api.php?action=parse&format=json&callback=?', {page:wikipediaPage, prop:'text|images', uselang:'en'}, wikipediaHTMLResult);
}

$('#readWikipediaURL').button().click( function() {
	    wikipediaFishResult.wikipediaURL = $('#wikipediaURL').val();
		var wikipediaPage = wikipediaFishResult.wikipediaURL.split('wikipedia.org/wiki/')[1];
		callWikipediaAPI(wikipediaPage)
    }
);
        
$( '#addFish-form' ).dialog({
    autoOpen: false,
    height: 400,
    width: 500,
    modal: true,
    buttons: {
        "&{'importfish'}": function() {
        	$.get('@{Application.createFish()}', wikipediaFishResult, marineLifeCreationSuccess);
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
</script>

