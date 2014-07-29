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
    <link href="${resource(dir: "css",file: "cart_page.css")}" rel="stylesheet"/>
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

            <div class="cart" id="cart">

                <div class="content">
                    <div class="prompt" style="display: none" id="cart_nothing">
                        <div class="nogoods"><b></b>餐车中还没有商品，赶紧选购吧！</div>
                    </div>
                    <div class="outerList" id="cart_list" style="display: block">
                        <ul>
                            <li>
                                <div class="top">
                                    <div class="title">
                                        落叶的测试饭店
                                    </div>
                                    <div class="subtotal">
                                        小计：￥124.98
                                    </div>
                                </div>
                                <div class="innerList">
                                    <ul>
                                        <li>
                                            <div class="img">
                                                <img src="${createLink(controller: "imageShow", action: "downloadThumbnail", params: [imgUrl: foodInfo?.image,width: 70,height: 70])}"/>
                                            </div>
                                            <div class="detail">
                                                <div class="dtop">
                                                    <div class="dtlable">鱼香肉丝</div>
                                                    <div class="dtclosebt"><input type="button" value="X"/></div>
                                                </div>
                                                <div class="dbottom">
                                                    <div class="dbleft">￥12.09</div>
                                                    <div class="dbright">
                                                        <input type="button" value="─"/>
                                                        <input type="text" value="2"/>
                                                        <input type="button" value="+">
                                                    </div>
                                                </div>
                                            </div>
                                        </li>
                                        <li>
                                            <div class="img">
                                                <img src="${createLink(controller: "imageShow", action: "downloadThumbnail", params: [imgUrl: foodInfo?.image,width: 70,height: 70])}"/>
                                            </div>
                                            <div class="detail">
                                                <div class="dtop">
                                                    <div class="dtlable">鱼香肉丝</div>
                                                    <div class="dtclosebt"><input type="button" value="X"/></div>
                                                </div>
                                                <div class="dbottom">
                                                    <div class="dbleft">￥12.09</div>
                                                    <div class="dbright">
                                                        <input type="button" value="─"/>
                                                        <input type="text" value="2"/>
                                                        <input type="button" value="+">
                                                    </div>
                                                </div>
                                            </div>
                                        </li>
                                        <li>
                                            <div class="img">
                                                <img src="${createLink(controller: "imageShow", action: "downloadThumbnail", params: [imgUrl: foodInfo?.image,width: 70,height: 70])}"/>
                                            </div>
                                            <div class="detail">
                                                <div class="dtop">
                                                    <div class="dtlable">鱼香肉丝</div>
                                                    <div class="dtclosebt"><input type="button" value="X"/></div>
                                                </div>
                                                <div class="dbottom">
                                                    <div class="dbleft">￥12.09</div>
                                                    <div class="dbright">
                                                        <input type="button" value="─"/>
                                                        <input type="text" value="2"/>
                                                        <input type="button" value="+">
                                                    </div>
                                                </div>
                                            </div>
                                        </li>
                                    </ul>
                                </div>
                            </li>
                            <li>
                                <div class="top">
                                    <div class="title">
                                        落叶的测试饭店
                                    </div>
                                    <div class="subtotal">
                                        小计：￥124.98
                                    </div>
                                </div>
                                <div class="innerList">
                                    <ul>
                                        <li>
                                            <div class="img">
                                                <img src="${createLink(controller: "imageShow", action: "downloadThumbnail", params: [imgUrl: foodInfo?.image,width: 70,height: 70])}"/>
                                            </div>
                                            <div class="detail">
                                                <div class="dtop">
                                                    <div class="dtlable">鱼香肉丝</div>
                                                    <div class="dtclosebt"><input type="button" value="X"/></div>
                                                </div>
                                                <div class="dbottom">
                                                    <div class="dbleft">￥12.09</div>
                                                    <div class="dbright">
                                                        <input type="button" value="─"/>
                                                        <input type="text" value="2"/>
                                                        <input type="button" value="+">
                                                    </div>
                                                </div>
                                            </div>
                                        </li>
                                        <li>
                                            <div class="img">
                                                <img src="${createLink(controller: "imageShow", action: "downloadThumbnail", params: [imgUrl: foodInfo?.image,width: 70,height: 70])}"/>
                                            </div>
                                            <div class="detail">
                                                <div class="dtop">
                                                    <div class="dtlable">鱼香肉丝</div>
                                                    <div class="dtclosebt"><input type="button" value="X"/></div>
                                                </div>
                                                <div class="dbottom">
                                                    <div class="dbleft">￥12.09</div>
                                                    <div class="dbright">
                                                        <input type="button" value="─"/>
                                                        <input type="text" value="2"/>
                                                        <input type="button" value="+">
                                                    </div>
                                                </div>
                                            </div>
                                        </li>
                                        <li>
                                            <div class="img">
                                                <img src="${createLink(controller: "imageShow", action: "downloadThumbnail", params: [imgUrl: foodInfo?.image,width: 70,height: 70])}"/>
                                            </div>
                                            <div class="detail">
                                                <div class="dtop">
                                                    <div class="dtlable">鱼香肉丝</div>
                                                    <div class="dtclosebt"><input type="button" value="X"/></div>
                                                </div>
                                                <div class="dbottom">
                                                    <div class="dbleft">￥12.09</div>
                                                    <div class="dbright">
                                                        <input type="button" value="─"/>
                                                        <input type="text" value="2"/>
                                                        <input type="button" value="+">
                                                    </div>
                                                </div>
                                            </div>
                                        </li>
                                    </ul>
                                </div>
                            </li>
                        </ul>
                        <div class="totalInfo" id="cart_list_total">
                            总计：￥123.98
                        </div>
                    </div>

                </div>
            </div>

            <div class="option">
                <div class="control-group">
                    <label class="control-label">用餐日期</label>

                    <div class="controls">
                        <input class="input-xlarge focused" type="text" name="date"
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
                        <input class="input-xlarge focused" type="text" name="customerName" value="${params.customerName ?: ""}"/>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label">联系电话</label>

                    <div class="controls">
                        <input class="input-xlarge focused" type="text" name="phone" value="${params.phone ?: ""}"/>
                    </div>
                </div>

            </div>

            <div class="address">
                <table class="table table-striped table-bordered table-condensed" style="width: 710px;">
                    <thead>
                    <tr>
                        <th>选择</th>
                        <th>联系人</th>
                        <th>所在地区</th>
                        <th>街道地址</th>
                        <th>电话/手机</th>
                        <th><a href="#">地址管理</a></th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>
                            <input type="checkbox" value="1" name="addressId" checked="checked"/>
                        </td>
                        <td>刘兆国</td>
                        <td>陕西省 西安市 雁塔区</td>
                        <td>高新一路12号</td>
                        <td>18699178734</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>
                            <input type="checkbox" value="1" name="addressId"/>
                        </td>
                        <td>刘兆国</td>
                        <td>陕西省 西安市 雁塔区</td>
                        <td>高新一路12号</td>
                        <td>18699178734</td>
                        <td></td>
                    </tr>
                    </tbody>
                </table>
            </div>

            <div class="operation">
                <input type="submit"
                       value="${message(code: 'default.button.submit.label', default: 'submit')}"
                       class="btn btn-primary"/>
            </div>

        </div>

    </div>
</div>
</body>
</html>