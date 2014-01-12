<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main_template"/>
    <title>我的收藏夹</title>
    <style type="text/css">
    .mc_main {
        width: 1000px;
        height: auto;
        margin: 0px 50px;
        background-color: #FFFFFF;
        float: left;
    }

    .mcm_left {
        width: 250px;
        height: auto;
        float: left;
        margin: 10px 0px;
    }

    .mcm_right {
        width: 740px;
        height: auto;
        margin-right: 10px;
        float: left;
        margin: 10px 0px;
    }

    .mcmr_title {
        width: 738px;
        height: 40px;
        line-height: 40px;
        font-size: 14px;
        font-weight: bolder;
        border: 1px solid #DBD7D8;
    }

    .mcmr_main {
        width: 740px;
        height: auto;
        float: left;
        margin-top: 20px;
    }
    .ml_row_img {
        width: 120px;
        height: 100px;
        overflow: hidden;
        margin: 0px auto;
    }

    .ml_row_txt {
        width: 120px;
        height: 30px;
        line-height: 30px;
        margin: 0px auto;
        overflow: hidden;
    }
    </style>
    <script type="text/javascript" src="${resource(dir:"js/common",file:"doDish.js")}"></script>
    <script type="text/javascript">
        $(function(){
            //注册点菜事件
            $("a[addToOrder]").doDish(
                    {
                        orderListUrl:"${createLink(controller: "customerAjax",action: "getOrdersByRestaurant")}",
                        doDishUrl:"${createLink(controller: "customerAjax",action: "addDishes")}"
                    }
            );
        });
    </script>
</head>

<body>

