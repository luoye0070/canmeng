<form class="form-horizontal" id="findPsd_form" action="findPsd" method="post">
    <fieldset>
        <div id="alert">

        </div>
        <div class="control-group">
            <label class="control-label" for="findType">找回方式</label>

            <div class="controls">
                <select id="findType" name="findType">
                    <option value="none"></option>
                    <option value="email">邮箱找回</option>
                    <option value="phone">手机找回</option>
                    <option value="question">密保找回</option>
                    <option value="man">人工帮助</option>
                </select>
            </div>
        </div>


        <div id="authCode_div">
            <div class="control-group">

                <label class="control-label" for="authCode">验证码</label>

                <div class="controls">
                    <input type="text" id="authCode" name="authCode" class="input-xlarge" value=""/> <input type="button" id="authCode_bt" value=""/>
                </div>
            </div>
        </div>

        <div id="question_div">
            <div class="control-group">
                <label class="control-label" for="answer">密保答案</label>

                <div class="controls">
                    <input type="text" id="answer" name="answer" class="input-xlarge" value=""/>
                </div>
            </div>
        </div>

        <div class="control-group">
            <div class="controls">
                <input type="submit" class="btn btn-success" value="上一步"/>
                <input type="button" id="submit" class="btn btn-success" value="下一步"/>
            </div>
        </div>
    </fieldset>
</form>

<script type="text/javascript">
    $(document).ready(function () {
        var authCode_div = $("#authCode_div").hide();
        var question_div = $("#question_div").hide();
        var authCode_bt=$("#authCode_bt").hide();
        var alert = $("#alert");
        var submit = $("#submit").hide();

        var findType = $("#findType").bind("change", function () {
            var type = findType.children("option:selected").val();
            if (type == "none") {
                alert.removeClass("alert alert-info");
            }
            //初始话
            submit.hide();
            time=60;
            timeOut=null;
            authCode_bt.hide();
            $("#authCode").val("");
            $("#quesiton").val("");

            if (type == "man") {
                alert.addClass("alert alert-info");
                alert.html("请拨打电话进行更改!");
            } else {
                $.ajax({
                    url:"./findPsdInfo",
                    dataType:'json',
                    data:{
                        findType:type
                    },
                    success:function (data) {
                        if (data.success) {
                            submit.show();
                            refreshTime();
                            if (type == "question") {
                                question_div.show();
                                authCode_div.hide();
                            } else if (type == "email" || type == "phone") {
                                question_div.hide();
                                authCode_div.show();
                                authCode_bt.show();
                            }
                        }
                        alert.addClass("alert alert-info");
                        alert.html(data.msg);
                    }
                });


            }
        });

        findType.val("");

        var findPsd_form = $("#findPsd_form");
        var v1 = findPsd_form.validate(
                {
                    rules:{
                        answer:{
                            required:true
                        }
                    },
                    messages:{
                        email:{
                            required:'请输入密保答案!'
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

        var v2 = findPsd_form.validate(
                {
                    rules:{
                        authCode:{
                            required:true,
                            digits:true,
                            rangelength:[6, 6]
                        }
                    },
                    messages:{
                        authCode:{
                            required:'请填写邮箱!',
                            digits:'验证码格式不正确!',
                            rangelength:"验证码必须是6位!"
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
        var submit = $("#submit").bind("click", function () {
            var type = findType.children("option:selected").val();
            var data = { findType:type}
            if (type == "email" || type == "phone") {
                if (!v2.form())
                    return;
                data['authCode'] = $("#authCode").val();
            } else if (type == "question") {
                if (!v1.form())
                    return;
                data['answer'] = $("#answer").val();
            }

            $.ajax({
                url:"./ajaxFindPsdSecond",
                dataType:'json',
                data:data, success:function (data) {
                    if (data.success)
                        location.href = "./newPsd";
                    else {
                        alert.html(data.msg);
                    }
                }
            });

        });


        authCode_bt.bind("click",function(){
            if(timeOut!=null)
            return;

            var type = findType.children("option:selected").val();
            $.ajax({
                url:"./findPsdInfo",
                dataType:'json',
                data:{
                    findType:type
                },
                success:function (data) {
                    if (data.success) {
                        refreshTime();
                    }
                    alert.addClass("alert alert-info");
                    alert.html(data.msg);
                }
            });
        });
        //时间递减
        var time = 60;
        var timeOut = null;
        var refreshTime = function () {
            time--;
            if (time <= 0) {
                time = 60;
                timeOut = null;
                authCode_bt.addClass("btn btn-success").val("发送")
                return;
            }
            else {
                authCode_bt.addClass("btn btn-info").val(time + "秒后可重新获取");
                timeOut = setTimeout(function () {
                    refreshTime();
                }, 1000);
            }
        };
    });
</script>