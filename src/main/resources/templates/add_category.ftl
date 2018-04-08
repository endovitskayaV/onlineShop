<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html>
<head>
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="../css/materialize.min.css" media="screen,projection"/>
    <link type="text/css" rel="stylesheet" href="../css/my_style.css" media="screen,projection"/>
</head>

<body>


<form>
    <div class="row">
        <div class="col s12">
            <div class="row">
                <div class="input-field col s3">
                    <input id="category_name" required="required" name="category_name" type="text" value="${category.name}">
                    <label for="name">Name</label>
                </div>
            </div>
            <div class="row">
                <div class="input-field col s3">
                    <input id="category_description"  name="category_description" type="text" value="${category.description}">
                    <label for="description">Description</label>
                </div>
            </div>
            <div class="row">
                <div class="input-field col s3">
                    <input id="category_rating" name="category_rating" type="number" value="${category.rating}">
                    <label for="rating">Rating</label>
                </div>
            </div>
            <div class="row">
            <div class="input-field col s3">
                <select  id="category_select" required="required" name="characteristicIdArray[0]" multiple>
                    <option value="" disabled selected>Choose your option</option>
                    <#assign i = 1>
                  <#list characteristics as characteristic>
                     <option id="characteristicIdArray[${i}].id" name="characteristicIdArray[${i}].id" value="${characteristic.id}">${characteristic.name}</option>
                      <#assign i++>
                  </#list>
                </select>
                <label for="category_select">Characteristics</label>
            </div>
            </div>
    </div>
    <p>
        <a href="javascript: addCategory('category_select')" class="waves-effect waves-light btn">Save</a>
        <button class="waves-effect waves-light btn" type="reset">Reset</button>
    </p>
</form>



<script type="text/javascript" src="../js/addCategoryHandler.js"></script>
<script type="text/javascript" src="../js/materialize.min.js"></script>
<script type="text/javascript" src="../js/initSelect.js"></script>
</body>
</html>