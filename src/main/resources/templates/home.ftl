<#import "/spring.ftl" as spring/>
<#escape x as x?html>
<!DOCTYPE html>
<html>
<head>
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="js/jquery.cookie.js"></script>
    <script type="text/javascript" src="https://unpkg.com/imagesloaded@4.1.4/imagesloaded.pkgd.min.js"></script>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="css/materialize.min.css" media="screen,projection"/>
    <link type="text/css" rel="stylesheet" href="css/my_style.css"/>

    <link type="text/css" rel="stylesheet" href="../css/materialize.min.css" media="screen,projection"/>
    <link type="text/css" rel="stylesheet" href="../css/my_style.css"/>
    <script type="text/javascript" src="../js/jquery.cookie.js"></script>
    <link rel="icon" href="https://cdn.crowdfundinsider.com/wp-content/uploads/2015/02/strongvolt-logo-300x248.jpg">

    <title>VOLT-Home</title>
</head>

<body id="body">
    <#include "header.ftl">

    <#if  search??  && items?size==0>
   <div class="row" style="margin-top: 30px">
        <div class="col s2 offset-s4 card horizontal">
            <div class="card-stacked">
                <div class="card-content">
                    <p>No items</p>
                </div>
            </div>
        </div>
    </div>
    <#else>


    <#include "sorting_select.ftl">
<div class="row">
    <#if categories??>
        <div id="categories">
            <div class="col s2">
                <div class="collection z-depth-1 hoverable">

<#if specifyCategory??>
    <div style="background-color: white"><br>
        <span style="margin-left: 10px">Specify category:</span></div>
    <br>

    <#list specifyCategory as category>
        <div>
            <a href="javascript: getByCategoryAndQuery('${category.id}', '${query}')"
               class="collection-item">${category.name}</a>
        </div>
        <#if selectedCategory??>
    <input id="chosenCategory" type="text" hidden value="${selectedCategory}">
        </#if>
    </#list>

<#else>
    <#list categories as category>
        <div>
            <a href="javascript: getByCategory('${category.name}')" class="collection-item">${category.name}</a>
        </div>
        <#if selectedCategory??>
    <input id="chosenCategory" type="text" hidden value="${selectedCategory}">
        </#if>
    </#list>
</#if>
                </div>
            </div>
        </div>
    </#if>


     <#if  items?size==0>
    <div class="row">
        <div class="col s2 offset-s2 card horizontal">
            <div class="card-stacked">
                <div class="card-content">
                    <p>No items</p>
                </div>
            </div>
        </div>
    </div>
     </#if>

    <div id="verifyCategory"></div>


    <div class="row">
        <div class="col s5 offset-s1">
            <div id="items_div">
    <#list items as item>
        <div id="${item.id}" class="card horizontal hoverable">
            <div class="card-image col s8">
               <#include "item_photo.ftl">
            </div>
            <div class="card-stacked">
                <div class="card-content">
                    <p class="flow-text"><a href="/items/${item.id}">${item.producer} ${item.name}</a></p>
                    <p> ${item.price} rub</p>

                    <p><span id="storage-${item.id}">${item.storage} </span> ps</p>
                </div>
                <div class="card-action">
                    <div class="row">
                        <div class="input-field col s1">
                            <a href="javascript: addItemToBasket(${item.id})"
                               class="waves-effect waves-light btn">buy</a>
                        </div>

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
                </div>
            </div>
        </div>
    </#list>
            </div>
        </div>

<#include "characteristic_filter.ftl">
    </div>

</div>
    </#if>
<div id="info-modal" class="modal modal-content"></div>

<script type="text/javascript" src="js/initHome.js"></script>
<script type="text/javascript" src="js/searchHandler.js"></script>
<script type="text/javascript" src="js/homeHandler.js"></script>
<script type="text/javascript" src="js/addToBasket.js"></script>
<script type="text/javascript" src="js/materialize.min.js"></script>
<script id="initSelect" type="text/javascript" src="js/initSelect.js"></script>
<script id="initSelect" type="text/javascript" src="js/initCollapsible.js"></script>
<script type="text/javascript" src="js/cookieHandler.js"></script>

<script type="text/javascript" src="../js/initHome.js"></script>
<script type="text/javascript" src="../js/searchHandler.js"></script>
<script type="text/javascript" src="../js/homeHandler.js"></script>
<script type="text/javascript" src="../js/addToBasket.js"></script>
<script type="text/javascript" src="../js/materialize.min.js"></script>
<script id="initSelect" type="text/javascript" src="../js/initSelect.js"></script>
<script id="initSelect" type="text/javascript" src="../js/initCollapsible.js"></script>
<script type="text/javascript" src="../js/cookieHandler.js"></script>



</#escape>
<#if search??>
<script>
    $('#search').val("${query}");
    query = getSaveQuery();
</script>
</#if>

<#if categoryId??>
 <input id="specifiedCategory" hidden value="${categoryId}">
</#if>

<#if cookies??>
    <#list cookies as cookie>
<script>setCookie("${cookie.name}");</script>
    </#list>
</#if>

</body>
</html>
