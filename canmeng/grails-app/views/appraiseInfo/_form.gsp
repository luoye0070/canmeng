<%@ page import="lj.data.AppraiseInfo" %>



<div class="fieldcontain ${hasErrors(bean: appraiseInfoInstance, field: 'orderId', 'error')} required">
	<label for="orderId">
		<g:message code="appraiseInfo.orderId.label" default="Order Id" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="orderId" type="number" min="0" value="${appraiseInfoInstance.orderId}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: appraiseInfoInstance, field: 'type', 'error')} required">
	<label for="type">
		<g:message code="appraiseInfo.type.label" default="Type" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="type" from="${appraiseInfoInstance.constraints.type.inList}" required="" value="${fieldValue(bean: appraiseInfoInstance, field: 'type')}" valueMessagePrefix="appraiseInfo.type"/>
</div>

<div class="fieldcontain ${hasErrors(bean: appraiseInfoInstance, field: 'hygienicQuality', 'error')} required">
	<label for="hygienicQuality">
		<g:message code="appraiseInfo.hygienicQuality.label" default="Hygienic Quality" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="hygienicQuality" type="number" min="0" value="${appraiseInfoInstance.hygienicQuality}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: appraiseInfoInstance, field: 'serviceAttitude', 'error')} required">
	<label for="serviceAttitude">
		<g:message code="appraiseInfo.serviceAttitude.label" default="Service Attitude" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="serviceAttitude" type="number" min="0" value="${appraiseInfoInstance.serviceAttitude}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: appraiseInfoInstance, field: 'deliverySpeed', 'error')} required">
	<label for="deliverySpeed">
		<g:message code="appraiseInfo.deliverySpeed.label" default="Delivery Speed" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="deliverySpeed" type="number" min="0" value="${appraiseInfoInstance.deliverySpeed}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: appraiseInfoInstance, field: 'taste', 'error')} required">
	<label for="taste">
		<g:message code="appraiseInfo.taste.label" default="Taste" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="taste" type="number" min="0" value="${appraiseInfoInstance.taste}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: appraiseInfoInstance, field: 'whole', 'error')} required">
	<label for="whole">
		<g:message code="appraiseInfo.whole.label" default="Whole" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="whole" type="number" min="0" value="${appraiseInfoInstance.whole}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: appraiseInfoInstance, field: 'content', 'error')} ">
	<label for="content">
		<g:message code="appraiseInfo.content.label" default="Content" />
		
	</label>
	<g:textField name="content" value="${appraiseInfoInstance?.content}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: appraiseInfoInstance, field: 'appraiseTime', 'error')} required">
	<label for="appraiseTime">
		<g:message code="appraiseInfo.appraiseTime.label" default="Appraise Time" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="appraiseTime" precision="day"  value="${appraiseInfoInstance?.appraiseTime}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: appraiseInfoInstance, field: 'clientId', 'error')} required">
	<label for="clientId">
		<g:message code="appraiseInfo.clientId.label" default="User Id" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="clientId" type="number" min="0" value="${appraiseInfoInstance.clientId}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: appraiseInfoInstance, field: 'isAnonymity', 'error')} ">
	<label for="isAnonymity">
		<g:message code="appraiseInfo.isAnonymity.label" default="Is Anonymity" />
		
	</label>
	<g:checkBox name="isAnonymity" value="${appraiseInfoInstance?.isAnonymity}" />
</div>

<div class="fieldcontain ${hasErrors(bean: appraiseInfoInstance, field: 'restaurantId', 'error')} required">
	<label for="restaurantId">
		<g:message code="appraiseInfo.restaurantId.label" default="Restaurant Id" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="restaurantId" type="number" min="0" value="${appraiseInfoInstance.restaurantId}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: appraiseInfoInstance, field: 'userName', 'error')} required">
	<label for="userName">
		<g:message code="appraiseInfo.userName.label" default="User Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="userName" maxlength="32" required="" value="${appraiseInfoInstance?.userName}"/>
</div>

