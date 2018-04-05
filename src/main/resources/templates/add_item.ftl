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

<body  style="background-color: #f5f5f5">


<form action="/items/add" method="post">
    <div class="col s3 offset-s4" >
    <input type="number" name="id" class="validate" hidden="hidden"> <label for="id" hidden="hidden">id</label>
    <div class="row">
        <div class="col s5">
            <div class="row">
                <div class="input-field col s3">
                    <select required="required" id="categoryId"  name="categoryId" required="required" onchange="loadCharacteristics(this)">
                        <option value="" disabled selected>Choose your option</option>
              <#list categories as category>
            <option id="${category.id}" name="categoryId" value="${category.id}">${category.name}</option>
              </#list>
                    </select>
                    <label for="categoryId">Category</label>
                    <a href="/categories/add" class="waves-effect waves-light btn" ><i class="material-icons">add</i></a>
                </div>
            </div>
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

            <div id="characteristicDiv"></div>
        </div>
    </div>

    <p>
        <button class="waves-effect waves-light btn" type="submit">Save</button>
        <button class="waves-effect waves-light btn" type="reset">Reset</button>
    </p>

        </div>

</form>




<script type="text/javascript" src="../js/addItemsHandler.js"></script>
<script type="text/javascript" src="../js/materialize.min.js"></script>
<script type="text/javascript" src="../js/initSelect.js"></script>
</body>
</html>