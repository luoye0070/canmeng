<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 13-11-9
  Time: 下午11:38
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main_template"/>
    <title>菜单详情</title>
    <g:javascript src="shop/shopSome.js"/>
    <script type="text/javascript" src="${resource(dir: "js/common", file: "doDish.js")}"></script>
    <style>
    #food_main {
        width: 1000px;
        height: auto;
        margin: 0px 50px;
        background-color: #FFFFFF;
        float: left;
        padding: 20px 0px 20px 0px;
    }

    #picArea {
        width: 300px;
        height: 250px;
        margin-left: 20px;
        float: left;
        overflow: hidden;
    }

    #fooddecribe {
        width: 600px;
        margin-left: 80px;
        float: left;
    }

    #foodname {
        font-size: 20px;
        font-weight: bold;
        color: #000;
        height: 23px;
        line-height: 23px;
    }

    .priceLabel {
        font-size: 15px;
        font-weight: bold;
        color: #cc0000;
        line-height: 23px;
    }

    #old_priceLabel {
        text-decoration: line-through
    }

    #fooddecribe {
        width: 600px;
    }

    .fieldcontain {
        line-height: 25px;
    }

    #detail_left {
        float: left;
        width: 705px;
        margin-left: 10px;
    }

    #detail_right {
        float: right;
        width: 260px;
        margin-right: 10px;
        overflow: hidden;
    }

    #detail_left_title {
        width: 705px;
        height: 35px;
        line-height: 37px;
        font-weight: bold;
        background: url('${resource(dir:"images",file:"detail_left_title.gif")}');
    }

    #detail_left_main {
        width: 671px;
        padding: 13px;
        margin-left: 3px;
        border: 1px solid #D2D2D2;
        border-top: 0px;
        line-height: 25px;
    }

    .detail_restour_title {
        width: 231px;
        height: 38px;
        padding-left: 20px;
        line-height: 39px;
        font-weight: bold;
        background: url('${resource(dir:"images",file:"detail_restour_title.gif")}');
    }

    .detail_restour_main {
        width: 240px;;
        margin-left: 1px;
        padding: 4px;
        border: 1px solid #D2D2D2;
        border-top: 0px;
        line-height: 25px;
    }

    #detail_other_food {
        margin-top: 10px;
    }

    #detail_other_food #wenhao {
        font-weight: normal;
        color: #cc0000;
    }

    #detail_other_food #wenhao_big {
        font-size: 13px;
    }

    #food_view {
        width: 1000px;
        height: auto;
        margin-bottom: 20px;
        float: left;
    }

    #food_detail {
    }

    #btn_ljdc a {
        background: url('${resource(dir:"images",file:"btn_ljdc.gif")}');
        width: 95px;
        height: 37px;
        line-height: 37px;
        padding-left: 40px;
        display: block;
        float: left;
        font-weight: bold;
        font-size: 14px;
        color: #fff;
        text-decoration: none;
    }

    #btn_ljdc a:hover {
        background: url('${resource(dir:"images",file:"btn_jrcc.gif")}');
    }

    #btn_jrcc a {
        background: url('${resource(dir:"images",file:"btn_ljdc.gif")}');
        width: 95px;
        height: 37px;
        line-height: 37px;
        padding-left: 40px;
        display: block;
        float: left;
        font-weight: bold;
        font-size: 14px;
        color: #fff;
        text-decoration: none;
    }

    #btn_jrcc a:hover {
        background: url('${resource(dir:"images",file:"btn_jrcc.gif")}');
    }
    </style>
    <script type="text/javascript">
        $(function () {
            //注册点菜事件
            $("a[addToOrder]").doDish(
                    {
                        orderListUrl: "${createLink(controller: "customerAjax",action: "getOrdersByRestaurant")}",
                        doDishUrl: "${createLink(controller: "customerAjax",action: "addDishes")}"
                    }
            );
        });
    </script>
</head>

