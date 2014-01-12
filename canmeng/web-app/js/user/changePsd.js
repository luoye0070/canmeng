
$(document).ready(function () {
    $("#oldPsd")[0].focus();
    $('#updatePsd_form').validate(
        {
            rules: {
                oldPsd: {
                    required: true
                },
                newPsd: {
                    minlength: 6,
                    maxlength: 16,
                    required: true
                },
                passWord_confirm:{
                    required:true,
                    equalTo:"#newPsd"
                }
            },
            messages:{
                oldPsd:
                {
                    required:'请输入旧密码!'
                },
                newPsd:{
                    required:"请输入新密码!",
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
                    .text('OK!').addClass('valid')
                    .closest('.control-group').removeClass('error').addClass('success');
            }
        });


});
