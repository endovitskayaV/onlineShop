<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html>
<head>
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="css/materialize.min.css" media="screen,projection"/>
    <link type="text/css" rel="stylesheet" href="css/my_style.css"/>
</head>

<body>
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
<#else>

    <#assign count=items?size>
    <p hidden="hidden"  id="count">${count}</p>

    <#assign overall=0>
    <#assign i=0>

    <div class="col s8">
        <div class="row">
            <div class="col s8 offset-s4">
    <#list items as item>
        <div id="${item.id}" class="card horizontal hoverable">

            <div class="card-image">
                <img width="60" height="200" src="../img/meizu.jpg">
            </div>
            <div id="anyCardLeft">
            <div class="card-stacked">
                <div class="card-content">
                    <p id="content" class="flow-text"><a href="/items/${item.id}">${item.producer} ${item.name}</a></p>
                    <div id="price-${item.id}"> ${item.price?string["0"]} </div>
                    rub
                    <div class="input-field col s3 offset-s1">
                        <input id="quantity-${item.id}" name="quantity" type="number" min="1"
                               value="${quantities[i]?string["0"]}"
                               onchange="increaseItemQuantity(${basketId},${item.id})">
                        <label for="quantity">Quantity</label>
                    </div>

                    <#assign sum=item.price*quantities[i]>
                    <#assign overall=overall+sum>
                    <p name="sum-${i}" id="sum-${item.id}"> ${sum?string["0"]} </p>rub
                     <#assign i=i+1>
                </div>
                <div class="card-action">
                    <div class="row">
                        <div class="input-field col s1">
                            <a href="javascript: deleteItem(${basketId},${item.id})"> <i
                                    class="material-icons cl-4db6a sz-30 modal-trigger">delete</i>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
            </div>
        </div>
    </#list>

                <div class="card">
                    <div class="card-content">
                        <div id="overall"> ${overall} </div>rub
                        <p>
                            <button>Order</button>
                        </p>
                    </div>
                </div>


            </div>
        </div>
    </div>

</#if>

</div>


<div id="info-modal" class="modal modal-content">
</div>
<script type="text/javascript" src="js/basketHandler.js"></script>
<script type="text/javascript" src="js/materialize.min.js"></script>
</body>
</html>