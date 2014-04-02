
<%@ page import="lj.data.DishesInfo" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'dishesInfo.label', default: 'DishesInfo')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-dishesInfo" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-dishesInfo" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list dishesInfo">
			
				<g:if test="${dishesInfoInstance?.orderId}">
				<li class="fieldcontain">
					<span id="orderId-label" class="property-label"><g:message code="dishesInfo.orderId.label" default="Order Id" /></span>
					
						<span class="property-value" aria-labelledby="orderId-label"><g:fieldValue bean="${dishesInfoInstance}" field="orderId"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${dishesInfoInstance?.foodId}">
				<li class="fieldcontain">
					<span id="foodId-label" class="property-label"><g:message code="dishesInfo.foodId.label" default="Food Id" /></span>
					
						<span class="property-value" aria-labelledby="foodId-label"><g:fieldValue bean="${dishesInfoInstance}" field="foodId"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${dishesInfoInstance?.status}">
				<li class="fieldcontain">
					<span id="status-label" class="property-label"><g:message code="dishesInfo.status.label" default="Status" /></span>
					
						<span class="property-value" aria-labelledby="status-label"><g:fieldValue bean="${dishesInfoInstance}" field="status"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${dishesInfoInstance?.valid}">
				<li class="fieldcontain">
					<span id="valid-label" class="property-label"><g:message code="dishesInfo.valid.label" default="Valid" /></span>
					
						<span class="property-value" aria-labelledby="valid-label"><g:fieldValue bean="${dishesInfoInstance}" field="valid"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${dishesInfoInstance?.cancelReason}">
				<li class="fieldcontain">
					<span id="cancelReason-label" class="property-label"><g:message code="dishesInfo.cancelReason.label" default="Cancel Reason" /></span>
					
						<span class="property-value" aria-labelledby="cancelReason-label"><g:fieldValue bean="${dishesInfoInstance}" field="cancelReason"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${dishesInfoInstance?.remark}">
				<li class="fieldcontain">
					<span id="remark-label" class="property-label"><g:message code="dishesInfo.remark.label" default="Remark" /></span>
					
						<span class="property-value" aria-labelledby="remark-label"><g:fieldValue bean="${dishesInfoInstance}" field="remark"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${dishesInfoInstance?.numInRestaurant}">
				<li class="fieldcontain">
					<span id="numInRestaurant-label" class="property-label"><g:message code="dishesInfo.numInRestaurant.label" default="Num In Restaurant" /></span>
					
						<span class="property-value" aria-labelledby="numInRestaurant-label"><g:fieldValue bean="${dishesInfoInstance}" field="numInRestaurant"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${dishesInfoInstance?.num}">
				<li class="fieldcontain">
					<span id="num-label" class="property-label"><g:message code="dishesInfo.num.label" default="Num" /></span>
					
						<span class="property-value" aria-labelledby="num-label"><g:fieldValue bean="${dishesInfoInstance}" field="num"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${dishesInfoInstance?.cookId}">
				<li class="fieldcontain">
					<span id="cookId-label" class="property-label"><g:message code="dishesInfo.cookId.label" default="Cook Id" /></span>
					
						<span class="property-value" aria-labelledby="cookId-label"><g:fieldValue bean="${dishesInfoInstance}" field="cookId"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${dishesInfoInstance?.foodPrice}">
				<li class="fieldcontain">
					<span id="foodPrice-label" class="property-label"><g:message code="dishesInfo.foodPrice.label" default="Food Price" /></span>
					
						<span class="property-value" aria-labelledby="foodPrice-label"><g:fieldValue bean="${dishesInfoInstance}" field="foodPrice"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${dishesInfoInstance?.id}" />
					<g:link class="edit" action="edit" id="${dishesInfoInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
