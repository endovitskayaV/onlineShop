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


<form action="/categories/add" method="post">

    <input type="number" name="id" class="validate" hidden="hidden">
    <div class="row">
        <div class="col s12">
            <div class="row">
                <div class="input-field col s3">
                    <input id="name" required="required" name="name" type="text" class="validate" value="${category.name}">
                    <label for="name">Name</label>
                </div>
            </div>
            <div class="row">
                <div class="input-field col s3">
                    <input id="description" required="required" name="description" type="text" class="validate" value="${category.description}">
                    <label for="description">Description</label>
                </div>
            </div>
            <div class="row">
                <div class="input-field col s3">
                    <input id="rating" name="rating" type="number" class="validate" value="${category.rating}">
                    <label for="rating">Rating</label>
                </div>
            </div>
            <div>
                <div class="row">
                    <div class="col s12">
                        <div class="row">
                            <div class="input-field col s3">
                                <input id="name" required="required" name="name" type="text" class="validate" value="${characteristic.name}">
                                <label for="name">Name</label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="input-field col s3">
                                <input id="type" required="required" name="type" type="text" class="validate" value="${characteristic.type}">
                                <label for="type">Type</label>
                            </div>
                        </div>
                    </div>
                </div>
                <div id="characteristicsDiv">
            </div>
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