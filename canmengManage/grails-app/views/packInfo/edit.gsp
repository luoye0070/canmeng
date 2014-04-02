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
    <g:form controller="PackInfo" action="save" method="post" >
        <table class="tableForm">
            <tr>
                <td><g:message code="packInfo.name.label" /></td>
                <td><input type="text" name="name" value="${PackInfo?.name}"/></td>
             </tr>
             <tr>
                <td><g:message code="packInfo.description.label" /></td>
                <td><input type="text" name="description" value="${PackInfo?.description}"/></td>
            </tr>
        </table>
        <input type="hidden" name="ids"/>
    </g:form>
</body>
</html>