<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet" href="${resource(dir: 'js/JessicaWhite/css', file: 'bootstrap.css')}"  >
    <link rel="stylesheet" href="${resource(dir: 'js/JessicaWhite/css', file: 'theme.css')}"  >
    <link rel="stylesheet" href="${resource(dir: 'js/JessicaWhite/css/skins/tango/', file: 'skin.css')}"  >
    <link rel="stylesheet" href="${resource(dir: 'js/JessicaWhite/css', file: 'bootstrap-responsive.css')}"   >
    <!--[if lt IE 9]>
    <g:javascript src="html5.js"/>
    <![endif]-->

    <style>
    #scrollUp {
        bottom: 20px;
        right: 20px;
        height: 38px;  /* Height of image */
        width: 38px; /* Width of image */
        background: url("${resource(dir:'js/JessicaWhite/img',file:'top.png')}") no-repeat;
    }
    </style>

    <g:layoutHead/>
    <r:layoutResources/>
</head>
<body>

<!--header-->
<div class="header">
    <div class="wrap">
        <div class="navbar navbar_clearfix">
            <div class="container">
                <div class="row">
                    <div class="span4">
                        <div class="logo">
                            <a href="index.html">
                                <g:img dir="js/JessicaWhite/img" file="logo.png" />
                            </a>
                        </div>
                    </div>
                    <div class="span8">
                        <nav id="main_menu">
                            <div class="menu_wrap">
                                <ul class="nav sf-menu">
                                   <% String url=request.getRequestURL();
                                       url=url.substring(url.lastIndexOf("/")+1);
                                   %>
                                    <li ${url==""?'class="current"':""}><g:link url="/canmeng"> 餐萌首页</g:link></li>
                                    <li  ${url in ["viewUserInfo","changePsd","emailAuth","phoneAuth",'securityQuestion','myFavorites','userAddresses']?'class="current"':""}><g:link controller="user" action="viewUserInfo">我的资料</g:link></li>
                                    <li  ${url in ["createOrder",'reserveTable',"orderList","orderShow","doDish"]?'class="current"':""}><g:link controller="customer" action="orderList">我的餐萌</g:link></li>
                                    <li  ${url in ["shopInfo","editShop"]?'class="current"':""}><g:link controller="shop" action="shopInfo">我的饭店</g:link></li>
                                    <li  ${url=="c"?'class="current"':""}><a href="about.html">联系餐萌</a></li>
                                    <li ${controllerName=="staff"?"class='current'":""}><g:link controller="staff" action="index">工作人员入口</g:link></li>
                                    <g:if test="${session && (session.clientId||session.staffInfo)}">
                                        <li><g:link controller="user" action="logout">退出餐萌</g:link></li>
                                    </g:if>

                                </ul>
                            </div>
                        </nav>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!--//header-->
<!--script-->
<g:javascript src="jquery-1.8.2.min.js"/>
<g:javascript src="JessicaWhite/js/jquery.easing.1.3.js"/>
<g:javascript src="JessicaWhite/js/jquery.mobile.customized.min.js"/>
<g:javascript src="JessicaWhite/js/bootstrap.js"/>
<g:javascript src="JessicaWhite/js/jquery.scrollUp.min.js"/>

<!--page_container-->
<div class="page_container">
    <g:layoutBody/>
</div>
<!--//page_container-->

<!--footer-->
<div id="footer">
    <div class="footer_bottom">
        <div class="wrap">
            <div class="container">
                <div class="row">
                    <div class="span5">
                        <div class="foot_logo"><g:img dir="js/JessicaWhite/img" file="foot_logo.png" /></div>
                        <div class="copyright">&copy; 2020 餐萌版权所有</div>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>
<!--//footer-->

<script>
    $(document).ready(function(){
        $.scrollUp({
            scrollName: 'scrollUp', // Element ID
            topDistance: '300', // Distance from top before showing element (px)
            topSpeed: 300, // Speed back to top (ms)
            animation: 'fade', // Fade, slide, none
            animationInSpeed: 200, // Animation in speed (ms)
            animationOutSpeed: 200, // Animation out speed (ms)
            scrollText: '', // Text for element
            activeOverlay: false  // Set CSS color to display scrollUp active point, e.g '#00FFFF'
        });
    });
</script>



<r:layoutResources/>
</body>
</html>