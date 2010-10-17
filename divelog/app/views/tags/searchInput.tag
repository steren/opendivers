*{ The input box to perform a search }*

<div id="searchBox">
    <h5><label for="searchInput">Search</label></h5>
    #{form @Application.search()}
        <div id="simpleSearch">
            <input id="searchInput" name="query" type="search"  title="Search" accesskey="f"  value="" placeholder="Search" autofocus/>
            <button id="searchButton" type='submit' name='button'  title="Search for this query"><img src="@{'public/images/search.png'}" alt="Search" /></button>
        </div>
    #{/form}
</div>

