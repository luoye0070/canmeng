<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<meta name="layout" content="main1"/>
	</head>
	<body style=" overflow: hidden">
    <script type="text/javascript" src="${resource(dir:"js/common",file:"cart.js")}"></script>
    <link href="${resource(dir: "css",file: "cart.css")}" rel="stylesheet"/>
    <script type="text/javascript">
//        function reinitIframe(){
//            var iframe = document.getElementById("foodList");
//            try{
//                var bHeight = iframe.contentWindow.document.body.scrollHeight;
//                var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
//                var height = Math.max(bHeight, dHeight);
//                iframe.height = height;
//            }catch (ex){}
//
//        }
//        function reinitIframe1(){
//            var iframe1 = document.getElementById("resList");
//            try{
//                var bHeight = iframe1.contentWindow.document.body.scrollHeight;
//                var dHeight = iframe1.contentWindow.document.documentElement.scrollHeight;
//                var height = Math.max(bHeight, dHeight);
//                iframe1.height = height;
//            }catch (ex){}
//
//        }
//        window.setInterval("reinitIframe()", 200);
//        window.setInterval("reinitIframe1()", 200);
        $(function () {
            var foodListHeight;
            var resListHeight;
            $("#foodList").load(function () {
                //foodListHeight = $(this).contents().find(".main").height()+30;
                foodListHeight=this.contentWindow.window.document.body.offsetHeight+30;
                //alert(foodListHeight);
                $(this).height(foodListHeight);
                //$("#foodListDiv").height(foodListHeight);
               // $(".m_content").height(foodListHeight);
            });

            $("#resList").load(function () {
                //resListHeight = $(this).contents().find(".main").height()+30;
                resListHeight=this.contentWindow.window.document.body.offsetHeight+30;
                //alert(resListHeight);
                $(this).height(resListHeight);
            });

            //设置点击事件
            $("#foodListTab").click(function(event){
                $("#foodListTab").attr("class","mht_selected");
                $("#resListTab").attr("class","mht_unselected");
                $("#foodListDiv").attr("class","mc_page_active");
                $("#resListDiv").attr("class","mc_page");
                //$("#foodListDiv").height(foodListHeight);
                //$(".m_content").height(foodListHeight);
                $("#foodList").attr("src","${createLink(controller:"search",action:"searchFood",params: [cityId:cityIdAndName?.cityId?:1])}");
            });
            $("#resListTab").click(function(event){
                $("#resListTab").attr("class","mht_selected");
                $("#foodListTab").attr("class","mht_unselected");
                $("#resListDiv").attr("class","mc_page_active");
                $("#foodListDiv").attr("class","mc_page");
               // $("#resListDiv").height(resListHeight);
                //$(".m_content").height(resListHeight);
                $("#resList").attr("src","${createLink(controller:"search",action:"searchShop",params: [cityId:cityIdAndName?.cityId?:1])}");
            });


            /*********测试标题栏闪动*****/
//            var   dhmt1,dhmt2,dhmt3;
//            var   oldtitle   =   document.title;
//            function   msgtitle(){
//                dhmt1   =   window.setInterval( "document.title= '【新消息】 - ' ",2000);
//                dhmt2   =   window.setTimeout(function(){dhmt3   =   window.setInterval( "document.title='【　　　】 - ' ",2000)},1000);
//            }
//            function   cleartitle(){
//                if(dhmt1!=null){
//                    window.clearInterval(dhmt1);
//                }
//                if(dhmt2!=null){
//                    window.clearTimeout(dhmt2);
//                }
//                if(dhmt3!=null){
//                    window.clearInterval(dhmt3);
//                }
//                document.title   =   oldtitle;
//            }
//            msgtitle();

                //初始化餐车
            initCart({
                addToCartUrl: "${createLink(controller: "cartOfCustomerAjax",action: "addFoodToCart")}",
                cartsAndDishesUrl:"${createLink(controller: "cartOfCustomerAjax",action: "getCartsAndDishes")}",
                checkOutUrl:"${createLink(controller: "cartOfCustomer",action: "checkout")}",
                imgUrl:"${createLink(controller: "imageShow", action: "downloadThumbnail", params: [width: 70,height: 70])}",
                delDishFromCartUrl:"${createLink(controller: "cartOfCustomerAjax",action: "delDish")}",
                updateDishOfCartUrl:"${createLink(controller: "cartOfCustomerAjax",action: "updateDish")}"
            });
        });

