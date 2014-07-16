package lj.order.staff

import lj.data.DishesInfo
import lj.data.OrderInfo
import lj.data.RestaurantInfo
import lj.data.StaffInfo
import lj.enumCustom.DishesStatus
import lj.enumCustom.DishesValid
import lj.enumCustom.MessageType
import lj.enumCustom.MsgSendType
import lj.enumCustom.OrderStatus
import lj.enumCustom.OrderValid
import lj.enumCustom.ReCode
import lj.order.common.MessageService
import lj.order.customer.CustomerOrderService
import lj.Number;

class StaffOrderService {
    CustomerOrderService customerOrderService;
    def webUtilService;
    StaffDishService staffDishService;
    MessageService messageService;
    def serviceMethod() {

    }

    /***********创建订单***********************
     * 参数和返回值同 customerOrderService.createOrder
     * ********/
    def createOrder(def params) {
        def session=webUtilService.getSession();
        StaffInfo staffInfo=webUtilService.getStaff();
        if(staffInfo){
        params.restaurantId=staffInfo.restaurantId;
            return customerOrderService.createOrder(params, true);
        }
        else{
            return [recode: ReCode.NOT_LOGIN];
        }
    }

    /**********订单状态改变***********
     * params是传入的参数
     * 参数格式为：
     * orderId=Number.toLong(params.orderId);//订单号
     * statusCode=params.statusCode?:OrderStatus.ORDERED_STATUS.code;// 状态代码
     * statusCode的取值有：
     * ORDERED_STATUS(1,'点菜完成'),
     * VERIFY_ORDERED_STATUS(2,'确认点菜完成') ,SERVED_STATUS(3,'上菜完成'),
     * SHIPPING_STATUS(4,'运送中'),CHECKOUTED_STATUS(5,'结账完成')
     * //点菜列表参数,非必须
     * foodIds=12或foodIds=[112,231...]
     * counts=1或counts=[2,3...]
     * remarks=ddd或remarks=[dd,,dd...]
     *
     * 返回值
     * [recode: ReCode.SAVE_FAILED,errors:orderInfo.errors.allErrors];保存失败
     * [recode: ReCode.NO_ORDER];订单不存在
     * [recode:ReCode.NOT_LOGIN];用户没有登录
     * [recode: ReCode.OK];成功
     * **************/
    def orderStatusUpdate(def params){
        def session = webUtilService.getSession();
        //SimpleDateFormat sdfDate=new SimpleDateFormat("yyyy-MM-dd");
        //SimpleDateFormat sdfTime=new SimpleDateFormat("HH:mm:ss");
        //工作人员ID
        long staffId = Number.toLong(session.staffId);//工作人员ID

        if (staffId) {
            //获取参数
            Long orderId = Number.toLong(params.orderId);//订单Id
            int statusCode=Number.toInteger(params.statusCode);//状态代码

            if (statusCode == OrderStatus.ORDERED_STATUS.code) { //如果是帮顾客做"点菜完成"操作则调用顾客方法就行了
                return customerOrderService.orderStatusUpdate(params, true);
            } else {
                if (statusCode == OrderStatus.VERIFY_ORDERED_STATUS.code) {// 确认点菜完成
                    //先修改订单中点菜的状态为状态为“0”有效性为“0”的菜的状态改为“1确定完成”，有效性改为“1有效”。
                    staffDishService.dishConfirm(params);
                    //发送消息让厨师端，点菜列表进行更新
                    //生成消息通知顾客
                    def msgParams=[:];
                    msgParams.orderId=orderId;
                    msgParams.type=MessageType.UPDATE_DISH_LIST.code;
                    msgParams.receiveId=staffId;
                    msgParams.content="需要更新点菜列表";
                    msgParams.sendType=MsgSendType.STAFF_TO_STAFF.code;
                    msgParams.restaurantId=0;
                    def reInfo=messageService.createMsg(msgParams);
                    if(reInfo.recode!=ReCode.OK){
                        println("保存消息失败，但对于订单的产生没有致命影响，故忽略此错误，请系统管理员注意查证："+",reInfo="+reInfo);
                    }

                }
                //修改订单订单状态，如果有效性为初始态则更新有效性改为“1有效”
                OrderInfo orderInfo=OrderInfo.get(orderId);
                if(orderInfo){
                    orderInfo.status=statusCode;         //状态更新为需要的状态
                    if(orderInfo.valid==OrderValid.ORIGINAL_VALID.code)
                        orderInfo.valid=OrderValid.EFFECTIVE_VALID.code;//有效性更新为有效
                    if(statusCode == OrderStatus.CHECKOUTED_STATUS.code){//如果是算账的话更新收银员id为当前工作人员id
                        orderInfo.cashierId=staffId;
                    }
                    if(orderInfo.save(flush: true)){

                        //生成消息通知顾客
                        def msgParams=[:];
                        msgParams.orderId=orderInfo.id;
                        msgParams.type=MessageType.ORDER_HANDLE_TYPE.code;
                        msgParams.receiveId=orderInfo.clientId;
                        msgParams.content="订单状态改变，"+OrderStatus.getLable(orderInfo.status);
                        msgParams.sendType=MsgSendType.STAFF_TO_CUSTOMER.code;
                        msgParams.restaurantId=orderInfo.restaurantId;
                        def reInfo=messageService.createMsg(msgParams);
                        if(reInfo.recode!=ReCode.OK){
                            println("保存消息失败，但对于订单的产生没有致命影响，故忽略此错误，请系统管理员注意查证："+",reInfo="+reInfo);
                        }

                        return [recode: ReCode.OK];
                    }
                    else{
                        return [recode: ReCode.SAVE_FAILED,errors:orderInfo.errors.allErrors];
                    }
                }
                else{
                    return [recode: ReCode.NO_ORDER];
                }
            }
        } else {
            return [recode: ReCode.NOT_LOGIN];
        }
    }

