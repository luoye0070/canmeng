package lj.order

import grails.converters.JSON
import lj.I118Error
import lj.enumCustom.ReCode
import lj.order.customer.CartOfCustomerService

class CartOfCustomerAjaxController {
    CartOfCustomerService cartOfCustomerService;
    //添加食品到购餐车中
    def addFoodToCart(){
        def reInfo=cartOfCustomerService.addFoodToCart(params);
        if(reInfo.recode!=ReCode.OK){
            if(reInfo.recode==ReCode.SAVE_FAILED){
                reInfo.recode.label=I118Error.getMessage(g,reInfo.errors,0);
            }
        }
        println("reInfo-->"+reInfo);
        render(reInfo as JSON);
    }

    //获取已经创建的购餐车对象列表
    def getCarts(){
        def reInfo=cartOfCustomerService.getCarts();
        println("reInfo-->"+reInfo);
        render(reInfo as JSON);
    }

    //获取餐车中菜品
    def getDishes(){
        def reInfo=cartOfCustomerService.getDishes(params);
        println("reInfo-->"+reInfo);
        render(reInfo as JSON);
    }
}
