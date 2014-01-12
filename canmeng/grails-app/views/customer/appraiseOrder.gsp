<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 13-11-26
  Time: 下午10:00
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="lj.enumCustom.ReserveType; lj.enumCustom.AppraiseType; lj.enumCustom.OrderValid; lj.enumCustom.OrderStatus; lj.FormatUtil" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main_template">
    <title>订单评价</title>
    <style type="text/css">
    .mc_main {
        width: 1000px;
        height: auto;
        margin: 0px 50px;
        background-color: #FFFFFF;
        float: left;
    }

    .mcm_top {
        width: 960px;
        height: 80px;
        margin: 20px;
        margin-top: 0px;
        border-bottom: 4px solid #FF9833;
        text-indent: 1em;
        line-height: 80px;
        font-size: 20px;
        font-weight: bolder;
    }

    .mcm_content {
        width: 960px;
        height: auto;
        margin: 20px;
    }

    .mcmc_detail {
        width: 900px;
        height: auto;
        margin-left: 30px;
        margin-right: 30px;
        float: left;
        border-bottom: 2px solid #DDDDDD;
        margin-bottom: 20px;
    }

    .mcmcd_title {
        width: 900px;
        height: 30px;
        line-height: 30px;
        float: left;
        overflow: hidden;
    }

    .mcmcdt_ico {
        border-left: 7px solid #FF9833;
        border-right: 7px solid #FFF;
        border-top: 7px solid #FFF;
        border-bottom: 7px solid #FFF;
        margin-top: 8px;
        margin-bottom: 8px;
        width: 0;
        height: 0;
        float: left;
    }

    .mcmcdt_info {
        color: #FF9833;
        float: left;
        font-size: 15px;
    }

    .mcmcd_item {
        width: 300px;
        height: 30px;
        line-height: 30px;
        float: left;
        overflow: hidden;
    }

    .mcmcdi_label {
        width: 100px;
        height: 30px;
        float: left;
        color: #C2BFBF;
        overflow: hidden;
    }

    .mcmcdi_info {
        width: 200px;
        height: 30px;
        float: left;
        overflow: hidden;
    }

    </style>
</head>

<body>
<div class="mc_main">
<div class="mcm_top">
    订单${params.orderId}-评价
</div>

<div class="mcm_content">

<!--提示消息-->
<g:if test="${flash.error}">
    <div class="alert alert-error">
        ${flash.error}
    </div>
</g:if>
<g:if test="${flash.message}">
    <div class="alert alert-info">
        ${flash.message}
    </div>
</g:if>

<!--订单信息-->
<div class="mcmc_detail">
<!--订单信息-->
<div class="mcmcd_title">
    <div class="mcmcdt_ico"></div>

    <div class="mcmcdt_info">订单信息</div>
</div>
<g:if test="${orderInfoInstance?.id}">

    <div class="mcmcd_item">
        <div class="mcmcdi_label">
            <g:message code="orderInfo.id.label" default="id"/>
        </div>

        <div class="mcmcdi_info" style="color: #FE4B1E;">
            <g:fieldValue bean="${orderInfoInstance}" field="id"/>
        </div>
    </div>
</g:if>

<g:if test="${orderInfoInstance?.restaurantId}">
    <div class="mcmcd_item">
        <div class="mcmcdi_label">
            <g:message code="orderInfo.restaurantId.label" default="Restaurant Id"/>
        </div>

        <div class="mcmcdi_info" style="color: #FE4B1E;">
            <g:fieldValue bean="${orderInfoInstance}" field="restaurantId"/>
        </div>
    </div>
</g:if>

<g:if test="${orderInfoInstance?.tableId}">
    <div class="mcmcd_item">
        <div class="mcmcdi_label">
            <g:message code="orderInfo.tableId.label" default="Table Id"/>
        </div>

        <div class="mcmcdi_info" style="color: #FE4B1E;">
            <g:fieldValue bean="${orderInfoInstance}" field="tableId"/>
        </div>
    </div>
</g:if>

<g:if test="${orderInfoInstance?.partakeCode}">
    <div class="mcmcd_item">
        <div class="mcmcdi_label">
            <g:message code="orderInfo.partakeCode.label" default="Partake Code"/>
        </div>

        <div class="mcmcdi_info" style="color: #FE4B1E;">
            <g:fieldValue bean="${orderInfoInstance}" field="partakeCode"/>
        </div>
    </div>