    /*******订单有效性确认，这里只是确认订单的有效性，这个时候点菜可能没完成，点菜确认也可能没完成**
     *  params是传入的参数
     * 参数格式为：
     * orderId=Number.toLong(params.orderId);//订单号
     *
     * 返回值
     * [recode: ReCode.SAVE_FAILED,errors:orderInfo.errors.allErrors];保存失败
     * [recode: ReCode.NO_ORDER];订单不存在
     * [recode:ReCode.NOT_LOGIN];用户没有登录
     * [recode: ReCode.ORDER_CANNOT_UPDATE_VALID];订单当前有效性下不能更改有效性
     * [recode: ReCode.OK];成功
     * **********/
    def orderValidAffirm(def params){
        def session = webUtilService.getSession();
        //SimpleDateFormat sdfDate=new SimpleDateFormat("yyyy-MM-dd");
        //SimpleDateFormat sdfTime=new SimpleDateFormat("HH:mm:ss");
        //工作人员ID
        long staffId = Number.toLong(session.staffId);//工作人员ID

        if (staffId) {
            //获取参数
            Long orderId = Number.toLong(params.orderId);//订单Id

            //根据订单号查询出订单
            OrderInfo orderInfo=OrderInfo.get(orderId);
            if(orderInfo){
                if(orderInfo.valid==OrderValid.ORIGINAL_VALID.code){//当前有效性是可以更新有效性的
                    orderInfo.valid=OrderValid.EFFECTIVE_VALID.code;//更新订单有效性为有效
                    if(orderInfo.save(flush: true)){

                        //生成消息通知顾客
                        def msgParams=[:];
                        msgParams.orderId=orderInfo.id;
                        msgParams.type=MessageType.ORDER_HANDLE_TYPE.code;
                        msgParams.receiveId=orderInfo.clientId;
                        msgParams.content="你的订单'"+orderInfo.orderNum+"'已经确认为有效订单";
                        msgParams.sendType=MsgSendType.STAFF_TO_CUSTOMER.code;
                        msgParams.restaurantId=orderInfo.restaurantId;
                        def reInfo=messageService.createMsg(msgParams);
                        if(reInfo.recode!=ReCode.OK){
                            println("保存消息失败，但对于订单的产生没有致命影响，故忽略此错误，请系统管理员注意查证："+",reInfo="+reInfo);
                        }

                        return [recode: ReCode.OK];
                    }
                    else{
                        return [recode: ReCode.SAVE_FAILED,errors: orderInfo.errors.allErrors];
                    }
                }
                else {//当前有效性下不能更改有效性
                    return [recode: ReCode.ORDER_CANNOT_UPDATE_VALID];
                }
            }
            else{
                return [recode: ReCode.NO_ORDER];
            }

        } else {
            return [recode: ReCode.NOT_LOGIN];
        }
    }

