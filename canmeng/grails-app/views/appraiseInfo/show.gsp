
<%@ page import="lj.data.AppraiseInfo" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'appraiseInfo.label', default: 'AppraiseInfo')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-appraiseInfo" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-appraiseInfo" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list appraiseInfo">
			
				<g:if test="${appraiseInfoInstance?.orderId}">
				<li class="fieldcontain">
					<span id="orderId-label" class="property-label"><g:message code="appraiseInfo.orderId.label" default="Order Id" /></span>
					
						<span class="property-value" aria-labelledby="orderId-label"><g:fieldValue bean="${appraiseInfoInstance}" field="orderId"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${appraiseInfoInstance?.type}">
				<li class="fieldcontain">
					<span id="type-label" class="property-label"><g:message code="appraiseInfo.type.label" default="Type" /></span>
					
						<span class="property-value" aria-labelledby="type-label"><g:fieldValue bean="${appraiseInfoInstance}" field="type"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${appraiseInfoInstance?.hygienicQuality}">
				<li class="fieldcontain">
					<span id="hygienicQuality-label" class="property-label"><g:message code="appraiseInfo.hygienicQuality.label" default="Hygienic Quality" /></span>
					
						<span class="property-value" aria-labelledby="hygienicQuality-label"><g:fieldValue bean="${appraiseInfoInstance}" field="hygienicQuality"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${appraiseInfoInstance?.serviceAttitude}">
				<li class="fieldcontain">
					<span id="serviceAttitude-label" class="property-label"><g:message code="appraiseInfo.serviceAttitude.label" default="Service Attitude" /></span>
					
						<span class="property-value" aria-labelledby="serviceAttitude-label"><g:fieldValue bean="${appraiseInfoInstance}" field="serviceAttitude"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${appraiseInfoInstance?.deliverySpeed}">
				<li class="fieldcontain">
					<span id="deliverySpeed-label" class="property-label"><g:message code="appraiseInfo.deliverySpeed.label" default="Delivery Speed" /></span>
					
						<span class="property-value" aria-labelledby="deliverySpeed-label"><g:fieldValue bean="${appraiseInfoInstance}" field="deliverySpeed"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${appraiseInfoInstance?.taste}">
				<li class="fieldcontain">
					<span id="taste-label" class="property-label"><g:message code="appraiseInfo.taste.label" default="Taste" /></span>
					
						<span class="property-value" aria-labelledby="taste-label"><g:fieldValue bean="${appraiseInfoInstance}" field="taste"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${appraiseInfoInstance?.whole}">
				<li class="fieldcontain">
					<span id="whole-label" class="property-label"><g:message code="appraiseInfo.whole.label" default="Whole" /></span>
					
						<span class="property-value" aria-labelledby="whole-label"><g:fieldValue bean="${appraiseInfoInstance}" field="whole"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${appraiseInfoInstance?.content}">
				<li class="fieldcontain">
					<span id="content-label" class="property-label"><g:message code="appraiseInfo.content.label" default="Content" /></span>
					
						<span class="property-value" aria-labelledby="content-label"><g:fieldValue bean="${appraiseInfoInstance}" field="content"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${appraiseInfoInstance?.appraiseTime}">
				<li class="fieldcontain">
					<span id="appraiseTime-label" class="property-label"><g:message code="appraiseInfo.appraiseTime.label" default="Appraise Time" /></span>
					
						<span class="property-value" aria-labelledby="appraiseTime-label"><g:formatDate date="${appraiseInfoInstance?.appraiseTime}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${appraiseInfoInstance?.clientId}">
				<li class="fieldcontain">
					<span id="clientId-label" class="property-label"><g:message code="appraiseInfo.clientId.label" default="User Id" /></span>
					
						<span class="property-value" aria-labelledby="clientId-label"><g:fieldValue bean="${appraiseInfoInstance}" field="clientId"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${appraiseInfoInstance?.isAnonymity}">
				<li class="fieldcontain">
					<span id="isAnonymity-label" class="property-label"><g:message code="appraiseInfo.isAnonymity.label" default="Is Anonymity" /></span>
					
						<span class="property-value" aria-labelledby="isAnonymity-label"><g:formatBoolean boolean="${appraiseInfoInstance?.isAnonymity}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${appraiseInfoInstance?.restaurantId}">
				<li class="fieldcontain">
					<span id="restaurantId-label" class="property-label"><g:message code="appraiseInfo.restaurantId.label" default="Restaurant Id" /></span>
					
						<span class="property-value" aria-labelledby="restaurantId-label"><g:fieldValue bean="${appraiseInfoInstance}" field="restaurantId"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${appraiseInfoInstance?.userName}">
				<li class="fieldcontain">
					<span id="userName-label" class="property-label"><g:message code="appraiseInfo.userName.label" default="User Name" /></span>
					
						<span class="property-value" aria-labelledby="userName-label"><g:fieldValue bean="${appraiseInfoInstance}" field="userName"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${appraiseInfoInstance?.id}" />
					<g:link class="edit" action="edit" id="${appraiseInfoInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