<div class="mc_main">
    <div class="mcm_left">
        <g:render template="../layouts/userNavigation"></g:render>
    </div>
    <div class="mcm_right">
        <div class="mcmr_title">&nbsp;&nbsp;&nbsp;&nbsp;${params.type=="restaurant"?"饭店收藏":"菜谱收藏"}</div>

        <div class="mcmr_main">
    %{--<div class="span7">--}%

        <g:if test="${type == "restaurant"}">
            <g:if test="${rows && rows.size() > 0}">
                <form class="form-horizontal" action="delFavorite" style="margin-left:20px; ">
                    <input type="hidden" name="type" value="restaurant">
                    <ul class="thumbnails">
                        <g:each in="${rows}" var="row">
                            <li class="span2" style="width: 140px;">
                                <div class="thumbnail">
                                    %{--<img src="${createLink(controller: "imageShow",action: "downloadThumbnail",params: [imgUrl:row.image])}" alt="" height="100" width="100">--}%

                                    %{--<div class="caption">--}%
                                        %{--<h3><input type="checkBox" name="checkes" id="foodId"--}%
                                                   %{--value="${row.id}">${row.name}</h3>--}%
                                    %{--</div>--}%

                                    <div class="ml_row_img">
                                        <img width="120"
                                             src="${createLink(controller: "imageShow", action: "downloadThumbnail", params: [imgUrl: row?.image,width:120])}"/>
                                    </div>

                                    <div class="ml_row_txt">
                                        <label style="float: left;font-size: 14px;overflow: hidden;height: 30px;line-height: 30px;">
                                            <a target="_parent" title="${row?.name}"
                                               href="${createLink(controller: "infoShow", action: "shopShow", params: [id: row.restaurantId])}">${row?.name}</a>
                                        </label>
                                    </div>
                                    <div class="ml_row_txt">
                                        <input style="float: left;margin-top: 9px;height: 12px;margin-bottom: 9px;" type="checkBox" name="checkes" id="restaurantId"
                                               value="${row.id}">
                                        <a style="float: right;"
                                           href="${createLink(controller: "infoShow", action: "tablesShow", params: [restaurantId: row?.restaurantId,restaurantName:row?.name])}"
                                           >
                                            预定桌位</a>
                                    </div>
                                </div>
                            </li>
                        </g:each>

                    </ul>


                    <div class="form-actions">
                        <input type="button" id="all_select" class="btn btn-primary" value="全选"/>
                        <input type="button" id="cancel_select" class="btn dark_btn" value="取消选择"/>
                        <input type="button" id="delete" class="btn btn-primary" value="删除"/>
                    </div>

                </form>
                <white:paginate total="${total}" prev="&larr;" next="&rarr;" params="[type: 'restaurant']" max="12"/>
            </g:if>
            <g:else>
                您还没有收藏店铺!
            </g:else>

        </g:if>


        <g:if test="${type == "food"}">
            <g:if test="${rows && rows.size() > 0}">
                <form class="form-horizontal" action="delFavorite" style="margin-left:20px; ">
                    <input type="hidden" name="type" value="food">
                    <ul class="thumbnails">
                        <g:each in="${rows}" var="row">
                            <li class="span2" style="width: 140px;">
                                <div class="thumbnail">
                                    %{--<img src="${createLink(controller: "imageShow",action: "downloadThumbnail",params: [imgUrl:row.image])}"--}%
                                         %{--alt="" height="100" width="100">--}%

                                    %{--<div class="caption">--}%
                                        %{--<h3><input type="checkBox" name="checkes" id="foodId"--}%
                                                   %{--value="${row.id}">${row.name}--}%
                                        %{--</h3>--}%
                                    %{--</div>--}%
                                    <!--图片-->
                                    <div class="ml_row_img">
                                        <img id="imageLabel" width="120"
                                             src="${createLink(controller: "imageShow", action: "downloadThumbnail", params: [imgUrl: row?.image,width:120])}"/>
                                    </div>

                                    <div class="ml_row_txt">
                                        <label id="nameLabel" style="float: left;font-size: 14px;overflow: hidden;height: 30px;line-height: 30px;">
                                            <a target="_parent" title="${row?.name}"
                                               href="${createLink(controller: "infoShow", action: "foodShow", params: [id: row.foodId])}">${row?.name}</a>
                                        </label>
                                    </div>

                                    <div class="ml_row_txt">
                                        %{--<g:if test="${foodInfoInstance?.canTakeOut}">--}%
                                        %{--<a style="float: left;" href="#"--}%
                                        %{--restaurantId="${foodInfoInstance?.restaurantId}"--}%
                                        %{--foodId="${foodInfoInstance?.id}">--}%
                                        %{--加入外卖餐车</a>--}%
                                        %{--</g:if>--}%
                                        <input style="float: left;margin-top: 9px;height: 12px;margin-bottom: 9px;" type="checkBox" name="checkes" id="foodId"
                                               value="${row.id}">
                                        <a style="float: right;" href="#"
                                           addToOrder="true"
                                           restaurantId="${row?.restaurantId}"
                                           foodId="${row?.foodId}">
                                            加入订单</a>
                                        %{--<a style="float: right" href="#"--}%
                                           %{--onclick="foodAddToFavorite('${createLink(controller: "user",action: "addFavorite",params: [type:"food",foodId:foodInfoInstance?.id])}')">收藏</a>--}%
                                    </div>
                                </div>
                            </li>
                        </g:each>

                    </ul>
                    <white:paginate total="${total}" prev="&larr;" next="&rarr;" params="[type: 'food']" max="12"/>

                    <div class="form-actions">
                        <input type="button" id="all_select" class="btn btn-primary" value="全选"/>
                        <input type="button" id="cancel_select" class="btn btn-primary" value="取消选择"/>
                        <input type="button" id="delete" class="btn btn-primary" value="删除"/>
                    </div>

                </form>

            </g:if>
            <g:else>
                您还没有收藏菜谱!
            </g:else>
        </g:if>

    %{--</div>--}%
</div>
    </div>
    </div>
<script>
    $(document).ready(function () {

        //全选
        var all_select_bt = $("#all_select").bind("click", function () {
            $("input[name='checkes']").attr("checked", true);
        });

        //取消选择
        var cancel_select_bt = $("#cancel_select").bind("click", function () {
            $("input[name='checkes']").attr("checked", false);
        });

        //删除
        var delete_bt = $("#delete").bind("click", function () {
            var arrChk = $("input[name='checkes']:checked");
            if (arrChk.length == 0)
                return;

            $("form").submit();

        });
    });
</script>
</body>
</html>