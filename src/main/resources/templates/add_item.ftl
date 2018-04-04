<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html>
<head>
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="../css/materialize.min.css" media="screen,projection"/>
    <link rel="stylesheet" type="text/css" href="<@spring.url '/css/style.css'/>"/>
</head>

<body>


<form action="/items/add" method="post">

    <input type="number" name="id" class="validate" hidden="hidden"> <label for="id"
                                                                                      hidden="hidden">id</label>
    <div class="row">
        <div class="col s12">
            <div class="row">
                <div class="input-field col s3">
                    <input id="name" required="required" name="name" type="text" class="validate" value="${item.name}">
                    <label for="name">Name</label>
                </div>
            </div>
            <div class="row">
                <div class="input-field col s3">
                    <input id="producer" required="required" name="producer" type="text" class="validate" value="${item.producer}">
                    <label for="producer">Producer</label>
                </div>
            </div>
            <div class="row">
                <div class="input-field col s3">
                    <input id="storage" name="storage" type="number" class="validate" value="${item.storage}">
                    <label for="storage">Count</label>
                </div>
            </div>
            <div class="row">
                <div class="input-field col s3">
                    <input id="price" name="price" type="number" class="validate" value="${item.price}">
                    <label for="price">Price</label>
                </div>
            </div>
            <div class="row">
                <div class="input-field col s3">
                    <input id="description" name="description" type="text" class="validate"
                           value="${item.description}">
                    <label for="description">Description</label>
                </div>
            </div>
            <div class="row">
                <div class="input-field col s3">
                    <select required="required" id="categoryId"  name="categoryId" required="required" onchange="loadCharacteristics(this)">
                        <option value="" disabled selected>Choose your option</option>
              <#list categories as category>
            <option id="${category.id}" name="categoryId" value="${category.id}">${category.name}</option>
              </#list>
                    </select>
                    <label for="categoryId">Category</label>
                    <button class="waves-effect waves-light btn" onclick="addCategory(this)"><i class="material-icons">add</i></button>
                </div>
            </div>
            <div id="characteristicDiv"></div>
        </div>
    </div>
    <p>
        <button class="waves-effect waves-light btn" type="submit">Save</button>
        <button class="waves-effect waves-light btn" type="reset">Reset</button>
    </p>
</form>




<script type="text/javascript" src="../js/addItemsHandler.js"></script>
<script type="text/javascript" src="../js/materialize.min.js"></script>
<script type="text/javascript" src="../js/initSelect.js"></script>
</body>
</html>