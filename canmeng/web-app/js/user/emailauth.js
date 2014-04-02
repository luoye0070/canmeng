
$(document).ready(function () {
    var form = $("#emailAuth_form");
    var form1 = $("#emailActive_form").hide();
    var validator = form.validate(
        {
            rules:{
                email:{
                    required:true,
                    email:true,
                    remote:"ajaxValidateEmail"
                }
            },
            messages:{
                email:{
                    required:'请填写邮箱!',
                    email:'邮箱格式不正确!',
                    remote:"该邮箱已经被占用!"
                }
            },
            highlight: function (element) {
                $(element).closest('.control-group').removeClass('success').addClass('error');
            },
            success: function (element) {
                element
                    .text('OK!').addClass('valid')
                    .closest('.control-group').removeClass('error').addClass('success');
            }
        });
    var validator1 = form1.validate(
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
            highlight: function (element) {
                $(element).closest('.control-group').removeClass('success').addClass('error');
            },
            success: function (element) {
                element
                    .text('OK!').addClass('valid')
                    .closest('.control-group').removeClass('error').addClass('success');
            }
        });
    var msgBt = $("#msgBt").hide();
    var alert = $("#alert");


    //发送邮件
    var emailAuth_bt = $("#emailAuth_bt").bind('click', function () {
        if (!validator.form())
            return false;

        $.ajax({
            url:"./ajaxEmailAuth",
            type:"post",
            dataType:'json',
            data:{
                email:$("#email").val()
            },
            success:function (data) {
                if (data.success) {
                    emailAuth_bt.hide();
                    msgBt.show();
                    refreshTime();
                    form1.show();
                    alert.addClass("alert alert-success");
                } else {
                    alert.addClass("alert alert-error");
                }

                alert.html(data.msg);
            },
            error:function (data) {
                alert.addClass("alert alert-error").html("邮件发送失败");
            }
        });
    });

    //提交验证码
    var emailActive_bt = $("#emailActive_bt").bind("click", function () {
        if (!validator1.form())
            return false;

        $.ajax({
            url:"./ajaxEmailActive",
            type:"post",
            dataType:'json',
            data:"authCode=" + $("#authCode").val(),
            success:function (data) {
                if (data.success) {
                    form.removeClass("well form-inline").empty();
                    form1.removeClass("well form-inline").empty();
                    alert.addClass("alert alert-success");
                } else {
                    alert.addClass("alert alert-error");
                }
                alert.html(data.msg);
            }, error:function (data) {
                alert.addClass("alert alert-error").html("邮箱验证失败!");
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
            emailAuth_bt.show();
            msgBt.hide();
            msgBt.html("");
            timeOut = null;
            msgBt.html("如果没有收到邮件，一分钟之后您可以再次点击发送，获取验证码！")
            return;
        }
        else {
            msgBt.html(time + "秒后可重新获取");
            timeOut = setTimeout(function () {
                refreshTime();
            }, 1000);
        }
    };

});
