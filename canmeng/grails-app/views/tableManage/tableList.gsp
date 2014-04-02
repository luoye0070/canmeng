
<%@ page import="lj.FormatUtil" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main_template"/>
  <title>桌位列表</title>
<style type="text/css">
    .mc_main {
        width: 1000px;
        height: auto;
        margin: 0px 50px;
        background-color: #FFFFFF;
        float: left;
    }
    </style>
</head>
<body>
<div class="mc_main">
    <div  class="span10" style="margin-left: 10px;margin-top: 10px;">

        <g:render template="../layouts/shopMenu"/>
        <g:if test="${errors != null && errors.size() > 0}">
            <div class="alert alert-error" STYLE="color: RED">
                <g:message error="${errors.get(0)}" message=""/>
            </div>
        </g:if>
        <g:if test="${flash.message}">
            <div class="alert alert-info">
                ${flash.message}
            </div>
        </g:if>

        <a href="${createLink(controller: "tableManage", action: "editTableInfo")}" class="btn btn-primary">新增桌位</a>
        <a href="${createLink(controller: "tableManage", action: "printTable")}" target="_blank" class="btn btn-primary">打印桌位标贴</a>

<g:if test="${tableList}">
    <table class="table table-striped table-bordered table-condensed">
        <thead>
        <tr>
            <g:sortableColumn property="restaurantId" title="${message(code: 'tableInfo.restaurantId.label', default: 'Restaurant Id')}" />
            <g:sortableColumn property="name" title="${message(code: 'tableInfo.name.label', default: 'Name')}" />
            <g:sortableColumn property="minPeople" title="${message(code: 'tableInfo.minPeople.label', default: 'Min People')}" />
            <g:sortableColumn property="maxPeople" title="${message(code: 'tableInfo.maxPeople.label', default: 'Max People')}" />
            <g:sortableColumn property="canMultiOrder" title="${message(code: 'tableInfo.canMultiOrder.label', default: 'Can Multi Order')}" />
            <g:sortableColumn property="canReserve" title="${message(code: 'tableInfo.canReserve.label', default: 'Can Reserve')}" />
            <th>桌位二维码</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <g:each in="${tableList}" status="i" var="tableInfoInstance">
            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

                <td><g:link action="show" id="${tableInfoInstance.tableInfo.restaurantId}">${fieldValue(bean: tableInfoInstance.tableInfo, field: "restaurantId")}</g:link></td>

                <td>${fieldValue(bean: tableInfoInstance.tableInfo, field: "name")}</td>

                <td>${fieldValue(bean: tableInfoInstance.tableInfo, field: "minPeople")}</td>

                <td>${fieldValue(bean: tableInfoInstance.tableInfo, field: "maxPeople")}</td>

                <td>${FormatUtil.boolFormat(tableInfoInstance.tableInfo.canMultiOrder)}</td>

                <td>${FormatUtil.boolFormat(tableInfoInstance.tableInfo.canReserve)}</td>

                <td>
                <g:tableQRCode restaurantId="${tableInfoInstance.tableInfo.restaurantId}" tableId="${tableInfoInstance.tableInfo.id}"></g:tableQRCode>
                </td>

                <td><a href="${createLink(controller: "tableManage",action: "editTableInfo",params: [id:tableInfoInstance.tableInfo.id])}" class="btn btn-link">编辑</a>
                    <a href="${createLink(controller: "tableManage",action: "delTableInfo",params: [ids:tableInfoInstance.tableInfo.id])}" class="btn btn-link">删除</a></td>

            </tr>
        </g:each>
        </tbody>
    </table>

    <white:paginate total="${totalCount?:0}" prev="&larr;" next="&rarr;" params="${params}" max="12"/>

    </div>
 </div>

</g:if>
<g:else>
    还没有桌位，赶快去添加吧
</g:else>
</body>
</html>