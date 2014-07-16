/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-7-17
 * Time: 上午12:10
 * To change this template use File | Settings | File Templates.
 */
(function ($) {
    $.fn.addFoodToCart = function (options) {
        return this.each(function () {
            $(this).click(function () { //注册点击事件
                var addToCartUrl = options.addToCartUrl + "?rand=" + Math.random();
                var cartListUrl = options.cartListUrl + "?rand=" + Math.random();
                var dishesListUrl= options.dishesListUrl  + "?rand=" + Math.random();
                var foodId = $(this).attr("foodId");
                $.getJSON(addToCartUrl, {foodId: foodId}, function (data) {
                    if (data.recode.code == 0) {//成功,添加到界面上购物车中

                    }
                    else {//显示错误信息
                        alert(data.recode.label);
                    }
                });
            });
        });
    }
})(jQuery);