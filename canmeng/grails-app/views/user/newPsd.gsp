<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main_template"/>
    <link rel="stylesheet" href="${resource(dir: 'css/user', file: 'register.css')}">

    <title>新密码设置</title>
    <style type="text/css">
    .mc_main {
        width: 1000px;
        height: auto;
        margin: 0px 50px;
        background-color: #FFFFFF;
        float: left;
    }

    .mcm_top {
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

    .mcm_content {
        width: 960px;
        height: auto;
        margin: 20px;
    }

    .mcmc_ssl {
        width: 960px;
        margin-top: 10px;
        margin-bottom: 10px;
        font-size: 14px;
    }

    .mcmcs_field {
        width: 320px;
        float: left;
    }

    .mcmcsf_input {
        width: 120px;
    }

    .mcmcs_field_middle {
        width: 160px;
        float: left;
    }

    .mcmcsf_input_middle {
        width: 80px;
    }

    .mcmcs_field_small {
        width: 80px;
        float: left;
    }

    .mcmcsf_input_small {
        width: 40px;
    }

    .m_list {
        width: 960px;
        height: auto;
    }

    .m_list li {
        width: 182px;
        margin: 5px;
    }

    .ml_row_img {
        width: 140px;
        height: 120px;
        overflow: hidden;
        margin: 0px auto;
    }

    .ml_row_txt {
        width: 140px;
        height: 30px;
        line-height: 30px;
        margin: 0px auto;
        overflow: hidden;
    }
    </style>
</head>

<body>

<div class="mc_main">
    <div class="mcm_top">
        新密码设置
    </div>

    <div class="mcm_content">
    <div class="span11">
        <form class="form-horizontal" method="POST" id="newPsd_form" action="newPsdSave">
            <fieldset>

                <g:if test="${errors != null}">
                    <div class="alert alert-error">
                        ${errors}
                    </div>
                </g:if>

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

                    <div class="controls">
                        <input type="submit" class="btn btn-success" value="提&nbsp;&nbsp;交"/>
                    </div>
                </div>
            </fieldset>
        </form>
    </div>
</div>

    </div>
<g:javascript src="jquery.validate.min.js"/>
<script type="text/javascript">

    $(document).ready(function () {

        $("#passWord")[0].focus();

        $('#newPsd_form').validate(
                {
                    rules: {
                        passWord: {
                            minlength: 6,
                            maxlength: 16,
                            required: true
                        },
                        passWord_confirm:{
                            required:true,
                            equalTo:"#passWord"
                        }
                    },
                    messages:{
                        passWord:{
                            required:"密码必填!",
                            minlength:"长度不能小于6",
                            maxlength:"长度不能大于16"
                        },
                        passWord_confirm:{
                            required:"请再次输入密码!",
                            equalTo:"两次输入密码不一样"
                        }
                    },
                    highlight: function (element) {
                        $(element).closest('.control-group').removeClass('success').addClass('error');
                    },
                    success: function (element) {
                        element
                                .addClass('valid')
                                .closest('.control-group').removeClass('error').addClass('success');
                    }
                });


    });

</script>
</body>
</html>