<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html>
<head>
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="css/materialize.min.css" media="screen,projection"/>

    <link type="text/css" rel="stylesheet" href="css/my_style.css" />
</head>

<body style="background-color: #f5f5f5">


<div class="white">
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
                    <a href=""> <i style="font-size: 36px; color: #4db6ac;" class="material-icons hoverable">shopping_cart</i>
                    </a>
                </div>
            </div>
        </form>
    </div>


    <div class="divider z-depth-3"></div>
</div>


<div class="row" style="margin-top: 100px">
    <div class="col s3">
        <div class="collection z-depth-1 hoverable">
    <#list categories as category>
        <div><a href="/items?category=${category.name}" class="collection-item">${category.name}</a></div>
    </#list>
        </div>
    </div>



                    <#if itemsSize<=0>
  <div class="col s2 offset-s1 card horizontal">
      <div class="card-stacked">
          <div class="card-content">
              <p>No items</p>
          </div>
      </div>
  </div>
                    </#if>
    <div class="col s8">
        <div class="row">
            <div class="col s8 offset-s1">


    <#list items as item>

        <div class="card horizontal hoverable">
            <div class="card-image">
                <img width="60" height="200" src="../img/meizu.jpg">
            </div>
            <div class="card-stacked">
                <div class="card-content">
                    <p class="flow-text"><a href="/items/${item.id}">${item.producer} ${item.name}</a></p>
                    <p> ${item.price} rub</p>
                    <p>${item.storage} ps</p>
                </div>
                <div class="card-action">
                    <a href="/basket" class="waves-effect waves-light btn">buy</a>
                    <a href="/items/edit/${item.id}"> <i style="font-size: 33px; color: #4db6ac; margin-top: 10px"
                                                         class="material-icons">edit</i>
                        <a href="/items/delete/${item.id}"> <i style="font-size: 33px; color: #4db6ac; margin-top: 10px"
                                                               class="material-icons modal-trigger">delete</i>
                        </a>
                </div>
            </div>
        </div>



    </#list>

            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="js/homeHandler.js"></script>

<!--JavaScript at end of body for optimized loading-->
<script type="text/javascript" src="js/materialize.min.js"></script>
</body>
</html>