package lj.order.staff

import lj.I118Error
import lj.data.AddressInfo
import lj.data.AreaInfo
import lj.data.CartInfo
import lj.data.DishesForCartInfo
import lj.data.FoodInfo
import lj.data.RestaurantInfo
import lj.data.StaffInfo
import lj.enumCustom.ReCode
import lj.enumCustom.VerifyStatus
import lj.order.customer.CustomerOrderService
import lj.util.WebUtilService

class StaffCartService {

    WebUtilService webUtilService;
    CustomerOrderService customerOrderService;
    static transactional = true;
    //添加一个菜品到购物车
    def addFoodToCart(def params){
        def g = new org.codehaus.groovy.grails.plugins.web.taglib.ApplicationTagLib();
        long foodId=lj.Number.toLong(params.foodId);//菜品ID

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
        StaffInfo staffInfo=webUtilService.getStaff();//服务员
        if(staffInfo){//根据客户ID和饭店ID查询出购餐车
            long restaurantId=foodInfo.restaurantId;//饭店id
            if(restaurantId!=staffInfo.restaurantId){
                return [recode: ReCode.NO_RESTAURANTINFO];
            }
            RestaurantInfo restaurantInfo=RestaurantInfo.findByIdAndEnabledAndVerifyStatus(restaurantId,true, VerifyStatus.PASS.code);
            if(!restaurantInfo){ //饭店不存在
                return [recode: ReCode.NO_RESTAURANTINFO];
            }
            CartInfo cartInfo=null;
            String sessionMark=webUtilService.getSessionId();
            cartInfo=CartInfo.findByWaiterIdAndRestaurantIdAndSessionMark(staffInfo.id,restaurantId,sessionMark);
            if(!cartInfo){//还没有则创建一个
                cartInfo=new CartInfo();
                cartInfo.waiterId=staffInfo.id;
                cartInfo.restaurantId=restaurantId;
                cartInfo.restaurantName=restaurantInfo.name;
                cartInfo.sessionMark=sessionMark;
                if(!cartInfo.save(flush: true)){//创建不成功
                    throw new RuntimeException("保存购餐车不成功:"+I118Error.getMessage(g,cartInfo.errors.allErrors,0));
                    //return [recode:ReCode.SAVE_FAILED,cartInfo:cartInfo,errors:cartInfo.errors.allErrors];
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
        }else{
            return [recode: ReCode.NOT_LOGIN];
        }

    }
    //获取已经创建的购餐车对象列表和其中的点菜
    def getCartsAndDishes(){
        def cartList=null;
        StaffInfo staffInfo=webUtilService.getStaff();//服务员
        if(staffInfo){
            String sessionMark=webUtilService.getSessionId();
            //if(sessionMark){
                cartList=CartInfo.findAllByWaiterIdAndRestaurantIdAndSessionMark(staffInfo.id,staffInfo.restaurantId,sessionMark);
            //}
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
        }else{
            return [recode: ReCode.NOT_LOGIN];
        }

    }
    //获取已经创建的购餐车对象列表
    def getCarts(){
        def cartList=null;
        StaffInfo staffInfo=webUtilService.getStaff();//服务员
        if(staffInfo){
            String sessionMark=webUtilService.getSessionId();
            //if(sessionMark){
            cartList=CartInfo.findAllByWaiterIdAndRestaurantIdAndSessionMark(staffInfo.id,staffInfo.restaurantId,sessionMark);
            //}
            if(cartList){
                return [recode:ReCode.OK,cartList:cartList];
            }else {
                return [recode:ReCode.NO_RESULT];
            }
        }else{
            return [recode: ReCode.NOT_LOGIN];
        }
    }

    //获取餐车中菜品
    def getDishes(def params){
        long cartId=lj.Number.toLong(params.cartId);
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
        long staffId=webUtilService.getStaffId();
        if(staffId){
            long dishId=lj.Number.toLong(params.dishId);
            if(dishId){
                DishesForCartInfo dishesForCartInfo=DishesForCartInfo.get(dishId);
                if(dishesForCartInfo){
                    //更新cart总价
                    CartInfo cartInfo=CartInfo.get(dishesForCartInfo.cartId);
                    if(cartInfo){
                        cartInfo.totalAccount-=dishesForCartInfo.foodPrice*dishesForCartInfo.num;
                        cartInfo.save(flush: true);
                    }
                    //删除点菜
                    dishesForCartInfo.delete(flush: true);
                    //检查餐车中是否还有菜品，没有则删除餐车
                    DishesForCartInfo dishesForCartInfoTest=DishesForCartInfo.findByCartId(dishesForCartInfo.cartId);
                    if(!dishesForCartInfoTest){
                        //CartInfo cartInfo=CartInfo.get(dishesForCartInfo.cartId);
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
        }else{
            return [recode: ReCode.NOT_LOGIN];
        }
    }

    //更新餐车中菜品的数量
    def updateDish(def params){
        long staffId=webUtilService.getStaffId();
        if(staffId){
            long dishId=lj.Number.toLong(params.dishId);
            int count=lj.Number.toInteger(params.count);
            if(count==0){
                return delDish(params);
            }
            if(dishId){
                DishesForCartInfo dishesForCartInfo=DishesForCartInfo.get(dishId);
                if(dishesForCartInfo){
                    //更新cart总价
                    //更新cart总价
                    CartInfo cartInfo=CartInfo.get(dishesForCartInfo.cartId);
                    if(cartInfo){
                        cartInfo.totalAccount-=dishesForCartInfo.foodPrice*dishesForCartInfo.num;
                        cartInfo.totalAccount+=dishesForCartInfo.foodPrice*count;
                        cartInfo.save(flush: true);
                    }
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
        }else{
            return [recode: ReCode.NOT_LOGIN];
        }
    }

    //从餐车产生订单
    def makeOrderFromCart(def params){
        def g = new org.codehaus.groovy.grails.plugins.web.taglib.ApplicationTagLib();
        def errList=[];
        StaffInfo staffInfo=webUtilService.getStaff();//服务员
        if(staffInfo){
            String linkManName=params.linkManName;
            String phone=params.phone;
            String province=params.province;
            String city=params.city;
            String area=params.area;
            String street=params.street;
            //去掉空格
            linkManName=linkManName.replaceAll("\\s*","");
            phone=phone.replaceAll("\\s*","");
            province=province.replaceAll("\\s*","");
            city=city.replaceAll("\\s*","");
            area=area.replaceAll("\\s*","");
            street=street.replaceAll("\\s*","");

            //查找是否存在地址
            AddressInfo addressInfo=AddressInfo.findByLinkManNameAndPhoneAndProvinceAndCityAndAreaAndStreet(linkManName,phone,province,city,area,street);
            if(!addressInfo){
                addressInfo=new AddressInfo();
                AreaInfo areaInfo=AreaInfo.findByArea(area);
                if(areaInfo){
                    addressInfo.areaId=areaInfo.id;
                }
                addressInfo.linkManName=linkManName;
                addressInfo.phone=phone;
                addressInfo.province=province;
                addressInfo.city=city;
                addressInfo.area=area;
                addressInfo.street=street;
                if(!addressInfo.save(flush: true)){
                    return [recode: ReCode.SAVE_FAILED,errorstr: I118Error.getMessage(g,addressInfo.errors.allErrors,0)];
                }
            }

            String sessionMark=webUtilService.getSessionId();
            CartInfo cartInfo=CartInfo.findByWaiterIdAndRestaurantIdAndSessionMark(staffInfo.id,staffInfo.restaurantId,sessionMark);
            if(cartInfo){
                    def dishesForCartInfos=DishesForCartInfo.findAllByCartId(cartInfo.id);
                    if(dishesForCartInfos){
                        def paramsT=[:];
                        paramsT.restaurantId=cartInfo.restaurantId;
                        paramsT.date=params.date; //日期,yyyy-MM-dd
                        paramsT.time=params.time; //时间,HH:mm:ss
                        paramsT.addressId =addressInfo.id;
                        paramsT.phone=phone;
                        paramsT.customerName=linkManName;
                        paramsT.reserveType=params.reserveType;
                        def foodIds=new ArrayList<String>();
                        def counts=new ArrayList<String>();
                        def remarks=new ArrayList<String>();
                        dishesForCartInfos.each {
                            foodIds.add(it.foodId+"");
                            counts.add(it.num+"");
                            remarks.add(it.remark+"");
                        }
                        println("foodIds->"+foodIds);
                        paramsT.foodIds=(String[])foodIds.toArray();
                        paramsT.counts=(String[])counts.toArray();
                        paramsT.remarks=(String[])remarks.toArray();

                        def reInfo=customerOrderService.createOrder(paramsT,true);
                        if(reInfo.recode==ReCode.OK){
                            //删除数据
                            String sqlStr="delete from DishesForCartInfo where cartId="+cartInfo.id;
                            DishesForCartInfo.executeUpdate(sqlStr);
                            cartInfo.delete(flush: true);
                        }else if (reInfo.recode==ReCode.SAVE_FAILED){
                            reInfo.errorstr=I118Error.getMessage(g,reInfo.errors,0);
                        }
                        return reInfo;
                    }else{//删除空购餐车
                        cartInfo.delete(flush: true);
                    }
            }else{
                return [recode: ReCode.NO_CARTS];
            }
        }else{
            return [recode:ReCode.NOT_LOGIN];
        }
    }
}
