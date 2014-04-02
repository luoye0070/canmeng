<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>创建订单</title>
</head>

<body>
<div class="row">
    <div class="span11 offset1">
        <g:if test="${flash.error}">
            <div class="alert alert-error">
                ${flash.error}
            </div>
        </g:if>
        <g:if test="${flash.message}">
            <div class="alert alert-info">
                ${flash.message}
            </div>
        </g:if>



        <form action="${createLink(controller: "customer", action: "createOrder")}">
            饭店ID：<input type="text" name="restaurantId" id="restaurantId" value="${params.restaurantId}"/> <br/>
            桌位ID：<input type="text" name="tableId" id="tableId" value="${params.tableId}"/>   <br/>
            <input type="submit" value="${message(code: 'default.button.createOrder.label', default: 'createOrder')}"/>
        </form>
    </div>
</div>
</body>
</html>