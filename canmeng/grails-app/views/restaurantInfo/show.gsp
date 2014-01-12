
<%@ page import="lj.data.RestaurantInfo" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'restaurantInfo.label', default: 'RestaurantInfo')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-restaurantInfo" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-restaurantInfo" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list restaurantInfo">
			
				<g:if test="${restaurantInfoInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="restaurantInfo.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${restaurantInfoInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${restaurantInfoInstance?.image}">
				<li class="fieldcontain">
					<span id="image-label" class="property-label"><g:message code="restaurantInfo.image.label" default="Image" /></span>
					
						<span class="property-value" aria-labelledby="image-label"><g:fieldValue bean="${restaurantInfoInstance}" field="image"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${restaurantInfoInstance?.areaId}">
				<li class="fieldcontain">
					<span id="areaId-label" class="property-label"><g:message code="restaurantInfo.areaId.label" default="Area Id" /></span>
					
						<span class="property-value" aria-labelledby="areaId-label"><g:fieldValue bean="${restaurantInfoInstance}" field="areaId"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${restaurantInfoInstance?.province}">
				<li class="fieldcontain">
					<span id="province-label" class="property-label"><g:message code="restaurantInfo.province.label" default="Province" /></span>
					
						<span class="property-value" aria-labelledby="province-label"><g:fieldValue bean="${restaurantInfoInstance}" field="province"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${restaurantInfoInstance?.city}">
				<li class="fieldcontain">
					<span id="city-label" class="property-label"><g:message code="restaurantInfo.city.label" default="City" /></span>
					
						<span class="property-value" aria-labelledby="city-label"><g:fieldValue bean="${restaurantInfoInstance}" field="city"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${restaurantInfoInstance?.area}">
				<li class="fieldcontain">
					<span id="area-label" class="property-label"><g:message code="restaurantInfo.area.label" default="Area" /></span>
					
						<span class="property-value" aria-labelledby="area-label"><g:fieldValue bean="${restaurantInfoInstance}" field="area"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${restaurantInfoInstance?.street}">
				<li class="fieldcontain">
					<span id="street-label" class="property-label"><g:message code="restaurantInfo.street.label" default="Street" /></span>
					
						<span class="property-value" aria-labelledby="street-label"><g:fieldValue bean="${restaurantInfoInstance}" field="street"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${restaurantInfoInstance?.longitude}">
				<li class="fieldcontain">
					<span id="longitude-label" class="property-label"><g:message code="restaurantInfo.longitude.label" default="Longitude" /></span>
					
						<span class="property-value" aria-labelledby="longitude-label"><g:fieldValue bean="${restaurantInfoInstance}" field="longitude"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${restaurantInfoInstance?.latitude}">
				<li class="fieldcontain">
					<span id="latitude-label" class="property-label"><g:message code="restaurantInfo.latitude.label" default="Latitude" /></span>
					
						<span class="property-value" aria-labelledby="latitude-label"><g:fieldValue bean="${restaurantInfoInstance}" field="latitude"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${restaurantInfoInstance?.phone}">
				<li class="fieldcontain">
					<span id="phone-label" class="property-label"><g:message code="restaurantInfo.phone.label" default="Phone" /></span>
					
						<span class="property-value" aria-labelledby="phone-label"><g:fieldValue bean="${restaurantInfoInstance}" field="phone"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${restaurantInfoInstance?.masterName}">
				<li class="fieldcontain">
					<span id="masterName-label" class="property-label"><g:message code="restaurantInfo.masterName.label" default="Master Name" /></span>
					
						<span class="property-value" aria-labelledby="masterName-label"><g:fieldValue bean="${restaurantInfoInstance}" field="masterName"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${restaurantInfoInstance?.userId}">
				<li class="fieldcontain">
					<span id="userId-label" class="property-label"><g:message code="restaurantInfo.userId.label" default="User Id" /></span>
					
						<span class="property-value" aria-labelledby="userId-label"><g:fieldValue bean="${restaurantInfoInstance}" field="userId"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${restaurantInfoInstance?.shopHoursBeginTime}">
				<li class="fieldcontain">
					<span id="shopHoursBeginTime-label" class="property-label"><g:message code="restaurantInfo.shopHoursBeginTime.label" default="Shop Hours Begin Time" /></span>
					
						<span class="property-value" aria-labelledby="shopHoursBeginTime-label"><g:formatDate date="${restaurantInfoInstance?.shopHoursBeginTime}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${restaurantInfoInstance?.shopHoursEndTime}">
				<li class="fieldcontain">
					<span id="shopHoursEndTime-label" class="property-label"><g:message code="restaurantInfo.shopHoursEndTime.label" default="Shop Hours End Time" /></span>
					
						<span class="property-value" aria-labelledby="shopHoursEndTime-label"><g:formatDate date="${restaurantInfoInstance?.shopHoursEndTime}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${restaurantInfoInstance?.enabled}">
				<li class="fieldcontain">
					<span id="enabled-label" class="property-label"><g:message code="restaurantInfo.enabled.label" default="Enabled" /></span>
					
						<span class="property-value" aria-labelledby="enabled-label"><g:formatBoolean boolean="${restaurantInfoInstance?.enabled}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${restaurantInfoInstance?.cuisineId}">
				<li class="fieldcontain">
					<span id="cuisineId-label" class="property-label"><g:message code="restaurantInfo.cuisineId.label" default="Cuisine Id" /></span>
					
						<span class="property-value" aria-labelledby="cuisineId-label"><g:fieldValue bean="${restaurantInfoInstance}" field="cuisineId"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${restaurantInfoInstance?.freight}">
				<li class="fieldcontain">
					<span id="freight-label" class="property-label"><g:message code="restaurantInfo.freight.label" default="Freight" /></span>
					
						<span class="property-value" aria-labelledby="freight-label"><g:fieldValue bean="${restaurantInfoInstance}" field="freight"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${restaurantInfoInstance?.freeFreight}">
				<li class="fieldcontain">
					<span id="freeFreight-label" class="property-label"><g:message code="restaurantInfo.freeFreight.label" default="Free Freight" /></span>
					
						<span class="property-value" aria-labelledby="freeFreight-label"><g:fieldValue bean="${restaurantInfoInstance}" field="freeFreight"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${restaurantInfoInstance?.packId}">
				<li class="fieldcontain">
					<span id="packId-label" class="property-label"><g:message code="restaurantInfo.packId.label" default="Pack Id" /></span>
					
						<span class="property-value" aria-labelledby="packId-label"><g:fieldValue bean="${restaurantInfoInstance}" field="packId"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${restaurantInfoInstance?.imageSpaceSize}">
				<li class="fieldcontain">
					<span id="imageSpaceSize-label" class="property-label"><g:message code="restaurantInfo.imageSpaceSize.label" default="Image Space Size" /></span>
					
						<span class="property-value" aria-labelledby="imageSpaceSize-label"><g:fieldValue bean="${restaurantInfoInstance}" field="imageSpaceSize"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${restaurantInfoInstance?.imageSpaceUsedSize}">
				<li class="fieldcontain">
					<span id="imageSpaceUsedSize-label" class="property-label"><g:message code="restaurantInfo.imageSpaceUsedSize.label" default="Image Space Used Size" /></span>
					
						<span class="property-value" aria-labelledby="imageSpaceUsedSize-label"><g:fieldValue bean="${restaurantInfoInstance}" field="imageSpaceUsedSize"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${restaurantInfoInstance?.verifyStatus}">
				<li class="fieldcontain">
					<span id="verifyStatus-label" class="property-label"><g:message code="restaurantInfo.verifyStatus.label" default="Verify Status" /></span>
					
						<span class="property-value" aria-labelledby="verifyStatus-label"><g:fieldValue bean="${restaurantInfoInstance}" field="verifyStatus"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${restaurantInfoInstance?.deliverRange}">
				<li class="fieldcontain">
					<span id="deliverRange-label" class="property-label"><g:message code="restaurantInfo.deliverRange.label" default="Deliver Range" /></span>
					
						<span class="property-value" aria-labelledby="deliverRange-label"><g:fieldValue bean="${restaurantInfoInstance}" field="deliverRange"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${restaurantInfoInstance?.averageConsume}">
				<li class="fieldcontain">
					<span id="averageConsume-label" class="property-label"><g:message code="restaurantInfo.averageConsume.label" default="Average Consume" /></span>
					
						<span class="property-value" aria-labelledby="averageConsume-label"><g:fieldValue bean="${restaurantInfoInstance}" field="averageConsume"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${restaurantInfoInstance?.remark}">
				<li class="fieldcontain">
					<span id="remark-label" class="property-label"><g:message code="restaurantInfo.remark.label" default="Remark" /></span>
					
						<span class="property-value" aria-labelledby="remark-label"><g:fieldValue bean="${restaurantInfoInstance}" field="remark"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${restaurantInfoInstance?.description}">
				<li class="fieldcontain">
					<span id="description-label" class="property-label"><g:message code="restaurantInfo.description.label" default="Description" /></span>
					
						<span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${restaurantInfoInstance}" field="description"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${restaurantInfoInstance?.cuisineName}">
				<li class="fieldcontain">
					<span id="cuisineName-label" class="property-label"><g:message code="restaurantInfo.cuisineName.label" default="Cuisine Name" /></span>
					
						<span class="property-value" aria-labelledby="cuisineName-label"><g:fieldValue bean="${restaurantInfoInstance}" field="cuisineName"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${restaurantInfoInstance?.packName}">
				<li class="fieldcontain">
					<span id="packName-label" class="property-label"><g:message code="restaurantInfo.packName.label" default="Pack Name" /></span>
					
						<span class="property-value" aria-labelledby="packName-label"><g:fieldValue bean="${restaurantInfoInstance}" field="packName"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${restaurantInfoInstance?.id}" />
					<g:link class="edit" action="edit" id="${restaurantInfoInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
