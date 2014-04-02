<%--
  Created by IntelliJ IDEA.
  User: ysm
  Date: 13-7-30
  Time: 上午12:27
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="head">
  <title></title>
</head>
<body>
    <g:form controller="AppraiseInfo" action="save" method="post" >
        <table class="tableForm">
            <tr>
                <td><g:message code="AppraiseInfo.name.label" /></td>
                <td><input type="text" name="name" value="${AppraiseInfo?.name}"/></td>
             </tr>
             <tr>
                <td><g:message code="AppraiseInfo.type.label" /></td>
                <td><input type="text" name="type" value="${AppraiseInfo?.description}"/></td>
            </tr>
            <tr>
                <td><g:message code="AppraiseInfo.hygienicQuality.label" /></td>
                <td><input type="text" name="hygienicQuality" value="${AppraiseInfo?.name}"/></td>
            </tr>
            <tr>
                <td><g:message code="AppraiseInfo.serviceAttitude.label" /></td>
                <td><input type="text" name="name" value="${AppraiseInfo?.name}"/></td>
            </tr>
            <tr>
                <td><g:message code="AppraiseInfo.name.label" /></td>
                <td><input type="text" name="serviceAttitude" value="${AppraiseInfo?.name}"/></td>
            </tr>
            <tr>
                <td><g:message code="AppraiseInfo.deliverySpeed.label" /></td>
                <td><input type="text" name="deliverySpeed" value="${AppraiseInfo?.name}"/></td>
            </tr>
            <tr>
                <td><g:message code="AppraiseInfo.name.label" /></td>
                <td><input type="text" name="taste" value="${AppraiseInfo?.name}"/></td>
            </tr>
            <tr>
                <td><g:message code="AppraiseInfo.name.label" /></td>
                <td><input type="text" name="name" value="${AppraiseInfo?.name}"/></td>
            </tr>
            <tr>
                <td><g:message code="AppraiseInfo.name.label" /></td>
                <td><input type="text" name="name" value="${AppraiseInfo?.name}"/></td>
            </tr>
            <tr>
                <td><g:message code="AppraiseInfo.name.label" /></td>
                <td><input type="text" name="name" value="${AppraiseInfo?.name}"/></td>
            </tr>
            <tr>
                <td><g:message code="AppraiseInfo.name.label" /></td>
                <td><input type="text" name="name" value="${AppraiseInfo?.name}"/></td>
            </tr>
        </table>
        <input type="hidden" name="ids"/>
    </g:form>
</body>
</html>