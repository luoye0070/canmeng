package lj.order.customer

import lj.I118Error
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
    static transactional = true;
    //添加一个菜品到购物车
    def addFoodToCart(def params){
        def g = new org.codehaus.groovy.grails.plugins.web.taglib.ApplicationTagLib();
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
                cartInfo.restaurantName=restaurantInfo.name;
                if(!cartInfo.save(flush: true)){//创建不成功
                    throw new RuntimeException("保存购餐车不成功:"+I118Error.getMessage(g,cartInfo.errors.allErrors,0));
                    //return [recode:ReCode.SAVE_FAILED,cartInfo:cartInfo,errors:cartInfo.errors.allErrors];
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
                cartInfo.restaurantName=restaurantInfo.name;
                if(!cartInfo.save(flush: true)){//创建不成功
                    throw new RuntimeException("保存购餐车不成功:"+I118Error.getMessage(g,cartInfo.errors.allErrors,0));
                    //return [recode:ReCode.SAVE_FAILED,cartInfo:cartInfo,errors:cartInfo.errors.allErrors];
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
                    throw new RuntimeException("保存点菜不成功:"+I118Error.getMessage(g,dishesForCartInfo.errors.allErrors,0));
                    //return [recode:ReCode.SAVE_FAILED,dishesForCartInfo:dishesForCartInfo,errors:dishesForCartInfo.errors.allErrors];
                }
            }
            dishesForCartInfo.num+=1;
            if(!dishesForCartInfo.save(flush: true)){//保存不成功
                throw new RuntimeException("保存点菜不成功:"+I118Error.getMessage(g,dishesForCartInfo.errors.allErrors,0));
                //return [recode:ReCode.SAVE_FAILED,dishesForCartInfo:dishesForCartInfo,errors:dishesForCartInfo.errors.allErrors];
            }
            cartInfo.totalAccount+=foodInfo.price;
            if(!cartInfo.save(flush: true)){//保存不成功
                throw new RuntimeException("保存购餐车不成功:"+I118Error.getMessage(g,cartInfo.errors.allErrors,0));
                //return [recode:ReCode.SAVE_FAILED,cartInfo:cartInfo,errors:cartInfo.errors.allErrors];
            }
            return [recode: ReCode.OK,cartInfo:cartInfo];
        }else{
            return [recode: ReCode.OTHER_ERROR];
        }
    }
    //获取已经创建的购餐车对象列表和其中的点菜
    def getCartsAndDishes(){
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
            //循环查出点菜
            def cartsAndDishes=[];
            cartList.each {
                def dishesForCartList=DishesForCartInfo.findAllByCartId(it.id);
                cartsAndDishes.add([cartInfo:it,dishesForCartList:dishesForCartList]);
            }
            return [recode:ReCode.OK,cartsAndDishes:cartsAndDishes];
        }else {
            return [recode:ReCode.NO_RESULT];
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

    //从购餐车中删除菜品
    def delDish(def params){
        long dishId=Number.toLong(params.dishId);
        if(dishId){
             DishesForCartInfo dishesForCartInfo=DishesForCartInfo.get(dishId);
            if(dishesForCartInfo){
                dishesForCartInfo.delete(flush: true);
                //检查餐车中是否还有菜品，没有则删除餐车
                DishesForCartInfo dishesForCartInfoTest=DishesForCartInfo.findByCartId(dishesForCartInfo.cartId);
                if(!dishesForCartInfoTest){
                    CartInfo cartInfo=CartInfo.get(dishesForCartInfo.cartId);
                    if(cartInfo){
                        cartInfo.delete(flush: true);
                    }
                }
                return [recode: ReCode.OK];
            }else{
                return [recode: ReCode.NO_RECORD];
            }
        }else{
            return [recode:ReCode.ERROR_PARAMS];
        }
    }

    //更新餐车中菜品的数量
    def updateDish(def params){
        long dishId=Number.toLong(params.dishId);
        int count=Number.toInteger(params.count);
        if(dishId){
            DishesForCartInfo dishesForCartInfo=DishesForCartInfo.get(dishId);
            if(dishesForCartInfo){
                dishesForCartInfo.num=count;
                if(dishesForCartInfo.save(flush: true)){
                    return [recode: ReCode.OK];
                }else{
                    return [recode:ReCode.SAVE_FAILED,dishesForCartInfo:dishesForCartInfo,errors:dishesForCartInfo.errors.allErrors];
                }
            }else{
                return [recode: ReCode.NO_RECORD];
            }
        }else{
            return [recode:ReCode.ERROR_PARAMS];
        }
    }
}
