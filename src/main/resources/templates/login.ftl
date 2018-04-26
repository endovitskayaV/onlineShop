<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html>
<head>
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="../js/addItemsHandler.js"></script>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="../css/materialize.min.css" media="screen,projection"/>
    <link type="text/css" rel="stylesheet" href="../css/my_style.css"/>

    <link rel="icon" href="https://cdn.crowdfundinsider.com/wp-content/uploads/2015/02/strongvolt-logo-300x248.jpg">
    <title>VOLT-login</title>
</head>

<body>
<div style="margin-top: 80px" class="row">
    <div class="col s6 offset-s3">
        <div class="card hoverable">
            <div class="card-content">
                <form method="post" action="/login">
                    <div class="row">
                        <div class="input-field col s10 offset-s1">
                            <div name="form-errors"></div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s10 offset-s1">
                            <input id="email" name="email" type="text" value="${user.email}">
                            <label class="active" for="email">Email</label>
                            <div name="email-errors"></div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s10 offset-s1">
                            <input id="password"  name="password" type="password"
                                   value="${user.password}">
                            <label class="active" for="password">Password</label>
                            <div name="password-errors"></div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="input-field col s2 offset-s1">
                            <button class="waves-effect waves-light btn" type="submit">Login</button>
                        </div>
                        <div class="input-field col s2 offset-s7">
                            <a href="/signup" class="cl-4db6ac">register</a>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<div id="error-modal" class="modal modal-content"></div>

<#if errors??>
    <#list errors as error>
<script>showError("${error.field}", "${error.message}");</script>
    </#list>
</#if>

</body>
</html>