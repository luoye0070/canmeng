<%@ page import="lj.data.RestaurantInfo" %>



<div class="fieldcontain ${hasErrors(bean: restaurantInfoInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="restaurantInfo.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textArea name="name" cols="40" rows="5" maxlength="256" required="" value="${restaurantInfoInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: restaurantInfoInstance, field: 'image', 'error')} ">
	<label for="image">
		<g:message code="restaurantInfo.image.label" default="Image" />
		
	</label>
	<g:textField name="image" maxlength="128" value="${restaurantInfoInstance?.image}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: restaurantInfoInstance, field: 'areaId', 'error')} required">
	<label for="areaId">
		<g:message code="restaurantInfo.areaId.label" default="Area Id" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="areaId" type="number" min="1" value="${restaurantInfoInstance.areaId}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: restaurantInfoInstance, field: 'province', 'error')} required">
	<label for="province">
		<g:message code="restaurantInfo.province.label" default="Province" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="province" maxlength="32" required="" value="${restaurantInfoInstance?.province}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: restaurantInfoInstance, field: 'city', 'error')} required">
	<label for="city">
		<g:message code="restaurantInfo.city.label" default="City" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="city" maxlength="32" required="" value="${restaurantInfoInstance?.city}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: restaurantInfoInstance, field: 'area', 'error')} required">
	<label for="area">
		<g:message code="restaurantInfo.area.label" default="Area" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="area" maxlength="32" required="" value="${restaurantInfoInstance?.area}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: restaurantInfoInstance, field: 'street', 'error')} required">
	<label for="street">
		<g:message code="restaurantInfo.street.label" default="Street" />
		<span class="required-indicator">*</span>
	</label>
	<g:textArea name="street" cols="40" rows="5" maxlength="256" required="" value="${restaurantInfoInstance?.street}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: restaurantInfoInstance, field: 'longitude', 'error')} ">
	<label for="longitude">
		<g:message code="restaurantInfo.longitude.label" default="Longitude" />
		
	</label>
	<g:field name="longitude" value="${fieldValue(bean: restaurantInfoInstance, field: 'longitude')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: restaurantInfoInstance, field: 'latitude', 'error')} ">
	<label for="latitude">
		<g:message code="restaurantInfo.latitude.label" default="Latitude" />
		
	</label>
	<g:field name="latitude" value="${fieldValue(bean: restaurantInfoInstance, field: 'latitude')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: restaurantInfoInstance, field: 'phone', 'error')} required">
	<label for="phone">
		<g:message code="restaurantInfo.phone.label" default="Phone" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="phone" maxlength="16" required="" value="${restaurantInfoInstance?.phone}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: restaurantInfoInstance, field: 'masterName', 'error')} required">
	<label for="masterName">
		<g:message code="restaurantInfo.masterName.label" default="Master Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="masterName" maxlength="32" required="" value="${restaurantInfoInstance?.masterName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: restaurantInfoInstance, field: 'userId', 'error')} required">
	<label for="userId">
		<g:message code="restaurantInfo.userId.label" default="User Id" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="userId" type="number" min="1" value="${restaurantInfoInstance.userId}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: restaurantInfoInstance, field: 'shopHoursBeginTime', 'error')} required">
	<label for="shopHoursBeginTime">
		<g:message code="restaurantInfo.shopHoursBeginTime.label" default="Shop Hours Begin Time" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="shopHoursBeginTime" precision="day"  value="${restaurantInfoInstance?.shopHoursBeginTime}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: restaurantInfoInstance, field: 'shopHoursEndTime', 'error')} required">
	<label for="shopHoursEndTime">
		<g:message code="restaurantInfo.shopHoursEndTime.label" default="Shop Hours End Time" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="shopHoursEndTime" precision="day"  value="${restaurantInfoInstance?.shopHoursEndTime}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: restaurantInfoInstance, field: 'enabled', 'error')} ">
	<label for="enabled">
		<g:message code="restaurantInfo.enabled.label" default="Enabled" />
		
	</label>
	<g:checkBox name="enabled" value="${restaurantInfoInstance?.enabled}" />
