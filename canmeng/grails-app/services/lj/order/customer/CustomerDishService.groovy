package lj.order.customer

import lj.data.DishesInfo
import lj.data.FoodInfo
import lj.data.OrderInfo
import lj.enumCustom.DishesStatus
import lj.enumCustom.DishesValid
import lj.enumCustom.MessageType
import lj.enumCustom.MsgSendType
import lj.enumCustom.OrderStatus
import lj.enumCustom.OrderValid
import lj.enumCustom.ReCode

import lj.Number
import lj.order.common.MessageService

import java.text.SimpleDateFormat;

class CustomerDishService {
    def webUtilService;
    MessageService messageService;
    def serviceMethod() {

    }

    /*******
     * 点菜
     * params是传入的参数
     * 参数格式为：
     * orderId=12
     * foodIds=12或foodIds=[112,231...]
     * counts=1或counts=[2,3...]
     * remarks=ddd或remarks=[dd,,dd...]
     * partakeCode=3454,点菜参与验证码,如果用户ID存在则不需要点菜参与验证码
     * ************/
    def addDishes(def params, boolean byWaiter) {
        def session = webUtilService.getSession();
        def failedList = [];//点菜失败的菜列表
        //println(session.userId)


        //获取参数
        //long userId = Number.toLong(session.userId);//用户ID
        long clientId=webUtilService.getClientId();
        long orderId = Number.toLong(params.orderId);//订单ID
        def foodIds = params.foodIds;//菜品Id列表
        def counts=params.counts;// 数量列表
        def remarks=params.remarks;//备注列表
        String partakeCode = params.partakeCode;//点菜参与码
        if(!partakeCode){ //没有点菜验证码则需要验证用户是否登录
            if (byWaiter) {//如果是服务员帮助创建订单，则取服务员ID作为用户ID
                clientId = Number.toLong(session.staffId);
                if (clientId == 0) { //没登录
                    return [recode: ReCode.NOT_LOGIN];
                }
            }
        }
        //设置状态和初始值
        int status = DishesStatus.ORIGINAL_STATUS.code;;
        int valid = DishesValid.ORIGINAL_VALID.code;

        if (orderId) {
            OrderInfo orderInfo = OrderInfo.get(orderId);
            if (orderInfo) {
                //检查定当前状态是否能点菜
                if (orderInfo.status == OrderStatus.SERVED_STATUS.code) {//上菜完成不能再点菜了
                    return [recode: ReCode.CANNOT_DISH];//不能点菜
                }
                if (orderInfo.valid <= OrderValid.EFFECTIVE_VALID.code) { //初始态或者有效态
                    if (orderInfo.status >= OrderStatus.VERIFY_ORDERED_STATUS.code) {//订单的状态是点菜确认完成后,这时点的菜默认设置点菜的有效性和状态为1和1
                        status = DishesStatus.VERIFYING_STATUS.code;
                        valid = DishesValid.EFFECTIVE_VALID.code;
                    }
                } else {//订单无效不能点菜
                    return [recode: ReCode.CANNOT_DISH];//不能点菜
                }
                //点菜参与码是否正确
                if (clientId == orderInfo.clientId || byWaiter) {//用户登录切是订单创建的用户或者是服务员帮忙点菜，则不需要参与验证码

                } else {//检查点菜参与验证码是否正确
                    if (partakeCode != orderInfo.partakeCode) { //点菜参与码不正确
                        return [recode: ReCode.ERROR_PARTAKECODE];
                    }
                }
            } else {
                return [recode: ReCode.NO_ORDER];//订单不存在
            }
            //点菜
            if (foodIds) {
                def dishList = [];
                if (foodIds instanceof String) {//如果只传入了一个Id
                    long foodId = Number.toLong(foodIds);
                    int foodCount = Number.toInteger(counts);
                    String remark=remarks;
                    def dishMap = [foodId: foodId, count: foodCount,remark:remark];
                    dishList.add(dishMap);
                } else if (foodIds instanceof String[]) {//传入一组ID
                    for (int i = 0; i<foodIds.length; i++) {
                        long foodId = Number.toLong(foodIds[i]);
                        int foodCount = Number.toInteger(counts[i]);
                        String remark=remarks[i];
                        def dishMap = [foodId: foodId, count: foodCount,remark:remark];
                        dishList.add(dishMap);
                    }
                }
                for (int i = 0; i < dishList.size(); i++) {
                    def it = dishList.get(i);
                    //更新菜品数量
                    FoodInfo foodInfo = FoodInfo.get(it.foodId);
                    if (foodInfo) {
                        if (foodInfo.enabled) {//在售
                            if (foodInfo.countLimit >= it.count+foodInfo.sellCount) {//数量足够
                                foodInfo.sellCount += it.count;//当日销售量加上点菜数量
                                if (!foodInfo.save(flush: true)) {//保存数据失败
                                    failedList.add([foodId: it.foodId, msg: "更新所点菜的限额失败"]);
                                    continue;
                                }
                            } else {//数量不够
                                failedList.add([foodId: it.foodId, msg: "所点的菜超出今日限额了"]);
                                continue;
                            }
                        } else {//下架
                            failedList.add([foodId: it.foodId, msg: "所点的菜已经下架"]);
                            continue;
                        }
                    } else {//不存在
                        failedList.add([foodId: it.foodId, msg: "所点的菜不存在"]);
                        continue;
                    }
                    if(foodInfo.isReady){
                        status=DishesStatus.COOKED_STATUS.code;
                    }
                    //创建点菜记录
                    DishesInfo dishesInfo = new DishesInfo();
                    dishesInfo.orderId = orderId;
                    dishesInfo.foodId = it.foodId;
                    dishesInfo.status = status;
                    dishesInfo.valid = valid;
                    dishesInfo.numInRestaurant = 0;//先不要店内编号
                    dishesInfo.num = it.count;
                    dishesInfo.remark=it.remark;//备注
                    dishesInfo.foodPrice= foodInfo.price;//价格
                    dishesInfo.restaurantId=orderInfo.restaurantId;//饭店ID
                    dishesInfo.foodName=foodInfo.name;
                    dishesInfo.date=orderInfo.date;
                    dishesInfo.time=orderInfo.time;
                    dishesInfo.foodImg=foodInfo.image;
                    dishesInfo.tableName=orderInfo.tableName;
                    dishesInfo.orderType=orderInfo.orderType;
                    if (!dishesInfo.save(flush: true)) {//保存数据失败输出日志
                        println("保存点菜记录失败:" + dishesInfo);
                        failedList.add([foodId: it.foodId, msg: "保存点菜记录失败",errors:dishesInfo.errors.allErrors]);
                    }
                }
                //点菜完成后，根据订单状态来确定是否，在饭店的点菜列表中更新
                if (orderInfo.status >= OrderStatus.VERIFY_ORDERED_STATUS.code) {//订单的状态是点菜确认完成后,这时点的菜默认设置点菜的有效性和状态为1和1
                    //生成消息通知顾客
                    def msgParams=[:];
                    msgParams.orderId=orderId;
                    msgParams.type=MessageType.UPDATE_DISH_LIST.code;
                    msgParams.receiveId=0;
                    msgParams.content="需要更新点菜列表";
                    msgParams.sendType=MsgSendType.STAFF_TO_STAFF.code;
                    msgParams.restaurantId=orderInfo.restaurantId;
                    def reInfo=messageService.createMsg(msgParams);
                    if(reInfo.recode!=ReCode.OK){
                        println("保存消息失败，但对于订单的产生没有致命影响，故忽略此错误，请系统管理员注意查证："+",reInfo="+reInfo);
                    }
                }

                if(failedList.size()>0){
                    return [recode: ReCode.DISH_HAVEERROR,failedList: failedList];
                }
                else{
                    return [recode: ReCode.OK];
                }
            } else {
                return [recode: ReCode.NO_ENOUGH_PARAMS];
            }

        } else {
            return [recode: ReCode.ERROR_PARAMS];//参数错误
        }
    }

