<div id="menuBar">
<ul>
    <li><a href="@{Application.index()}"    #{currentMenu 'menuHome' /} >&{'menuhome'}</a></li>
    <li><a href="@{Application.explore()}"  #{currentMenu 'menuExplore' /} >&{'menuexplore'}</a></li>
    #{if controllers.Secure.Security.isConnected()}
    <li><a href="@{Dives.index()}"          #{currentMenu 'menuYourDives' /} >&{'menuyourdives'}</a></li>
    #{/if }
</ul>
</div>