function showDialog(htmlStr,foodId){
    var doDishUrl="${createLink(controller: "customerAjax",action: "addDishes")}"
    var dialog = $(htmlStr);
    $("body").append(htmlStr);
    $("#ratyService").modal();
    //dialog.modal();
    //注册事件
    $("input[name='orderId']").click(function(){//选择按钮
        if($(this).attr('checked')){
            $(this).parent().children("input[name='counts']").removeAttr("disabled");
        }
        else{
            $(this).parent().children("input[name='counts']").attr("disabled",'disabled');
            $(this).parent().parent().children("div[name='info']").remove();
        }
    });
    $("#submitData").click(function(event){//数据提交按钮
        $("div[name='dishData']").each(function(){
            var checkBox=$(this).children("div[name='field']").children("input[name='orderId']");
            var textInput=$(this).children("div[name='field']").children("input[name='counts']");
            if(checkBox.attr("checked")){//提交数据
                if(typeof($(this).children("div[name='info']"))!='undefined')
                    $(this).children("div[name='info']").remove();
                //$('<div class="progress progress-striped active"><div class="bar" style="width: 40%;"></div></div>').appendTo($(this).children("div[name='info']"));
                $("<div name='info' style='float: left;width: 400px;height: 50px;line-height: 50px;'>"+
                        '<img style="width: 50px;height: 50px;float: left;" src="../images/progcess.gif"/>'+
                        '<label style="width: 300px;height: 50px;">正在添加，请稍后...</label></div>').appendTo($(this));
                var orderId= checkBox.val();
                var counts=textInput.val();
                var foodIds=foodId;
                var remarks="";
                $.ajax({
                    context:this,
                    url:doDishUrl,
                    async:false,
                    type:'post',
                    data:{'orderId':orderId,'counts':counts,'foodIds':foodIds,'remarks':remarks},
                    dataType: 'json',
                    success:function(data){
                        $(this).children("div[name='info']").html("");
                        if(data.recode.code==0){
                            $(this).children("div[name='info']").html("<label style='height: 50px;line-height:50px;color: green'>点菜"+data.recode.label+"</label>");
                        }else{
                            if(data.recode.code==5)
                                $(this).children("div[name='info']").html("<label style='height: 50px;line-height:50px;color: red'>点菜不成功："+data.failedList[0].msg+"</label>");
                            else
                                $(this).children("div[name='info']").html("<label style='height: 50px;line-height:50px;color: red'>点菜不成功："+data.recode.label+"</label>");
                        }
                    },
                    error:function(data){
                        $(this).children("div[name='info']").html("<label style='height: 50px;line-height:50px;color: red'>"+"未知错误"+"</label>");
                    }
                });
            }
        });
    });
}
    </script>

    <style type="text/css">
        *{
            margin: 0px;
            border: 0px;
            padding: 0px;
        }
        ul,li{
            list-style: none;
            padding: 0px;
            margin: 0px;
        }
        body{
            background: #bfbfbf;
        }
        .main{
            width: 1100px;
            height: auto;
            margin: 0px auto;
            background: #f7f3e7;
        }
        .m_head{
            width: 1100px;
            height: 250px;
            margin: 0px auto;
            background:url('${resource(dir:"images",file:"main_head.png")}');
        }
        .mh_menu{
            width: 1100px;
            height: 50px;
            float: left;
            line-height: 50px;
            background: #000000;
            background-repeat: repeat;
            FILTER: Alpha(opacity=60);/*IE下半透明*/
            -moz-opacity: 0.6;/*FF下半透明*/
            opacity: 0.6; /* 支撑CSS3的阅读器（FF 1.5也支持）透明度20%*/
        }
        .mhm_city{
            width: 200px;
            height: 50px;
            float: left;
        }
        .mhm_city label{
            line-height: 50px;
            font-size: 20px;
            margin-left: 20px;
            margin-right: 5px;
            float: left;
            color: #ffffff;
            font-weight: 800;
        }
        .mhm_city a{
            float: left;
        }
        .mh_menu ul,.mh_menu li{
            float: left;
        }
        .mh_menu li{
            height: 50px;
            line-height: 50px;
            margin: 0px 5px 0px 5px;
            color: #ffffff;
            font-family: 'Open Sans', sans-serif;
            font-weight: 800;
            font-size: 14px;
        }
        .mh_menu .current a{
            color: #c5c5c5;
        }
        .mh_menu li a:hover{
            color: #c5c5c5;
            text-decoration: none;
        }
        .mh_center{
            width: 1100px;
            height: 150px;
            float: left;
        }
        .mh_tab{
            width: 1100px;
            height: 50px;
            float: left;
        }
        .mh_tab ul{
            width: 1100px;
            height: 50px;
            list-style: none;
            padding: 0px;
            margin: 0px;
            float: left;
        }
        .mh_tab ul .mht_selected{
            width: 150px;
            height: 49px;
            list-style: none;
            padding: 0px;
            margin: 0px;
            margin-top: 1px;
            float: left;
            background:url('${resource(dir:"images",file:"main_tab_selected.png")}');
            background-repeat: no-repeat;
            cursor: pointer;
            line-height: 50px;
            text-align: center;
            font-size: 20px;
            color: #000000;
        }
        .mh_tab ul .mht_unselected{
            width: 150px;
            height: 49px;
            list-style: none;
            padding: 0px;
            margin: 0px;
            margin-top: 1px;
            float: left;
            background:url('${resource(dir:"images",file:"main_tab_unselected.png")}');
            background-repeat: no-repeat;
            cursor: pointer;
            line-height: 50px;
            text-align: center;
            font-size: 20px;
            color: #89846e;
        }
        .m_content{
            width: 1100px;
            height: auto;
            background-color: #f7f3e7;
            overflow: hidden;
            float: left;
        }
        .mc_page_active{
            width: 1100px;
            height: auto;
            display: block;
            overflow: hidden;
        }
        .mc_page{
            width: 1100px;
            height: auto;
            display: none;
            overflow: hidden;
        }
        #scrollUp {
            bottom: 20px;
            right: 20px;
            height: 38px;  /* Height of image */
            width: 38px; /* Width of image */
            background: url("${resource(dir:'js/JessicaWhite/img',file:'top.png')}") no-repeat;
        }
    </style>
    <g:render template="/layouts/top"></g:render>
    <div class="main">
        <div class="m_head">
            <div class="mh_menu">
                <div class="mhm_city">
                    <label>${cityIdAndName?.cityName?:"乌鲁木齐"}</label>
                    <a href="${createLink(controller: "citySelect",action: "cityList",params: [co:controllerName,ac:actionName])}">切换城市</a>
                </div>
                <ul style="float: right;margin-right: 20px;">
                    <% String url=request.getRequestURL();
                    url=url.substring(url.lastIndexOf("/")+1);
                    %>
                    <li ${url==""?'class="current"':""}><g:link url="/canmeng"> 餐萌首页</g:link></li>
                    <li  ${controllerName=="user"?'class="current"':""}><g:link controller="user" action="viewUserInfo">我的餐萌</g:link></li>
                    <li  ${controllerName=="customer"?'class="current"':""}><g:link controller="customer" action="orderList">我的订单</g:link></li>
                    <li  ${controllerName=="shop"?'class="current"':""}><g:link controller="shop" action="shopInfo">我的饭店</g:link></li>
                    <li  ${url=="c"?'class="current"':""}><a href="about.html">联系餐萌</a></li>
                    <li ${controllerName=="staff"?"class='current'":""}><g:link controller="staff" action="index">工作人员入口</g:link></li>
                    <g:if test="${session && (session.clientId||session.staffInfo)}">
                        <li><g:link controller="user" action="logout">退出餐萌</g:link></li>
                    </g:if>
                    <g:else>
                        <li><g:link controller="user" action="login">登录餐萌</g:link></li>
                    </g:else>
                </ul>
            </div>
            <div class="mh_center">
                %{--<a href="${resource(dir: "uploadFile",file: "CmCustomer.apk")}">顾客手机端下载</a>--}%
                %{--<img src="${createLink(controller: "imageShow", action: "showQRCode",--}%
                        %{--params: [width: 150, str: resource(dir: "uploadFile",file: "CmCustomer.apk",absolute: true)])}"--}%
                     %{--alt=""/>--}%
                %{--<a href="${resource(dir: "uploadFile",file: "CmStaff.apk")}">饭店手机端下载</a>--}%
                %{--<img src="${createLink(controller: "imageShow", action: "showQRCode",--}%
                        %{--params: [width: 150, str: resource(dir: "uploadFile",file: "CmStaff.apk",absolute: true)])}"--}%
                     %{--alt=""/>--}%
            </div>
            <div class="mh_tab">
                <ul>
                    <li id="foodListTab" class="mht_selected">美食</li>
                    <li id="resListTab" class="mht_unselected">餐厅</li>
                </ul>
            </div>
        </div>
        <div class="m_content">
            <div class="mc_page_active" id="foodListDiv">
                <iframe id="foodList" width='100%'style="border: 0px" frameborder="no" border="0" marginwidth="0"
                        marginheight="0" src="${createLink(controller:"search",action:"searchFood",params: [cityId:cityIdAndName?.cityId?:1])}"></iframe>
                %{--${createLink(controller: "search", action: "searchFood", params: [cityId:cityIdAndName?.cityId?:1],absolute: true).toString().toURL().getText()}--}%
            </div>
            <div class="mc_page" id="resListDiv">
                <iframe id="resList" width='100%'  style="border: 0px" frameborder="no" border="0" marginwidth="0"
                        marginheight="0" src="${createLink(controller:"search",action:"searchShop",params: [cityId:cityIdAndName?.cityId?:1])}"></iframe>
            </div>
        </div>

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
    </div>

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
    </body>
</html>