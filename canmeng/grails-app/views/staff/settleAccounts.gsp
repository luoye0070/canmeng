<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 13-11-25
  Time: 下午11:59
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="lj.enumCustom.OrderValid; lj.enumCustom.OrderStatus; lj.enumCustom.ReserveType; lj.FormatUtil" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
  <title>结账</title>
</head>
<body>
<!--提示消息-->
<div class="alert">
    <div class="alert-error" STYLE="color: RED">
        <g:if test="${flash.error}">
            ${flash.error}
        </g:if>
    </div>
    <div class="alert-message" STYLE="color: green">
        <g:if test="${flash.message}">
            ${flash.message}
        </g:if>
    </div>
</div>

<!--订单信息-->
<div id="show-orderInfo" class="content scaffold-show" role="main">
<h1><g:message code="default.show.label" args="[entityName]" /></h1>
<g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
</g:if>
<ol class="property-list orderInfo">

    <g:if test="${orderInfo?.id}">
        <li class="fieldcontain">
            <span id="id-label" class="property-label"><g:message code="orderInfo.id.label" default="id" /></span>

            <span class="property-value" aria-labelledby="id-label"><g:fieldValue bean="${orderInfo}" field="id"/></span>

        </li>
    </g:if>

    <g:if test="${orderInfo?.restaurantId}">
        <li class="fieldcontain">
            <span id="restaurantId-label" class="property-label"><g:message code="orderInfo.restaurantId.label" default="Restaurant Id" /></span>

            <span class="property-value" aria-labelledby="restaurantId-label"><g:fieldValue bean="${orderInfo}" field="restaurantId"/></span>

        </li>
    </g:if>

    <g:if test="${orderInfo?.userId}">
        <li class="fieldcontain">
            <span id="userId-label" class="property-label"><g:message code="orderInfo.userId.label" default="User Id" /></span>

            <span class="property-value" aria-labelledby="userId-label"><g:fieldValue bean="${orderInfo}" field="userId"/></span>

        </li>
    </g:if>

    <g:if test="${orderInfo?.tableId}">
        <li class="fieldcontain">
            <span id="tableId-label" class="property-label"><g:message code="orderInfo.tableId.label" default="Table Id" /></span>

            <span class="property-value" aria-labelledby="tableId-label"><g:fieldValue bean="${orderInfo}" field="tableId"/></span>

        </li>
    </g:if>

    <g:if test="${orderInfo?.date}">
        <li class="fieldcontain">
            <span id="date-label" class="property-label"><g:message code="orderInfo.date.label" default="Date" /></span>

            <span class="property-value" aria-labelledby="date-label">${FormatUtil.dateFormat(orderInfo?.date)}</span>

        </li>
    </g:if>

    <g:if test="${orderInfo?.time}">
        <li class="fieldcontain">
            <span id="time-label" class="property-label"><g:message code="orderInfo.time.label" default="Time" /></span>

            <span class="property-value" aria-labelledby="time-label">${FormatUtil.timeFormat(orderInfo?.time)} </span>

        </li>
    </g:if>

    <g:if test="${orderInfo?.reserveType}">
        <li class="fieldcontain">
            <span id="reserveType-label" class="property-label"><g:message code="orderInfo.reserveType.label" default="Reserve Type" /></span>

            <span class="property-value" aria-labelledby="reserveType-label">${ReserveType.getLabel(orderInfo?.reserveType)}</span>

        </li>
    </g:if>

    <g:if test="${orderInfo?.status}">
        <li class="fieldcontain">
            <span id="status-label" class="property-label"><g:message code="orderInfo.status.label" default="Status" /></span>

            <span class="property-value" aria-labelledby="status-label">${OrderStatus.getLable(orderInfo?.status)}</span>

        </li>
    </g:if>

    <g:if test="${orderInfo?.valid!=null}">
        <li class="fieldcontain">
            <span id="valid-label" class="property-label"><g:message code="orderInfo.valid.label" default="Valid" /></span>

            <span class="property-value" aria-labelledby="valid-label">${OrderValid.getLable(orderInfo?.valid)}</span>

        </li>
    </g:if>

    <g:if test="${orderInfo?.cancelReason}">
        <li class="fieldcontain">
            <span id="cancelReason-label" class="property-label"><g:message code="orderInfo.cancelReason.label" default="Cancel Reason" /></span>

            <span class="property-value" aria-labelledby="cancelReason-label"><g:fieldValue bean="${orderInfo}" field="cancelReason"/></span>

        </li>
    </g:if>

    <g:if test="${orderInfo?.addressId}">
        <li class="fieldcontain">
            <span id="addressId-label" class="property-label"><g:message code="orderInfo.addressId.label" default="Address Id" /></span>

            <span class="property-value" aria-labelledby="addressId-label"><g:fieldValue bean="${orderInfo}" field="addressId"/></span>

        </li>
    </g:if>

    <g:if test="${orderInfo?.waiterId}">
        <li class="fieldcontain">
            <span id="waiterId-label" class="property-label"><g:message code="orderInfo.waiterId.label" default="Waiter Id" /></span>

            <span class="property-value" aria-labelledby="waiterId-label"><g:fieldValue bean="${orderInfo}" field="waiterId"/></span>

        </li>
    </g:if>

    <g:if test="${orderInfo?.listenWaiterId}">
        <li class="fieldcontain">
            <span id="listenWaiterId-label" class="property-label"><g:message code="orderInfo.listenWaiterId.label" default="Listen Waiter Id" /></span>

            <span class="property-value" aria-labelledby="listenWaiterId-label"><g:fieldValue bean="${orderInfo}" field="listenWaiterId"/></span>

        </li>
    </g:if>

    <g:if test="${orderInfo?.cashierId}">
        <li class="fieldcontain">
            <span id="cashierId-label" class="property-label"><g:message code="orderInfo.cashierId.label" default="Cashier Id" /></span>

            <span class="property-value" aria-labelledby="cashierId-label"><g:fieldValue bean="${orderInfo}" field="cashierId"/></span>

        </li>
    </g:if>

    <g:if test="${orderInfo?.remark}">
        <li class="fieldcontain">
            <span id="remark-label" class="property-label"><g:message code="orderInfo.remark.label" default="Remark" /></span>

            <span class="property-value" aria-labelledby="remark-label"><g:fieldValue bean="${orderInfo}" field="remark"/></span>

        </li>
    </g:if>

    <g:if test="${orderInfo?.numInRestaurant}">
        <li class="fieldcontain">
            <span id="numInRestaurant-label" class="property-label"><g:message code="orderInfo.numInRestaurant.label" default="Num In Restaurant" /></span>

            <span class="property-value" aria-labelledby="numInRestaurant-label"><g:fieldValue bean="${orderInfo}" field="numInRestaurant"/></span>

        </li>
    </g:if>

    <g:if test="${orderInfo?.orderNum}">
        <li class="fieldcontain">
            <span id="orderNum-label" class="property-label"><g:message code="orderInfo.orderNum.label" default="Order Num" /></span>

            <span class="property-value" aria-labelledby="orderNum-label"><g:fieldValue bean="${orderInfo}" field="orderNum"/></span>

        </li>
    </g:if>

    <g:if test="${orderInfo?.partakeCode}">
        <li class="fieldcontain">
            <span id="partakeCode-label" class="property-label"><g:message code="orderInfo.partakeCode.label" default="Partake Code" /></span>

            <span class="property-value" aria-labelledby="partakeCode-label"><g:fieldValue bean="${orderInfo}" field="partakeCode"/></span>

        </li>
    </g:if>

    <g:if test="${orderInfo?.totalAccount}">
        <li class="fieldcontain">
            <span id="totalAccount-label" class="property-label"><g:message code="orderInfo.totalAccount.label" default="Total Account" /></span>

            <span class="property-value" aria-labelledby="totalAccount-label"><g:fieldValue bean="${orderInfo}" field="totalAccount"/></span>

        </li>
    </g:if>

    <g:if test="${orderInfo?.postage}">
        <li class="fieldcontain">
            <span id="postage-label" class="property-label"><g:message code="orderInfo.postage.label" default="Postage" /></span>

            <span class="property-value" aria-labelledby="postage-label"><g:fieldValue bean="${orderInfo}" field="postage"/></span>

        </li>
    </g:if>



</ol>
</div>

<!--提交算账的表单-->
<form method="post" action="${createLink(controller: "staff",action: "settleAccounts")}">
    <input type="hidden" name="orderId" value="${orderInfo?.id}"/>
    <g:if test="${orderInfo?.realAccount!=null}">
        <li class="fieldcontain">
            <span id="realAccount-label" class="property-label"><g:message code="orderInfo.realAccount.label" default="Real Account" /></span>

            <span class="property-value" aria-labelledby="realAccount-label">
                <input type="text" name="realAccount" value="${orderInfo?.realAccount}" />
            </span>

        </li>
    </g:if>
    <input type="submit" value="${message(code: "default.button.submit.label",default: "submit")}"/>
</form>

</body>
</html>