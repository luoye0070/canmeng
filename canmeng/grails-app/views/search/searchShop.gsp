<%@ page import="lj.FormatUtil" contentType="text/html;charset=UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta name="layout" content="main1"/>
    <title>饭店搜索</title>
    <g:javascript src="jquery-1.8.2.min.js"></g:javascript>
    <g:javascript src="shop/shopRegister.js"/>
    <g:javascript src="shop/shopSome.js"/>

    <link rel="stylesheet" href="${resource(dir:"js/timePicker",file:"timePicker.css")}" type="text/css" media="screen" />
    <script type="text/javascript" src="${resource(dir:"js/timePicker",file:"jquery.timePicker.min.js")}"></script>

    <style type="text/css">
    body {
        background-color: #f7f3e7;
    }

    .main {
        width: 1100px;
        height: auto;
        margin: 0px auto;
        background-color: #f7f3e7;
    }

    .m_ssl {
        margin-top: 10px;
        margin-bottom: 10px;
    }

    .ms_field {
        width: 180px;
        float: left;
    }

    .ms_field_small {
        width: 80px;
        float: left;
    }

    .msf_label {
        width: auto;
    }

    .msf_input {
        width: 120px;
    }

    .msf_input_small {
        width: 40px;
    }

    .m_list {
        width: 1100px;
        height: auto;
    }
    .ml_item{

    }
    .mli_top{
        height: 130px;
        /*float: left;*/
        margin: 5px 5px;
        overflow: hidden;
    }
    .mlit_left{
        width: 100px;
        height: 128px;
        float: left;
        overflow: hidden;
        border: 1px solid #9c9a9a;
        text-align: center;
    }
    .mlit_left img{
        margin: auto 0px;
    }
    .mlit_right{
        float: left;
        margin-left: 10px;
        overflow: hidden;
    }
    .mlitr_title{
        height: 30px;
        width: 400px;
        line-height: 30px;
        font-size: 20px;
        font-weight: 900;
        overflow: hidden;
    }
    .mlitr_title a{
        overflow: hidden;
        text-decoration: none;
        color: #000000;
    }
    .mlitr_title a:hover{
        overflow: hidden;
        text-decoration: none;
        color: #ff9966;
    }
    .mlitr_info{
        height: 25px;
        width: 400px;
        line-height: 25px;
        overflow: hidden;
    }
    .mlitri_label{
        width: 40px;
        float: left;
        font-size: 12px;
        line-height: 25px;
        color: #c2bfbf;
    }
    .mlitri_info{
        width: 360px;
        float: left;
        line-height: 25px;
        overflow: hidden;
        font-size: 14px;
    }

    .mli_bottom{
        height: 40px;
        background-color: #EEEEEE;
        border-bottom: 1px solid #AAAAAA;
        line-height: 40px;
    }
    .mlib_bt{
        height: 40px;
        margin: 0px 10px;
        line-height: 40px;
        float: left;
    }
    </style>
    <script type="text/javascript">
        $(function () {
            //加载区域选择列表;
            var cityId = $("#cityId").val();
            //异步查询城市对应的区域
            var rand = Math.random();
            var url = $("#areaUrl").val() + "?cityId=" + cityId + "&rand=" + rand;
            $.get(url, function (data, status) {
                //alert("Data: " + data.length + "\nStatus: " + status);
                $("#areaId").html("");
                if (data) {
                    var areaId = $("#areaIdValue").val();
                    for (i = 0; i < data.length; i++) {
                        if (data[i].id == areaId)
                            $("#areaId").append("<option selected=selected value='" + data[i].id + "'>" + data[i].area + "</option>");
                        else
                            $("#areaId").append("<option value='" + data[i].id + "'>" + data[i].area + "</option>");
                    }
                }
            });

            //根据val设置被选择项
            var cuisineId = $("#cuisineIdValue").val();
            //alert("cuisineId"+cuisineId);
            $.each($("#cuisineId").children("option"), function (i, obj) {
                //alert($(obj).val());
                if ($(obj).val() == cuisineId) {
                    //alert($(obj).val());
                    $(obj).attr("selected", "selected");
                }
                else {
                    $(obj).removeAttr("selected");
                }
            });

            //添加一个时间选择器
            $("#shopHours").timePicker();
            $("#shopHours").change(function(){
                var timeV=$("#shopHours").val();
                if(timeV.length>0){
                    $("#shopHours").val(timeV+":00");
                }
            });
        });
    </script>
</head>

<body>
<div class="main">
    <g:if test="${err}">
        <div class="alert alert-error">
            <g:message error="${err}" message=""/>
        </div>
    </g:if>
    <g:if test="${msg}">
        <div class="alert alert-info">
            ${msg}
        </div>
    </g:if>

