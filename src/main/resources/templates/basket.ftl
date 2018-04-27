<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html>
<head>
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="js/jquery.cookie.js"></script>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="css/materialize.min.css" media="screen,projection"/>
    <link type="text/css" rel="stylesheet" href="css/my_style.css"/>

    <link rel="icon" href="https://cdn.crowdfundinsider.com/wp-content/uploads/2015/02/strongvolt-logo-300x248.jpg">
    <title>Cart</title>
</head>

<body>
<#assign itemsCount=0>
<#include "header.ftl">
<div id="top" class="row" style="margin-top: 100px">
<#if  items?size==0>

    <div class="col s2 offset-s4 card horizontal">
        <div class="card-stacked">
            <div class="card-content">
                <p>No items</p>
            </div>
        </div>
    </div>


<#elseif itemsSize??>
 <div class="col s2 offset-s4 card horizontal">
     <div class="card-stacked">
         <div class="card-content">
             <p>No items</p>
         </div>
     </div>
 </div>
<#else>
<label for="id"></label>
    <input id="id" hidden="hidden" type="number" value="${basketId}">

    <#assign count=items?size>
    <p hidden="hidden" id="count">${count}</p>

    <#assign overall=0>
    <#assign i=0>

    <div class="col s8">
        <div class="row">
            <div class="col s8 offset-s4">
    <#list items as item>
        <#if item??>

            <#assign itemsCount=itemsCount+1>
        <div id="${item.id}" class="card horizontal hoverable">

            <div class="card-image">
                <#include "item_photo.ftl">
            </div>
            <div id="anyCardLeft">
                <div class="card-content">
                    <label for="itemId"></label>
                    <input id="itemId" hidden="hidden" type="number" value="${item.id}">

                    <p id="content" class="flow-text"><a href="/items/${item.id}">${item.producer} ${item.name}</a></p>

                    <div class="row">
                        <div class="input-field col s5">Price, rub:</div>
                        <div class="input-field col s1">
                            <div id="price-${item.id}"> ${item.price?string["0"]} </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col s4">
                            <div class="input-field col s5">
                                <a href="javascript: setItemQuantity(${basketId},${item.id},true)">
                                    <i class="material-icons cl-4db6a sz-20">add</i></a>
                                <span id="quantity-${item.id}">
                                    ${quantities[i]?replace(",", "")}
                                </span>ps
                                <a href="javascript: setItemQuantity(${basketId},${item.id},false)">
                                    <i class="material-icons cl-4db6a sz-20">remove</i></a>
                            </div>
                            <div id="error-quantity-${item.id}" class="input-field col s1">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s5">Sum, rub:</div>
                        <div class="input-field col s1">
                                     <#assign sum=item.price*quantities[i]>
                                      <#assign overall=overall+sum>
                            <div name="sum-${i}" id="sum-${item.id}"> ${sum?string["0"]} </div>
                                    <#assign i=i+1>
                        </div>
                    </div>
                </div>
                <div class="card-action">
                    <div class="row">
                        <div class="input-field col s1 offset-s12">
                            <a href="javascript: deleteItem(${basketId},${item.id})"> <i
                                    class="material-icons cl-4db6a sz-30 modal-trigger">delete</i>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        </#if>
    </#list>
                <#if (itemsCount>0)>
                <div class="card hoverable">
                    <div class="card-content">
                        <div class="row">
                            <div class="input-field col s3">
                                <div class="left">Overall sum, rub:
                                </div>
                            </div>
                            <div class="input-field col s1">
                                <b id="overall">${overall?string["0"]}</b>
                            </div>
                        </div>
                        <div class="row">
                            <div class="input-field col s1 offset-s10">
                                <a href="javascript: checkItemQuantity(${basketId})"
                                   class="waves-effect waves-light btn">Order</a>
                            </div>
                        </div>
                    </div>
                </div>
                </#if>
            </div>
        </div>
    </div>
</#if>
</div>


<div id="info-modal" class="modal modal-content"></div>

<script type="text/javascript" src="js/basketHandler.js"></script>
<script type="text/javascript" src="js/materialize.min.js"></script>
</body>
</html>