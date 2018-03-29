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
<table class="col s6 offset-s3" style="margin-top: 100px">
    <tbody>
    <tr class="card-panel grey lighten-5 z-depth-1">
        <td class=>Alvin</td>
        <td>Eclair</td>
        <td>$0.87</td>
    </tr>
    </tbody>
</table>
</div>


<table border="1">
    <tr>
        <th>First Name</th>
        <th>Last Name</th>
    </tr>
            <#list items as item>
            <tr>
                <td>${item.name}</td>
                <td>${item.price}</td>
            </tr>
            </#list>
</table>
<!--JavaScript at end of body for optimized loading-->
<script type="text/javascript" src="js/materialize.min.js"></script>
</body>
</html>