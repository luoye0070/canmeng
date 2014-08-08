package lj.order.staff

import lj.data.DishesInfo
import lj.data.FoodInfo
import lj.data.OrderInfo
import lj.data.StaffInfo
import lj.enumCustom.DishesStatus
import lj.enumCustom.DishesValid
import lj.enumCustom.MessageType
import lj.enumCustom.MsgSendType
import lj.enumCustom.OrderType
import lj.enumCustom.OrderValid
import lj.enumCustom.ReCode
import lj.order.common.MessageService
import lj.order.customer.CustomerDishService
import lj.Number
import lj.util.WebUtilService;

class StaffDishService {
    CustomerDishService customerDishService;
    WebUtilService webUtilService;
    MessageService messageService;
    def serviceMethod() {

    }

    //点菜
    def addDishes(def params){
        return customerDishService.addDishes(params,true);
    }

    /**************点菜确认*****
     * params是传入的参数
     * 参数格式为：
     * orderId=Number.toLong(params.orderId);//订单Id
     * dishIds=12或dishIds=[112,231...] //点菜列表参数
     * 以上参数任选一个传入，传入订单ID则按订单ID操作
     *
     * 返回值
     * [recode: ReCode.ERROR_PARAMS];//参数错误
     * [recode:ReCode.NOT_LOGIN];用户没有登录
     * [recode: ReCode.OK];成功
     * ********/
    def dishConfirm(def params){
        def session=webUtilService.getSession();
        //SimpleDateFormat sdfDate=new SimpleDateFormat("yyyy-MM-dd");
        //SimpleDateFormat sdfTime=new SimpleDateFormat("HH:mm:ss");
        //工作人员ID
        long staffId=Number.toLong(session.staffId);//工作人员ID

        if(staffId){
            //获取参数
            Long orderId=Number.toLong(params.orderId);//订单Id
            def dishIds=params.dishIds;//点菜列表

            def dishList=null;
            if(orderId){//订单ID存在则按订单ID取消
                dishList=DishesInfo.findAllByOrderId(orderId);
            }
            else if(dishIds){//不然如果按点菜Id列表取消
                def dishIdList=[];
                if(dishIds instanceof String){
                    dishIdList.add(Number.toLong(dishIds));
                }
                else if(dishIds instanceof String[]){
                    for(int i=0;i<dishIds.length;i++){
                        dishIdList.add(Number.toLong(dishIds[i]));
                    }
                }
                dishList=DishesInfo.findAllByIdInList(dishIdList);
            }
            else {
                return [recode: ReCode.ERROR_PARAMS];//参数错误
            }
            if(dishList){
                dishList.each {
                    if(it.valid==DishesValid.ORIGINAL_VALID.code)
                    {//有效性为初始状态下且状态也为初始态时更新有效性和状态分别为1有效和1确认完成
                        it.valid=DishesValid.EFFECTIVE_VALID.code;
                    }
                    if(it.status==DishesStatus.ORIGINAL_STATUS.code){
                        it.status=DishesStatus.VERIFYING_STATUS.code;
                    }
                }
            }
            return [recode: ReCode.OK];//成功
        }
        else{
            return [recode:ReCode.NOT_LOGIN];
        }
    }

    /***************
     * 点菜取消
     * params是传入的参数
     * 参数格式为：
     * orderId=12 //订单Id
     * dishIds=12或dishIds=[112,231...] //点菜ID列表
     * cancelReason=ddd //点菜取消原因
     * 传入订单Id则按订单ID取消
     * *****************/
    def cancelDish(def params){
        def session=webUtilService.getSession();
        //SimpleDateFormat sdfDate=new SimpleDateFormat("yyyy-MM-dd");
        //SimpleDateFormat sdfTime=new SimpleDateFormat("HH:mm:ss");
        //工作人员ID
        long staffId=Number.toLong(session.staffId);//工作人员ID

        if(staffId){
            //获取参数
            Long orderId=Number.toLong(params.orderId);//订单Id
            def dishIds=params.dishIds;//点菜列表
            String cancelReason=params.cancelReason;//订单取消原因

            def dishList=null;
            if(orderId){//订单ID存在则按订单ID取消
                dishList=DishesInfo.findAllByOrderId(orderId);
            }
            else if(dishIds){//不然如果按点菜Id列表取消
                def dishIdList=[];
                if(dishIds instanceof String){
                    dishIdList.add(Number.toLong(dishIds));
                }
                else if(dishIds instanceof String[]){
                    for(int i=0;i<dishIds.length;i++){
                        dishIdList.add(Number.toLong(dishIds[i]));
                    }
                }
                dishList=DishesInfo.findAllByIdInList(dishIdList);
            }
            else {
                return [recode: ReCode.ERROR_PARAMS];//参数错误
            }
            if(dishList){
                dishList.each {
                    if((it.valid<=DishesValid.EFFECTIVE_VALID.code&&it.status<=DishesStatus.VERIFYING_STATUS.code)
                    ||(it.orderType==OrderType.TAKE_OUT.code&&it.valid<=DishesValid.EFFECTIVE_VALID.code&&it.status<DishesStatus.SERVED_STATUS.code))//外卖可以在打包前取消
                    {//有效性小于1且状态小于1可以取消
                        //根据订单状态确定3或4
                        OrderInfo orderInfo=OrderInfo.get(it.orderId);
                        if(orderInfo){//订单存在
                            if(orderInfo.valid==OrderValid.ORIGINAL_VALID.code){//订单还没有确认
                                it.cancelReason=cancelReason;
                                it.valid=DishesValid.RESTAURANT_BEFORE_VERIFYED_CANCEL_VALID.code;
                            }
                            else if (orderInfo.valid==OrderValid.EFFECTIVE_VALID.code){ //订单已经确认
                                it.cancelReason=cancelReason;
                                it.valid=DishesValid.RESTAURANT_AFTER_VERIFYED_CANCEL_VALID.code;
                            }
                            FoodInfo.executeUpdate("update FoodInfo set sellCount=sellCount-"+it.num+" where id="+it.foodId);//更新菜的销量
                        }
                    }
                }
            }
            return [recode: ReCode.OK];//成功
        }
        else{
            return [recode:ReCode.NOT_LOGIN];
        }
    }

