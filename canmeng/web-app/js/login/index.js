
$(document).ready(function () {

    $("#userName")[0].focus();
    $('#login_form').validate(
        {
            rules: {
                userName: {
                    required: true
                } ,
                passWord:{
                    required:true
                }
            },
            messages:{
                userName:
                {
                    required:'请填写用户名!',
                    email:'请填写密码!'
                }
            }
        });

});

