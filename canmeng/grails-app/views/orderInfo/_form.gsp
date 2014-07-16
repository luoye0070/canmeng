<%@ page import="lj.data.OrderInfo" %>



<div class="fieldcontain ${hasErrors(bean: orderInfoInstance, field: 'restaurantId', 'error')} required">
	<label for="restaurantId">
		<g:message code="orderInfo.restaurantId.label" default="Restaurant Id" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="restaurantId" type="number" value="${orderInfoInstance.restaurantId}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: orderInfoInstance, field: 'clientId', 'error')} required">
	<label for="clientId">
		<g:message code="orderInfo.clientId.label" default="Client Id" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="clientId" type="number" value="${orderInfoInstance.clientId}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: orderInfoInstance, field: 'tableId', 'error')} required">
	<label for="tableId">
		<g:message code="orderInfo.tableId.label" default="Table Id" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="tableId" type="number" value="${orderInfoInstance.tableId}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: orderInfoInstance, field: 'date', 'error')} required">
	<label for="date">
		<g:message code="orderInfo.date.label" default="Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="date" precision="day"  value="${orderInfoInstance?.date}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: orderInfoInstance, field: 'time', 'error')} required">
	<label for="time">
		<g:message code="orderInfo.time.label" default="Time" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="time" precision="day"  value="${orderInfoInstance?.time}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: orderInfoInstance, field: 'reserveType', 'error')} required">
	<label for="reserveType">
		<g:message code="orderInfo.reserveType.label" default="Reserve Type" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="reserveType" type="number" value="${orderInfoInstance.reserveType}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: orderInfoInstance, field: 'status', 'error')} required">
	<label for="status">
		<g:message code="orderInfo.status.label" default="Status" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="status" from="${orderInfoInstance.constraints.status.inList}" required="" value="${fieldValue(bean: orderInfoInstance, field: 'status')}" valueMessagePrefix="orderInfo.status"/>
</div>

<div class="fieldcontain ${hasErrors(bean: orderInfoInstance, field: 'valid', 'error')} required">
	<label for="valid">
		<g:message code="orderInfo.valid.label" default="Valid" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="valid" from="${orderInfoInstance.constraints.valid.inList}" required="" value="${fieldValue(bean: orderInfoInstance, field: 'valid')}" valueMessagePrefix="orderInfo.valid"/>
</div>

<div class="fieldcontain ${hasErrors(bean: orderInfoInstance, field: 'cancelReason', 'error')} ">
	<label for="cancelReason">
		<g:message code="orderInfo.cancelReason.label" default="Cancel Reason" />
		
	</label>
	<g:textField name="cancelReason" maxlength="128" value="${orderInfoInstance?.cancelReason}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: orderInfoInstance, field: 'addressId', 'error')} required">
	<label for="addressId">
		<g:message code="orderInfo.addressId.label" default="Address Id" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="addressId" type="number" value="${orderInfoInstance.addressId}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: orderInfoInstance, field: 'waiterId', 'error')} required">
	<label for="waiterId">
		<g:message code="orderInfo.waiterId.label" default="Waiter Id" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="waiterId" type="number" value="${orderInfoInstance.waiterId}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: orderInfoInstance, field: 'listenWaiterId', 'error')} required">
	<label for="listenWaiterId">
		<g:message code="orderInfo.listenWaiterId.label" default="Listen Waiter Id" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="listenWaiterId" type="number" value="${orderInfoInstance.listenWaiterId}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: orderInfoInstance, field: 'cashierId', 'error')} required">
	<label for="cashierId">
		<g:message code="orderInfo.cashierId.label" default="Cashier Id" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="cashierId" type="number" value="${orderInfoInstance.cashierId}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: orderInfoInstance, field: 'remark', 'error')} ">
	<label for="remark">
		<g:message code="orderInfo.remark.label" default="Remark" />
		
	</label>
	<g:textField name="remark" value="${orderInfoInstance?.remark}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: orderInfoInstance, field: 'numInRestaurant', 'error')} required">
	<label for="numInRestaurant">
		<g:message code="orderInfo.numInRestaurant.label" default="Num In Restaurant" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="numInRestaurant" type="number" value="${orderInfoInstance.numInRestaurant}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: orderInfoInstance, field: 'orderNum', 'error')} required">
	<label for="orderNum">
		<g:message code="orderInfo.orderNum.label" default="Order Num" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="orderNum" maxlength="32" required="" value="${orderInfoInstance?.orderNum}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: orderInfoInstance, field: 'partakeCode', 'error')} ">
	<label for="partakeCode">
		<g:message code="orderInfo.partakeCode.label" default="Partake Code" />
		
	</label>
	<g:textField name="partakeCode" maxlength="4" value="${orderInfoInstance?.partakeCode}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: orderInfoInstance, field: 'totalAccount', 'error')} ">
	<label for="totalAccount">
		<g:message code="orderInfo.totalAccount.label" default="Total Account" />
		
	</label>
	<g:field name="totalAccount" value="${fieldValue(bean: orderInfoInstance, field: 'totalAccount')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: orderInfoInstance, field: 'postage', 'error')} ">
	<label for="postage">
		<g:message code="orderInfo.postage.label" default="Postage" />
		
	</label>
	<g:field name="postage" value="${fieldValue(bean: orderInfoInstance, field: 'postage')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: orderInfoInstance, field: 'realAccount', 'error')} ">
	<label for="realAccount">
		<g:message code="orderInfo.realAccount.label" default="Real Account" />
		
	</label>
	<g:field name="realAccount" value="${fieldValue(bean: orderInfoInstance, field: 'realAccount')}"/>
</div>

