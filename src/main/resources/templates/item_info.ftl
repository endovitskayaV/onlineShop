<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html>
<head>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="/css/my_style.css"/>
    <link type="text/css" rel="stylesheet" href="/css/materialize.min.css" media="screen,projection"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>


</head>

<body style="background-color: #f5f5f5">

<div class="white">
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
                    <a href=""> <i style="font-size: 36px; color: #4db6ac;" class="material-icons">shopping_cart</i>
                    </a>
                </div>
            </div>
        </form>
    </div>
    <div class="divider z-depth-3"></div>
</div>


<div class="row" style="margin-top: 100px">
    <div class="col s3">
        <div class="collection">
    <#list categories as category>
        <div><a href="/items?category=${category.name}" class="collection-item">${category.name}</a></div>
    </#list>
        </div>
    </div>


    <div class="col s8">
        <div class="row">
            <div class="col s8 offset-s1">
                <div class="card horizontal hoverable">
                    <div class="card-image">
                        <img width="60" height="200" src="../img/meizu.jpg">
                    </div>
                    <div class="card-stacked">
                        <div class="card-content">
                            <p><span class="card-title">${item.producer} ${item.name}</span></p>
                            <p> ${item.price} rub</p>
                            <p>${item.storage} ps</p>
                            <p><a style="margin-top: 30px; margin-bottom: 30px" href="/basket"
                                  class="waves-effect waves-light btn">buy</a></p>
                        </div>
                        <div class="card-action">
                            <a href="#!" onclick="dropdown_div('description')">Description</a>
                            <div id="description" style="margin: 0 0 10px 10px; display:none">
                        <#if item.description=="">
                            -
                        <#else>
                            ${item.description}
                        </#if>
                            </div>

                            <a href="#!" onclick="dropdown_div('characteristic_list')">Characteristics</a>
                            <div id="characteristic_list" style="margin: 0 0 10px 10px; display:none">
                                <table class="highlight" style="padding-left: 20px">
                                    <#if characteristics?size==0>
                                    -
                                    <#else>
                            <#list characteristics as characteristic>
                                <tr>
                                    <td>${characteristic.name}</td>
                                    <td style="text-align: right">${characteristic.value} ${characteristic.type}</td>
                                </tr>
                            </#list>
                                    </#if>
                                </table>
                            </div>


                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<script type="text/javascript" src="../js/itemInfoHandler.js"></script>
<script type="text/javascript" src="../js/materialize.min.js"></script>
</body>
</html>