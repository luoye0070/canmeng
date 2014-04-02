<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 14-1-7
  Time: 上午12:43
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <meta name="layout" content="main_template"/>
  <title></title>
    <style type="text/css">
    .mc_main {
        width: 1000px;
        height: auto;
        margin: 0px 50px;
        background-color: #FFFFFF;
        float: left;
    }
        .mcm_left{
            width: 250px;
            height: auto;
            float: left;
        }
        .mcm_right{
            width: 750px;
            height: auto;
            float: left;
        }
    </style>
    <g:layoutHead/>
    <r:layoutResources/>
</head>
<body>
<div class="mc_main">
<!--navigation-->
<div class="mcm_left">



    </div>
<!--content-->
<div class="mcm_right">
    <g:layoutBody/>
</div>
</div>
</body>
</html>