<body>
<div id="food_main">
    <div id="food_view">
        <!--菜单信息-->
        <!--图片-->
        <div id="picArea">
            <img id="imageLabel"
                 src="${createLink(controller: "imageShow", action: "downloadThumbnail", params: [imgUrl: foodInfo.image, width: 300, height: 250])}
                 "/>
        </div>

        <div id="fooddecribe">
            <!--名称-->
            <div class="fieldcontain">
                <span id="foodname">${foodInfo?.name}</span>
            </div>
            <!--价格-->
            <div class="fieldcontain">
                <g:message code="foodInfo.price.label" default="Price"/>：
                <span class="priceLabel">￥${fieldValue(bean: foodInfo, field: 'price')}</span>
            </div>
            <g:if test="${fieldValue(bean: foodInfo, field: 'originalPrice')}">
                <!--原价-->
                <div class="fieldcontain">
                    <g:message code="foodInfo.originalPrice.label" default="Original Price"/>：
                    <span class="priceLabel"
                          id="old_priceLabel">￥${fieldValue(bean: foodInfo, field: 'originalPrice')}</span>
                </div>
            </g:if>
            <div class="fieldcontain">售出总量：${foodInfo?.totalSellCount}份</div>

            %{--<div class="fieldcontain">我 要 点：<input type="text" value="1" style="width: 40px;"> 份（剩余量：${foodInfo?.countLimit-foodInfo?.sellCount}份）</div>--}%
            <div class="fieldcontain">今日剩余：${foodInfo?.countLimit - foodInfo?.sellCount}份</div>
            %{--<g:if test="${foodInfo?.canTakeOut}"><div id="btn_jrcc"><a href="#">加入餐车</a></div></g:if>--}%

            <div>
                <g:if test="${foodInfo?.countLimit - foodInfo?.sellCount}">
                    <a addToOrder="true"
                       restaurantId="${foodInfo?.restaurantId}"
                       foodId="${foodInfo?.id}"
                       href="#">加入订单</a>&nbsp;&nbsp;
                </g:if>
                <a href="#"
                   onclick="foodAddToFavorite('${createLink(controller: "user",action: "addFavorite",params: [type:"food",foodId:foodInfo?.id])}')">收藏</a>
            </div>

            %{--<div>--}%
            %{--<a href="#"onclick="foodAddToFavorite('${createLink(controller: "user",action: "addFavorite",params: [type:"food",foodId:foodInfo?.id])}')">收藏</a>--}%
            %{--</div>--}%
        </div>
    </div>

    <div id="food_detail">
        <!--详细信息-->
        <div id="detail_left">
            <div id="detail_left_title"></div>

            <div id="detail_left_main">
                <g:if test="${foodInfo.description}">
                    ${foodInfo.description}
                </g:if>
                <g:else>
                    <img width="671"
                         src="${createLink(controller: "imageShow", action: "downloadThumbnail", params: [imgUrl: foodInfo.image, width: 671, height: 671])}
                         "/>
                </g:else>
            </div>
        </div>

        <div id="detail_right">
            <div class="detail_restour">
                <div class="detail_restour_title">${restaurantInfo?.name}</div>

                <div class="detail_restour_main">
                    <g:message code="restaurantInfo.image.label" default="image"/>
                    <g:if test="${restaurantInfo?.image}">
                        <img id="imageLabel"
                             src="${createLink(controller: "imageShow", action: "downloadThumbnail", params: [imgUrl: restaurantInfo?.image])}"/>
                    </g:if>
                <!--店铺名称-->
                    <div>
                        <g:message code="restaurantInfo.name.label" default="name"/>
                        ${restaurantInfo?.name}
                    </div>
                    <!--店铺地址-->
                    <div>
                        <g:message code="restaurantInfo.street.label" default="Street"/>
                        ${restaurantInfo?.province}
                        ${restaurantInfo?.city}
                        ${restaurantInfo?.area}
                        ${restaurantInfo?.street}
                    </div>

                    <div>
                        <a href="#"
                           onclick="shopAddToFavorite('${createLink(controller: "user",action: "addFavorite",params: [type:"shop",restaurantId:restaurantInfo?.id])}')">收藏饭店</a>
                        <a href="${createLink(controller: "infoShow", action: "shopShow", params: [id: restaurantInfo?.id])}">进入饭店</a>
                    </div>
                </div>
            </div>

            <div class="detail_restour" id="detail_other_food">
                <div class="detail_restour_title">本店还有哪些美食<span id="wenhao">？</span></div>

                <div class="detail_restour_main">
                    <g:if test="${foodList}">
                        <ul>
                            <g:each in="${foodList}" var="foodInfo">
                                <li><a href="${createLink(controller: "infoShow", action: "foodShow", params: [id: foodInfo.id])}">${foodInfo.name}</a>
                                </li>
                            </g:each>
                        </ul>
                    </g:if>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>