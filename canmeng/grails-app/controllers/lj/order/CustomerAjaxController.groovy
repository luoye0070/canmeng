package lj.order

import grails.converters.JSON
import lj.I118Error
import lj.common.StrCheckUtil
import lj.data.OrderInfo
import lj.enumCustom.OrderStatus
import lj.enumCustom.OrderValid
import lj.enumCustom.ReCode
import lj.order.customer.CustomerAppraiseService
import lj.order.customer.CustomerDishService
import lj.order.customer.CustomerOrderService
import lj.shop.SearchService

class CustomerAjaxController {
    CustomerOrderService customerOrderService;
    CustomerDishService customerDishService;
    SearchService searchService;
    CustomerAppraiseService customerAppraiseService;
    //创建订单
    def createOrder(){
        def reInfo=customerOrderService.createOrder(params,false);
        if(reInfo.recode!=ReCode.OK){
            if(reInfo.recode==ReCode.SAVE_FAILED){
                reInfo.errors=I118Error.getMessage(g,reInfo.errors,0);
            }
        }
        println("reInfo-->"+reInfo);
        render(reInfo as JSON);
    }
    //点菜界面数据
    def dataOfDish(){
        //查询订单信息
        OrderInfo orderInfoInstance=null;
        def reInfo=customerOrderService.orderInfo(params,false);
        if(reInfo.recode==ReCode.OK){
            orderInfoInstance=reInfo.orderInfoInstance;
            //判断订单当前状态是否能点菜
            if (orderInfoInstance.status >= OrderStatus.SERVED_STATUS.code||orderInfoInstance.valid>=OrderValid.USER_CANCEL_VALID.code) {//上菜完成不能再点菜了
                reInfo= [recode: ReCode.CANNOT_DISH];//不能点菜
                render(reInfo as JSON);
                return;
            }
            //查询菜谱
            def paramsT=[restaurantId:orderInfoInstance.restaurantId,enabled:true,offset:params.offset,max:params.max];
            reInfo=searchService.searchFood(paramsT);
            reInfo<<[orderInfoInstance:orderInfoInstance];
        }
        render(reInfo as JSON);
    }

    //点菜
    def addDishes(){
        def reInfo=customerDishService.addDishes(params,false);
        println("reInfo-->"+reInfo);
        render(reInfo as JSON);
    }

    //完成点菜
    def completeDish(){
        def reInfo=customerOrderService.completeDish(params,false);
        println("reInfo-->"+reInfo);
        render(reInfo as JSON);
    }

    //订单列表
    def orderList(){
        def reInfo=customerOrderService.orderList(params,false);
        println("reInfo-->"+reInfo);
        render(reInfo as JSON);
    }

    //订单详细信息
    def orderShow(){
        OrderInfo orderInfoInstance=null;
        def reInfo=customerOrderService.orderInfo(params,false);
        if(reInfo.recode==ReCode.OK){
            orderInfoInstance=reInfo.orderInfoInstance;
            //查询点菜
            def reInfo1=customerDishService.dishList(params,false);
            //查询评价
            def reInfo2=customerAppraiseService.appraiseInfo(params,false);
            reInfo1<<[orderInfoInstance:orderInfoInstance]<<[appraiseInfoInstance:reInfo2.appraiseInfoInstance];
            reInfo=reInfo1;
        }
        println("reInfo--->"+reInfo);
        render(reInfo as JSON);
    }
    //点菜取消
    def cancelDish(){
        def reInfo=customerDishService.cancelDish(params);
        println("reInfo-->"+reInfo);
        render(reInfo as JSON);
    }
    //点菜删除
    def delDish(){
        def reInfo=customerDishService.delDish(params);
        println("reInfo-->"+reInfo);
        render(reInfo as JSON);
    }

    //订单取消
    def cancelOrder(){
        def reInfo=customerOrderService.cancelOrder(params);
        println("reInfo-->"+reInfo);
        render(reInfo as JSON);
    }
    //订单删除
    def delOrder(){
        def reInfo=customerOrderService.delOrder(params);
        println("reInfo-->"+reInfo);
        render(reInfo as JSON);
    }
    //评价
    def appraiseOrder(){
        def reInfo=customerAppraiseService.createApprase(params);
        println("reInfo1-->"+reInfo);
        render(reInfo as JSON);
    }

    //预定桌位
    def reserveTable(){
        //获取参数
        println("params-->"+params);
        def restaurantId=params.restaurantId;
        def date=params.date;
        def reserveType=params.reserveType;
        def tableId=params.tableId;
        def phone=params.phone as String;

        def reInfo=[:];
        if(!restaurantId){
            reInfo=[recode: ReCode.NO_RESERVE_RESTAURANT];
            render(reInfo as JSON);
            return ;
        }
        if(!date){
            reInfo=[recode: ReCode.NO_RESERVE_DATE];
            render(reInfo as JSON);
            return ;
        }
        if(!reserveType){
            reInfo=[recode: ReCode.NO_RESERVE_TYPE];
            render(reInfo as JSON);
            return ;
        }
        if(!phone){
            reInfo=[recode: ReCode.NO_RIGHT_PHONE];
            render(reInfo as JSON);
            return ;
        }else if(!StrCheckUtil.chkStrFormat(phone,"phone")){
            reInfo=[recode: ReCode.NO_RIGHT_PHONE];
            render(reInfo as JSON);
            return ;
        }
        def errorList=[];
        def okList=[];
        params.remark="联系电话："+phone;//将联系电话写入备注信息中
        if(tableId instanceof String){//预定了一个桌位
            reInfo=customerOrderService.createOrder(params,false);
            if(reInfo.recode!=ReCode.OK){
                errorList.add([tableId:tableId]<<reInfo);
            }
            else{
                okList.add([tableId:tableId])
            }
        }else if(tableId instanceof String[]){//预定了多个桌位
            def paramsT=params.clone();
            paramsT.remove("tableId");
            for(int i=0;i<tableId.length;i++){
                paramsT.tableId=tableId[i];
                reInfo=customerOrderService.createOrder(paramsT,false);
                if(reInfo.recode!=ReCode.OK){
                    errorList.add([tableId:tableId[i]]<<reInfo);
                }
                else{
                    okList.add([tableId:tableId[i]]);
                }
            }
        }
        else{//没有预定任何桌位
            reInfo=[recode: ReCode.NO_RESERVE_TABLE];
            render(reInfo as JSON);
            return ;
        }
        //
        if(errorList!=null&&errorList.size()>0){
            for(int i=0;i<errorList.size();i++){
                def reInfoTemp=errorList.get(i);
                if(reInfoTemp.recode==ReCode.SAVE_FAILED){
                    reInfoTemp=[tableId:reInfoTemp.tableId,recode:[code:ReCode.SAVE_FAILED.code,label:I118Error.getMessage(g,reInfoTemp.errors,0)]];
                }
            }
            reInfo=[recode: ReCode.OK,errorList:errorList,okList:okList];
        }
        else{
            //预定成功，跳转到订单详情页面
            reInfo=[recode: ReCode.OK];
        }
        println("reInfo1-->"+reInfo);
        render(reInfo as JSON);
    }
    //查询有效订单,传入参数为params.restaurantId;
     def getOrdersByRestaurant(){
         params.statusLe=OrderStatus.VERIFY_ORDERED_STATUS.code;//上菜前的订单
         params.validLe=OrderValid.EFFECTIVE_VALID.code;//没有取消的订单
         params.max=40;//设置大一点，使全部查回
         def reInfo=customerOrderService.orderList(params,false);
         println("reInfo-->"+reInfo);
         render(reInfo as JSON);
     }
}
