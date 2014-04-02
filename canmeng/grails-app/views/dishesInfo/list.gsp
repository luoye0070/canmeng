
<%@ page import="lj.data.DishesInfo" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'dishesInfo.label', default: 'DishesInfo')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-dishesInfo" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-dishesInfo" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="orderId" title="${message(code: 'dishesInfo.orderId.label', default: 'Order Id')}" />
					
						<g:sortableColumn property="foodId" title="${message(code: 'dishesInfo.foodId.label', default: 'Food Id')}" />
					
						<g:sortableColumn property="status" title="${message(code: 'dishesInfo.status.label', default: 'Status')}" />
					
						<g:sortableColumn property="valid" title="${message(code: 'dishesInfo.valid.label', default: 'Valid')}" />
					
						<g:sortableColumn property="cancelReason" title="${message(code: 'dishesInfo.cancelReason.label', default: 'Cancel Reason')}" />
					
						<g:sortableColumn property="remark" title="${message(code: 'dishesInfo.remark.label', default: 'Remark')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${dishesInfoInstanceList}" status="i" var="dishesInfoInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${dishesInfoInstance.id}">${fieldValue(bean: dishesInfoInstance, field: "orderId")}</g:link></td>
					
						<td>${fieldValue(bean: dishesInfoInstance, field: "foodId")}</td>
					
						<td>${fieldValue(bean: dishesInfoInstance, field: "status")}</td>
					
						<td>${fieldValue(bean: dishesInfoInstance, field: "valid")}</td>
					
						<td>${fieldValue(bean: dishesInfoInstance, field: "cancelReason")}</td>
					
						<td>${fieldValue(bean: dishesInfoInstance, field: "remark")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${dishesInfoInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
