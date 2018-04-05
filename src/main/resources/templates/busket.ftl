<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html>
<head>
    <!--Import Google Icon Font-->
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <!--Import materialize.css-->
    <link type="text/css" rel="stylesheet" href="css/materialize.min.css" media="screen,projection"/>

    <!--Let browser know website is optimized for mobile-->
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

    <link rel="stylesheet" type="text/css" href="<@spring.url '/css/style.css'/>"/>

</head>

<body>
<div class="row">
    <div class="col s3 offset-s9" style="margin-top: 10px">
        <a href="" style=" color: #4db6ac; margin-right: 20px">log in</a>
        <a href="" style=" color: #4db6ac">register</a>
    </div>
</div>


<div class="row">
    <form class="col s12">
        <div class="row">
            <div class="input-field col s3 offset-s4">
                <input placeholder="Search" id="search" type="text" class="validate">
            </div>
            <div>
                <div class="btn" style="margin-top: 25px; margin-right: 300px"><i class="material-icons">search</i>
                </div>
                <a href=""> <i style="font-size: 36px; color: #4db6ac;" class="material-icons">shopping_cart</i> </a>
            </div>
        </div>
    </form>
</div>
<div class="divider z-depth-3"></div>


<div class="row">
    <div class="col s6 offset-s3" style="margin-top: 100px">

        <#if empty_busket==true >
            <div class="col s1">Busket is empty</div>
        <#else>

    <#list order.itemList as item>
        <div class="card-panel grey lighten-5 z-depth-1">
            <div class="col s3">    <a href="/items/${item.id}">${item.name}</a></div>
            <div class="col s1">${item.price}rub</div>
            <div class="col s1">${item.storage}ps</div>
            <div style="alignment: right"><a class="waves-effect waves-light btn">order</a></div>
        </div>
    </#list>
        </#if>
    </div>
</div>


<!--JavaScript at end of body for optimized loading-->
<script type="text/javascript" src="js/materialize.min.js"></script>
</body>
</html>