</div>

<div class="fieldcontain ${hasErrors(bean: restaurantInfoInstance, field: 'cuisineId', 'error')} required">
	<label for="cuisineId">
		<g:message code="restaurantInfo.cuisineId.label" default="Cuisine Id" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="cuisineId" type="number" min="1" value="${restaurantInfoInstance.cuisineId}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: restaurantInfoInstance, field: 'freight', 'error')} ">
	<label for="freight">
		<g:message code="restaurantInfo.freight.label" default="Freight" />
		
	</label>
	<g:field name="freight" value="${fieldValue(bean: restaurantInfoInstance, field: 'freight')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: restaurantInfoInstance, field: 'freeFreight', 'error')} ">
	<label for="freeFreight">
		<g:message code="restaurantInfo.freeFreight.label" default="Free Freight" />
		
	</label>
	<g:field name="freeFreight" value="${fieldValue(bean: restaurantInfoInstance, field: 'freeFreight')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: restaurantInfoInstance, field: 'packId', 'error')} required">
	<label for="packId">
		<g:message code="restaurantInfo.packId.label" default="Pack Id" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="packId" type="number" min="0" value="${restaurantInfoInstance.packId}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: restaurantInfoInstance, field: 'imageSpaceSize', 'error')} required">
	<label for="imageSpaceSize">
		<g:message code="restaurantInfo.imageSpaceSize.label" default="Image Space Size" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="imageSpaceSize" type="number" min="0" value="${restaurantInfoInstance.imageSpaceSize}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: restaurantInfoInstance, field: 'imageSpaceUsedSize', 'error')} required">
	<label for="imageSpaceUsedSize">
		<g:message code="restaurantInfo.imageSpaceUsedSize.label" default="Image Space Used Size" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="imageSpaceUsedSize" type="number" min="0" value="${restaurantInfoInstance.imageSpaceUsedSize}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: restaurantInfoInstance, field: 'verifyStatus', 'error')} required">
	<label for="verifyStatus">
		<g:message code="restaurantInfo.verifyStatus.label" default="Verify Status" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="verifyStatus" type="number" min="0" value="${restaurantInfoInstance.verifyStatus}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: restaurantInfoInstance, field: 'deliverRange', 'error')} ">
	<label for="deliverRange">
		<g:message code="restaurantInfo.deliverRange.label" default="Deliver Range" />
		
	</label>
	<g:field name="deliverRange" type="number" min="0" value="${restaurantInfoInstance.deliverRange}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: restaurantInfoInstance, field: 'averageConsume', 'error')} ">
	<label for="averageConsume">
		<g:message code="restaurantInfo.averageConsume.label" default="Average Consume" />
		
	</label>
	<g:field name="averageConsume" value="${fieldValue(bean: restaurantInfoInstance, field: 'averageConsume')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: restaurantInfoInstance, field: 'remark', 'error')} ">
	<label for="remark">
		<g:message code="restaurantInfo.remark.label" default="Remark" />
		
	</label>
	<g:textField name="remark" maxlength="128" value="${restaurantInfoInstance?.remark}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: restaurantInfoInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="restaurantInfo.description.label" default="Description" />
		
	</label>
	<g:textArea name="description" cols="40" rows="5" maxlength="131072" value="${restaurantInfoInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: restaurantInfoInstance, field: 'cuisineName', 'error')} ">
	<label for="cuisineName">
		<g:message code="restaurantInfo.cuisineName.label" default="Cuisine Name" />
		
	</label>
	<g:textField name="cuisineName" maxlength="16" value="${restaurantInfoInstance?.cuisineName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: restaurantInfoInstance, field: 'packName', 'error')} ">
	<label for="packName">
		<g:message code="restaurantInfo.packName.label" default="Pack Name" />
		
	</label>
	<g:textField name="packName" maxlength="16" value="${restaurantInfoInstance?.packName}"/>
</div>

