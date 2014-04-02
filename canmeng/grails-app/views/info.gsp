<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title><g:message code='info.label'/></title>
</head>

<body>
<g:if test="${params.success}">
    <div class="alert  alert-success">
        ${params.message}
    </div>
</g:if>
<g:else>
    <div class="alert  alert-error">
    ${params.errors}
    </div>
</g:else>

</body>
</html>