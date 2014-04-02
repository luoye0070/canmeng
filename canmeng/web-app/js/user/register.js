function changeJcaptcha(obj) {
    obj.src = "./getValidateImage?"+Math.random();
}
$(document).ready(function () {

    $("#userName")[0].focus();

    $('#register_form').validate(
        {
            rules: {
                userName: {
                    minlength: 4,
                    maxlength: 12,
                    required: true,
                    remote:"validateUser"
                },
                passWord: {
                    minlength: 6,
                    maxlength: 16,
                    required: true
                },
                passWord_confirm:{
                    required:true,
                    equalTo:"#passWord"
                }
                 ,
                verification_code:{
                    remote:"validateResponse"
                }
            },
            messages:{
                userName:{
                    required:"用户名必填!",
                    minlength:"长度不能小于4!",
                    maxlength:"长度不能大于12!",
                    remote:"用户已经存在!"
                } ,
                passWord:{
                    required:"密码必填!",
                    minlength:"长度不能小于6",
                    maxlength:"长度不能大于16"
                },
                passWord_confirm:{
                    required:"请再次输入密码!",
                    equalTo:"两次输入密码不一样"
                },
                verification_code:{
                    remote:"验证码输入错误"
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
