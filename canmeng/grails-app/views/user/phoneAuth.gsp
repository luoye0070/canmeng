<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main_template"/>
    <link rel="stylesheet" href="${resource(dir: 'css/user', file: 'register.css')}">
    <title><g:message code='userInfo.mailAuth.label'/></title>
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
        <div class="mcmr_title">&nbsp;&nbsp;&nbsp;&nbsp;手机号验证</div>

        <div class="mcmr_main">
            <div class="span7">
                <g:if test="${phone != null && phoneEnabled}">
                    <div id="alert" class="alert alert-info">
                        您已经验证过手机了。<br/>
                        您可以输入新的手机号码，更换验证手机！
                    </div>
                </g:if>
                <g:else>
                    <div id="alert">

                    </div>
                </g:else>


                <form class="well form-inline" id="phoneAuth_form">
                    <input type="text" class="span3" id="phone" name="phone" placeholder="手机号码" value="${phone}"/>
                    <input type="button" id="phoneAuth_bt" class="btn btn-success" value="发  送"/>
                    <button class="btn btn-info" id="msgBt"></button>
                </form>

                <form class="well form-inline" id="emailActive_form">
                    <input type="text" class="span3" id="authCode" name="authCode" placeholder="验证码"/>
                    <input type="button" id="phoneActive_bt" class="btn btn-success" value="提  交"/>
                </form>

            </div>
        </div>
    </div>
</div>
<g:javascript src="jquery.validate.min.js"/>
<g:javascript src="user/phoneauth.js"/>
<script>

</script>
</body>
</html>