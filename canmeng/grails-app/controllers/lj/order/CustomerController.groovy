package lj.order

import lj.I118Error
import lj.common.StrCheckUtil
import lj.data.OrderInfo
import lj.enumCustom.ReCode
import lj.enumCustom.ReserveType
import lj.order.customer.*
import lj.shop.SearchService

import java.text.SimpleDateFormat;
class CustomerController {
    CustomerOrderService customerOrderService;
    CustomerDishService customerDishService;
    SearchService searchService;
    CustomerAppraiseService customerAppraiseService;

    def index() {
        redirect([action:"test"]);
    }
    def test(){
//        if(params.test instanceof String){
//            println("isString")
//        }
//        if(params.test instanceof String[]){
//            println("isList")
//        }
//        println(params.test)
//
//        SimpleDateFormat sdfDate=new SimpleDateFormat("yyyy-MM-dd");
//        Date date=sdfDate.parse(params.date);
//        println("date-->"+date);

        //创建订单
        /*tableId=Number.toLong(params.tableId);//桌位ID
        *restaurantId=Number.toLong(params.restaurantId);//饭店ID,必须
        *dateStr=params.date; //日期,yyyy-MM-dd ,到店吃饭可以不要
        *timeStr=params.time; //时间,HH:mm:ss ，到店吃饭可以不要
        *reserveType=Number.toInteger(params.reserveType);//预定类别 ,非预定可以不要
        *addressId=Number.toLong(params.addressId);//外卖地址ID ,非外卖可以不要
        * //点菜列表参数,非必须
        * foodIds=12或foodIds=[112,231...]
        * counts=1或counts=[2,3...]
        * remarks=ddd或remarks=[dd,,dd...]
        * */

//        session.setAttribute("userId","23");
//
//        params.tableId=1;
//        params.restaurantId=1;
//        params.dateStr="2013-08-28";
//        params.timeStr="20:12:00";
//        params.reserveType=ReserveType.NIGHT.code;
//        //params.addressId=2;
//        //params.foodIds=[12,34,45];
//        //params.counts=[2,1,3];
//        //params.remarks=["33","","dd"];
//
//        def reInfo=customerOrderService.createOrder(params,false);
//        println("reInfo-->"+reInfo);

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

        if(!restaurantId){
            flash.error="请选择饭店";
            redirect(controller: "infoShow",action: "tablesShow",params: params);
            return ;
        }
        if(!date){
            flash.error="请选择预定日期";
            redirect(controller: "infoShow",action: "tablesShow",params: params);
            return ;
        }
        if(!reserveType){
            flash.error="请选择预定的类型";
            redirect(controller: "infoShow",action: "tablesShow",params: params);
            return ;
        }
        if(!phone){
            flash.error="请填写联系电话";
            redirect(controller: "infoShow",action: "tablesShow",params: params);
            return ;
        }else if(!StrCheckUtil.chkStrFormat(phone,"phone")){
            flash.error="填写的联系电话格式不正确";
            redirect(controller: "infoShow",action: "tablesShow",params: params);
            return ;
        }

        def reInfo=null;
        def errorList=[];
        def okList=[];
        params.remark="联系电话："+phone;//将联系电话写入备注信息中
        if(tableId instanceof String){//预定了一个桌位
            reInfo=customerOrderService.createOrder(params,false);
            if(reInfo.recode!=ReCode.OK){
                errorList.add([tableId:tableId,reInfo:reInfo]);
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
                    errorList.add([tableId:tableId[i],reInfo:reInfo]);
                }
                else{
                    okList.add([tableId:tableId[i]]);
                }
            }
        }
        else{//没有预定任何桌位
            flash.error="请选择预定的桌位";
            redirect(controller: "infoShow",action: "tablesShow",params: params);
            return ;
        }

