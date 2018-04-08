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
<div class="row" style="margin-top: 100px">
    <div class="col s3">
        <div class="collection z-depth-1 hoverable">
    <#list categories as category>
        <div><a href="/items?category=${category.name}" class="collection-item">${category.name}</a></div>
    </#list>
        </div>
    </div>


<#if  items?size==0>
  <div class="col s2 offset-s1 card horizontal">
      <div class="card-stacked">
          <div class="card-content">
              <p>No items</p>
          </div>
      </div>
  </div>
</#if>

    <div class="col s8">
        <div class="row">
            <div class="col s8 offset-s1">
    <#list items as item>
        <div id="${item.id}" class="card horizontal hoverable">
            <div class="card-image">
                <img width="60" height="200" src="../img/meizu.jpg">
            </div>
            <div class="card-stacked">
                <div class="card-content">
                    <p class="flow-text"><a href="/items/${item.id}">${item.producer} ${item.name}</a></p>
                    <p> ${item.price} rub</p>
                    <p>${item.storage} ps</p>
                </div>
                <div class="card-action">
                    <div class="row">
                        <div class="input-field col s1">
                            <a href="" class="waves-effect waves-light btn">buy</a>
                        </div>
                        <div class="input-field col s1 offset-s7">
                            <a href="/items/edit/${item.id}"> <i class="material-icons cl-4db6a sz-30">edit</i></a>
                        </div>
                        <div class="input-field col s1">
                            <a href="javascript: deleteItem(${item.id})"> <i
                                    class="material-icons cl-4db6a sz-30 modal-trigger">delete</i>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </#list>
            </div>
        </div>
    </div>
</div>

<div id="delete-modal" class="modal modal-content">
</div>

<script type="text/javascript" src="js/homeHandler.js"></script>
<script type="text/javascript" src="js/materialize.min.js"></script>
</body>
</html>