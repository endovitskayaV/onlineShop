<#import "/spring.ftl" as spring/>

<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Welcome</title>
    <link rel="stylesheet"
          type="text/css" href="<@spring.url '/css/style.css'/>"/>

    <script src="webjars/jquery/2.0.3/jquery.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $('p').animate({
                fontSize: '48px'
            }, "slow");
        });
    </script>

    <link th:href="@{/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css}"
          rel="stylesheet" media="screen"/>

    <script src="http://code.jquery.com/jquery.js"></script>
    <script th:src="@{/webjars/bootstrap/3.3.7-1/js/bootstrap.min.js}"></script>
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

<!--<a href="<@spring.url '/personList'/>">Person List</a>-->

</body>

</html>