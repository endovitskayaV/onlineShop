<#import "/spring.ftl" as spring/>
<#escape x as x?html>
<!DOCTYPE html>
<html>
<head>
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="/js/jquery.cookie.js"></script>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="/css/materialize.min.css" media="screen,projection"/>
    <link type="text/css" rel="stylesheet" href="/css/my_style.css"/>

    <link rel="icon" href="https://cdn.crowdfundinsider.com/wp-content/uploads/2015/02/strongvolt-logo-300x248.jpg">
    <title>Finish order</title>
</head>

<body>
<script type="text/javascript" src="/js/cookieHandler.js"></script>
<#include "header.ftl">

<div class="row" style="margin-top: 100px">
    <div id="verifyCategory"></div>

    <#assign overall=0>
    <#assign i=0>
    <label for="id"></label>
    <input id="id" hidden="hidden" type="number" value="${order.id}">
    <div class="col s8">
        <div class="row">
            <div class="col s8 offset-s4">
                <div id="items_div">
    <#list items as item>
        <div id="${item.id}" class="card horizontal hoverable">

            <div class="card-image">
                 <#if item.photoName??>
                     <img width="60" height="200" src="../../img/${item.photoName}">
                 <#else>
                    <img width="60" height="200" src="../../img/no_photo.png">
                 </#if>
            </div>
            <div id="anyCardLeft">
                <div class="card-stacked">
                    <div class="card-content">
                        <label for="itemId"></label>
                        <input id="itemId" hidden="hidden" type="number" value="${item.id}">
                        <p id="content" class="flow-text"><a href="/items/${item.id}">${item.producer} ${item.name}</a>
                        </p>
                        <div id="price-${item.id}"> ${item.price?string["0"]} rub</div>
                        <p type="number">
                            Ordered: <b id="quantity-${item.id}"> ${quantities[i]?string["0"]} ps</b> &nbsp;&mdash;

                    <#assign sum=item.price*quantities[i]>
                    <#assign overall=overall+sum>
                            <b>${sum?string["0"]} rub </b></p>
                     <#assign i=i+1>
                    </div>
                </div>
            </div>
        </div>
    </#list>
                <div class="card hoverable">
                    <div class="card-content">
                        <div class="row">
                            <div> Overall sum: <b>${overall} rub</b></div>

                            <div class="col s6">

                            <input id="deliveryAddress" name="deliveryAddress" type="text"
                                   value=${order.deliveryAddress?html}>
                                <label for="deliveryAddress">Delivery address</label>
                            </div>
                            <div name="deliveryAddress-errors">
                            </div>

                            <div class="col s1 offset-s10">
                                <a href="javascript: confirmOrder()" class="waves-effect waves-light btn">Order</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            </div>
            <#include "characteristic_filter.ftl">
        </div>
    </div>
</div>

<div id="info-modal" class="modal modal-content">
</div>

<#if cookies??>
    <#list cookies as cookie>
<script>setCookie("${cookie.name}");</script>
    </#list>
</#if>

<script type="text/javascript" src="/js/materialize.min.js"></script>
<script type="text/javascript" src="/js/orderHandler.js"></script>
<script type="text/javascript" src="/js/searchHandler.js"></script>
</body>
</html>
</#escape>