</g:if>

<g:if test="${orderInfoInstance?.restaurantName}">
    <div class="mcmcd_item">
        <div class="mcmcdi_label">
            <g:message code="orderInfo.restaurantName.label" default="restaurantName"/>
        </div>

        <div class="mcmcdi_info">
            <g:fieldValue bean="${orderInfoInstance}" field="restaurantName"/>
        </div>
    </div>
</g:if>

<g:if test="${orderInfoInstance?.tableName}">
    <div class="mcmcd_item">
        <div class="mcmcdi_label">
            <g:message code="orderInfo.tableName.label" default="tableName"/>
        </div>

        <div class="mcmcdi_info">
            <g:fieldValue bean="${orderInfoInstance}" field="tableName"/>
        </div>
    </div>
</g:if>

<g:if test="${orderInfoInstance?.userId}">
    <div class="mcmcd_item">
        <div class="mcmcdi_label">
            <g:message code="orderInfo.userId.label" default="User Id"/>
        </div>

        <div class="mcmcdi_info">
            <g:fieldValue bean="${orderInfoInstance}" field="userId"/>
        </div>
    </div>
</g:if>

<g:if test="${orderInfoInstance?.date}">
    <div class="mcmcd_item">
        <div class="mcmcdi_label">
            <g:message code="orderInfo.date.label" default="Date"/>
        </div>

        <div class="mcmcdi_info">
            ${FormatUtil.dateFormat(orderInfoInstance?.date)}
        </div>
    </div>
</g:if>

<g:if test="${orderInfoInstance?.time}">
    <div class="mcmcd_item">
        <div class="mcmcdi_label">
            <g:message code="orderInfo.time.label" default="Time"/>
        </div>

        <div class="mcmcdi_info">
            ${FormatUtil.timeFormat(orderInfoInstance?.time)}
        </div>
    </div>
</g:if>

<g:if test="${orderInfoInstance?.reserveType}">
    <div class="mcmcd_item">
        <div class="mcmcdi_label">
            <g:message code="orderInfo.reserveType.label" default="Reserve Type"/>
        </div>

        <div class="mcmcdi_info">
            ${ReserveType.getLabel(orderInfoInstance?.reserveType)}
        </div>
    </div>
</g:if>

<g:if test="${orderInfoInstance?.status}">
    <div class="mcmcd_item">
        <div class="mcmcdi_label">
            <g:message code="orderInfo.status.label" default="Status"/>
        </div>

        <div class="mcmcdi_info">
            ${OrderStatus.getLable(orderInfoInstance?.status)}
        </div>
    </div>
</g:if>

<g:if test="${orderInfoInstance?.valid != null}">
    <div class="mcmcd_item">
        <div class="mcmcdi_label">
            <g:message code="orderInfo.valid.label" default="Valid"/>
        </div>

        <div class="mcmcdi_info">
            ${OrderValid.getLable(orderInfoInstance?.valid)}
        </div>
    </div>
</g:if>

<g:if test="${orderInfoInstance?.cancelReason}">
    <div class="mcmcd_item">
        <div class="mcmcdi_label">
            <g:message code="orderInfo.cancelReason.label" default="Cancel Reason"/>
        </div>

        <div class="mcmcdi_info">
            <g:fieldValue bean="${orderInfoInstance}" field="cancelReason"/>
        </div>
    </div>
</g:if>

<g:if test="${orderInfoInstance?.addressId}">
    <div class="mcmcd_item">
        <div class="mcmcdi_label">
            <g:message code="orderInfo.addressId.label" default="Address Id"/>
        </div>

        <div class="mcmcdi_info">
            <g:fieldValue bean="${orderInfoInstance}" field="addressId"/>
        </div>
    </div>
</g:if>

<g:if test="${orderInfoInstance?.waiterId}">
    <div class="mcmcd_item">
        <div class="mcmcdi_label">
            <g:message code="orderInfo.waiterId.label" default="Waiter Id"/>
        </div>

        <div class="mcmcdi_info">
            <g:fieldValue bean="${orderInfoInstance}" field="waiterId"/>
        </div>
    </div>
</g:if>

%{--<g:if test="${orderInfoInstance?.listenWaiterId}">--}%
%{--<div class="mcmcd_item">--}%
%{--<div class="mcmcdi_label">--}%
%{--<g:message code="orderInfo.listenWaiterId.label" default="Listen Waiter Id"/>--}%
%{--</div>--}%

