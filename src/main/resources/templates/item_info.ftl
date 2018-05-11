<#import "/spring.ftl" as spring/>
<#escape x as x?html>
<!DOCTYPE html>
<html>
<head>
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="../js/jquery.cookie.js"></script>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="../css/my_style.css"/>
    <link type="text/css" rel="stylesheet" href="../css/materialize.min.css" media="screen,projection"/>

    <link rel="icon" href="https://cdn.crowdfundinsider.com/wp-content/uploads/2015/02/strongvolt-logo-300x248.jpg">
    <title>${item.producer} ${item.name}</title>
</head>

<body id="body">
<#include "header.ftl">
<div class="row" style="margin-top: 100px">
    <div id="categories">
        <div class="col s2">
            <div class="collection z-depth-1 hoverable">
    <#list categories as category>
        <div><a href="javascript: getByCategory('${category.name}')" class="collection-item">${category.name}</a></div>
   <#if selectedCategory??>
    <input id="chosenCategory" type="text" hidden value="${selectedCategory}">
   </#if>
    </#list>
            </div>
        </div>
    </div>
    <div id="verifyCategory"></div>

    <div id="${item.id}">
    <div  class="col s8">
        <div class="row">
            <div class="col s8 offset-s1">
                <div id="items_div">
                    <div  class="card horizontal hoverable">
                        <div class="card-image col s5">
                        <#include "item_photo.ftl">
                        </div>
                        <div class="card-stacked">
                            <div class="card-content">
                                <p><span class="card-title">${item.producer} ${item.name}</span></p>
                                <p> ${item.price} rub</p>
                                <p>${item.storage} ps</p>
                                <br>
                                <div class="input-field col s1"><a href="javascript: addItemToBasket(${item.id})"
                                                                   class="waves-effect waves-light btn">buy</a></div>

                                 <#if currentUser??>
                                     <#if currentUser.roleId==1 && currentUser.id==item.sellerId>
                                <div class="input-field col s1 offset-s7">
                                    <a href="/items/edit/${item.id}"> <i class="material-icons cl-4db6a sz-30">edit</i></a>
                                </div>
                                <div class="input-field col s1">
                                    <a href="javascript: deleteItem(${item.id})"> <i
                                            class="material-icons cl-4db6a sz-30 modal-trigger">delete</i>
                                    </a>
                                </div>
                                     </#if>

                                     </#if>
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

                                    <td style="text-align: right">${characteristic.value} ${characteristic.measureUnit}</td>
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
    </div>
</div>

<div id="info-modal" class="modal modal-content"></div>

<script type="text/javascript" src="../js/homeHandler.js"></script>
<script type="text/javascript" src="../js/searchHandler.js"></script>
<script type="text/javascript" src="../js/addToBasket.js"></script>
<script type="text/javascript" src="../js/itemInfoHandler.js"></script>
<script type="text/javascript" src="../js/materialize.min.js"></script>
<script type="text/javascript" src="../js/cookieHandler.js"></script>

<#if cookies??>
    <#list cookies as cookie>
<script>setCookie("${cookie.name}");</script>
    </#list>
</#if>

</body>
</html>
</#escape>