package lj.order

import grails.converters.JSON
import lj.I118Error
import lj.enumCustom.ReCode
import lj.order.customer.CartOfCustomerService

class CartOfCustomerAjaxController {
    CartOfCustomerService cartOfCustomerService;
    //添加食品到购餐车中
    def addFoodToCart(){
        def reInfo=[recode:ReCode.OTHER_ERROR];
        try {
            reInfo=cartOfCustomerService.addFoodToCart(params);
        }catch (Exception ex){
            reInfo=[recode:ReCode.OTHER_ERROR];
            reInfo.recode.label=ex.message;
        }
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
    //获取已经创建的购餐车对象列表和其中的点菜
    def getCartsAndDishes(){
        def reInfo=cartOfCustomerService.getCartsAndDishes();
        println("reInfo-->"+reInfo);
        render(reInfo as JSON);
    }
    //从购餐车中删除菜品
    def delDish(){
        def reInfo=cartOfCustomerService.delDish(params);
        println("reInfo-->"+reInfo);
        render(reInfo as JSON);
    }
    //更新餐车中菜品的数量
    def updateDish(){
        def reInfo=cartOfCustomerService.updateDish(params);
        println("reInfo-->"+reInfo);
        render(reInfo as JSON);
    }
}
