<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html>
<head>
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <!-Let browser know website is optimized for mobile-->
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

    <link rel="stylesheet" type="text/css" href="<@spring.url '/css/style.css'/>"/>

</head>

<body>


<form action="/items/add" method="post">

     <input type="text"  class="validate"> <label for="1" hidden="hidden">weight</label>
    <input type="number" name="id" class="validate" hidden="hidden" value="0"> <label for="id" hidden="hidden">Category</label>
    <input type="text" name="name"  required="required" value="${item.name}"/>
    <input type="text" name="description"  value="${item.description}"/>
    <input type="number" name="storage"  required="required" value="${item.storage}"/>
    <input type="number" name="price"  required="required" value="${item.price}"/>

    <select id="categoryId" name="categoryId" required="required" onchange="loadCharacteristics(this)">
        <option value="" disabled selected>Choose your option</option>
         <#list categories as category>
       <option id="${category.id}" name="categoryId"  value="${category.id}">${category.name}</option>
         </#list>
    </select>
    <label for="categoryId">Category</label>
    <p>
        <button type="reset">Cancel</button>
        <button type="submit">Save</button>
    </p>

    <div id="characteristicDiv"></div>
</form>

<script type="text/javascript" src="../js/items.js"></script>
<script type="text/javascript" src="../js/materialize.min.js"></script>
</body>
</html>