    //点菜列表,参数同 customerDishService.dishList(params,true);
    def dishList(def params){
        def session=webUtilService.getSession();
        //SimpleDateFormat sdfDate=new SimpleDateFormat("yyyy-MM-dd");
        //SimpleDateFormat sdfTime=new SimpleDateFormat("HH:mm:ss");
        //工作人员ID
        StaffInfo staffInfo=webUtilService.getStaff();//工作人员ID
        if(staffInfo){
            params.restaurantId=staffInfo.restaurantId;//加上饭店id
            return customerDishService.dishList(params,true);
        }
        else{
            return [recode:ReCode.NOT_LOGIN];
        }
    }

    //点菜状态改变
    def dishStatusUpdate(def params){
        def session=webUtilService.getSession();
        //SimpleDateFormat sdfDate=new SimpleDateFormat("yyyy-MM-dd");
        //SimpleDateFormat sdfTime=new SimpleDateFormat("HH:mm:ss");
        //工作人员ID
        long staffId=Number.toLong(session.staffId);//工作人员ID

        if(staffId){
            //获取参数
            Long orderId=Number.toLong(params.orderId);//订单Id
            def dishIds=params.dishIds;//点菜列表
            int statusCode=Number.toInteger(params.statusCode);//状态代码

            def dishList=null;
            if(orderId){//订单ID存在则按订单ID更新点菜状态
                dishList=DishesInfo.findAllByOrderId(orderId);
            }
            else if(dishIds){//不然如果按点菜Id列表更新点菜状态
                def dishIdList=[];
                if(dishIds instanceof String){
                    dishIdList.add(Number.toLong(dishIds));
                }
                else if(dishIds instanceof String[]){
                    for(int i=0;i<dishIds.length;i++){
                        dishIdList.add(Number.toLong(dishIds[i]));
                    }
                }
                dishList=DishesInfo.findAllByIdInList(dishIdList);
            }
            else {
                return [recode: ReCode.ERROR_PARAMS];//参数错误
            }
            if(dishList){
                dishList.each {
                    if(it.status<=statusCode&&it.valid==DishesValid.EFFECTIVE_VALID.code)
                    {//点菜状态小于当前要更新为的状态才更新
                        it.status=statusCode;
                        if(statusCode==DishesStatus.COOKING_ORDERED_STATUS.code){// 更新为做菜中，则更新厨师ID
                            it.cookId=staffId;
                        }
                        if(statusCode==DishesStatus.COOKED_STATUS.code){//做菜完成，通知服务员上菜
                            //生成消息通知服务员
                            def msgParams=[:];
                            msgParams.orderId=it.orderId;
                            msgParams.type=MessageType.SERVED_FOOD.code;
                            msgParams.receiveId=0;
                            msgParams.content="桌位“"+it.tableName+"”点的菜“"+it.foodName+"”已做好，请上菜。";
                            msgParams.sendType=MsgSendType.STAFF_TO_STAFF.code;
                            msgParams.restaurantId=it.restaurantId;
                            def reInfo=messageService.createMsg(msgParams);
                            if(reInfo.recode!=ReCode.OK){
                                println("保存消息失败，但对于订单的产生没有致命影响，故忽略此错误，请系统管理员注意查证："+",reInfo="+reInfo);
                            }
                        }
                    }
                }
            }
            return [recode: ReCode.OK];//成功
        }
        else{
            return [recode:ReCode.NOT_LOGIN];
        }
    }

    /***************
     * 点菜删除
     * params是传入的参数
     * 参数格式为：
     * orderId=12
     * dishIds=12或dishIds=[112,231...]
     * *****************/
    def delDish(def params){
        def session = webUtilService.getSession();
        //取出用户ID
        long staffId = Number.toLong(session.staffId);//用户ID
        if(staffId==0){
            //没登录
            return [recode: ReCode.NOT_LOGIN];
        }

        //取参数
        long orderId = Number.toLong(params.orderId);//订单ID
        def dishIds = params.dishIds;//点菜Id列表

        def dishList=null;
        if(orderId){//订单ID存在则按订单ID取消
            dishList=DishesInfo.findAllByOrderId(orderId);
        }
        else if(dishIds){//不然如果按点菜Id列表取消
            def dishIdList=[];
            if(dishIds instanceof String){
                dishIdList.add(Number.toLong(dishIds));
            }
            else if(dishIds instanceof String[]){
                for(int i=0;i<dishIds.length;i++){
                    dishIdList.add(Number.toLong(dishIds[i]));
                }
            }
            dishList=DishesInfo.findAllByIdInList(dishIdList);
        }
        else {
            return [recode: ReCode.ERROR_PARAMS];//参数错误
        }
        if(dishList){
            dishList.each {
                if(it.valid==DishesValid.RESTAURANT_AFTER_VERIFYED_CANCEL_VALID.code||it.valid==DishesValid.RESTAURANT_BEFORE_VERIFYED_CANCEL_VALID.code)
                {//有效性为饭店取消下能删除
                    it.delete(flush: true);
                }
            }
        }
        return [recode: ReCode.OK];//成功
    }

}
