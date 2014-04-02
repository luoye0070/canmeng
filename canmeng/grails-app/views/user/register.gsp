<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main_template"/>
    <link rel="stylesheet" href="${resource(dir: 'css/user', file: 'register.css')}">
    <title>用户注册</title>
    <style>
    #food_main {
        width: 1000px;
        height: auto;
        margin: 0px 50px;
        background-color: #FFFFFF;
        float: left;
        padding: 20px 0px 20px 0px;
    }

    #login_name {
        font-size: 20px;
        font-weight: bold;
        padding: 10px 0px 10px 20px;
    }

    #login_banner {
        width: 100%;
        height: 4px;
        background: url('${resource(dir:"images",file:"login_banner.gif")}');
        margin: 0px auto;
        margin-bottom: 30px;
    }
    .login_btn {
        width: 172px;
        height: 37px;
        background: url('${resource(dir:"images",file:"login_btn.gif")}');
        color: #fff;
    }

    #login_area {
        margin: 0px auto;
        padding-left: 180px;
    }
    </style>

</head>

<body>
<div id="food_main">
    <div id="legend">
        <div id="login_name"><g:message code='userInfo.register.label'/></div>

        <div id="login_banner"></div>
    </div>

    <g:if test="${errors != null}">
        <div class="alert alert-error">
            ${errors}
        </div>
    </g:if>
    <div class="row">
        <div class="span11 offset1">
            <form class="form-horizontal" method="POST" id="register_form" action="handleRegister">
                <fieldset>


                    <div class="control-group">
                        <label class="control-label" for="userName"><g:message code='userInfo.userName.label'/></label>

                        <div class="controls">

                            <input type="text" id="userName" name="userName" placeholder="" class="input-xlarge"
                                   value="${user.userName}"/>

                            <p class="help-block">用户名包含英文单词或数字，不能包含空格！长度大于4，小于12</p>
                        </div>
                    </div>


                    <div class="control-group">
                        <label class="control-label" for="passWord"><g:message code='userInfo.passWord.label'/></label>


                        <div class="controls">
                            <input type="password" id="passWord" name="passWord" placeholder="" class="input-xlarge"/>

                            <p class="help-block">密码长度大于等于6，小于16</p>
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="passWord_confirm"><g:message
                                code='userInfo.passWord_confirm.label'/></label>

                        <div class="controls">
                            <input type="password" id="passWord_confirm" name="passWord_confirm" placeholder=""
                                   class="input-xlarge"/>
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="captchaResponse"><g:message
                                code='userInfo.captchaResponse.label'/></label>

                        <div class="controls">
                            <input type="text" id="captchaResponse" name="captchaResponse" placeholder=""
                                   class="input-xlarge"/>
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="captchaResponse"></label>

                        <div class="controls">
                            <img src="${createLink(controller: "user", action: "getValidateImage")}?rand=${java.lang.Math.random()}"
                                 onclick="changeJcaptcha(this)"/>

                            <p class="help-block">点击图片切换验证码</p>
                        </div>
                    </div>

                    <div class="control-group">
                        <!-- Button -->
                        <div class="controls" id="login_area">
                            <input type="submit" style="width: 172px;font-weight: bold;font-size: 14px;" class="login_btn" value="注册"/> &nbsp;|&nbsp; <a
                                href="${createLink(controller: 'user', action: 'login')}">已有账号,登录</a>
                        </div>
                    </div>
                </fieldset>
            </form>
        </div>
    </div>
</div>
<g:javascript src="jquery.validate.min.js"/>
<g:javascript src="user/register.js"/>
</body>
</html>