    def customerReach(def params){
        def session = webUtilService.getSession();
        //SimpleDateFormat sdfDate=new SimpleDateFormat("yyyy-MM-dd");
        //SimpleDateFormat sdfTime=new SimpleDateFormat("HH:mm:ss");
        //工作人员ID
        long staffId = Number.toLong(session.staffId);//工作人员ID

        if (staffId) {
            //获取参数
            Long orderId = Number.toLong(params.orderId);//订单Id

            //根据订单号查询出订单
            OrderInfo orderInfo=OrderInfo.get(orderId);
            if(orderInfo){
                if(orderInfo.valid==OrderValid.ORIGINAL_VALID.code){//当前有效性是可以更新有效性的
                    orderInfo.valid=OrderValid.EFFECTIVE_VALID.code;//更新订单有效性为有效
                }
                if(orderInfo.status < OrderStatus.SERVED_STATUS.code){
                    orderInfo.reachRestaurant=true;
                    if(orderInfo.save(flush: true)){

                        //生成消息通知顾客
//                        def msgParams=[:];
//                        msgParams.orderId=orderInfo.id;
//                        msgParams.type=MessageType.ORDER_HANDLE_TYPE.code;
//                        msgParams.receiveId=orderInfo.clientId;
//                        msgParams.content="你的订单'"+orderInfo.orderNum+"'已经确认为有效订单";
//                        msgParams.sendType=MsgSendType.STAFF_TO_CUSTOMER.code;
//                        msgParams.restaurantId=orderInfo.restaurantId;
//                        def reInfo=messageService.createMsg(msgParams);
//                        if(reInfo.recode!=ReCode.OK){
//                            println("保存消息失败，但对于订单的产生没有致命影响，故忽略此错误，请系统管理员注意查证："+",reInfo="+reInfo);
//                        }

                        return [recode: ReCode.OK];
                    }
                    else{
                        return [recode: ReCode.SAVE_FAILED,errors: orderInfo.errors.allErrors];
                    }
                }
                else {//当前有效性下不能更改有效性
                    return [recode: ReCode.ORDER_CANNOT_UPDATE_VALID];
                }
            }
            else{
                return [recode: ReCode.NO_ORDER];
            }

        } else {
            return [recode: ReCode.NOT_LOGIN];
        }
    }

