<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 13-10-31
  Time: 下午8:08
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="lj.FormatUtil" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main_template"/>
    <title>注册饭店</title>
    <style type="text/css">
    .mc_main {
        width: 1000px;
        height: auto;
        margin: 0px 50px;
        background-color: #FFFFFF;
        float: left;
    }
    .mcm_top {
        margin-top: 20px;
    }
    .mcm_top_name {
        font-size: 20px;
        font-weight: bold;
        padding: 10px 0px 10px 20px;
    }

    .mcm_top_banner {
        width: 100%;
        height: 4px;
        background: url('${resource(dir:"images",file:"login_banner.gif")}');
        margin: 0px auto;
        margin-bottom: 30px;
    }

    /*.mcm_top {*/
        /*width: 960px;*/
        /*height: 80px;*/
        /*margin: 20px;*/
        /*margin-top: 0px;*/
        /*border-bottom: 4px solid #FF9833;*/
        /*text-indent: 1em;*/
        /*line-height: 80px;*/
        /*font-size: 20px;*/
        /*font-weight: bolder;*/
    /*}*/

    .mcm_content {
        width: 960px;
        height: auto;
        margin: 20px;
    }

    .mcmc_ssl {
        width: 960px;
        margin-top: 10px;
        margin-bottom: 10px;
    }

    .mcmcs_field {
        width: 320px;
        float: left;
    }

    .mcmcsf_input {
        width: 120px;
    }

    .mcmcs_field_middle {
        width: 160px;
        float: left;
    }

    .mcmcsf_input_middle {
        width: 80px;
    }

    .mcmcs_field_small {
        width: 80px;
        float: left;
    }

    .mcmcsf_input_small {
        width: 40px;
    }
    </style>
    <g:javascript src="shop/shopRegister.js"/>
    <link rel="stylesheet" href="${resource(dir: "js/timePicker", file: "timePicker.css")}" type="text/css"
          media="screen"/>
    <script type="text/javascript" src="${resource(dir: "js/timePicker", file: "jquery.timePicker.min.js")}"></script>
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

        //时间选择器
        $("#shopHoursBeginTime").timePicker({step: 15});
        $("#shopHoursBeginTime").change(function () {
            var timeV = $("#shopHoursBeginTime").val();
            if (timeV.length > 0) {
                $("#shopHoursBeginTime").val(timeV + ":00");
            }
        });
        $("#shopHoursEndTime").timePicker({step: 15});
        $("#shopHoursEndTime").change(function () {
            var timeV = $("#shopHoursEndTime").val();
            if (timeV.length > 0) {
                $("#shopHoursEndTime").val(timeV + ":00");
            }
        });
    });
    </script>
</head>

<body>

