<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main_template"/>
    <link rel="stylesheet" href="${resource(dir: 'css/user', file: 'register.css')}">
    <title>地址管理</title>
    <style type="text/css">
    .mc_main {
        width: 1000px;
        height: auto;
        margin: 0px 50px;
        background-color: #FFFFFF;
        float: left;
    }

    .mcm_left {
        width: 250px;
        height: auto;
        float: left;
        margin: 10px 0px;
    }

    .mcm_right {
        width: 740px;
        height: auto;
        margin-right: 10px;
        float: left;
        margin: 10px 0px;
    }

    .mcmr_title {
        width: 738px;
        height: 40px;
        line-height: 40px;
        font-size: 14px;
        font-weight: bolder;
        border: 1px solid #DBD7D8;
    }

    .mcmr_main {
        width: 740px;
        height: auto;
        float: left;
        margin-top: 20px;
    }
    </style>
</head>

<body>
<div class="mc_main">
    <div class="mcm_left">
        <g:render template="../layouts/userNavigation"></g:render>
    </div>

    <div class="mcm_right">
        <div class="mcmr_title">&nbsp;&nbsp;&nbsp;&nbsp;地址管理</div>

        <div class="mcmr_main">
            <div class="span11">
                <form class="form-horizontal" id="addresses_form" style="width: 710px;">
                    <div id="alert"></div>
                    <fieldset>
                        <div class="control-group">
                            <label class="control-label" for="linkManName">联系人</label>

                            <div class="controls">
                                <input type="text" class="input-xlarge" id="linkManName" name="linkManName">
                            </div>
                        </div>

                        <input type="hidden" id="cityUrl"
                               value="${createLink(controller: "areaParam", action: "getCityList")}"/>
                        <input type="hidden" id="areaUrl"
                               value="${createLink(controller: "areaParam", action: "getAreaList")}"/>

                        <div class="control-group">
                            <label class="control-label" for="province">所在地区</label>

                            <div class="controls">
                                省
                                <select id="province" name="province">
                                    <option value="">请选择</option>
                                    <g:each in="${provinces}">
                                        <option value="${it.id}">${it.province}</option>
                                    </g:each>
                                </select><br/>
                                市
                                <select id="city" name="city">
                                    <option value="">请选择</option>
                                </select><br/>
                                区
                                <select id="area" name="area">
                                    <option value="">请选择</option>
                                </select>
                            </div>
                        </div>

                        <div class="control-group">
                            <label class="control-label" for="street">街道地址</label>

                            <div class="controls">
                                <textarea class="input-xlarge" id="street" name="street" rows="1"
                                          placeholder="为了您的方便，请填写详细地址"></textarea>
                            </div>
                        </div>

                        <div class="control-group">
                            <label class="control-label" for="phone">电话号码</label>

                            <div class="controls">
                                <input type="text" class="input-xlarge" id="phone" name="phone">
                            </div>
                        </div>

                        <div class="control-group">
                            <label class="control-label" for="defaultAddrId">设为默认</label>

                            <div class="controls">
                                <label class="checkbox">
                                    <input type="checkbox" id="defaultAddrId" value="1" name="defaultAddrId">
                                </label>
                            </div>
                        </div>

                        <div class="form-actions">
                            <input type="button" class="btn send_btn" id="addAddress" value="新增"/>
                            <input type="button" class="btn send_btn" id="cancel" value="取消"/>
                            <input type="button" class="btn send_btn" id="save" value="保存修改"/>
                        </div>
                    </fieldset>
                </form>

                <table class="table table-striped table-bordered table-condensed" style="width: 710px;">
                    <thead>
                    <tr>
                        <th>联系人</th>
                        <th>所在地区</th>
                        <th>街道地址</th>
                        <th>电话/手机</th>
                        <th>默认地址</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>

                    </tbody>
                </table>
            </div>


            <div id="myModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
                 aria-hidden="true">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>

                    <h3 id="myModalLabel">删除地址</h3>
                </div>

                <div class="modal-body">
                    <h4>确认删除该地址?</h4>
                </div>

                <div class="modal-footer">
                    <input type="button" id="close_dialog" class="btn" data-dismiss="modal" value="关闭">
                    <input type="button" id="del_dialog" class="btn btn-primary" value="删除">
                </div>
            </div>
        </div>
    </div>
</div>
<g:javascript src="common/address.js"/>
<g:javascript src="jquery.validate.min.js"/>
<g:javascript src="common/jqueryV.js"/>
<g:javascript src="user/userAddresses.js"/>
<script type="text/javascript">

</script>
</body>

</html>