<div id="userOptions">
#{if controllers.Secure.Security.isConnected()}
    ${controllers.Security.connectedUserName()}
    <a href="@{Secure.logout()}">&{'logout'}</a> 
#{/if}
#{else}
    <span id="signupLink"><a href="" onclick="return false">&{'signup'}</a></span>
    <span id="loginLink"><a href="" onclick="return false">&{'login'}</a></span>
#{/else}
</div>

#{if !controllers.Secure.Security.isConnected()}
    <div id="signupBox" class="floatingBox" style="display:none;">
    <h2>&{'signup'}</h2>
    #{form @Register.registerNew()}
        <p id="email-field">
            <label for="email">&{'email'}</label>
            <input type="text" name="email" id="email" value="&{flash.email}" />
        </p>
        <p id="username-field">
            <label for="username">&{'username'}</label>
            <input type="text" name="username" id="username" value="&{flash.username}" />
        </p>
        <p id="password-field">
            <label for="password">&{'password'}</label>
            <input type="password" name="password" id="password" value="" />
        </p>
        <p id="signin-field">
        <input type="submit" id="signin" value="&{'signup'}" />
        </p>
    #{/form}
    </div>

    <div id="loginBox" class="floatingBox" style="display:none;">
    <h2>&{'login'}</h2>
       #{form @Secure.authenticate()}
            <p id="email-field">
                <label for="username">&{'email'}</label> *{ Secure module works using "username", but for us, username is the email }*
                <input type="text" name="username" id="username" value="${flash.username}" />
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