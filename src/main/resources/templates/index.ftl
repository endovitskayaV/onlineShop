<#import "/spring.ftl" as spring/>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8" />
    <title>${shopName}</title>
    <link rel="stylesheet" type="text/css" href="<@spring.url '/css/style.css'/>"/>
    <script src="webjars/jquery/2.0.3/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="/webjars/bootstrap/3.2.0/css/bootstrap.min.css"/>
    <script  src="/webjars/bootstrap/3.2.0/js/bootstrap.min.js"></script>
</head>
<body>

<div class="container"  style="margin-top: 20px">

    <a href="/login" class="btn btn-primary pull-right" style="margin-left: 7px"><span class="fa fa-user"></span> SignIn</a>
    <a href="/logout" class="btn btn-danger pull-right">Logout <span class="fa fa-sign-out"></span> </a>
</div>
<div class="container" style="margin-top: 1px">
<#if shopName??>
      <p > ${shopName}</p>
</#if>
<!--<a href="<@spring.url '/personList'/>">Person List</a>-->
</div>
</body>

<script type="text/javascript">
    $(document).ready(function() {
        $('p').animate({
            fontSize: '48px'
        }, "slow");
    });
</script>
</html>