    /**********订单取消
     *  params是传入的参数
     * 参数格式为：
     * orderId=Number.toLong(params.orderId);//订单号
     * cancelReason=params.cancelReason;//订单取消原因
     *
     * 返回值
     * [recode: ReCode.SAVE_FAILED,errors:orderInfo.errors.allErrors];保存失败
     * [recode: ReCode.NO_ORDER];订单不存在
     * [recode:ReCode.NOT_LOGIN];用户没有登录
     * [recode: ReCode.ORDER_CANNOT_UPDATE_VALID];订单当前有效性下不能更改有效性
     * [recode: ReCode.OK];成功
     * **********/
    def orderCancel(def params){
        def session = webUtilService.getSession();
        //SimpleDateFormat sdfDate=new SimpleDateFormat("yyyy-MM-dd");
        //SimpleDateFormat sdfTime=new SimpleDateFormat("HH:mm:ss");
        //工作人员ID
        long staffId = Number.toLong(session.staffId);//工作人员ID

        if (staffId) {
            //获取参数
            Long orderId = Number.toLong(params.orderId);//订单Id
            String cancelReason=params.cancelReason;//订单取消原因

            //根据订单号查询出订单
            OrderInfo orderInfo=OrderInfo.get(orderId);
            if(orderInfo){
                //检查订单当前状态是否能取消
                if(orderInfo.status<=OrderStatus.VERIFY_ORDERED_STATUS.code&&orderInfo.valid<=OrderValid.EFFECTIVE_VALID.code){ //订单能取消
//                    if(){//如果有状态大于等于做菜中的点菜，则订单不能取消??,这个地方让服务员也可以取消吧，不然出现正在做菜的过程中，顾客走掉的话，可以取消订单来阻止厨师继续为该订单做菜
//
//                    }
                    //调用点菜服务方法取消订单所有点菜
                    staffDishService.cancelDish(params);
                    //更新订单有效性为饭店取消
                    orderInfo.valid=OrderValid.RESTAURANT_CANCEL_VALID.code;
                    orderInfo.cancelReason=cancelReason;
                    if(orderInfo.save(flush: true)){

                        //生成消息通知顾客
                        def msgParams=[:];
                        msgParams.orderId=orderInfo.id;
                        msgParams.type=MessageType.ORDER_HANDLE_TYPE.code;
                        msgParams.receiveId=orderInfo.clientId;
                        msgParams.content="你的订单'"+orderInfo.orderNum+"'已经被饭店取消，取消原因是："+orderInfo.cancelReason;
                        msgParams.sendType=MsgSendType.STAFF_TO_CUSTOMER.code;
                        msgParams.restaurantId=orderInfo.restaurantId;
                        def reInfo=messageService.createMsg(msgParams);
                        if(reInfo.recode!=ReCode.OK){
                            println("保存消息失败，但对于订单的产生没有致命影响，故忽略此错误，请系统管理员注意查证："+",reInfo="+reInfo);
                        }

                        return [recode: ReCode.OK];
                    }
                    else{
                        return [recode: ReCode.SAVE_FAILED,errors: orderInfo.errors.allErrors];
                    }
                }
                else{//订单不能取消
                    return [recode: ReCode.ORDER_CANNOT_UPDATE_VALID];
                }
            }
            else{
                return [recode: ReCode.NO_ORDER];
            }
        } else {
            return [recode: ReCode.NOT_LOGIN];
        }
    }

    /*******************订单列表*
     *  参数和返回值同 customerOrderService.orderList
     * ***********/
    def orderList(def params){
        return customerOrderService.orderList(params,true);
    }

