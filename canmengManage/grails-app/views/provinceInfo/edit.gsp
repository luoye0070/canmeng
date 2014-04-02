<%@ page import="lj.data.ProvinceInfo" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'provinceInfo.label', default: 'ProvinceInfo')}" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
	</head>
	<body>
    <g:form controller="ProvinceInfo" action="save" method="post" >
        <table class="tableForm">
            <tr>
                <td><g:message code="provinceInfo.province.label" /></td>
                <td><input type="text" name="province" value="${provinceInfo?.name}"/></td>
            </tr>
        </table>
        <input type="hidden" name="ids"/>
    </g:form>
	</body>
</html>
