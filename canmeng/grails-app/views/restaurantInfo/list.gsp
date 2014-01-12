
<%@ page import="lj.data.RestaurantInfo" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'restaurantInfo.label', default: 'RestaurantInfo')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-restaurantInfo" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-restaurantInfo" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="name" title="${message(code: 'restaurantInfo.name.label', default: 'Name')}" />
					
						<g:sortableColumn property="image" title="${message(code: 'restaurantInfo.image.label', default: 'Image')}" />
					
						<g:sortableColumn property="areaId" title="${message(code: 'restaurantInfo.areaId.label', default: 'Area Id')}" />
					
						<g:sortableColumn property="province" title="${message(code: 'restaurantInfo.province.label', default: 'Province')}" />
					
						<g:sortableColumn property="city" title="${message(code: 'restaurantInfo.city.label', default: 'City')}" />
					
						<g:sortableColumn property="area" title="${message(code: 'restaurantInfo.area.label', default: 'Area')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${restaurantInfoInstanceList}" status="i" var="restaurantInfoInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${restaurantInfoInstance.id}">${fieldValue(bean: restaurantInfoInstance, field: "name")}</g:link></td>
					
						<td>${fieldValue(bean: restaurantInfoInstance, field: "image")}</td>
					
						<td>${fieldValue(bean: restaurantInfoInstance, field: "areaId")}</td>
					
						<td>${fieldValue(bean: restaurantInfoInstance, field: "province")}</td>
					
						<td>${fieldValue(bean: restaurantInfoInstance, field: "city")}</td>
					
						<td>${fieldValue(bean: restaurantInfoInstance, field: "area")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${restaurantInfoInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
