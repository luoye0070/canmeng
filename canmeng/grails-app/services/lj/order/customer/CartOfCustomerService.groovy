package lj.order.customer

import lj.Number
import lj.data.CartInfo
import lj.data.DishesForCartInfo
import lj.data.FoodInfo
import lj.data.RestaurantInfo
import lj.enumCustom.ReCode
import lj.enumCustom.VerifyStatus
import lj.util.WebUtilService;
class CartOfCustomerService {
      WebUtilService webUtilService;
    //添加一个菜品到购物车
    def addFoodToCart(def params){
        long foodId=Number.toLong(params.foodId);//菜品ID

        FoodInfo foodInfo=FoodInfo.get(foodId);
        if(!foodInfo){//菜品不存在
            return [recode: ReCode.FOOD_NOT_EXIST];
        }
        if(!foodInfo.canTakeOut){//不能外卖
            return [recode: ReCode.FOOD_CAN_NOT_TAKE_OUT];
        }
        if(foodInfo.countLimit<foodInfo.sellCount+1){//售完
            return [recode: ReCode.FOOD_NOT_ENOUGH];
        }

        long restaurantId=foodInfo.restaurantId;//饭店id
        RestaurantInfo restaurantInfo=RestaurantInfo.findByIdAndEnabledAndVerifyStatus(restaurantId,true, VerifyStatus.PASS.code);
        if(!restaurantInfo){ //饭店不存在
            return [recode: ReCode.NO_RESTAURANTINFO];
        }

        CartInfo cartInfo=null;
        long clientId=webUtilService.getClientId();//客户id
        if(clientId){//根据客户ID和饭店ID查询出购餐车
            cartInfo=CartInfo.findByClientIdAndRestaurantId(clientId,restaurantId);
            if(!cartInfo){//还没有则创建一个
                cartInfo=new CartInfo();
                cartInfo.clientId=clientId;
                cartInfo.restaurantId=restaurantId;
                if(!cartInfo.save(flush: true)){//创建不成功
                    return [recode:ReCode.SAVE_FAILED,cartInfo:cartInfo,errors:cartInfo.errors.allErrors];
                }
            }
        }else{//根据会话标示来获取或创建一个购餐车
//            String sessionMark=webUtilService.readCookie("sessionMark");
//            if(!sessionMark){
//                sessionMark=Number.makeNum()+""+webUtilService.getSessionId();
//                webUtilService.writeCookie("sessionMark",sessionMark,7);
//            }
            String sessionMark=webUtilService.getSessionId();
            cartInfo=CartInfo.findBySessionMarkAndRestaurantId(sessionMark,restaurantId);
            if(!cartInfo){//还没有则创建一个
                cartInfo=new CartInfo();
                cartInfo.sessionMark=sessionMark;
                cartInfo.restaurantId=restaurantId;
                if(!cartInfo.save(flush: true)){//创建不成功
                    return [recode:ReCode.SAVE_FAILED,cartInfo:cartInfo,errors:cartInfo.errors.allErrors];
                }
            }
        }
        if(cartInfo){
            //查找或创建一个DishForCart
            DishesForCartInfo dishesForCartInfo=DishesForCartInfo.findByCartIdAndFoodId(cartInfo.id,foodId);
            if(!dishesForCartInfo){
                dishesForCartInfo=new DishesForCartInfo();
                dishesForCartInfo.cartId=cartInfo.id;
                dishesForCartInfo.foodId=foodId;
                dishesForCartInfo.foodImg=foodInfo.image;
                dishesForCartInfo.foodName=foodInfo.name;
                dishesForCartInfo.foodPrice=foodInfo.price;
                dishesForCartInfo.num=0;
                if(!dishesForCartInfo.save(flush: true)){//创建不成功
                    return [recode:ReCode.SAVE_FAILED,dishesForCartInfo:dishesForCartInfo,errors:dishesForCartInfo.errors.allErrors];
                }
            }
            dishesForCartInfo.num+=1;
            if(!dishesForCartInfo.save(flush: true)){//保存不成功
                return [recode:ReCode.SAVE_FAILED,dishesForCartInfo:dishesForCartInfo,errors:dishesForCartInfo.errors.allErrors];
            }
            cartInfo.totalAccount+=foodInfo.price;
            if(!cartInfo.save(flush: true)){//保存不成功
                return [recode:ReCode.SAVE_FAILED,cartInfo:cartInfo,errors:cartInfo.errors.allErrors];
            }
        }
    }

    //获取已经创建的购餐车对象列表
    def getCarts(){
        long clientId=webUtilService.getClientId();//客户id
        def cartList=null;
        if(clientId){
            //合并SessionId标记的购餐车对象
            //String sessionMark=webUtilService.readCookie("sessionMark");
            String sessionMark=webUtilService.getSessionId();
            if(sessionMark){
                def cartListForSM=CartInfo.findAllBySessionMark(sessionMark);
                if(cartListForSM){
                    cartListForSM.each {
                        CartInfo cartInfo=CartInfo.findByClientIdAndRestaurantId(clientId,it.restaurantId);
                        if(cartInfo){
                            DishesForCartInfo.executeUpdate("update DishesForCartInfo set cartId="+cartInfo.id+" where cartId="+it.id);
                            it.delete(flush: true);
                        }else {
                            it.clientId=clientId;
                            it.sessionMark=null;
                        }
                    }
                }
            }
            //查询出餐车对象
            cartList=CartInfo.findAllByClientId(clientId);
        }else{
            String sessionMark=webUtilService.getSessionId();
            if(sessionMark){
                cartList=CartInfo.findAllBySessionMark(sessionMark);
            }
        }
        if(cartList){
            return [recode:ReCode.OK,cartList:cartList];
        }else {
            return [recode:ReCode.NO_RESULT];
        }
    }

    //获取餐车中菜品
    def getDishes(def params){
        long cartId=Number.toLong(params.cartId);
        if(cartId){
            def dishesForCartList=DishesForCartInfo.findAllByCartId(cartId);
            if(dishesForCartList){
                return [recode:ReCode.OK,dishesForCartList:dishesForCartList];
            }else{
                return [recode:ReCode.NO_RESULT];
            }
        }else {
            return [recode:ReCode.ERROR_PARAMS];
        }

    }
}
