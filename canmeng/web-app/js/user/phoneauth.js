jQuery.validator.addMethod("phone", function(value, element) {
   var length = value.length;
   var mobile = /^(((13[0-9]{1})|(15[0-9]{1}))+\d{8})$/;
   return this.optional(element) || (length == 11 && mobile.test(value));
    }, "请正确填写您的手机号码");
$(document).ready(function () {
    var form = $("#phoneAuth_form");
    var form1 = $("#phoneActive_form").hide();
    var validator = form.validate(
        {
            rules:{
                phone:{
                    required:true,
                    phone:true,
                    remote:"ajaxValidatePhone"
                }
            },
            messages:{
                phone:{
                    required:'请填写手机号码!',
                    phone:"手机号码不正确",
                    remote:"该手机号码已经被占用!"
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
                    required:'请填写手机号码!',
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
    var phoneAuth_bt = $("#phoneAuth_bt").bind('click', function () {
        if (!validator.form())
            return false;

        $.ajax({
            url:"./ajaxPhoneAuth",
            type:"post",
            dataType:'json',
            data:{
                phone:$("#phone").val()
            },
            success:function (data) {
                if (data.success) {
                    phoneAuth_bt.hide();
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
                alert.addClass("alert alert-error").html("手机发送失败！");
            }
        });
    });

    //提交验证码
    var phoneActive_bt = $("#phoneActive_bt").bind("click", function () {
        if (!validator1.form())
            return false;

        $.ajax({
            url:"./ajaxPhoneActive",
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
                alert.addClass("alert alert-error").html("手机验证失败!");
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
            phoneAuth_bt.show();
            msgBt.hide();
            msgBt.html("");
            timeOut = null;
            msgBt.html("如果没有收到短信，一分钟之后您可以再次点击发送，获取验证码！")
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
