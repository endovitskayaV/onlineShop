<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html>
<head>
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="../css/materialize.min.css" media="screen,projection"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link type="text/css" rel="stylesheet" href="../css/my_style.css" />
</head>

<body>


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
                    <a href=""> <i style="font-size: 36px; color: #4db6ac;" class="material-icons">shopping_cart</i>
                    </a>
                </div>
            </div>
        </form>
    </div>


    <div class="divider z-depth-3"></div>
</div>

<div style="margin-top: 50px" class="row">
  <div class="col s3 offset-s4 card hoverable">
          <div class="card-content">
              <p class="center-align">${message}</p>
      </div>
  </div>
</div>

<script type="text/javascript" src="../js/materialize.min.js"></script>
</body>
</html>