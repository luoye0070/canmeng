<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 14-7-28
  Time: 下午5:33
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="lj.enumCustom.ReserveType" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main_template"/>
    <title>确认订单</title>
    <style type="text/css">

    </style>
    <link rel="stylesheet" href="${resource(dir: "js/timePicker", file: "timePicker.css")}" type="text/css"
          media="screen"/>
    <script type="text/javascript" src="${resource(dir: "js/timePicker", file: "jquery.timePicker.min.js")}"></script>
    <link rel="stylesheet" href="${resource(dir: "js/Datepicker", file: "datepicker.css")}" type="text/css"
          media="screen"/>
    <script type="text/javascript" src="${resource(dir: "js/Datepicker", file: "bootstrap-datepicker.js")}"></script>

    <script type="text/javascript" src="${resource(dir: "js/common", file: "cart.js")}"></script>
    <link href="${resource(dir: "css", file: "cart_page.css")}" rel="stylesheet"/>
    <script type="text/javascript">
        $(function () {
            //初始化餐车
            initCart({
                addToCartUrl: "${createLink(controller: "cartOfCustomerAjax",action: "addFoodToCart")}",
                cartsAndDishesUrl: "${createLink(controller: "cartOfCustomerAjax",action: "getCartsAndDishes")}",
                checkOutUrl: "${createLink(controller: "cartOfCustomer",action: "cartCheckout")}",
                imgUrl: "${createLink(controller: "imageShow", action: "downloadThumbnail", params: [width: 70,height: 70])}",
                delDishFromCartUrl: "${createLink(controller: "cartOfCustomerAjax",action: "delDish")}",
                updateDishOfCartUrl: "${createLink(controller: "cartOfCustomerAjax",action: "updateDish")}",
                parentTag: "#checkout_carts",
                headDisplay: "none"
            });

            //日期选择器
            $("#date").datepicker({format: "yyyy-mm-dd"});
            //时间选择器
            $("#time").timePicker({step: 15});
            $("#time").change(function () {
                var timeV = $("#time").val();
                if (timeV.length > 0) {
                    $("#time").val(timeV + ":00");
                }
            });
        });
        function showAddressManageDialog() {
            $("#ratyService").modal();
        }
    </script>
</head>

<body>
<div class="mc_main">
    <div class="mcm_top">
        订单生成
    </div>

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

        <div class="mcmc_detail">
            <div id="checkout_carts">

            </div>

            <form action="${createLink(controller: "cartOfCustomer",action: "cartCheckout")}" method="post">
                <div class="address">
                    <table class="table table-striped table-bordered table-condensed">
                        <thead>
                        <tr>
                            <th>选择</th>
                            <th>联系人</th>
                            <th>所在地区</th>
                            <th>街道地址</th>
                            <th>电话/手机</th>
                            <th><a href="#" onclick="showAddressManageDialog()">地址管理</a></th>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${addresses}" status="i" var="address">
                            <tr>
                                <td>
                                    <input type="radio" value="${address.id}"
                                           name="addressId" ${(address.id == defaultAddrId) ? "checked='checked'" : ""}/>
                                </td>
                                <td>${address.linkManName}</td>
                                <td>${address.province} ${address.city} ${address.area}</td>
                                <td>${address.street}</td>
                                <td>${address.phone}</td>
                                <td></td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>

                <div class="option">
                    <div class="control-group">
                        <label class="control-label">用餐日期</label>

                        <div class="controls">
                            <input class="input-xlarge focused" type="text" name="date" id="date"
                                   value="${params.date ?: lj.FormatUtil.dateFormat(new Date())}"/>
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label">用餐类型</label>

                        <div class="controls">
                            <select name="reserveType" class="mcmcsf_input">
                                <g:each in="${lj.enumCustom.ReserveType.reserveTypes}">
                                    <option value="${it.code}" ${(lj.Number.toInteger(params.reserveType) == it.code) ? "selected='selected'" : ""}>${it.label}</option>
                                </g:each>
                            </select>
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label">希望送到时间</label>

                        <div class="controls">
                            <input class="input-xlarge focused" type="text" name="time" id="time"
                                   value="${params.time ?: lj.FormatUtil.timeFormat(new Date())}"/>
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label">联系人</label>

                        <div class="controls">
                            <input class="input-xlarge focused" type="text" name="customerName"
                                   value="${params.customerName ?: ""}"/>
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label">联系电话</label>

                        <div class="controls">
                            <input class="input-xlarge focused" type="text" name="phone" value="${params.phone ?: ""}"/>
                        </div>
                    </div>

                </div>

                <div class="operation">
                    <input type="submit"
                           value="${message(code: 'default.button.submit.label', default: 'submit')}"
                           class="btn btn-primary"/>
                </div>

            </form>
        </div>

    </div>
</div>


<!--rating modal's content-->
<div id="ratyService" class="modal hide fade" style="width: 850px;height: auto">

    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" onclick="location.reload()">&times;</button>

        <h3>管理地址</h3>
    </div><!--Modal header-->
    <div class="modal-body" style="height: auto">
        <iframe id="imageSelect"
                src="${createLink(controller: "user", action: "userAddresses", params: [showInDialog: true])}"
                width="800px" height="500px"></iframe>
    </div><!--Modal body-->
    <div class="modal-footer">
        <a href="#" class="btn" data-dismiss="modal" onclick="location.reload()">Close</a>
        %{--<a href="#" class="btn btn-primary">Save changes</a>--}%
    </div><!--Modal footer-->
</div> <!--Modal-->
</body>
</html>