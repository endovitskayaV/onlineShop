<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html>
<head>
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="../css/materialize.min.css" media="screen,projection"/>
    <link type="text/css" rel="stylesheet" href="../css/my_style.css"/>
</head>

<body>
 <@spring.bind "item"/>
<div style="margin-top: 80px" class="row">
    <div class="col s6 offset-s3">
        <div class="card hoverable">
            <div class="card-content">
                <form class="container" method="post" action="/items/add">
                    <div class="row">
                        <div class="input-field col s10 offset-s1">
                              <@spring.bind "item.name"/>
                            <label for="name">Name</label>
                            <@spring.formInput "item.name"/>


                            <@spring.showErrors ""/>

                              <#if errors??>
                                  <#list errors as error>
                                      ${error}
                                  </#list>
                              </#if>
                             <#list spring.status.errors.allErrors as error>
                                 ${error.defaultMessage}
                             </#list>


                    </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s10 offset-s1">
                               <@spring.formInput "item.producer"/>
                            <label for="producer">Producer</label>
                          
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s10 offset-s1">
                              <@spring.formInput "item.storage"/>
                            <label for="storage">Count</label>
                          
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s10 offset-s1">
                            <@spring.formInput "item.price"/>
                            <label for="price">Price</label>
                          
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s10 offset-s1">
                             <@spring.formInput "item.description"/>
                            <label for="description">Description</label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s10 offset-s1">
                          
                            <select   id="categoryId" name="categoryId" onchange="loadCharacteristics(this)">
                                <option selected disabled value=''>Choose your option</option>
              <#list categories as category>
            <option id="${category.id}"  value="${category.id}">${category.name}</option>
              </#list>
                            </select>
                            <label for="categoryId">Category</label>
                        </div>
                        <div style="margin-top: 30px" class="input-field col s1">
                            <a class="modal-trigger" href="#category_modal">
                                <i class="material-icons cl-4db6a sz-33 fw-b">add</i>
                            </a>
                        </div>
                    </div>

                    <div id="characteristicDiv"></div>

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
    <div class="modal-content">
        <#include "add_category.ftl">
    </div>
</div>


<div id="error-modal" class="modal modal-content">
</div>

<script type="text/javascript" src="../js/materialize.min.js"></script>
<script type="text/javascript" src="../js/addCategoryHandler.js"></script>
<script type="text/javascript" src="../js/addItemsHandler.js"></script>
<script type="text/javascript" src="../js/initSelect.js"></script>
<script type="text/javascript" src="../js/initModal.js"></script>

</body>
</html>