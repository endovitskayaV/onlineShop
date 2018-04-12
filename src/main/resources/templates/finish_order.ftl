<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html>
<head>
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="/css/materialize.min.css" media="screen,projection"/>
    <link type="text/css" rel="stylesheet" href="/css/my_style.css"/>
</head>

<body>
<#include "header.ftl">

<div  class="row" style="margin-top: 100px">
    <#assign overall=0>
    <#assign i=0>

    <div class="col s8">
        <div class="row">
            <div class="col s8 offset-s4">
    <#list items as item>
        <div id="${item.id}" class="card horizontal hoverable">

            <div class="card-image">
                <img width="60" height="200" src="../../img/meizu.jpg">
            </div>
            <div id="anyCardLeft">
                <div class="card-stacked">
                    <div class="card-content">
                        <label for="itemId"></label>
                        <input id="itemId" hidden="hidden" type="number" value="${item.id}">
                        <p id="content" class="flow-text"><a href="/items/${item.id}">${item.producer} ${item.name}</a></p>
                        <div id="price-${item.id}"> ${item.price?string["0"]} </div>
                        rub
                        <div class="input-field col s3 offset-s1">
                            <p id="quantity-${item.id}" type="number"
                               value="${quantities[i]?string["0"]}"></p> ps
                        </div>

                    <#assign sum=item.price*quantities[i]>
                    <#assign overall=overall+sum>
                        <p> ${sum?string["0"]} </p>rub
                     <#assign i=i+1>
                    </div>
                </div>
            </div>
        </div>
    </#list>

                <div class="card">
                    <div class="card-content">
                        <div id="overall"> ${overall} </div>rub
                        <div>
                            <input id="deliveryAddress" name="deliveryAddress" type="text"
                                   value="${order.deliveryAddress}">
                            <label for="deliveryAddress">Delivery address</label>
                        </div>
                        <p>
                            <a href="javascript: confirmOrder()" class="waves-effect waves-light btn">Order</a>
                        </p>
                    </div>
                </div>


            </div>
        </div>
    </div>


</div>


<div id="info-modal" class="modal modal-content">
</div>

<script type="text/javascript" src="/js/orderHandler.js"></script>
<script type="text/javascript" src="/js/materialize.min.js"></script>
</body>
</html>