%{--<div class="mcmcdi_info">--}%
%{--<g:fieldValue bean="${orderInfoInstance}" field="listenWaiterId"/>--}%
%{--</div>--}%
%{--</div>--}%
%{--</g:if>--}%

<g:if test="${orderInfoInstance?.cashierId}">
    <div class="mcmcd_item">
        <div class="mcmcdi_label">
            <g:message code="orderInfo.cashierId.label" default="Cashier Id"/>
        </div>

        <div class="mcmcdi_info">
            <g:fieldValue bean="${orderInfoInstance}" field="cashierId"/>
        </div>
    </div>
</g:if>

<g:if test="${orderInfoInstance?.remark}">
    <div class="mcmcd_item">
        <div class="mcmcdi_label">
            <g:message code="orderInfo.remark.label" default="Remark"/>
        </div>

        <div class="mcmcdi_info">
            <g:fieldValue bean="${orderInfoInstance}" field="remark"/>
        </div>
    </div>
</g:if>

<g:if test="${orderInfoInstance?.numInRestaurant}">
    <div class="mcmcd_item">
        <div class="mcmcdi_label">
            <g:message code="orderInfo.numInRestaurant.label" default="Num In Restaurant"/>
        </div>

        <div class="mcmcdi_info">
            <g:fieldValue bean="${orderInfoInstance}" field="numInRestaurant"/>
        </div>
    </div>
</g:if>

%{--<g:if test="${orderInfoInstance?.orderNum}">--}%
%{--<div class="mcmcd_item">--}%
%{--<div class="mcmcdi_label">--}%
%{--<g:message code="orderInfo.orderNum.label" default="Order Num"/>--}%
%{--</div>--}%

%{--<div class="mcmcdi_info">--}%
%{--<g:fieldValue bean="${orderInfoInstance}" field="orderNum"/>--}%
%{--</div>--}%
%{--</div>--}%
%{--</g:if>--}%

<g:if test="${orderInfoInstance?.totalAccount}">
    <div class="mcmcd_item">
        <div class="mcmcdi_label">
            <g:message code="orderInfo.totalAccount.label" default="Total Account"/>
        </div>

        <div class="mcmcdi_info">
            <g:fieldValue bean="${orderInfoInstance}" field="totalAccount"/>
        </div>
    </div>
</g:if>

<g:if test="${orderInfoInstance?.postage}">
    <div class="mcmcd_item">
        <div class="mcmcdi_label">
            <g:message code="orderInfo.postage.label" default="Postage"/>
        </div>

        <div class="mcmcdi_info">
            <g:fieldValue bean="${orderInfoInstance}" field="postage"/>
        </div>
    </div>
</g:if>

<g:if test="${orderInfoInstance?.realAccount}">
    <div class="mcmcd_item">
        <div class="mcmcdi_label">
            <g:message code="orderInfo.realAccount.label" default="Real Account"/>
        </div>

        <div class="mcmcdi_info">
            <g:fieldValue bean="${orderInfoInstance}" field="realAccount"/>
        </div>
    </div>
</g:if>

<div class="mcmcd_item" style="width: 900px;">
    <a href="${params.backUrl ?: createLink(controller: "customer", action: "orderList")}"
       class="btn btn-link">返回</a>
</div>

</div>

