<#import "/spring.ftl" as spring/>

<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Welcome</title>
    <link rel="stylesheet" type="text/css" href="<@spring.url '/css/style.css'/>"/>
    <script src="webjars/jquery/2.0.3/jquery.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $('p').animate({
                fontSize: '48px'
            }, "slow");
        });
    </script>

    <link rel="stylesheet" type="text/css" href="/webjars/bootstrap/3.2.0/css/bootstrap.min.css"/>
    <script  src="/webjars/bootstrap/3.2.0/js/bootstrap.min.js"></script>
</head>

<body>
      <#if shopName??>
      <p> ${shopName}</p>
      </#if>
<div class="btn-group">
    <button type="button" class="btn btn-success">This is a success button</button>
    <button type="button" class="btn btn-warning">This is a warning button</button>
    <button type="button" class="btn btn-danger">This is a danger button</button>
</div>


<div class="container">

    <a href="/login" class="btn btn-primary"><span class="fa fa-user"></span> SignIn</a>
    <a href="/logout" class="btn btn-danger">Logout <span class="fa fa-sign-out"></span> </a>
    <a href="/facebook" class="btn btn-primary">Facebook <span class="fa fa-facebook"></span> </a>
    <a href="/google" class="btn btn-danger"> Google <span class="fa fa-google-plus"></span> </a>
    <a href="/linkedin" class="btn btn-primary">LinkedIn <span class="fa fa-linkedin"></span> </a>
</div>

<!--<a href="<@spring.url '/personList'/>">Person List</a>-->

</body>

</html>