<%@ page import="lj.data.DishesInfo" %>



<div class="fieldcontain ${hasErrors(bean: dishesInfoInstance, field: 'orderId', 'error')} required">
	<label for="orderId">
		<g:message code="dishesInfo.orderId.label" default="Order Id" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="orderId" type="number" value="${dishesInfoInstance.orderId}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: dishesInfoInstance, field: 'foodId', 'error')} required">
	<label for="foodId">
		<g:message code="dishesInfo.foodId.label" default="Food Id" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="foodId" type="number" value="${dishesInfoInstance.foodId}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: dishesInfoInstance, field: 'status', 'error')} required">
	<label for="status">
		<g:message code="dishesInfo.status.label" default="Status" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="status" from="${dishesInfoInstance.constraints.status.inList}" required="" value="${fieldValue(bean: dishesInfoInstance, field: 'status')}" valueMessagePrefix="dishesInfo.status"/>
</div>

<div class="fieldcontain ${hasErrors(bean: dishesInfoInstance, field: 'valid', 'error')} required">
	<label for="valid">
		<g:message code="dishesInfo.valid.label" default="Valid" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="valid" from="${dishesInfoInstance.constraints.valid.inList}" required="" value="${fieldValue(bean: dishesInfoInstance, field: 'valid')}" valueMessagePrefix="dishesInfo.valid"/>
</div>

<div class="fieldcontain ${hasErrors(bean: dishesInfoInstance, field: 'cancelReason', 'error')} ">
	<label for="cancelReason">
		<g:message code="dishesInfo.cancelReason.label" default="Cancel Reason" />
		
	</label>
	<g:textField name="cancelReason" value="${dishesInfoInstance?.cancelReason}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: dishesInfoInstance, field: 'remark', 'error')} ">
	<label for="remark">
		<g:message code="dishesInfo.remark.label" default="Remark" />
		
	</label>
	<g:textField name="remark" value="${dishesInfoInstance?.remark}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: dishesInfoInstance, field: 'numInRestaurant', 'error')} required">
	<label for="numInRestaurant">
		<g:message code="dishesInfo.numInRestaurant.label" default="Num In Restaurant" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="numInRestaurant" type="number" value="${dishesInfoInstance.numInRestaurant}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: dishesInfoInstance, field: 'num', 'error')} required">
	<label for="num">
		<g:message code="dishesInfo.num.label" default="Num" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="num" type="number" min="1" value="${dishesInfoInstance.num}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: dishesInfoInstance, field: 'cookId', 'error')} required">
	<label for="cookId">
		<g:message code="dishesInfo.cookId.label" default="Cook Id" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="cookId" type="number" value="${dishesInfoInstance.cookId}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: dishesInfoInstance, field: 'foodPrice', 'error')} required">
	<label for="foodPrice">
		<g:message code="dishesInfo.foodPrice.label" default="Food Price" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="foodPrice" value="${fieldValue(bean: dishesInfoInstance, field: 'foodPrice')}" required=""/>
</div>

