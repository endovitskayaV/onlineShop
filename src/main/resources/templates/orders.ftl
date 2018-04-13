<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html>
<head>
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="/css/materialize.min.css" media="screen,projection"/>
    <link type="text/css" rel="stylesheet" href="/css/my_style.css"/>
</head>

<body>
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

    <div class="col s8">
        <div class="row">
            <div class="col s5 offset-s6">
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
    </div>
</div>

<script type="text/javascript" src="/js/materialize.min.js"></script>
</body>
</html>