<div class="mcmc_detail">
    <!--订单对应的点菜信息-->
    <div class="mcmcd_title">
        <div class="mcmcdt_ico"></div>

        <div class="mcmcdt_info">评价</div>
    </div>
    <!--评价表单-->
    <form action="${createLink(controller: "customer", action: "appraiseOrder")}" method="post" class="form-horizontal offset1">

        <input type="hidden" name="orderId" value="${orderInfoInstance?.id}"/>
        <input type="hidden" name="restaurantId" value="${orderInfoInstance?.restaurantId}"/>

        <div class="control-group">
            <label for="type" class="control-label">
                <g:message code="appraiseInfo.type.label" default="Type"/>
                <span class="required-indicator">*</span>
            </label>

            <div class="controls">
                <g:radioGroupCustom radioGroup="${AppraiseType.appraiseTypes}" name="type" required=""
                                    value="${appraiseInfoInstance?.type}"/>
            </div>
        </div>

        <div class="control-group">
            <label for="hygienicQuality" class="control-label">
                <g:message code="appraiseInfo.hygienicQuality.label" default="Hygienic Quality"/>
                <span class="required-indicator">*</span>
            </label>

            <div class="controls">
                <g:field name="hygienicQuality" type="number" min="0" max="10" value="${appraiseInfoInstance?.hygienicQuality}"
                         required=""/>
            </div>
        </div>

        <div class="control-group">
            <label for="serviceAttitude" class="control-label">
                <g:message code="appraiseInfo.serviceAttitude.label" default="Service Attitude"/>
                <span class="required-indicator">*</span>
            </label>

            <div class="controls">
                <g:field name="serviceAttitude" type="number" min="0" max="10" value="${appraiseInfoInstance?.serviceAttitude}"
                         required=""/>
            </div>
        </div>

        <g:if test="${!orderInfoInstance?.tableId}">
            <div class="control-group">
                <label for="deliverySpeed" class="control-label">
                    <g:message code="appraiseInfo.deliverySpeed.label" default="Delivery Speed"/>
                    <span class="required-indicator">*</span>
                </label>

                <div class="controls">
                    <g:field name="deliverySpeed" type="number" min="0" max="10" value="${appraiseInfoInstance?.deliverySpeed}"
                             required=""/>
                </div>
            </div>
        </g:if>

        <div class="control-group">
            <label for="taste" class="control-label">
                <g:message code="appraiseInfo.taste.label" default="Taste"/>
                <span class="required-indicator">*</span>
            </label>

            <div class="controls">
                <g:field name="taste" type="number" min="0" max="10" value="${appraiseInfoInstance?.taste}" required=""/>
            </div>
        </div>

        <div class="control-group">
            <label for="whole" class="control-label">
                <g:message code="appraiseInfo.whole.label" default="Whole"/>
                <span class="required-indicator">*</span>
            </label>

            <div class="controls">
                <g:field name="whole" type="number" min="0" max="10" value="${appraiseInfoInstance?.whole}" required=""/>
            </div>
        </div>

        <div class="control-group">
            <label for="content" class="control-label">
                <g:message code="appraiseInfo.content.label" default="Content"/>

            </label>

            <div class="controls">
                <textarea id="content" name="content">${appraiseInfoInstance?.content}</textarea>
            </div>
        </div>

        %{--<div class="fieldcontain ${hasErrors(bean: appraiseInfoInstance, field: 'appraiseTime', 'error')} required">--}%
        %{--<label for="appraiseTime">--}%
        %{--<g:message code="appraiseInfo.appraiseTime.label" default="Appraise Time" />--}%
        %{--<span class="required-indicator">*</span>--}%
        %{--</label>--}%
        %{--<g:datePicker name="appraiseTime" precision="day"  value="${appraiseInfoInstance?.appraiseTime}"  />--}%
        %{--</div>--}%

        %{--<div class="fieldcontain ${hasErrors(bean: appraiseInfoInstance, field: 'userId', 'error')} required">--}%
        %{--<label for="userId">--}%
        %{--<g:message code="appraiseInfo.userId.label" default="User Id" />--}%
        %{--<span class="required-indicator">*</span>--}%
        %{--</label>--}%
        %{--<g:field name="userId" type="number" min="0" value="${appraiseInfoInstance?.userId}" required=""/>--}%
        %{--</div>--}%

        <div class="control-group">
            <label for="isAnonymity" class="control-label">
                <g:message code="appraiseInfo.isAnonymity.label" default="Is Anonymity"/>

            </label>

            <div class="controls">
                <g:checkBox name="isAnonymity" value="${appraiseInfoInstance?.isAnonymity}"/>
            </div>
        </div>
        %{--<div class="fieldcontain ${hasErrors(bean: appraiseInfoInstance, field: 'userName', 'error')} required">--}%
        %{--<label for="userName">--}%
        %{--<g:message code="appraiseInfo.userName.label" default="User Name" />--}%
        %{--<span class="required-indicator">*</span>--}%
        %{--</label>--}%
        %{--<g:textField name="userName" maxlength="32" required="" value="${appraiseInfoInstance?.userName}"/>--}%
        %{--</div>--}%

        <div class="control-group">
            <label for="isAnonymity" class="control-label">

            </label>

            <div class="controls">
                <g:submitButton name="create" class="btn btn-primary"
                                value="${message(code: 'default.button.submit.label', default: 'Create')}"/>
            </div>
        </div>
    </form>
</div>

</div>
</div>
</body>
</html>