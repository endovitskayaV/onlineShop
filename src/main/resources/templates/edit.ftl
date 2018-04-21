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
<script type="text/javascript" src="/../js/addItemsHandler.js"></script>
<div style="margin-top: 80px" class="row">
    <div class="col s6 offset-s3">
        <div class="card hoverable">
            <div class="card-content">
                <form enctype="multipart/form-data" class="container" method="post" action="/items/edit">
                    <input id="id" name="id" type="number" value="${item.id}" hidden>
                    <div class="row">
                        <div class="input-field col s10 offset-s1">
                            <input id="name" name="name" type="text" value="${item.name}">
                            <label for="name">Name</label>
                            <div name="name-errors"></div>
                        </div>

                    </div>
                    <div class="row">
                        <div class="input-field col s10 offset-s1">
                            <input id="producer" name="producer" type="text" value="${item.producer}">
                            <label for="producer">Producer</label>
                            <div name="producer-errors"></div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s10 offset-s1">
                             <#if item.storage??>
                              <input id="storage" name="storage" type="number" value="${item.storage?replace(",", "")}"
                                     min="0">
                             <#else>
                              <input id="storage" name="storage" type="number" value="" min="0">
                             </#if>
                            <label for="storage">Count</label>
                            <div name="storage-errors">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s10 offset-s1">
                               <#if item.price??>
                              <input id="price" name="price" type="number" min="1" value="${item.price?replace(",", "")}">
                               <#else>
                              <input id="price" name="price" type="number" value="" min="1">
                               </#if>
                            <label for="price">Price</label>
                            <div name="price-errors">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s10 offset-s1">
                             <@spring.formInput "item.description"/>
                            <label for="description">Description</label>
                            <div name="description-errors"></div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s10 offset-s1">

                            <select required id="categoryId" name="categoryId"
                                    onchange="loadCharacteristics('categoryId')">
                                <option selected id="${selectedCategory.id}"
                                        value="${selectedCategory.id}">${selectedCategory.name}</option>
              <#list categories as category>
                   <option id="${category.id}" value="${category.id}">${category.name}</option>
              </#list>
                            </select>


                            <label for="categoryId">Category</label>
                            <div name="characteristics-errors">
                            </div>
                        </div>
                        <div style="margin-top: 30px" class="input-field col s1">
                            <a class="modal-trigger" href="#category_modal">
                                <i class="material-icons cl-4db6a sz-33 fw-b">add</i>
                            </a>
                        </div>
                    </div>


                    <div id="characteristicDiv">
                          <#if selectedCategory??>
                              <#assign i=0>
                               <#list item_characteristics as characteristic>
                              <input type="number" name="characteristics[${i}].id"
                                     hidden="hidden" value="${characteristic.id}">
                              <input type="text" name="characteristics[${i}].measureUnit"
                                     hidden="hidden" value="${characteristic.measureUnit}">

                              <input name="characteristics[${i}].required"
                                     hidden="hidden" value="${characteristic.required?c}">

                              <input type="text" name="characteristics[${i}].name"
                                     hidden="hidden" value="${characteristic.name}">

                                <input type="text" name="characteristics[${i}].valueDataType"
                                       hidden="hidden" value="${characteristic.valueDataType}">

                                  <div class="row">
                                      <div class="input-field col s10 offset-s1">
                                          <input id="value" name="characteristics[${i}].value" type="text"
                                                 value="${characteristic.value}">
                                          <label for="value">${characteristic.name}</label>
                                          <div name="characteristics[${i}]-errors"></div>
                                          <span class="helper-text">${characteristic.measureUnit}</span>
                                      </div>
                                  </div>
                                   <#assign i=i+1>
                               </#list>
                          </#if>
                    </div>

                <#if item.photoName??>
                    <div id="image_div" class="row">
                        <div class="input-field col s11 offset-s1">
                            <img width="60" height="200" src="../../../img/${item.photoName}">
                            <a href="javascript: deleteImage()">
                                <i class="material-icons cl-4db6a sz-20 modal-trigger">clear</i>
                            </a>
                            <input id="fileName" name="photoName" type="text" value="${item.photoName}" hidden>
                        </div>
                    </div>
                </#if>

                    <div class="row">
                        <div class="input-field col s11 offset-s1">
                            <label for="file">Change photo</label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s11 offset-s1"><input type="file" id="file" name="file"/></div>
                    </div>
                    <div class="row">
                        <div class="input-field col s2 offset-s1">
                            <button class="waves-effect waves-light btn" type="submit">Save</button>
                        </div>
                        <div class="input-field col s2 offset-s5">
                            <button class="waves-effect waves-light btn" type="reset">Reset</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>


<div id="category_modal" class="modal">
    <div id="add_category" class="modal-content">
        <#include "add_category.ftl">
    </div>
</div>


<div id="error-modal" class="modal modal-content">
</div>

<#if errors??>
    <#list errors as error>
<script>showError("${error.field}", "${error.message}");</script>
    </#list>
</#if>

<script type="text/javascript" src="/../js/materialize.min.js"></script>
<script type="text/javascript" src="/../js/addCategoryHandler.js"></script>
<script type="text/javascript" src="/../js/editItemHandler.js"></script>
<script type="text/javascript" src="/../js/initSelect.js"></script>

</body>
</html>