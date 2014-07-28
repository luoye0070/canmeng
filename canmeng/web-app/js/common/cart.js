/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-7-17
 * Time: 上午12:10
 * To change this template use File | Settings | File Templates.
 */
function initCart(options){
    var delDishFromCartUrl = options.delDishFromCartUrl + "?rand=" + Math.random();
    var cartsAndDishesUrl = options.cartsAndDishesUrl + "?rand=" + Math.random();
    var checkOutUrl= options.checkOutUrl  + "?rand=" + Math.random();
    var imgUrl=options.imgUrl;
    var updateDishOfCartUrl=options.updateDishOfCartUrl  + "?rand=" + Math.random();
   /*****初始化添加cart浮动块*****/
    var cartsObj=$("#carts");
    if(typeof(cartsObj)!="undefined"){
        cartsObj.remove();
    }
    var cartsHtml='<div class="cart" id="carts">                                                               '+
                '    <div class="head">                                                                     '+
                '        <div class="jsmenu">                                                               '+
                '            <div class="shopping-amount">                                                  '+
                '                <div class="sa-left"></div>                                                '+
                '                <div class="sa-right" id="cart_dish_count">0</div>                                             '+
                '            </div>                                                                         '+
                '            <div class="jsmenu-real">                                                      '+
                '                <div class="jsr-text"><a href="'+checkOutUrl+'">去餐车结算</a></div><div class="b"></div>'+
                '            </div>                                                                         '+
                '        </div>                                                                             '+
                '    </div>                                                                                 '+
                '    <div class="content">                                                                  '+
                '        <div class="prompt" id="cart_nothing">                                             '+
                '            <div class="nogoods"><b></b>餐车中还没有商品，赶紧选购吧！</div>               '+
                '        </div>                                                                             '+
                '        <div class="outerList" id="cart_list">                                             '+
                '            <ul>                                                                           '+
                '            </ul>                                                                          '+
                '            <div class="totalInfo" id="cart_list_total">                                   '+
                '            </div>                                                                         '+
                '        </div>                                                                             '+
                '    </div>                                                                                 '+
                '</div>                                                                                     ';
    cartsObj=$(cartsHtml);
    cartsObj.appendTo("body");
  /********添加初始数据*************/
  $.getJSON(cartsAndDishesUrl,function (data) {
      if (data.recode.code == 0) {//成功,添加到界面上购物车中
          //alert(data.recode.label);
          $("#cart_nothing").css("display","none");
          var cartsAndDishes=data.cartsAndDishes;
          var cart_listObj=$("#cart_list>ul");
          var dishCount=0;
          var accountTotal=0;
          for(i=0;i<cartsAndDishes.length;i++){
              var cartInfo=cartsAndDishes[i].cartInfo;
              var cartHtml='<li>                                              '+
                    '   <div class="top">                              '+
                    '       <div class="title">                        '+
                    '           '+cartInfo.restaurantName+'                         '+
                    '       </div>                                     '+
                    '       <div class="subtotal" id="cart_'+cartInfo.id+'_subtotal">                     '+
                    '           小计：￥'+cartInfo.totalAccount+'                         '+
                    '       </div>                                     '+
                    '   </div>                                         '+
                    '    <div class="innerList" id="cart_'+cartInfo.id+'_dish_list">'+
                    '        <ul>                                      '+
                    '        </ul>                                     '+
                    '    </div>                                        '+
                    '</li>                                             ';
              var cartObj=$(cartHtml);
              cartObj.appendTo(cart_listObj);
              var dishesForCartList= cartsAndDishes[i].dishesForCartList;
              for(j=0;j<dishesForCartList.length;j++){
                  var dishesForCartInfo=dishesForCartList[j];
                  var dishesForCartHtml='<li>                                                                     '+
                      '    <div class="img">                                                    '+
                      '        <img src="'+imgUrl+'&imgUrl='+dishesForCartInfo.foodImg+'"/>                                                    '+
                      '    </div>                                                               '+
                      '    <div class="detail">                                                 '+
                      '        <div class="dtop">                                               '+
                      '            <div class="dtlable">'+dishesForCartInfo.foodName+'</div>                          '+
                      '            <div class="dtclosebt"><input type="button" delDish="true" dishId="'+dishesForCartInfo.id+'" value="X"/></div>'+
                      '        </div>                                                           '+
                      '        <div class="dbottom">                                            '+
                      '            <div class="dbleft">￥'+dishesForCartInfo.foodPrice+'</div>                            '+
                      '            <div class="dbright" dishId="'+dishesForCartInfo.id+'">                                        '+
                      '                <input type="button" updateType="decrease" value="─"/>                         '+
                      '                <input type="text" updateType="change" value="'+dishesForCartInfo.num+'"/>                           '+
                      '                <input type="button" updateType="add" value="+">                          '+
                      '            </div>                                                       '+
                      '        </div>                                                           '+
                      '    </div>                                                               '+
                      '</li>                                                                    ';
                  var dishesForCartObj=$(dishesForCartHtml);
                  dishesForCartObj.appendTo("#cart_"+cartInfo.id+"_dish_list>ul");
                  dishCount+=1;
              }
              accountTotal+=cartInfo.totalAccount;
          }
          $("#cart_dish_count").html(dishCount);
          $("#cart_list_total").html("总计：￥"+accountTotal);
          //注册事件
          $("#cart_list input[delDish]").delDishFromCart(options);
          $("#cart_list input[updateType]").updateDishOfCart(options);

      }
      else {//显示错误信息
          //alert(data.recode.label);
      }
  });

}
(function ($) {
    $.fn.addFoodToCart = function (options) {
        return this.each(function () {
            $(this).click(function () { //注册点击事件
                var addToCartUrl = options.addToCartUrl + "?rand=" + Math.random();
                var foodId = $(this).attr("foodId");
                $.getJSON(addToCartUrl, {foodId: foodId}, function (data) {
                    if (data.recode.code == 0) {//成功,添加到界面上购物车中
                        //alert(data.recode.label);
                        //updateCart(data.cartInfo,dishesListUrl);
                        initCart(options);
                    }
                    else {//显示错误信息
                        alert(data.recode.label);
                    }
                });
            });
        });
    };
    $.fn.delDishFromCart = function (options) {
        return this.each(function () {
            $(this).click(function () { //注册点击事件
                var delDishFromCartUrl = options.delDishFromCartUrl + "?rand=" + Math.random();
                var dishId = $(this).attr("dishId");
                $.getJSON(delDishFromCartUrl, {dishId: dishId}, function (data) {
                    if (data.recode.code == 0) {//成功,添加到界面上购物车中
                        //alert(data.recode.label);
                        //updateCart(data.cartInfo,dishesListUrl);
                        initCart(options);
                    }
                    else {//显示错误信息
                        alert(data.recode.label);
                    }
                });
            });
        });
    };
    function updateDish(options){
        var updateDishOfCartUrl=options.updateDishOfCartUrl  + "?rand=" + Math.random();
        var dishId = options.dishId;
        var count=options.count;
        $.getJSON(updateDishOfCartUrl, {dishId: dishId,count:count}, function (data) {
            if (data.recode.code == 0) {//成功,添加到界面上购物车中
                //alert(data.recode.label);
                //updateCart(data.cartInfo,dishesListUrl);
                initCart(options);
            }
            else {//显示错误信息
                alert(data.recode.label);
            }
        });
    }
    $.fn.updateDishOfCart = function (options) {
        return this.each(function () {
            if($(this).attr("updateType")=="change"){
                $(this).blur(function () {
                    options.dishId = $(this).parent().attr("dishId");
                    options.count=$(this).val();
                    updateDish(options);
                });
            }else{
                $(this).click(function () {
                    options.dishId = $(this).parent().attr("dishId");
                    var count=parseInt($(this).parent().children("input[updateType=change]").val());
                    if($(this).attr("updateType")=="decrease"){
                        count-=1;
                        if(count<0){
                            count=0;
                        }
                    }else{
                        count+=1;
                    }
                    $(this).parent().children("input[updateType=change]").val(count);
                    options.count=count;
                    updateDish(options);
                });
            }
        });
    };
})(jQuery);