<!--搜索栏-->
    <div class="m_ssl">
        <form class="well form-inline" action="searchShop" method="post">
            <!--地区条件-->
            <div class="ms_field">
                <label for="areaId">
                    区域：
                </label>
                <input type="hidden" name="cityId" id="cityId" value="${cityIdAndName?.cityId ?: "1"}">
                <input type="hidden" id="areaIdValue" value="${params?.areaId ?: "0"}">
                <select id="areaId" name="areaId" class="msf_input">
                    <option value="0">请选择</option>
                </select>
                <input type="hidden" id="areaUrl"
                       value="${createLink(controller: "areaParam", action: "getAreaList")}"/>
            </div>
            <!--菜系-->
            <div class="ms_field">
                <label for="cuisineId">
                    菜系：
                </label>
                <input type="hidden" id="cuisineIdValue" value="${params?.cuisineId ?: "0"}"/>
                <select id="cuisineId" name="cuisineId" class="msf_input">
                    <g:each in="${cuisineList}" var="cuisine" status="i">
                        <option value="${cuisine.id}">${cuisine.name}</option>
                    </g:each>
                </select>
            </div>

            <!--营业时间-->
            <div class="ms_field" style="width: 220px;">
                <label for="cuisineId">
                    营业时间：
                </label>
                <input name="shopHours" type="text" id="shopHours" class="msf_input" value="${params?.shopHours}"/>
            </div>
            <!--人均消费水平-->
            <!--
            <div class="ms_field" style="width: 160px;">
                <label>
                    消费水平：
                </label>
                ￥<input name="averageConsume" type="text" class="msf_input_small" value="${params?.averageConsume}"/>
            </div>
            -->
            <!--饭店名称条件-->
            <div class="ms_field">
                <input name="name" type="text" class="msf_input" style="width: 160px;"
                       placeholder="请输入餐厅名" value="${params?.name}"/>
            </div>

            <div class="ms_field_small">
                <input type="submit" value="${message(code: 'default.button.search.label', default: 'Create')}"
                       class="btn send_btn"/>
            </div>
        </form>
    </div>


    <!--店铺列表-->
    <div class="m_list">
        <g:if test="${restaurantList}">
            <ul class="thumbnails">
                <g:each in="${restaurantList}" status="i" var="restaurantInfoInstance">
                    <li class="span7">
                        <div class="thumbnail" style="background-color: #ffffff">
                            <div class="mli_top">
                                <!--店铺图片-->
                                <div class="mlit_left">
                                    <img id="imageLabel"
                                     src="${createLink(controller: "imageShow", action: "downloadThumbnail", params: [imgUrl: restaurantInfoInstance?.image,height:148])}"/>
                                </div>
                                <div class="mlit_right">
                                    <!--店铺名称-->
                                    <div class="mlitr_title">
                                        <a target="_parent" title="${restaurantInfoInstance?.name}"
                                           href="${createLink(controller: "infoShow", action: "shopShow", params: [id: restaurantInfoInstance?.id], max: 12)}"
                                           style="">${restaurantInfoInstance?.name}</a>
                                    </div>
                                    <!--店铺联系电话-->
                                    <div class="mlitr_info">
                                        <label class="mlitri_label">电话：</label>
                                        <label class="mlitri_info" style="color: #fc6408">
                                            ${restaurantInfoInstance?.phone}
                                        </label>
                                    </div>
                                    <!--店铺地址-->
                                    <div class="mlitr_info">
                                        <label class="mlitri_label">地址：</label>
                                        <label class="mlitri_info" title="${restaurantInfoInstance?.province} ${restaurantInfoInstance?.city} ${restaurantInfoInstance?.area} ${restaurantInfoInstance?.street}">
                                            ${restaurantInfoInstance?.province}
                                            ${restaurantInfoInstance?.city}
                                            ${restaurantInfoInstance?.area}
                                            ${restaurantInfoInstance?.street}
                                        </label>
                                    </div>
                                    <!--营业时间-->
                                    <div class="mlitr_info">
                                        <label class="mlitri_label" style="width: 60px;">营业时间：</label>
                                        <label class="mlitri_info" style="width: 340px;">
                                            ${FormatUtil.timeFormat(restaurantInfoInstance?.shopHoursBeginTime)}-
                                            ${FormatUtil.timeFormat(restaurantInfoInstance?.shopHoursEndTime)}
                                        </label>
                                    </div>
                                    <!--消费水平-->
                                    <div class="mlitr_info">
                                        <label class="mlitri_label" style="width: 60px;">消费水平：</label>
                                        <label class="mlitri_info" style="width: 340px;">
                                            <g:if test="${restaurantInfoInstance?.averageConsume}">
                                                ￥${restaurantInfoInstance?.averageConsume}
                                            </g:if>
                                            <g:else>
                                                暂时没有统计
                                            </g:else>
                                        </label>
                                    </div>
                                </div>
                            </div>

                            <div class="mli_bottom">
                                <div class="mlib_bt">
                                <a href="#"
                                   onclick="shopAddToFavorite('${createLink(controller: "user",action: "addFavorite",params: [type:"shop",restaurantId:restaurantInfoInstance?.id])}')"
                                   style="color:#000000">收藏饭店</a>
                                </div>
                                <!--<div class="mlib_bt">
                                <a target="_parent"
                                   href="${createLink(controller: "infoShow", action: "shopShow", params: [id: restaurantInfoInstance?.id], max: 12)}"
                                   style="color:#000000">进入饭店</a>
                                </div>-->
                                <div class="mlib_bt" style="float: right">
                                <a target="_parent" class="btn send_btn"
                                   href="${createLink(controller: "infoShow", action: "tablesShow", params: [restaurantId: restaurantInfoInstance?.id,restaurantName:restaurantInfoInstance?.name])}"
                                   style="color:#ffffff;">桌位预定</a>
                                </div>
                            </div>
                        </div>
                    </li>
                </g:each>
            </ul>
            <white:paginate total="${totalCount ?: 0}" prev="&larr;" next="&rarr;" params="${params}"/>
        </g:if>
        <g:else>
            <div style="margin: 0px auto;">
                <label style="text-align: center">没有搜索到记录</label>
            </div>
        </g:else>
    </div>
</div>
</body>
</html>