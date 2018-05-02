<#import "/spring.ftl" as spring/>
<#escape x as x?html>
<!DOCTYPE html>
<html>
<head>
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="../js/jquery.cookie.js"></script>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="../css/materialize.min.css" media="screen,projection"/>
    <link type="text/css" rel="stylesheet" href="../css/my_style.css" />

    <link rel="icon" href="https://cdn.crowdfundinsider.com/wp-content/uploads/2015/02/strongvolt-logo-300x248.jpg">
    <title>Error</title>
</head>

<body>
<#include "header.ftl">

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
</#escape>