<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html>
<head>
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="css/materialize.min.css" media="screen,projection"/>
    <link type="text/css" rel="stylesheet" href="css/my_style.css"/>
</head>

<body id="body">
<#include "header.ftl">
<#include "sorting_select.ftl">
<div class="row">
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

<#if  items?size==0>
  <div class="col s2 offset-s1 card horizontal">
      <div class="card-stacked">
          <div class="card-content">
              <p>No items</p>
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
            <div class="card-image">
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


    <div class="col s3 offset-s1">
        <#if characteristics??>
            <#if chosenCharacteristics??>
            <div id="chosenCharacteristics">
            <#else>
            <div>
            </#if>
                <ul class="collapsible expandable">
            <#list characteristics as characteristic>
                <li id="characteristics_values">
                    <div id="characteristic_name" class="collapsible-header">${characteristic.name}</div>
                    <div id="values" class="collapsible-body">
                    <#list characteristic.values as valueChecked>
                          <#if chosenCharacteristics??>
                         <#list chosenCharacteristics?values as chosenCharacteristicValue>
                             <#if chosenCharacteristicValue==valueChecked>
                              <p>
                                  <label>
                                      <input type="checkbox" checked="checked" id="characteristic_value"/>
                                      <span>${valueChecked}</span>
                                  </label>
                              </p>
                                 <#break>
                             <#else>

                        <p>
                            <label>
                                <input type="checkbox"  id="characteristic_value"/>
                                <span>${valueChecked}</span>
                            </label>
                        </p>
                             </#if>
                         </#list>
                    <#else>
                             <p>
                                 <label>
                                     <input type="checkbox"  id="characteristic_value"/>
                                     <span>${valueChecked}</span>
                                 </label>
                             </p>
                        </#if>
                    </#list>
                    </div>
                </li>
            </#list>

                </ul>
            </div>
        </#if>
    </div>
    </div>
        </div>

    <div id="info-modal" class="modal modal-content">
    </div>

    <script type="text/javascript" src="js/homeHandler.js"></script>
    <script type="text/javascript" src="js/searchHandler.js"></script>
    <script type="text/javascript" src="js/addToBasket.js"></script>
    <script type="text/javascript" src="js/materialize.min.js"></script>
    <script id="initSelect" type="text/javascript" src="js/initSelect.js"></script>
    <script id="initSelect" type="text/javascript" src="js/initCollapsible.js"></script>
</body>
</html>