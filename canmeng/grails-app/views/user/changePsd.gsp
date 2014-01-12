<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main_template"/>
    <link rel="stylesheet" href="${resource(dir: 'css/user', file: 'register.css')}">
    <title><g:message code='userInfo.changePwd.label'/></title>
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
        <div class="mcmr_title">&nbsp;&nbsp;&nbsp;&nbsp;密码修改</div>

        <div class="mcmr_main">
            <div class="span7">
        <form class="form-horizontal" method="POST" id="updatePsd_form" action="updatePsd">
            <fieldset>
                <g:if test="${errors != null}">
                    <div class="alert alert-error">
                        ${errors}
                    </div>
                </g:if>

                <div class="control-group">
                    <label class="control-label" for="oldPsd"><g:message code='userInfo.oldPsd.label'/></label>
                    <div class="controls">
                        <input type="password" id="oldPsd" name="oldPsd" placeholder="" class="input-xlarge">
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label" for="newPsd"><g:message code='userInfo.newPsd.label'/></label>
                    <div class="controls">
                        <input type="password" id="newPsd" name="newPsd" placeholder="" class="input-xlarge">
                        <p class="help-block">密码长度大于等于6，小于16</p>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label" for="passWord_confirm"><g:message code='userInfo.passWord_confirm.label'/></label>

                    <div class="controls">
                        <input type="password" id="passWord_confirm" name="passWord_confirm" placeholder=""
                               class="input-xlarge">
                    </div>
                </div>

                <div class="control-group">
                    <!-- Button -->
                    <div class="controls">
                        <button id="updatePsd" class="btn btn-success"><g:message code='confirm.label'/> </button>
                    </div>
                </div>

            </fieldset>
        </form>
    </div>
</div>
 </div>
    </div>
<g:javascript src="jquery.validate.min.js"/>
<g:javascript src="user/changePsd.js"/>
</body>
</html>