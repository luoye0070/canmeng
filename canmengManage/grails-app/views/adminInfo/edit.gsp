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
    <g:form controller="AdminInfo" action="save" method="post" >
        <table class="tableForm">
            <tr>
                <td><g:message code="adminInfo.username.label" /></td>
                <td><input type="text" name="username" value="${AdminInfo?.username}"/></td>
             </tr>
             <tr>
                <td><g:message code="adminInfo.password.label" /></td>
                <td><input type="text" name="password" value="${AdminInfo?.password}"/></td>
            </tr>
            <tr>
                <td><g:message code="adminInfo.authority.label" /></td>
                <td><input type="text" name="authority" value="${AdminInfo?.authority}"/></td>
            </tr>
        </table>
        <input type="hidden" name="ids"/>
    </g:form>
</body>
</html>