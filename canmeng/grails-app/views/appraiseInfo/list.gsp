
<%@ page import="lj.data.AppraiseInfo" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'appraiseInfo.label', default: 'AppraiseInfo')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-appraiseInfo" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-appraiseInfo" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="orderId" title="${message(code: 'appraiseInfo.orderId.label', default: 'Order Id')}" />
					
						<g:sortableColumn property="type" title="${message(code: 'appraiseInfo.type.label', default: 'Type')}" />
					
						<g:sortableColumn property="hygienicQuality" title="${message(code: 'appraiseInfo.hygienicQuality.label', default: 'Hygienic Quality')}" />
					
						<g:sortableColumn property="serviceAttitude" title="${message(code: 'appraiseInfo.serviceAttitude.label', default: 'Service Attitude')}" />
					
						<g:sortableColumn property="deliverySpeed" title="${message(code: 'appraiseInfo.deliverySpeed.label', default: 'Delivery Speed')}" />
					
						<g:sortableColumn property="taste" title="${message(code: 'appraiseInfo.taste.label', default: 'Taste')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${appraiseInfoInstanceList}" status="i" var="appraiseInfoInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${appraiseInfoInstance.id}">${fieldValue(bean: appraiseInfoInstance, field: "orderId")}</g:link></td>
					
						<td>${fieldValue(bean: appraiseInfoInstance, field: "type")}</td>
					
						<td>${fieldValue(bean: appraiseInfoInstance, field: "hygienicQuality")}</td>
					
						<td>${fieldValue(bean: appraiseInfoInstance, field: "serviceAttitude")}</td>
					
						<td>${fieldValue(bean: appraiseInfoInstance, field: "deliverySpeed")}</td>
					
						<td>${fieldValue(bean: appraiseInfoInstance, field: "taste")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${appraiseInfoInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
