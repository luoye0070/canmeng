<%@ page import="lj.data.CityInfo" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'cityInfo.label', default: 'CityInfo')}" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
	</head>
	<body>
    <g:form controller="CityInfo" action="save" method="post" >
        <table class="tableForm">
            <tr>
                <td><g:message code="cityInfo.provinceId.label" /></td>
                <td><input type="text" name="provinceId" value="${provinceInfo?.provinceId}"/></td>
            </tr>
            <tr>
                <td><g:message code="cityInfo.city.label" /></td>
                <td><input type="text" name="city" value="${provinceInfo?.city}"/></td>
            </tr>
        </table>
        <input type="hidden" name="ids"/>
    </g:form>
	</body>
</html>
