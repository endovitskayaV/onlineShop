<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html>
<head>
    <!--Import Google Icon Font-->
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <!--Import materialize.css-->
    <link type="text/css" rel="stylesheet" href="css/materialize.min.css" media="screen,projection"/>

    <!--Let browser know website is optimized for mobile-->
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

    <link rel="stylesheet" type="text/css" href="<@spring.url '/css/style.css'/>"/>

</head>

<body>
<div class="row">
    <div class="col s3 offset-s9" style="margin-top: 10px">
        <a href="" style=" color: #4db6ac; margin-right: 20px">log in</a>
        <a href="" style=" color: #4db6ac">register</a>
    </div>
</div>


<div class="row">
    <form class="col s12">
        <div class="row">
            <div class="input-field col s3 offset-s4">
                <input placeholder="Search" id="search" type="text" class="validate">
            </div>
            <div>
                <div class="btn" style="margin-top: 25px; margin-right: 300px"><i class="material-icons">search</i>
                </div>
                <a href=""> <i style="font-size: 36px; color: #4db6ac;" class="material-icons">shopping_cart</i> </a>
            </div>
        </div>
    </form>
</div>
<div class="divider z-depth-3"></div>

<form action="/items/add" method="post">
    <input type="number" name="id" class="validate" hidden="hidden" value="0">
    <input type="text" name="name"  required="required" value="${item.name}"/>
    <input type="text" name="description"  value="${item.description}"/>
    <input type="number" name="storage"  required="required" value="${item.storage}"/>
    <input type="number" name="price"  required="required" value="${item.price}"/>
    <select  required="required">
        <option value="" disabled selected>Choose your option</option>
         <#list categories as category>
 <option name="categoryId"  value="${category.id}">${category.name}</option>
           <input type="number" name="id" hidden="hidden" value="0">
           <input type="text" name="description"  hidden="hidden" value=${category.description}>
           <input type="number" name="rating" hidden="hidden" value=${category.rating}>

         </#list>
    </select>
    <p>
        <button type="reset">Cancel</button>
        <button type="submit">Save</button>
    </p>
</form>


<div class="row">
    <form class="col s12">
        <div class="row">
            <div class="input-field col s6">
                <input id="name" type="text" class="validate">
                <label for="name">Name</label>
            </div>

        </div>
    </form>
</div>

<div class="row">
    <form class="col s12">
        <div class="row">
            <div class="input-field col s6">
                <input id="description" type="text" class="validate">
                <label for="description">Description</label>
            </div>

        </div>
    </form>
</div>

<div class="row">
    <form class="col s12">
        <div class="row">
            <div class="input-field col s6">
                <input id="storage" type="number" class="validate">
                <label for="storage">Count</label>
            </div>
        </div>
    </form>
</div>

<div class="row">
    <form class="col s12">
        <div class="row">
            <div class="input-field col s6">
                <input id="price" type="number" class="validate">
                <label for="price">Price</label>
            </div>
        </div>
    </form>
</div>

<div class="input-field col s12">
    <select required="required">
        <option value="" disabled selected>Choose your option</option>
         <#list categories as category>
        <option value="${category.id}">${category.name}</option>
         </#list>
    </select>
    <label>Category</label>
</div>


<!--JavaScript at end of body for optimized loading-->
<script type="text/javascript" src="js/materialize.min.js"></script>
</body>
</html>