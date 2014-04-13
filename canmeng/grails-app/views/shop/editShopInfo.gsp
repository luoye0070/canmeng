
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="lj.FormatUtil; lj.enumCustom.VerifyStatus; lj.enumCustom.ReCode" %>
<html>
<head>
    <meta name="layout" content="main_template"/>
  <title>修改饭店资料</title>
    <style type="text/css">
    .mc_main {
        width: 1000px;
        height: auto;
        margin: 0px 50px;
        background-color: #FFFFFF;
        float: left;
    }
    </style>
    <g:javascript src="shop/shopRegister.js"/>
</head>
<body>

<div class="mc_main">
    <div  class="span10" style="margin-left: 10px;margin-top: 10px;">
    <g:render template="../layouts/shopMenu" />

        <form class="form-horizontal" method="POST" action="editShopInfo" enctype="multipart/form-data">
            <fieldset>
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

                <div class="control-group">
                    <label class="control-label" for="name"> <g:message code="restaurantInfo.name.label" default="Name"/><span class="required-indicator">*</span></label>
                    <div class="controls">
                        <input type="text" name="name" id="name" value="${restaurantInfoInstance?.name}"/>
                    </div>
                </div>


                <div class="control-group">
                    <label class="control-label" for="image"> <g:message code="restaurantInfo.image.label" default="image"/><span class="required-indicator">*</span></label>
                    <div class="controls">
                        <g:if test="${restaurantInfoInstance?.image}"><img src="${createLink(controller: "imageShow",action: "downloadThumbnail",params: [imgUrl:restaurantInfoInstance?.image]) }"/></g:if>
                        <input type="file" id="image" name="imgfile"/>
                    </div>
                </div>


                <div class="control-group">
                    <label class="control-label" for="cuisineId">  <g:message code="restaurantInfo.cuisineId.label" default="Cuisine Id"/><span class="required-indicator">*</span></label>
                    <div class="controls">
                        <select id="cuisineId" name="cuisineId" value="${restaurantInfoInstance?.cuisineId}">
                            <g:each in="${cuisineList}" var="cuisine" status="i">
                                <option value="${cuisine.id}" ${restaurantInfoInstance?.cuisineId==cuisine.id?"selected=\"selected\"":""}>${cuisine.name}</option>
                            </g:each>
                        </select>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label" for="masterName"><g:message code="restaurantInfo.masterName.label" default="Master Name"/><span class="required-indicator">*</span></label>

                    <div class="controls">
                        <input type="text" id="masterName" name="masterName" value="${restaurantInfoInstance?.masterName}"/>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label" for="phone">  <g:message code="restaurantInfo.phone.label" default="Phone"/><span class="required-indicator">*</span></label>

                    <div class="controls">
                        <input type="text" id="phone" name="phone" maxlength="16" required="" value="${restaurantInfoInstance?.phone}"/>
                    </div>
                </div>


                <div class="control-group">
                    <label class="control-label" for="shopHoursBeginTime"> <g:message code="restaurantInfo.shopHoursBeginTime.label" default="Shop Hours Begin Time"/><span class="required-indicator">*</span></label>

                    <div class="controls">
                        <input type="text" id="shopHoursBeginTime" name="shopHoursBeginTime" value="${FormatUtil.timeFormat(restaurantInfoInstance?.shopHoursBeginTime)}"/>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label" for="shopHoursEndTime"> <g:message code="restaurantInfo.shopHoursEndTime.label" default="Shop Hours End Time"/><span class="required-indicator">*</span></label>

                    <div class="controls">
                        <input type="text" id="shopHoursEndTime" name="shopHoursEndTime" value="${FormatUtil.timeFormat(restaurantInfoInstance?.shopHoursEndTime)}"/>

                    </div>
                </div>





                <div class="control-group">
                    <label class="control-label" for="province"><g:message code="restaurantInfo.areaId.label" default="Area Id"/></label>
                    <input name="areaId" id="areaId" type="hidden" value="${restaurantInfoInstance?.areaId}">
                    <input type="hidden" name="province" id="provinceHidden"  value="${restaurantInfoInstance?.province}">
                    <div class="controls">
                        省 <span class="required-indicator">*</span>
                        <select id="province">
                            <g:each in="${provinceList}">
                                <option value="${it.id}">${it.province}</option>
                            </g:each>
                        </select>

                        <input type="hidden" name="city" id="cityHidden" value="${restaurantInfoInstance?.city}">
                        市<span class="required-indicator">*</span>
                        <select id="city" >
                            <option value="">请选择</option>
                        </select>
                        <input type="hidden" id="cityUrl"  value="${createLink(controller: "areaParam", action: "getCityList")}"/>
                        <input type="hidden" name="area" id="areaHidden" value="${restaurantInfoInstance?.area}">
                        区<span class="required-indicator">*</span>
                        <select id="area" >
                            <option value="">请选择</option>
                        </select>
                        <input type="hidden" id="areaUrl"     value="${createLink(controller: "areaParam", action: "getAreaList")}"/>

                    </div>
                </div>

                <div class="control-group">
                    <div class="controls">
                        街道地址
                        <textarea class="input-xlarge" id="street" name="street" rows="1"
                                  placeholder="为了您的方便，请填写详细地址">${restaurantInfoInstance?.street}</textarea>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label" for="packId">  <g:message code="restaurantInfo.packId.label" default="Freight" /><span class="required-indicator">*</span></label>

                    <div class="controls">
                        <select id="packId" name="packId" value="${restaurantInfoInstance?.packId}">
                            <g:each in="${packList}" var="pack" status="i">   ${restaurantInfoInstance?.packId}
                                <option value="${pack.id}" ${restaurantInfoInstance?.packId==pack.id?"selected=\"selected\"":""}>${pack.name}</option>
                            </g:each>
                        </select>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label" for="freight">  <g:message code="restaurantInfo.freight.label" default="Freight" /></label>

                    <div class="controls">
                        <input type="text" id="freight" name="freight" value="${fieldValue(bean: restaurantInfoInstance, field: 'freight')}"/>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label" for="freeFreight">  <g:message code="restaurantInfo.freeFreight.label" default="Freight" /></label>

                    <div class="controls">
                        <input type="text" id="freeFreight" name="freeFreight" value="${fieldValue(bean: restaurantInfoInstance, field: 'freeFreight')}"/>
                    </div>
                </div>




                <div class="control-group">
                    <label class="control-label" for="name"> <g:message code="restaurantInfo.enabled.label" default="Enabled" /></label>
                    <div class="controls">
                        <g:checkBox name="enabled" value="${restaurantInfoInstance?.enabled}" />
                    </div>
                </div>


                <div class="control-group">
                    <label class="control-label" for="longitude">  <g:message code="restaurantInfo.longitude.label" default="Longitude"/></label>

                    <div class="controls">
                        <input type="text" id="longitude" name="longitude"
                               value="${fieldValue(bean: restaurantInfoInstance, field: 'longitude')}"/>
                    </div>
                </div>


                <div class="control-group">
                    <label class="control-label" for="latitude"> <g:message code="restaurantInfo.latitude.label" default="Latitude"/></label>

                    <div class="controls">
                        <input type="text" id="latitude" name="latitude"
                               value="${fieldValue(bean: restaurantInfoInstance, field: 'latitude')}"/>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label" for="deliverRange"> <g:message code="restaurantInfo.deliverRange.label" default="deliverRange"/></label>

                    <div class="controls">
                        <input type="text" id="deliverRange" name="deliverRange"
                               value="${fieldValue(bean: restaurantInfoInstance, field: 'deliverRange')}"/>
                    </div>
                </div>


                <div class="control-group">
                    <label class="control-label" for="averageConsume"> <g:message code="restaurantInfo.averageConsume.label" default="averageConsume"/></label>

                    <div class="controls">
                        <input type="text" id="averageConsume" name="averageConsume"
                               value="${fieldValue(bean: restaurantInfoInstance, field: 'averageConsume')}"/>
                    </div>
                </div>

            <div class="control-group">
                <label class="control-label" for="remark"> <g:message code="restaurantInfo.remark.label" default="remark"/></label>

                <div class="controls">
                    <textarea class="input-xlarge" id="remark" name="remark" rows="1" >${restaurantInfoInstance?.remark}</textarea>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="description"> <g:message code="restaurantInfo.description.label" default="description"/></label>

                <div class="controls">
                    <textarea class="input-xlarge" id="description" name="description" rows="1" >${restaurantInfoInstance?.description}</textarea>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label"> <g:message code="restaurantInfo.imageSpaceSize.label" default="imageSpaceSize"/></label>

                <div class="controls">
                    <label>${FormatUtil.byteToKB(restaurantInfoInstance?.imageSpaceSize?:0)}</label>
                </div>
            </div>


            <div class="control-group">
                <label class="control-label"> <g:message code="restaurantInfo.imageSpaceUsedSize.label" default="imageSpaceUsedSize"/></label>

                <div class="controls">
                   <label>${FormatUtil.byteToKB(restaurantInfoInstance?.imageSpaceUsedSize?:0)}</label>
                </div>
            </div>

            <input id="verifyStatusHidden" type="hidden" name="verifyStatus" value="${restaurantInfoInstance?.verifyStatus}"/>
            <div class="control-group">
                <label class="control-label" for="verifyStatus"> <g:message code="restaurantInfo.verifyStatus.label" default="verifyStatus"/><span class="required-indicator">*</span></label>

                <div class="controls">
                    <label id="verifyStatus">${VerifyStatus.getLabel(restaurantInfoInstance?.verifyStatus?:0)} <input type="button" class="btn send_btn" value="重新申请审核" id="verifyStatusButton"/></label>
                </div>
            </div>

            <div class="form-actions">
                <g:submitButton name="create" class="btn send_btn" value="${message(code: 'default.button.update.label', default: 'Update')}" />
            </div>

            </fieldset>
        </form>


    </div>


