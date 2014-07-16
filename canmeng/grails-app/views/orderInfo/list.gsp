
<%@ page import="lj.data.OrderInfo" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'orderInfo.label', default: 'OrderInfo')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-orderInfo" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-orderInfo" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="restaurantId" title="${message(code: 'orderInfo.restaurantId.label', default: 'Restaurant Id')}" />
					
						<g:sortableColumn property="clientId" title="${message(code: 'orderInfo.clientId.label', default: 'User Id')}" />
					
						<g:sortableColumn property="tableId" title="${message(code: 'orderInfo.tableId.label', default: 'Table Id')}" />
					
						<g:sortableColumn property="date" title="${message(code: 'orderInfo.date.label', default: 'Date')}" />
					
						<g:sortableColumn property="time" title="${message(code: 'orderInfo.time.label', default: 'Time')}" />
					
						<g:sortableColumn property="reserveType" title="${message(code: 'orderInfo.reserveType.label', default: 'Reserve Type')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${orderInfoInstanceList}" status="i" var="orderInfoInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${orderInfoInstance.id}">${fieldValue(bean: orderInfoInstance, field: "restaurantId")}</g:link></td>
					
						<td>${fieldValue(bean: orderInfoInstance, field: "clientId")}</td>
					
						<td>${fieldValue(bean: orderInfoInstance, field: "tableId")}</td>
					
						<td><g:formatDate date="${orderInfoInstance.date}" /></td>
					
						<td><g:formatDate date="${orderInfoInstance.time}" /></td>
					
						<td>${fieldValue(bean: orderInfoInstance, field: "reserveType")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${orderInfoInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
