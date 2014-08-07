<%@ page import="lj.enumCustom.OrderStatus; lj.enumCustom.OrderValid; lj.enumCustom.ReserveType; lj.FormatUtil" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main_template">
    <title>我的订单</title>
    <style type="text/css">
    .mc_main{
        width: 1000px;
        height: auto;
        margin: 0px 50px;
        background-color: #FFFFFF;
        float: left;
    }
    .mcm_top{
        width: 960px;
        height: 80px;
        margin: 20px;
        margin-top: 0px;
        border-bottom: 4px solid #FF9833;
        text-indent: 1em;
        line-height: 80px;
        font-size: 20px;
        font-weight: bolder;
    }
    .mcm_content{
        width: 960px;
        height: auto;
        margin: 20px;
    }
    .mcmc_ssl {
        width: 960px;
        margin-top: 10px;
        margin-bottom: 10px;
    }
    .mcmcs_field {
        width: 280px;
        float: left;
    }
    .mcmcsf_input {
        width: 120px;
    }
    .mcmcs_field_middle {
        width: 140px;
        float: left;
    }
    .mcmcsf_input_middle {
        width: 60px;
    }
    .mcmcs_field_small {
        width: 80px;
        float: left;
    }
    .mcmcsf_input_small {
        width: 40px;
    }
    </style>
    <link rel="stylesheet" href="${resource(dir: "js/Datepicker", file: "datepicker.css")}" type="text/css"
          media="screen"/>
    <script type="text/javascript" src="${resource(dir: "js/Datepicker", file: "bootstrap-datepicker.js")}"></script>
    <script type="text/javascript">
        $(function () {
            //日期选择器
            $("#beginDate").datepicker({format: "yyyy-mm-dd"});
            $("#endDate").datepicker({format: "yyyy-mm-dd"});
            $("#beginDateDel").click(function(){
                $("#beginDate").val("");
            });
            $("#endDateDel").click(function(){
                $("#endDate").val("");
            });
        });
    </script>
</head>

<body>

<div class="mc_main">

