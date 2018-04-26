<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html>
<head>
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="../js/addItemsHandler.js"></script>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="../css/materialize.min.css" media="screen,projection"/>
    <link type="text/css" rel="stylesheet" href="../css/my_style.css"/>

    <title>VOLT-signup</title>
</head>

<body>
<div style="margin-top: 80px" class="row">
    <div class="col s6 offset-s3">
        <div class="card hoverable">
            <div class="card-content">
                <form method="post" action="/signup">
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
                            <input id="password" name="password" type="password" value="${user.password}">
                            <label class="active" for="password">Password</label>
                            <div name="password-errors"></div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s10 offset-s1">
                            <input id="confirmPassword" name="confirmPassword" type="password"
                                   value="${user.confirmPassword}">
                            <label class="active" for="confirmPassword">Confirm password</label>
                            <div name="confirmPassword-errors"></div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s4 offset-s1">
                            <span>I`m </span>
                             <#if user.roleId==1>
                            <p><label>
                                <input type="radio" name="roleId" id="roleId" value="1" checked/>
                                <span>Seller</span>
                            </label></p>
                            <p><label>
                                <input type="radio" name="roleId" id="roleId" value="2"/>
                                <span>Customer</span>
                            </label></p>

                             <#else>
                              <p><label>
                                  <input type="radio" name="roleId" id="roleId" value="1"/>
                                  <span>Seller</span>
                              </label></p>
                            <p><label>
                                <input type="radio" name="roleId" id="roleId" value="2" checked/>
                                <span>Customer</span>
                            </label></p>
                             </#if>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s2 offset-s1">
                            <button class="waves-effect waves-light btn" type="submit">Signup</button>
                        </div>
                        <div class="input-field col s2 offset-s7">
                            <a href="/login" class="cl-4db6ac">login</a>
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