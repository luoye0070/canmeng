<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main_template"/>
    <title><g:message code='userInfo.viewUser.label'/></title>
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
        <div class="mcmr_title">&nbsp;&nbsp;&nbsp;&nbsp;个人资料</div>

        <div class="mcmr_main">
            <div class="span11">

                <form class="form-horizontal" method="POST" id="register_form" action="handleRegister">
                    <fieldset>
                        <g:if test="${msg != null}">
                            <div class="alert alert-success">
                                ${msg}
                            </div>
                        </g:if>

                        <g:if test="${errors != null}">
                            <div class="alert alert-error">
                                ${errors}
                            </div>
                        </g:if>

                        <div class="control-group">
                            <label class="control-label" for="userName"><g:message
                                    code='userInfo.userName.label'/></label>

                            <div class="controls">
                                <input type="text" id="userName" name="userName" placeholder="" class="input-xlarge"
                                       value="${user?.userName}" readonly="true">
                            </div>
                        </div>


                        <div class="control-group">
                            <label class="control-label" for="email"><g:message code='userInfo.email.label'/></label>

                            <div class="controls">
                                <input type="text" id="email" name="email" placeholder="" class="input-xlarge"
                                       readonly="true" value="${user?.email}">
                                <g:if test="${user?.emailEnabled}">
                                    <span class="label label-success">已验证</span>
                                </g:if>
                                <g:else>
                                    <span class="label label-warning">未验证</span>
                                </g:else>
                            </div>
                        </div>

                        <div class="control-group">
                            <label class="control-label" for="phone"><g:message code='userInfo.phone.label'/></label>

                            <div class="controls">
                                <input type="text" id="phone" name="phone" placeholder="" class="input-xlarge"
                                       readonly="true" value="${user?.phone}">
                                <g:if test="${user?.phoneEnabled}">
                                    <span class="label label-success">已验证</span>
                                </g:if>
                                <g:else>
                                    <span class="label label-warning">未验证</span>
                                </g:else>
                            </div>
                        </div>

                        <div class="control-group">
                            <label class="control-label" for="loginTime"><g:message
                                    code='userInfo.loginTime.label'/></label>

                            <div class="controls">
                                <input type="text" id="loginTime" name="loginTime" placeholder="" class="input-xlarge"
                                       value="${user?.loginTime}" readonly="true">
                            </div>
                        </div>

                        <div class="control-group">
                            <label class="control-label" for="loginIp"><g:message
                                    code='userInfo.loginIp.label'/></label>

                            <div class="controls">
                                <input type="text" id="loginIp" name="loginTime" placeholder="" class="input-xlarge"
                                       value="${user?.loginIp}" readonly="true">
                            </div>
                        </div>

                        <div class="control-group">
                            <label class="control-label" for="defaultAddrId"><g:message
                                    code='userInfo.defaultAddrId.label'/></label>

                            <div class="controls">
                                <input type="text" id="defaultAddrId" name="defaultAddrId" placeholder=""
                                       class="input-xlarge"
                                       value="${user?.defaultAddrId}" readonly="true">
                            </div>
                        </div>
                    </fieldset>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>