</div>

<div>

</div>

</form>

<script>

    function setSelectText(element, text) {

        var count = $("#" + element + " option").length;

        for (var i = 0; i < count; i++) {
            if ($("#" + element).get(0).options[i].text == text) {
                $("#" + element).get(0).options[i].selected = true;
                break;
            }
        }
    }

    $(document).ready(function () {
        setSelectText("province",$("#provinceHidden").val());
        //获取市

        var url=$("#cityUrl").val()+"?provinceId="+$("#province").val()+"&rand="+Math.random();;
        $.get(url,function(data,status){
            $("#city").empty();
            if(data){
                var array=new Array()
                for(i=0;i<data.length;i++){
                    if(data[i].city==$("#cityHidden").val()){
                        array.push("<option selected='true' value='"+data[i].id+"'>"+data[i].city+"</option>");
                    }else{
                        array.push("<option value='"+data[i].id+"'>"+data[i].city+"</option>");
                    }
                }
                $("#city").append(array.join())


                //请求区域
                var url=$("#areaUrl").val()+"?cityId="+$("#city").val()+"&rand="+Math.random();;
                $.get(url,function(data,status){
                    $("#area").empty();
                    if(data){
                        var array=new Array();
                        for(i=0;i<data.length;i++){
                            if(data[i].area==$("#areaHidden").val()){
                                array.push("<option selected='true' value='"+data[i].id+"'>"+data[i].area+"</option>");
                            }else{
                                array.push("<option value='"+data[i].id+"'>"+data[i].area+"</option>");
                            }
                        }
                        $("#area").append(array.join())
                    }
                });
            }
        });


    });
</script>
</body>
</html>