<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main_template"/>
    <title><g:message code='userInfo.login.label'/></title>
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
        width: 1000px;
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
        <div id="login_name"><g:message code='userInfo.login.label'/></div>

        <div id="login_banner"></div>
        <g:if test="${errors != null}">
            <div class="alert alert-error">
                ${errors}
            </div>
        </g:if>
        <g:if test="${msg != null}">
            <div class="alert alert-success">
                ${msg}
            </div>
        </g:if>
    </div>
    <div class="row">
        <div class="span11">
            <g:form class="form-horizontal" method="POST" action="auth">
                <fieldset>




                    <div class="control-group">
                        <label class="control-label" for="userName"><g:message code='userInfo.userName.label'/></label>

                        <div class="controls">
                            <input type="text" id="userName" name="userName" placeholder="" class="input-xlarge"
                                   value="${user?.userName}"/>
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="passWord"><g:message code='userInfo.passWord.label'/></label>

                        <div class="controls">
                            <input type="password" id="passWord" name="passWord" placeholder="" class="input-xlarge"/>
                            <g:link controller="user" action="findPsd">忘记密码？</g:link>
                        </div>
                    </div>

                    <div class="control-group" id="login_area_b">
                        <!-- Button -->
                        <div class="controls" id="login_area">
                            <input type="submit" class="login_btn" style="width: 172px;font-weight: bold;font-size: 14px;"  value="登录"/> |  <a
                                href="${createLink(controller: 'user', action: 'register')}">注册用户</a>
                        </div>
                    </div>
                </fieldset>
            </g:form>

        </div>
    </div>
</div>
<g:javascript src="jquery.validate.min.js"/>
<g:javascript src="login/index.js"/>
</body>
</html>