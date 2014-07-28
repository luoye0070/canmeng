<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 14-7-28
  Time: 下午5:33
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
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
        详情
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
                    <div class="outerList" id="cart_list">
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

        </div>

    </div>
</div>
</body>
</html>