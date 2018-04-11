<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html>
<head>
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="../css/my_style.css"/>
    <link type="text/css" rel="stylesheet" href="../css/materialize.min.css" media="screen,projection"/>
</head>

<body>
<#include "header.ftl">

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
                            <p><a style="margin-top: 30px; margin-bottom: 30px" href="javascript: addItemToBasket(${item.id})"
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

<div id="info-modal" class="modal modal-content">
</div>

<script type="text/javascript" src="../js/addToBasket.js"></script>
<script type="text/javascript" src="../js/itemInfoHandler.js"></script>
<script type="text/javascript" src="../js/materialize.min.js"></script>
</body>
</html>