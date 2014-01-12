<form class="form-horizontal" method="POST" id="findPsd_form" action="findPsdFirst">
    <fieldset>

        <div id="alert">

        </div>
        <g:if test="${error!=null}">
            <div class="alert alert-error">
                ${error}
            </div>
        </g:if>
        <div class="control-group">
            <label class="control-label" for="userName">用户名</label>

            <div class="controls">
                <input type="text" id="userName" name="userName" class="input-xlarge" value="${userName}">
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="captchaResponse"><g:message
                    code='userInfo.captchaResponse.label'/></label>

            <div class="controls">
                <input type="text" id="captchaResponse" name="captchaResponse" class="input-xlarge">
            </div>
        </div>

        <div class="control-group">
            <label class="control-label"></label>

            <div class="controls">
                <img src="${createLink(controller: "user", action: "getValidateImage")}?rand=${java.lang.Math.random()}"
                     onclick="changeJcaptcha(this)">

                <p class="help-block">点击图片切换验证码</p>
            </div>
        </div>


        <div class="control-group">
            <div class="controls">
                <input type="submit" id="submit" class="btn btn-success" value="下一步"/>
            </div>
        </div>
    </fieldset>
</form>

<script type="text/javascript">

    function changeJcaptcha(obj) {
        obj.src = "./getValidateImage?" + Math.random();
    }
    ;
    $(document).ready(function () {
        $("#findPsd_form").validate(
                {
                    onkeyup:false,
                    rules:{
                        userName:{
                            required:true ,
                            remote:"isUser"
                        },
                        captchaResponse:{
                            required:true
                            ,
                            remote:"validateResponse"
                        }
                    },
                    messages:{
                        userName:{
                            required:'请填写账号!',
                            remote:"账号不存在!"
                        }, captchaResponse:{
                            required:"请输入验证码!",
                            remote:"验证码错误!"
                        }

                    },
                    highlight:function (element) {
                        $(element).closest('.control-group').removeClass('success').addClass('error');
                    },
                    success:function (element) {
                        element
                                .text('OK!').addClass('valid')
                                .closest('.control-group').removeClass('error').addClass('success');
                    }
                });
    });
</script>