<#import "/spring.ftl" as spring/>
<#escape x as x?html>
<!DOCTYPE html>
<html>
<head>
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="/js/jquery.cookie.js"></script>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="/css/materialize.min.css" media="screen,projection"/>
    <link type="text/css" rel="stylesheet" href="/css/my_style.css"/>

    <link rel="icon" href="https://cdn.crowdfundinsider.com/wp-content/uploads/2015/02/strongvolt-logo-300x248.jpg">
    <title>VOLT-orders</title>
</head>

<body>
<script type="text/javascript" src="/js/cookieHandler.js"></script>

<#include "header.ftl">
<div class="row" style="margin-top: 100px">


<#if  orders?size==0>
  <div class="col s2 offset-s1 card horizontal">
      <div class="card-stacked">
          <div class="card-content">
              <p>No orders</p>
          </div>
      </div>
  </div>
</#if>
    <div id="verifyCategory"></div>
    <div class="col s8">
        <div class="row">
            <div class="col s5 offset-s6">
                <div id="items_div">
    <#list orders as order>
        <div id="${order.id}" class="card horizontal hoverable">
            <div class="card-stacked">
                <div class="card-content">
                    <p><a href="/orders/${order.id}">Order №${order.id}
                        ${order.date?datetime?string('dd-MM-yyyy')}</a></p>
                </div>

            </div>
        </div>
    </#list>
            </div>
            </div>
            <#include "characteristic_filter.ftl">
        </div>
    </div>
</div>

<#if cookies??>
    <#list cookies as cookie>
<script>setCookie("${cookie.name}");</script>
    </#list>
</#if>
<script type="text/javascript" src="/js/searchHandler.js"></script>
<script type="text/javascript" src="/js/materialize.min.js"></script>
</body>
</html>
</#escape>