        //返回桌位展示页
        if(errorList!=null&&errorList.size()>0){
            flash.message="";
            for(int i=0;i<okList.size();i++){
                flash.message+="桌位:"+okList.get(i).tableId+"，预定成功<br/>";
            }

            flash.error="";
            for(int i=0;i<errorList.size();i++){
                reInfo=errorList.get(i).reInfo;
                flash.error+="桌位:"+errorList.get(i).tableId+"，预定不成功，失败原因是：";
                if(reInfo.recode==ReCode.SAVE_FAILED){
                    flash.error+= I118Error.getMessage(g,reInfo.errors,0)+"<br/>";
                }
                else{
                    flash.error+=reInfo.recode.label+"<br/>";
                }
            }
            redirect(controller: "infoShow",action: "tablesShow",params: params);

        }
        else{
            //预定成功，跳转到订单详情页面
            redirect(action: "orderList",params: [orderId:reInfo.orderInfo.id]);
        }
    }



    //创建订单
    def createOrder(){
        def reInfo=customerOrderService.createOrder(params,false);
        if(reInfo.recode==ReCode.OK){
            //预定成功，跳转到订单详情页面
            redirect(action: "orderShow",params: [orderId:reInfo.orderInfo.id]);
        }
        else{//返回桌位展示页
            if(reInfo.recode==ReCode.SAVE_FAILED){
                flash.error= I118Error.getMessage(g,reInfo.errors,0);
            }
            else{
                flash.error=reInfo.recode.label;
            }

            redirect(controller: "infoShow",action: "orderInput",params: params);
        }
    }
    //订单取消
    def cancelOrder(){
        def reInfo=customerOrderService.cancelOrder(params);
        println("reInfo-->"+reInfo);
        //render(reInfo);
        if(reInfo.recode==ReCode.OK){
            flash.message=reInfo.recode.label;
        }
        else{
            flash.error=reInfo.recode.label;
        }
        if(params.backUrl){
            redirect(url: params.backUrl);
            return ;
        }
        redirect(action: "orderList");
    }
    //订单列表
    def orderList(){
        def reInfo=customerOrderService.orderList(params,false);
        println("reInfo-->"+reInfo);
        render(view: "orderList",model: reInfo);
    }
    //订单删除
    def delOrder(){
        def reInfo=customerOrderService.delOrder(params);
        println("reInfo-->"+reInfo);
        //render(reInfo);
        if(reInfo.recode==ReCode.OK){
            flash.message=reInfo.recode.label;
        }
        else{
            flash.error=reInfo.recode.label;
        }
        redirect(action: "orderList");
    }
    //订单详细信息
    def orderShow(){
        //customerOrderService.orderInfo(params,false);

        OrderInfo orderInfoInstance=null;
        def reInfo=customerOrderService.orderInfo(params,false);
        if(reInfo.recode==ReCode.OK){
            orderInfoInstance=reInfo.orderInfoInstance;
            //查询点菜
            def reInfo1=customerDishService.dishList(params,false);
            //查询评价
            def reInfo2=customerAppraiseService.appraiseInfo(params,false);

            reInfo1<<[orderInfoInstance:orderInfoInstance]<<[appraiseInfoInstance:reInfo2.appraiseInfoInstance]<<[params:params];
            reInfo=reInfo1;
        }
        //reInfo<<[orderInfoInstance:orderInfoInstance]<<[params:params];
        println("reInfo--->"+reInfo);
        render(view: "orderShow",model: reInfo);
    }
    //进入点菜界面
    def doDish(){
        //查询订单信息
        OrderInfo orderInfoInstance=null;
        def reInfo=customerOrderService.orderInfo(params,false);
        if(reInfo.recode==ReCode.OK){
            orderInfoInstance=reInfo.orderInfoInstance;
            //查询菜谱
            def paramsT=[restaurantId:orderInfoInstance.restaurantId,enabled:true,offset:params.offset,max:params.max]
            reInfo=searchService.searchFood(paramsT);
        }
        reInfo<<[orderInfoInstance:orderInfoInstance]<<[params:params];
        render(view: "doDish",model: reInfo);
    }
    //完成点菜
    def completeDish(){
        def reInfo=customerOrderService.completeDish(params,false);
        println("reInfo-->"+reInfo);
        //render(reInfo);
        if(reInfo.recode==ReCode.OK){
            flash.message=reInfo.recode.label;
        }
        else{
            flash.error=reInfo.recode.label;
        }
        if(params.backUrl){
            redirect(url: params.backUrl);
            return ;
        }
        redirect(action: "orderShow",params: [orderId:params.orderId]);
    }

    //点菜
    def addDishes(){
        def reInfo=customerDishService.addDishes(params,false);
        println("reInfo-->"+reInfo);
        if(reInfo.recode==ReCode.OK){
            flash.message=reInfo.recode.label;
        }
        else{
            flash.error=reInfo.recode.label;
        }
        redirect(action: "doDish",params: [orderId:params.orderId]);
    }
    //点菜取消
    def cancelDish(){
        def orderId=params.orderId;
        params.remove("orderId");
        def reInfo=customerDishService.cancelDish(params);
        println("reInfo-->"+reInfo);
        if(reInfo.recode==ReCode.OK){
            flash.message=reInfo.recode.label;
        }
        else{
            flash.error=reInfo.recode.label;
        }
        if(params.backUrl){
            redirect(url: params.backUrl);
            return ;
        }
        redirect(action: "orderShow",params: [orderId:orderId]);
    }
    //点菜列表
    def dishList(){ //暂时不用
        customerDishService.dishList(params,false);
    }
    //点菜删除
    def delDish(){
        def orderId=params.orderId;
        params.remove("orderId");
        def reInfo=customerDishService.delDish(params);
        println("reInfo-->"+reInfo);
        if(reInfo.recode==ReCode.OK){
            flash.message=reInfo.recode.label;
        }
        else{
            flash.error=reInfo.recode.label;
        }
        if(params.backUrl){
            redirect(url: params.backUrl);
            return ;
        }
        redirect(action: "orderShow",params: [orderId:orderId]);
    }
    //评价
    def appraiseOrder(){
        def reInfo=customerOrderService.orderInfo(params,false);
        if(request.method=="POST"){
            def reInfo1=customerAppraiseService.createApprase(params);
            if(reInfo1.recode==ReCode.OK){
                flash.message="评价成功";
                redirect(action: "orderShow",params: [orderId: params.orderId]);
                return ;
            }
            else{
                flash.error=reInfo1.recode.label;
            }
            println("reInfo1-->"+reInfo1);
        }
        reInfo<<[appraiseInfoInstance:params];
        render(view: "appraiseOrder",model: reInfo);
    }
}
