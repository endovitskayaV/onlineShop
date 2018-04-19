<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html>
<head>
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="/../css/materialize.min.css" media="screen,projection"/>
    <link type="text/css" rel="stylesheet" href="/../css/my_style.css"/>
</head>

<body>
<div style="margin-top: 80px" class="row">
    <div class="col s6 offset-s3">
        <div class="card hoverable">
            <div class="card-content">
                    <div class="row">
                        <div class="input-field col s10 offset-s1">
                            <input id="name"  name="name" type="text" value="${item.name}">
                            <label for="name">Name</label>
                            <div name="name-errors"></div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s10 offset-s1">
                            <input id="producer"  name="producer" type="text"
                                   value="${item.producer}">
                            <label for="producer">Producer</label>
                            <div name="producer-errors"></div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s10 offset-s1">
                             <#if item.storage??>
                              <input id="storage" name="storage" type="number" value="${item.storage}" >
                             <#else>
                              <input id="storage" name="storage" type="number" value="">
                             </#if>
                            <label for="storage" >Count</label>
                            <div name="storage-errors">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s10 offset-s1">
                               <#if item.price??>
                              <input id="price" name="price" type="number" value="${item.price?string["0"]}" >
                               <#else>
                              <input id="price" name="price" type="number" value="">
                               </#if>
                            <label for="price" >Price</label>
                            <div name="price-errors">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s10 offset-s1">
                            <input id="description" name="description" type="text"
                                   value="${item.description}">
                            <label for="description">Description</label>
                            <div name="description-errors"></div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s10 offset-s1">
                            <select required id="categoryId" name="categoryId" onchange="loadCharacteristics('categoryId')">
                                <option selected id="${selectedCategory.id}" value="${selectedCategory.id}">${selectedCategory.name}</option>
              <#list categories as category>
                   <option id="${category.id}" value="${category.id}">${category.name}</option>
              </#list>
                            </select>
                            <label for="categoryId">Category</label>
                            <div name="characteristics-errors"></div>
                        </div>
                        <div style="margin-top: 30px" class="input-field col s1">
                            <a class="modal-trigger" href="#category_modal">
                                <i class="material-icons cl-4db6a sz-33 fw-b">add</i>
                            </a>
                        </div>
                    </div>

                    <div id="characteristicDiv">
                        <#assign i=0>
                         <#list item_characteristics as characteristic>
                         <div id="characteristic_">
                             <input id= "characteristic-id" type="number" name="${characteristic.id}" class="validate" hidden="hidden" value="${characteristic.id}">
                             <input id= "characteristic-measureUnit" type="text" name="${characteristic.measureUnit}" class="validate" hidden="hidden" value="${characteristic.measureUnit}">
                             <input id= "characteristic-name" type="text" name="${characteristic.name}" class="validate" hidden="hidden" value="${characteristic.name}">
                             <input id= "characteristic-valueDataType" type="text" name="${characteristic.valueDataType}" class="validate" hidden="hidden" value="${characteristic.valueDataType}">
                             <input id= "characteristic-required" type="text" name="${characteristic.required?c}" class="validate" hidden="hidden" value="${characteristic.required?c}">
                                 <div id="1" class="row">
                                             <div id="2" class="input-field col s3">
                                                   <#if characteristic.value??>
                              <input id= "characteristic-value" name="value" value="${characteristic.value}" type="text" class="validate">
                                                   <#else>
                             <input id= "characteristic-value" name="value" value="" type="text" class="validate">
                                                   </#if>
                                                     <label for="value"> ${characteristic.name} </label>
                                  <span class="helper-text">${characteristic.measureUnit}</span>
                                                 <div name="characteristics[${i}]-errors"></div>
                                                 </div>
                                         </div>
                             <#assign i=i+1>
                         </div>
                         </#list>
                    </div>

                    <div class="row">
                        <div class="input-field col s2 offset-s1">
                            <button class="waves-effect waves-light btn" onclick="save(${item.id})">Save</button>
                        </div>
                        <div class="input-field col s2 offset-s5">
                            <button class="waves-effect waves-light btn" type="reset">Reset</button>
                        </div>
                    </div>

            </div>
        </div>
    </div>
</div>


<div id="category_modal" class="modal">
    <div class="modal-content">
        <#include "add_category.ftl">
    </div>
</div>


<div id="error-modal" class="modal modal-content">
</div>


<script type="text/javascript" src="/../js/materialize.min.js"></script>
<script type="text/javascript" src="/../js/addCategoryHandler.js"></script>
<script type="text/javascript" src="/../js/editItemHandler.js"></script>
<script type="text/javascript" src="/../js/addItemsHandler.js"></script>
<script type="text/javascript" src="/../js/initSelect.js"></script>
<script type="text/javascript" src="/../js/initModal.js"></script>

</body>
</html>