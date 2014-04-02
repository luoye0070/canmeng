//jquery validation
$.validator.setDefaults({
    highlight:  function (element) {
        $(element).closest('.control-group').removeClass('success').addClass('error');
    },
    success:function (element) {
        element
            .addClass('valid')
            .closest('.control-group').removeClass('error').addClass('success');
    }
});

jQuery.validator.addMethod("ismobile", function (value, element) {
    var length = value.length;
    var mobile = /^(((13[0-9]{1})|(15[0-9]{1}))+\d{8})$/;
    return (length == 11 && mobile.exec(value)) ? true : false;
}, "请正确填写您的手机号码");

jQuery.validator.addMethod("isTel", function (value, element) {
    var tel = /^\d{3,4}-?\d{7,9}$/;    //电话号码格式010-12345678
    return this.optional(element) || (tel.test(value));
}, "请正确填写您的电话号码");

// 联系电话(手机/电话皆可)验证
jQuery.validator.addMethod("isPhone", function (value, element) {
    var length = value.length;
    var mobile = /^(((13[0-9]{1})|(15[0-9]{1}))+\d{8})$/;
    var tel = /^\d{3,4}-?\d{7,9}$/;
    return this.optional(element) || (tel.test(value) || mobile.test(value));
}, "请正确填写您的联系电话");