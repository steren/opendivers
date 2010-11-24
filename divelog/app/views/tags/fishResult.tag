<li class="fishResult ui-widget-content ui-corner" id="${_fish.id}">
    <a href="#" title="&{'addfish'}"    class="ui-icon ui-icon-circle-plus addfish"     #{if !_inLibrary } style="display:none;"#{/if}   >&{'addfish'}</a>
    <a href="#" title="&{'removefish'}" class="ui-icon ui-icon-circle-close removefish" #{if _inLibrary } style="display:none;"#{/if}    >&{'removefish'}</a>
    <img alt="${_fish.name}" src="${_fish.wikipediaImageURL}" />
    <h5>${_fish.name}</h5>
</li>