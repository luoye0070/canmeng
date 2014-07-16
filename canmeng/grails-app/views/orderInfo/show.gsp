
<%@ page import="lj.data.OrderInfo" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'orderInfo.label', default: 'OrderInfo')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-orderInfo" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-orderInfo" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list orderInfo">
			
				<g:if test="${orderInfoInstance?.restaurantId}">
				<li class="fieldcontain">
					<span id="restaurantId-label" class="property-label"><g:message code="orderInfo.restaurantId.label" default="Restaurant Id" /></span>
					
						<span class="property-value" aria-labelledby="restaurantId-label"><g:fieldValue bean="${orderInfoInstance}" field="restaurantId"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${orderInfoInstance?.clientId}">
				<li class="fieldcontain">
					<span id="clientId-label" class="property-label"><g:message code="orderInfo.clientId.label" default="Client Id" /></span>
					
						<span class="property-value" aria-labelledby="clientId-label"><g:fieldValue bean="${orderInfoInstance}" field="clientId"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${orderInfoInstance?.tableId}">
				<li class="fieldcontain">
					<span id="tableId-label" class="property-label"><g:message code="orderInfo.tableId.label" default="Table Id" /></span>
					
						<span class="property-value" aria-labelledby="tableId-label"><g:fieldValue bean="${orderInfoInstance}" field="tableId"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${orderInfoInstance?.date}">
				<li class="fieldcontain">
					<span id="date-label" class="property-label"><g:message code="orderInfo.date.label" default="Date" /></span>
					
						<span class="property-value" aria-labelledby="date-label"><g:formatDate date="${orderInfoInstance?.date}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${orderInfoInstance?.time}">
				<li class="fieldcontain">
					<span id="time-label" class="property-label"><g:message code="orderInfo.time.label" default="Time" /></span>
					
						<span class="property-value" aria-labelledby="time-label"><g:formatDate date="${orderInfoInstance?.time}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${orderInfoInstance?.reserveType}">
				<li class="fieldcontain">
					<span id="reserveType-label" class="property-label"><g:message code="orderInfo.reserveType.label" default="Reserve Type" /></span>
					
						<span class="property-value" aria-labelledby="reserveType-label"><g:fieldValue bean="${orderInfoInstance}" field="reserveType"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${orderInfoInstance?.status}">
				<li class="fieldcontain">
					<span id="status-label" class="property-label"><g:message code="orderInfo.status.label" default="Status" /></span>
					
						<span class="property-value" aria-labelledby="status-label"><g:fieldValue bean="${orderInfoInstance}" field="status"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${orderInfoInstance?.valid}">
				<li class="fieldcontain">
					<span id="valid-label" class="property-label"><g:message code="orderInfo.valid.label" default="Valid" /></span>
					
						<span class="property-value" aria-labelledby="valid-label"><g:fieldValue bean="${orderInfoInstance}" field="valid"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${orderInfoInstance?.cancelReason}">
				<li class="fieldcontain">
					<span id="cancelReason-label" class="property-label"><g:message code="orderInfo.cancelReason.label" default="Cancel Reason" /></span>
					
						<span class="property-value" aria-labelledby="cancelReason-label"><g:fieldValue bean="${orderInfoInstance}" field="cancelReason"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${orderInfoInstance?.addressId}">
				<li class="fieldcontain">
					<span id="addressId-label" class="property-label"><g:message code="orderInfo.addressId.label" default="Address Id" /></span>
					
						<span class="property-value" aria-labelledby="addressId-label"><g:fieldValue bean="${orderInfoInstance}" field="addressId"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${orderInfoInstance?.waiterId}">
				<li class="fieldcontain">
					<span id="waiterId-label" class="property-label"><g:message code="orderInfo.waiterId.label" default="Waiter Id" /></span>
					
						<span class="property-value" aria-labelledby="waiterId-label"><g:fieldValue bean="${orderInfoInstance}" field="waiterId"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${orderInfoInstance?.listenWaiterId}">
				<li class="fieldcontain">
					<span id="listenWaiterId-label" class="property-label"><g:message code="orderInfo.listenWaiterId.label" default="Listen Waiter Id" /></span>
					
						<span class="property-value" aria-labelledby="listenWaiterId-label"><g:fieldValue bean="${orderInfoInstance}" field="listenWaiterId"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${orderInfoInstance?.cashierId}">
				<li class="fieldcontain">
					<span id="cashierId-label" class="property-label"><g:message code="orderInfo.cashierId.label" default="Cashier Id" /></span>
					
						<span class="property-value" aria-labelledby="cashierId-label"><g:fieldValue bean="${orderInfoInstance}" field="cashierId"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${orderInfoInstance?.remark}">
				<li class="fieldcontain">
					<span id="remark-label" class="property-label"><g:message code="orderInfo.remark.label" default="Remark" /></span>
					
						<span class="property-value" aria-labelledby="remark-label"><g:fieldValue bean="${orderInfoInstance}" field="remark"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${orderInfoInstance?.numInRestaurant}">
				<li class="fieldcontain">
					<span id="numInRestaurant-label" class="property-label"><g:message code="orderInfo.numInRestaurant.label" default="Num In Restaurant" /></span>
					
						<span class="property-value" aria-labelledby="numInRestaurant-label"><g:fieldValue bean="${orderInfoInstance}" field="numInRestaurant"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${orderInfoInstance?.orderNum}">
				<li class="fieldcontain">
					<span id="orderNum-label" class="property-label"><g:message code="orderInfo.orderNum.label" default="Order Num" /></span>
					
						<span class="property-value" aria-labelledby="orderNum-label"><g:fieldValue bean="${orderInfoInstance}" field="orderNum"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${orderInfoInstance?.partakeCode}">
				<li class="fieldcontain">
					<span id="partakeCode-label" class="property-label"><g:message code="orderInfo.partakeCode.label" default="Partake Code" /></span>
					
						<span class="property-value" aria-labelledby="partakeCode-label"><g:fieldValue bean="${orderInfoInstance}" field="partakeCode"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${orderInfoInstance?.totalAccount}">
				<li class="fieldcontain">
					<span id="totalAccount-label" class="property-label"><g:message code="orderInfo.totalAccount.label" default="Total Account" /></span>
					
						<span class="property-value" aria-labelledby="totalAccount-label"><g:fieldValue bean="${orderInfoInstance}" field="totalAccount"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${orderInfoInstance?.postage}">
				<li class="fieldcontain">
					<span id="postage-label" class="property-label"><g:message code="orderInfo.postage.label" default="Postage" /></span>
					
						<span class="property-value" aria-labelledby="postage-label"><g:fieldValue bean="${orderInfoInstance}" field="postage"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${orderInfoInstance?.realAccount}">
				<li class="fieldcontain">
					<span id="realAccount-label" class="property-label"><g:message code="orderInfo.realAccount.label" default="Real Account" /></span>
					
						<span class="property-value" aria-labelledby="realAccount-label"><g:fieldValue bean="${orderInfoInstance}" field="realAccount"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${orderInfoInstance?.id}" />
					<g:link class="edit" action="edit" id="${orderInfoInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
