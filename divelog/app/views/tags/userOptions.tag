<div id="userOptions">
#{if controllers.Secure.Security.isConnected()}
    ${controllers.Security.connectedUserName()}
    <a href="@{Secure.logout()}">&{'logoutlink'}</a> 
#{/if}
#{else}
    <a id="signupLink" href="@{Register.register}">&{'signuplink'}</a>
    <a id="loginLink" href="#" >&{'loginlink'}</a>
#{/else}
</div>

#{if !controllers.Secure.Security.isConnected()}
    <div id="loginBox" class="floatingBox" style="display:none;">
    <h2>&{'login'}</h2>
       #{form @Secure.authenticate()}
            <p id="email-field">
                <label for="username">&{'email'}</label> *{ Secure module works using "username", but for us, username is the email }*
                <input type="email" name="username" id="username" value="${flash.username}" />
            </p>
            <p id="password-field">
                <label for="password">&{'password'}</label>
                <input type="password" name="password" id="password" value="" />
            </p>
            <p id="remember-field">
                <input type="checkbox" name="remember" id="remember" value="true" ${flash.remember ? 'checked="true"' : ''} />
                <label for="remember">&{'remember'}</label>
            </p>
            <p id="signin-field">
                <input type="submit" id="signin" value="&{'login'}" />
            </p>
        #{/form}
    </div>
#{/if}