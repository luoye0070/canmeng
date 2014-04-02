<%@ page import="lj.data.AreaInfo" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'areaInfo.label', default: 'AreaInfo')}" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
	</head>
	<body>
    <g:form controller="AreaInfo" action="save" method="post" >
        <table class="tableForm">
            <tr>
                <td><g:message code="areaInfo.provinceId.label" /></td>
                <td><input type="text" name="provinceId" value="${provinceInfo?.provinceId}"/></td>
            </tr>
            <tr>
                <td><g:message code="areaInfo.city.label" /></td>
                <td><input type="text" name="city" value="${provinceInfo?.city}"/></td>
            </tr>
        </table>
        <input type="hidden" name="ids"/>
    </g:form>
	</body>
</html>