<div class="mc_main">
    <div class="mcm_top">
        <div class="mcm_top_name"><g:message code='restaurantInfo.register.label'/></div>

        <div class="mcm_top_banner"></div>
    </div>


    <g:if test="${errors != null && errors.size() > 0}">
        <div class="alert alert-error" STYLE="color: RED">
            <g:message error="${errors.get(0)}" message=""/>
        </div>
    </g:if>
    <g:if test="${flash.message}">
        <div class="alert alert-info">
            ${flash.message}
        </div>
    </g:if>

    <div class="span11">
        <form class="form-horizontal" method="POST" id="register_form" action="shopRegister">

            <div class="control-group">
                <label class="control-label" for="name"><g:message code="restaurantInfo.name.label"
                                                                   default="Name"/><span
                        class="required-indicator">*</span></label>

                <div class="controls">
                    <input type="text" style="width: 280px;" name="name" id="name" value="${restaurantInfoInstance?.name}"/>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="province"><g:message code="restaurantInfo.areaId.label"
                                                                       default="Area Id"/>
                    <span class="required-indicator">*</span>
                </label>

                <input name="areaId" id="areaId" type="hidden" value="0">
                <input type="hidden" name="province" id="provinceHidden" value="${restaurantInfoInstance?.province}">
                <div class="controls">
                    <select id="province" style="width: 180px;">
                        <g:each in="${provinceList}">
                            <option value="${it.id}">${it.province}</option>
                        </g:each>
                    </select>省
                    &nbsp;
                    <input type="hidden" name="city" id="cityHidden" value="${restaurantInfoInstance?.city}">
                    <select id="city" style="width: 180px;">
                        <option value="">请选择</option>
                    </select>市
                    &nbsp;
                    <input type="hidden" id="cityUrl"
                           value="${createLink(controller: "areaParam", action: "getCityList")}"/>
                    <input type="hidden" name="area" id="areaHidden" value="${restaurantInfoInstance?.area}">
                    <select id="area" style="width: 180px;">
                        <option value="">请选择</option>
                    </select>区
                    <input type="hidden" id="areaUrl"
                           value="${createLink(controller: "areaParam", action: "getAreaList")}"/>

                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="longitude">
                    <g:message code="restaurantInfo.street.label"
                                                                        default="Street"/>
                    <span class="required-indicator">*</span>
                </label>
                <div class="controls">
                    %{--<textarea class="input-xxlarge" id="street" name="street" rows="1"--}%
                              %{--placeholder="为了您的方便，请填写详细地址">${restaurantInfoInstance?.street}</textarea>--}%
                    <input type="text" class="input-xxlarge" id="street" name="street"
                              placeholder="为了您的方便，请填写详细地址" value="${restaurantInfoInstance?.street}" />
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="phone"><g:message code="restaurantInfo.phone.label"
                                                                    default="Phone"/><span
                        class="required-indicator">*</span></label>

                <div class="controls">
                    <input type="text" id="phone" name="phone" maxlength="16" required=""
                           value="${restaurantInfoInstance?.phone}"/>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="masterName"><g:message code="restaurantInfo.masterName.label"
                                                                         default="Master Name"/><span
                        class="required-indicator">*</span></label>

                <div class="controls">
                    <input type="text" id="masterName" name="masterName" value="${restaurantInfoInstance?.masterName}"/>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="shopHoursBeginTime"><g:message
                        code="restaurantInfo.shopHoursBeginTime.label" default="Shop Hours Begin Time"/><span
                        class="required-indicator">*</span></label>

                <div class="controls">
                    <input type="text" id="shopHoursBeginTime" name="shopHoursBeginTime"
                           value="${FormatUtil.timeFormat(restaurantInfoInstance?.shopHoursBeginTime)}"/>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="shopHoursEndTime"><g:message
                        code="restaurantInfo.shopHoursEndTime.label" default="Shop Hours End Time"/><span
                        class="required-indicator">*</span></label>

                <div class="controls">
                    <input type="text" id="shopHoursEndTime" name="shopHoursEndTime"
                           value="${FormatUtil.timeFormat(restaurantInfoInstance?.shopHoursEndTime)}"/>

                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="cuisineId"><g:message code="restaurantInfo.cuisineId.label"
                                                                        default="Cuisine Id"/><span
                        class="required-indicator">*</span></label>

                <div class="controls">
                    <input type="hidden" id="cuisineIdValue" value="${restaurantInfoInstance?.cuisineId}"/>
                    <select id="cuisineId" name="cuisineId">
                        <g:each in="${cuisineList}" var="cuisine" status="i">
                            <option value="${cuisine.id}">${cuisine.name}</option>
                        </g:each>
                    </select>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="longitude"><g:message code="restaurantInfo.longitude.label"
                                                                        default="Longitude"/></label>

                <div class="controls">
                    <input type="text" id="longitude" name="longitude"
                           value="${fieldValue(bean: restaurantInfoInstance, field: 'longitude')}"/>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="latitude"><g:message code="restaurantInfo.latitude.label"
                                                                       default="Latitude"/></label>

                <div class="controls">
                    <input type="text" id="latitude" name="latitude"
                           value="${fieldValue(bean: restaurantInfoInstance, field: 'latitude')}"/>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label"></label>

                <div class="controls">
                <input type="submit" value="${message(code: 'default.button.create.label', default: 'Create')}"
                       class="btn send_btn"/>
                    </div>
            </div>

        </form>

    </div>
</div>
</body>
</html>