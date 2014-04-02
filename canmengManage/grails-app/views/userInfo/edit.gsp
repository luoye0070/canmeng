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
    <g:form controller="userInfo" action="save" method="post" >
        <table class="tableForm">
            <tr>
                <td><g:message code="userInfo.userName.label" /></td>
                <td><input type="text" name="userName" value="${userInfo?.name}"/></td>
             </tr>
             <tr>
                <td><g:message code="userInfo.passWord.label" /></td>
                <td><input type="text" name="passWord" value="${userInfo?.description}"/></td>
            </tr>
            <tr>
                <td><g:message code="userInfo.email.label" /></td>
                <td><input type="text" name="email" value="${userInfo?.description}"/></td>
            </tr>
            <tr>
                <td><g:message code="userInfo.emailEnabled.label" /></td>
                <td><input type="text" name="emailEnabled" value="${userInfo?.description}"/></td>
            </tr>
            <tr>
                <td><g:message code="userInfo.phone.label" /></td>
                <td><input type="text" name="phone" value="${userInfo?.description}"/></td>
            </tr>
            <tr>
                <td><g:message code="userInfo.phoneEnabled.label" /></td>
                <td><input type="text" name="phoneEnabled" value="${userInfo?.description}"/></td>
            </tr>
            <tr>
                <td><g:message code="userInfo.question.label" /></td>
                <td><input type="text" name="question" value="${userInfo?.description}"/></td>
            </tr>
            <tr>
                <td><g:message code="userInfo.answer.label" /></td>
                <td><input type="text" name="answer" value="${userInfo?.description}"/></td>
            </tr>
            <tr>
                <td><g:message code="userInfo.loginIp.label" /></td>
                <td><input type="text" name="loginIp" value="${userInfo?.description}"/></td>
            </tr>
            <tr>
                <td><g:message code="userInfo.enabled.label" /></td>
                <td><input type="text" name="enabled" value="${userInfo?.description}"/></td>
            </tr>
            <tr>
                <td><g:message code="userInfo.defaultAddrId.label" /></td>
                <td><input type="text" name="defaultAddrId" value="${userInfo?.description}"/></td>
            </tr>
        </table>
        <input type="hidden" name="ids"/>
    </g:form>
</body>
</html>