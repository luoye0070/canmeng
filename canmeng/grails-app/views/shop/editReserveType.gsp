
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="lj.FormatUtil" %>
<html>
<head>
    <meta name="layout" content="main_template"/>
  <title>预订类型设置</title>
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

        <g:if test="${err}">
            <div class="alert alert-error" STYLE="color: RED">
                <g:message error="${err}" message=""/>
            </div>
        </g:if>
        <g:if test="${msg}">
            <div class="alert alert-info">
                ${msg}
            </div>
        </g:if>

<form method="post" action="editReserveType" class="form-horizontal">

    <div class="control-group">
        <label class="control-label">${reserveTypes.morning.reserveType.label}</label>

        <div class="controls">
            <input type="hidden" name="id" value="${reserveTypes.morning.id}"/>
            <input type="hidden" name="reserveType" value="${reserveTypes.morning.reserveType.code}"/>

            开始<input type="text" name="beginTime" value="${FormatUtil.timeFormat(reserveTypes.morning.beginTime)}"/>
            结束<input type="text" name="endTime" value="${FormatUtil.timeFormat(reserveTypes.morning.endTime)}"/>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">   ${reserveTypes.noon.reserveType.label}
        </label>

        <div class="controls">
            <input type="hidden" name="id" value="${reserveTypes.noon.id}"/>
            <input type="hidden" name="reserveType" value="${reserveTypes.noon.reserveType.code}"/>

            开始<input type="text" name="beginTime" value="${FormatUtil.timeFormat(reserveTypes.noon.beginTime)}"/>
            结束<input type="text" name="endTime" value="${FormatUtil.timeFormat(reserveTypes.noon.endTime)}"/>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">  ${reserveTypes.night.reserveType.label}
        </label>

        <div class="controls">
            <input type="hidden" name="id" value="${reserveTypes.night.id}"/>
            <input type="hidden" name="reserveType" value="${reserveTypes.night.reserveType.code}"/>

           开始 <input type="text" name="beginTime" value="${FormatUtil.timeFormat(reserveTypes.night.beginTime)}"/>
            结束<input type="text" name="endTime" value="${FormatUtil.timeFormat(reserveTypes.night.endTime)}"/>
        </div>
    </div>




    <div class="form-actions">
        <g:submitButton name="create"  class="btn btn-primary" value="${message(code: 'default.button.create.label', default: 'Create')}" />
    </div>

</form>
        </div>
    </div>
</body>
</html>