    /***********************算账,这里只是计算出费用结果并将算账结果更新到订单中去,更新订单状态为算账完成用更新订单状态的方法
     *
     * *****************/
     def castAccounts(def params){
         def session = webUtilService.getSession();
         //SimpleDateFormat sdfDate=new SimpleDateFormat("yyyy-MM-dd");
         //SimpleDateFormat sdfTime=new SimpleDateFormat("HH:mm:ss");
         //工作人员ID
         long staffId = Number.toLong(session.staffId);//工作人员ID

         if (staffId) {
             //获取参数
             Long orderId = Number.toLong(params.orderId);//订单Id

             //根据订单号查询出订单
             OrderInfo orderInfo=OrderInfo.get(orderId);
             if(orderInfo){
                 // 订单有效且没有到已算账状态才能算账
                 if(orderInfo.valid==OrderValid.EFFECTIVE_VALID.code&&orderInfo.status<OrderStatus.CHECKOUTED_STATUS.code&&orderInfo.status>=OrderStatus.VERIFY_ORDERED_STATUS.code){
                     //如果已经计算出来了的则直接返回
//                     if (orderInfo.totalAccount != null && orderInfo.realAccount != null) {
//                         return [recode: ReCode.OK, orderInfo: orderInfo];
//                     }
                     //根据订单号查出点菜总金额
                     String sqlStr = "select sum(foodPrice*num) from DishesInfo where orderId=" + orderId +
                             " and status=" + DishesStatus.SERVED_STATUS.code + " and valid=" + DishesValid.EFFECTIVE_VALID.code;
                     def totalAccounts = DishesInfo.executeQuery(sqlStr);
                     def totalAccount = 0d;
                     if (totalAccounts) {
                         totalAccount = totalAccounts[0];
                     }
                     def postage=0d;
                     //如果是外卖需要加上运费
                     if(orderInfo.tableId==0){
                        RestaurantInfo restaurantInfo=RestaurantInfo.get(orderInfo.restaurantId);
                        if(restaurantInfo){
                            if(restaurantInfo.freeFreight==0||totalAccount<restaurantInfo.freeFreight){
                                postage=restaurantInfo.freight;
                            }
                        }
                     }

                     //这里暂时实付金额就是总金额加运费
                     if(!totalAccount){
                         totalAccount=0;
                     }
                     if(!postage){
                         postage=0;
                     }
                     Double realAccount=totalAccount+postage;

                     //保存数据到数据库
                     orderInfo.totalAccount=totalAccount;
                     orderInfo.postage=postage;
                     orderInfo.realAccount=realAccount;
                     if(orderInfo.save(flush: true)){
                         //查询是否存在做菜中或做菜完成的点菜，有则需提醒收银员
                         DishesInfo dishesInfo=DishesInfo.findByStatusBetweenAndValidAndOrderId(DishesStatus.COOKING_ORDERED_STATUS.code,DishesStatus.COOKED_STATUS.code,DishesValid.EFFECTIVE_VALID.code,orderId);
                         if(dishesInfo){
                             return [recode: ReCode.OK, orderInfo: orderInfo,warning:"还有做菜中或做菜完成的点菜因未上菜而未将费用计入到消费费用中，请注意查看确定后再做结账操作！"];
                         }else{
                            return [recode: ReCode.OK, orderInfo: orderInfo];
                         }
                     }
                     else{
                         return [recode: ReCode.SAVE_FAILED,errors: orderInfo.errors.allErrors];
                     }
                 }
                 else{//订单不能算账
                     return [recode: ReCode.ORDER_CANNOT_CAST_ACCOUNT];
                 }
             }
             else{
                 return [recode: ReCode.NO_ORDER];
             }
         } else {
             return [recode: ReCode.NOT_LOGIN];
         }
     }
    /*******算账，更新状态和更新实收金额**
     *  params是传入的参数
     * 参数格式为：
     * orderId=Number.toLong(params.orderId);//订单号
     * realAccount=Number.toDouble(params.realAccount);//实收金额
     *
     * 返回值
     * [recode: ReCode.SAVE_FAILED,errors:orderInfo.errors.allErrors];保存失败
     * [recode: ReCode.NO_ORDER];订单不存在
     * [recode:ReCode.NOT_LOGIN];用户没有登录
     * [recode: ReCode.ORDER_CANNOT_UPDATE_VALID];订单当前有效性下不能更改有效性
     * [recode: ReCode.OK];成功
     * **********/
    def submitCastAccounts(def params){
        def session = webUtilService.getSession();
        //SimpleDateFormat sdfDate=new SimpleDateFormat("yyyy-MM-dd");
        //SimpleDateFormat sdfTime=new SimpleDateFormat("HH:mm:ss");
        //工作人员ID
        long staffId = Number.toLong(session.staffId);//工作人员ID

        if (staffId) {
            //获取参数
            Long orderId = Number.toLong(params.orderId);//订单Id
            double realAccount=Number.toDouble(params.realAccount);//实收金额

            //根据订单号查询出订单
            OrderInfo orderInfo=OrderInfo.get(orderId);
            if(orderInfo){
                if(orderInfo.valid==OrderValid.EFFECTIVE_VALID.code&&orderInfo.status<OrderStatus.CHECKOUTED_STATUS.code){// 订单有效且没有到已算账状态才能算账
                    orderInfo.status=OrderStatus.CHECKOUTED_STATUS.code;//更新订单状态为算账完成
                    if(realAccount>0)
                        orderInfo.realAccount=realAccount;
                    if(orderInfo.save(flush: true)){
                        //未做的有效的菜改为取消，并给厨师端点菜列表发消息刷新列表
                        def dishesInfos=DishesInfo.findAllByRestaurantIdAndValidAndStatus(orderInfo.restaurantId,DishesValid.EFFECTIVE_VALID.code,DishesStatus.VERIFYING_STATUS.code);
                        if(dishesInfos){
                            dishesInfos.each {
                                it.valid=DishesValid.RESTAURANT_AFTER_VERIFYED_CANCEL_VALID.code;
                                it.cancelReason="结账完成";
                            }
                            //发送消息让厨师端，点菜列表进行更新
                            def msgParams=[:];
                            msgParams.orderId=orderId;
                            msgParams.type=MessageType.UPDATE_DISH_LIST.code;
                            msgParams.receiveId=staffId;
                            msgParams.content="需要更新点菜列表";
                            msgParams.sendType=MsgSendType.STAFF_TO_STAFF.code;
                            msgParams.restaurantId=0;
                            def reInfo=messageService.createMsg(msgParams);
                            if(reInfo.recode!=ReCode.OK){
                                println("保存消息失败，但对于订单的产生没有致命影响，故忽略此错误，请系统管理员注意查证："+",reInfo="+reInfo);
                            }
                        }

                        //生成消息通知顾客
                        def msgParams=[:];
                        msgParams.orderId=orderInfo.id;
                        msgParams.type=MessageType.ORDER_HANDLE_TYPE.code;
                        msgParams.receiveId=orderInfo.clientId;
                        msgParams.content="你的订单'"+orderInfo.orderNum+"'已经算账完成，你可以对本次用餐进行评价";
                        msgParams.sendType=MsgSendType.STAFF_TO_CUSTOMER.code;
                        msgParams.restaurantId=orderInfo.restaurantId;
                        def reInfo=messageService.createMsg(msgParams);
                        if(reInfo.recode!=ReCode.OK){
                            println("保存消息失败，但对于订单的产生没有致命影响，故忽略此错误，请系统管理员注意查证："+",reInfo="+reInfo);
                        }

                        return [recode: ReCode.OK,orderInfo:orderInfo];
                    }
                    else{
                        return [recode: ReCode.SAVE_FAILED,orderInfo:orderInfo,errors: orderInfo.errors.allErrors];
                    }
                }
                else {//当前有效性下不能更改有效性
                    return [recode: ReCode.ORDER_CANNOT_UPDATE_VALID];
                }
            }
            else{
                return [recode: ReCode.NO_ORDER];
            }

        } else {
            return [recode: ReCode.NOT_LOGIN];
        }
    }