    /***************
     * 点菜取消
     * params是传入的参数
     * 参数格式为：
     * orderId=12 //订单Id
     * dishIds=12或dishIds=[112,231...] //点菜ID列表
     * 传入订单Id则按订单ID取消
     * *****************/
    def cancelDish(def params){
        def session = webUtilService.getSession();
        //SimpleDateFormat sdfDate=new SimpleDateFormat("yyyy-MM-dd");
        //SimpleDateFormat sdfTime=new SimpleDateFormat("HH:mm:ss");
        //取出用户ID
        //long userId = Number.toLong(session.userId);//用户ID
        long clientId=webUtilService.getClientId();
        if(clientId==0){
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
                if(it.valid==DishesValid.ORIGINAL_VALID.code||orderId!=0)
                {//有效性为初始状态下能取消或者对应的订单取消了能取消
                    it.valid=DishesValid.USER_CANCEL_VALID.code;
                    FoodInfo.executeUpdate("update FoodInfo set sellCount=sellCount-"+it.num+" where id="+it.foodId);//更新菜的销量
                }
            }
        }
        return [recode: ReCode.OK];//成功
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
        //long userId = Number.toLong(session.userId);//用户ID
        long clientId=webUtilService.getClientId();
        if(clientId==0){
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
                if(it.valid==DishesValid.USER_CANCEL_VALID.code||orderId!=0)
                {//有效性为用户取消下能删除或者对应的订单删除了能删除
                    it.delete(flush: true);
                }
            }
        }
        return [recode: ReCode.OK];//成功
    }

    //点菜查询
    def dishList(def params,boolean byWaiter){
        def session = webUtilService.getSession();
        SimpleDateFormat sdfDate=new SimpleDateFormat("yyyy-MM-dd");
        //SimpleDateFormat sdfTime=new SimpleDateFormat("HH:mm:ss");
        //取出用户ID
        //long userId=Number.toLong(session.userId);//用户ID
        long clientId=webUtilService.getClientId();
        if(byWaiter){//如果是工作人员查询，则取服务员ID作为用户ID
            clientId=Number.toLong(session.staffId);
        }
        if(clientId==0){
            //没登录
            return [recode: ReCode.NOT_LOGIN];
        }

        //取参数
        long orderId = Number.toLong(params.orderId);//订单ID
        long dishId=Number.toLong(params.dishId);//点菜ID
        long restaurantId=Number.toLong(params.restaurantId);//饭店ID
        int status=-1;//状态
        if(params.status!=null){
            status=Number.toInteger(params.status);
        }
        int valid=-1;//有效性
        if(params.valid!=null){
            valid=Number.toInteger(params.valid);
        }

        int statusGe=-1;
        if(params.statusGe!=null)
            statusGe=Number.toInteger(params.statusGe);//订单状态
        int validGe=-1;
        if (params.validGe!=null)
            validGe=Number.toInteger(params.validGe);//有效性
        int statusLe=-1;
        if(params.statusLe!=null)
            statusLe=Number.toInteger(params.statusLe);//订单状态
        int validLe=-1;
        if (params.validLe!=null)
            validLe=Number.toInteger(params.validLe);//有效性

        String dateStr=params.date;//开始日期
        //println("dateStr-->"+dateStr);
        Date date=null;
        try{date=sdfDate.parse(dateStr);}catch (Exception ex){}
        //println("date-->"+date);
        //如果是用户查询的话必须传入订单ID，且订单是该用户所有 ,这里不能加用户限定，因为有可能是用户创建的订单但是服务员帮忙点菜
        if(!byWaiter){
            OrderInfo orderInfo=OrderInfo.get(orderId);
            if(orderInfo){
                if(orderInfo.clientId!=clientId){//不属于该用户的订单
                    return [recode: ReCode.ERROR_PARAMS] ;
                }
            }
            else{ //订单不存在
                return [recode: ReCode.ERROR_PARAMS] ;
            }
        }

        if (!params.max) {
            params.max = 10
        }
        if (!params.offset) {
            params.offset = 0;
        }

        def condition={
            if(byWaiter){//如果是工作人员（这里一般是厨师）查询，需要加上饭店ID
                eq("restaurantId",restaurantId);
            }
            if(orderId){
                eq("orderId",orderId);
            }
            if(dishId){
                eq("id",dishId);
            }
            if(status>=0){
                eq("status",status);
            }
            if(valid>=0){
                eq("valid",valid);
            }
            if(date){
                eq("date",date);
            }
            if(statusGe>=0){
                ge("status",statusGe);//订单状态条件
            }
            if(validGe>=0){
                ge("valid",validGe);//订单有效性条件
            }
            if(statusLe>=0){
                le("status",statusLe);//订单状态条件
            }
            if(validLe>=0){
                le("valid",validLe);//订单有效性条件
            }
        }

        if(!params.sort){//如果没有排序，则按ID倒排序
            params.sort="id";
            params.order="desc";
        }

        def dishList=DishesInfo.createCriteria().list(params,condition);
        def totalCount=DishesInfo.createCriteria().count(condition);

        return [recode: ReCode.OK,totalCount:totalCount,dishList:dishList];

    }

    //根据ID获取一个点菜
    //参数 dishId
    def getDish(def params){
        def session = webUtilService.getSession();
        //取出用户ID
        //long userId = Number.toLong(session.userId);//用户ID
        long clientId=webUtilService.getClientId();
        if(clientId==0){
            //没登录
            return [recode: ReCode.NOT_LOGIN];
        }

        //取参数
        long dishId = Number.toLong(params.dishId);//点菜Id
        DishesInfo dishesInfo=DishesInfo.get(dishId);
        if(dishesInfo){
            return [recode: ReCode.OK,dishesInfo:dishesInfo];//成功
        }
        else{
            return [recode: ReCode.NO_RESULT];
        }

    }
}