<div class="mcm_content">
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

    <div class="mcmc_ssl">
        <form class="well form-inline" action="${createLink(controller: "customer", action: "orderList")}">

            <div class="mcmcs_field">
                <label style="float: left;">
                    日期：
                </label>
                <div class="input-append">
                    <input style="float: left;" id="beginDate" name="beginDate" type="text"
                           value="${params.beginDate}" class="mcmcsf_input_middle"/>
                    <span class="add-on"><a id="beginDateDel" class="close" href="#" style="float: left;">&times;</a></span>
                </div>
                <label style="">&nbsp;-&nbsp;</label>
                <div class="input-append">
                    <input style="float: left;" id="endDate" name="endDate" type="text"
                           value="${params.endDate}" class="mcmcsf_input_middle"/>
                    <span class="add-on"><a id="endDateDel" class="close" href="#" style="float: left;">&times;</a></span>
                </div>
            </div>

            <div class="mcmcs_field_middle" style="width: 140px">
                <label>
                    订单类型：
                </label>
                <select name="orderType" class="mcmcsf_input_middle">
                    <option value="-1" ${params.orderType == "-1" ? "selected='selected'" : ""}>全部</option>
                    <g:each in="${lj.enumCustom.OrderType.orderTypes}">
                        <option value="${it.code}" ${(lj.Number.toInteger(params.orderType) == it.code) ? "selected='selected'" : ""}>${it.label}</option>
                    </g:each>
                </select>
            </div>

            <div class="mcmcs_field_middle" style="width: 140px">
                <label>
                    用餐类型：
                </label>
                <select name="reserveType" class="mcmcsf_input_middle">
                    <option value="-1" ${params.reserveType == "-1" ? "selected='selected'" : ""}>全部</option>
                    <option value="0" ${params.reserveType == "0" ? "selected='selected'" : ""}>非预定</option>
                    <g:each in="${lj.enumCustom.ReserveType.reserveTypes}">
                        <option value="${it.code}" ${(lj.Number.toInteger(params.reserveType) == it.code) ? "selected='selected'" : ""}>${it.label}</option>
                    </g:each>
                </select>
            </div>
            <div class="mcmcs_field_middle">
                <label>
                    有效性：
                </label>
                <select name="valid" class="mcmcsf_input_middle">
                    <option value="-1" ${params.status == "-1" ? "selected='selected'" : ""}>全部</option>
                    <g:each in="${lj.enumCustom.OrderValid.valids}">
                        <option value="${it.code}" ${params.valid == it.code.toString() ? "selected='selected'" : ""}>${it.label}</option>
                    </g:each>
                </select>
            </div>

            <div class="mcmcs_field_middle">
                <label>
                    状态：
                </label>
                <select name="status" class="mcmcsf_input_middle">
                    <option value="-1" ${params.status == "-1" ? "selected='selected'" : ""}>全部</option>
                    <g:each in="${lj.enumCustom.OrderStatus.statuses}">
                        <option value="${it.code}" ${params.status == it.code.toString() ? "selected='selected'" : ""}>${it.label}</option>
                    </g:each>
                </select>
            </div>

            <div class="ms_field_small">
                <input type="submit" value="${message(code: 'default.button.search.label', default: 'search')}"
                       class="btn btn-primary"/>
            </div>

        </form>
    </div>

    <g:if test="${orderList}">
    <!--订单列表-->
    <table class="table table-striped table-bordered table-condensed">
        <thead>
        <tr>
            <g:sortableColumn property="id"
                              title="${message(code: 'orderInfo.id.label', default: 'Id')}" params="${params}"/>

            <g:sortableColumn property="restaurantId"
                              title="${message(code: 'orderInfo.restaurantName.label', default: 'Restaurant Name')}" params="${params}"/>

            %{--<g:sortableColumn property="userId" title="${message(code: 'orderInfo.userId.label', default: 'User Id')}" />--}%

            <g:sortableColumn property="tableId"
                              title="${message(code: 'orderInfo.tableName.label', default: 'Table Name')}" params="${params}"/>

            <g:sortableColumn property="date" title="${message(code: 'orderInfo.date.label', default: 'Date')}" params="${params}"/>

            <g:sortableColumn property="time" title="${message(code: 'orderInfo.time.label', default: 'Time')}" params="${params}"/>

            <g:sortableColumn property="valid" title="${message(code: 'orderInfo.valid.label', default: 'Valid')}" params="${params}"/>

            <g:sortableColumn property="status" title="${message(code: 'orderInfo.status.label', default: 'Status')}" params="${params}"/>

            <g:sortableColumn property="reserveType"
                              title="${message(code: 'orderInfo.reserveType.label', default: 'Reserve Type')}" params="${params}"/>
            <th>操作</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <g:each in="${orderList}" status="i" var="orderInfoInstance">
            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                <td>${orderInfoInstance.id}</td>

                <td style="max-width: 150px"><g:link controller="infoShow" action="shopShow"
                            id="${orderInfoInstance.restaurantId}">
                    <g:if test="${orderInfoInstance.restaurantName}">
                    ${fieldValue(bean: orderInfoInstance, field: "restaurantName")}
                    </g:if>
                    <g:else>
                        饭店&nbsp;${orderInfoInstance.restaurantId}
                    </g:else>
                </g:link></td>

                %{--<td>${fieldValue(bean: orderInfoInstance, field: "userId")}</td>--}%

                <td style="max-width: 100px">
                <g:if test="${orderInfoInstance.tableName}">
                    ${fieldValue(bean: orderInfoInstance, field: "tableName")}
                </g:if>
                <g:else>
                    <g:if test="${orderInfoInstance.tableId!=0}">
                    桌位&nbsp;${fieldValue(bean: orderInfoInstance, field: "tableId")}
                    </g:if>
                </g:else>
                </td>

                <td>${FormatUtil.dateFormat(orderInfoInstance.date)}</td>

                <td>${FormatUtil.timeFormat(orderInfoInstance.time)}</td>

                <td>${OrderValid.getLable(orderInfoInstance.valid)}</td>

                <td>${OrderStatus.getLable(orderInfoInstance.status)}</td>

                <td>${ReserveType.getLabel(orderInfoInstance.reserveType)}</td>
                <td>
                    <g:customerOrderOperation orderId="${orderInfoInstance.id}"
                    backUrl="${createLink(controller:"customer",action:  "orderList",params: params,absolute: true)}"></g:customerOrderOperation>
                 </td>
                <td>
                    <a href="${createLink(controller: "customer", action: "orderShow", params: [orderId: orderInfoInstance.id])}" class="btn btn-link">订单详情</a>
                </td>
            </tr>
        </g:each>
        </tbody>
    </table>
    <!--分页-->
    <white:paginate action="orderList" total="${totalCount ?: 0}" prev="&larr;" next="&rarr;" params="${params}"/>
    </g:if>
    <g:else>
        <div style="margin: 0px auto;">
            <label style="text-align: center">还没有订单哦</label>
        </div>
    </g:else>
</div>
</div>
</body>
</html>