    /********************订单删除******
     *params是传入的参数
     * 参数格式为：
     * orderId=Number.toLong(params.orderId);//订单号
     *
     * 返回值
     * [recode: ReCode.ORDER_CANNOT_DELETE];订单不能删除
     * [recode: ReCode.ORDER_NOT_EXIST];订单不存在
     * [recode:ReCode.NOT_LOGIN];用户没有登录
     * [recode: ReCode.DENIED_USER] ;//非法用户
     * [recode: ReCode.OK];成功
     * ****************/
    def delOrder(def params){
        def session=webUtilService.getSession();
        //SimpleDateFormat sdfDate=new SimpleDateFormat("yyyy-MM-dd");
        //SimpleDateFormat sdfTime=new SimpleDateFormat("HH:mm:ss");
        //取出用户ID
        long staffId=Number.toLong(session.staffId);//用户ID
        if(staffId){
            //获取参数
            long orderId=Number.toLong(params.orderId);//订单号

            //获取饭店ID
            StaffInfo staffInfo=StaffInfo.get(staffId);
            if(!staffInfo){
                return [recode: ReCode.DENIED_USER] ;
            }
            long  restaurantId= staffInfo.restaurantId;

            OrderInfo orderInfo=OrderInfo.findByIdAndRestaurantId(orderId,restaurantId);
            if(orderInfo){
                //检查订单是否可以删除
                if(orderInfo.valid!=OrderValid.RESTAURANT_CANCEL_VALID.code){//订单不能删除
                    return [recode: ReCode.ORDER_CANNOT_DELETE];
                }
                //删除订单中点菜
                staffDishService.delDish(params);
                orderInfo.delete(flush: true);
                return [recode: ReCode.OK];
            }
            else{
                return [recode: ReCode.ORDER_NOT_EXIST];
            }
        }
        else{
            return [recode:ReCode.NOT_LOGIN];
        }
    }
    def orderInfo(def params){
        return customerOrderService.orderInfo(params,true);
    }
    def completeDish(def params){
        return customerOrderService.completeDish(params,true);
    }
}
