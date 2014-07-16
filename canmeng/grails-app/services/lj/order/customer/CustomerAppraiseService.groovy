package lj.order.customer

import lj.data.AppraiseInfo
import lj.Number
import lj.data.ClientInfo
import lj.data.OrderInfo
import lj.data.UserInfo
import lj.enumCustom.OrderStatus
import lj.enumCustom.ReCode

class CustomerAppraiseService {
    def webUtilService;
    def userService;
    def serviceMethod() {

    }

    //创建一个评价
    def createApprase(def params){
        def session = webUtilService.getSession();
        //UserInfo user=session.user;//用户ID
        long clientId=webUtilService.getClientId();
        //if(user){
        if(clientId){
            //检查订单是否已经评价
            long orderId=Number.toLong(params.orderId);
            OrderInfo orderInfo=OrderInfo.findByClientIdAndId(clientId,orderId);
            if(orderInfo){
                if(orderInfo.status== OrderStatus.APPRAISED_STATUS.code){
                    return [recode: ReCode.ORDER_APPRAISED];
                }
            }
            else{
                return [recode: ReCode.NO_ORDER];
            }

            ClientInfo clientInfo=webUtilService.getClient();
            //创建评价
            params.clientId=clientId;
            if(clientInfo.userName){
                params.userName=clientInfo.userName;
            }else{
                params.userName="客户"+clientId;
            }
            params.appraiseTime=new Date();
            params.restaurantId=orderInfo.restaurantId;
            AppraiseInfo appraiseInfo=new AppraiseInfo(params);
            if(appraiseInfo.validate()){
                if(appraiseInfo.save(flush: true))
                {
                    //更新订单的状态为评价完成
                    orderInfo.status= OrderStatus.APPRAISED_STATUS.code;
                    if(!orderInfo.save(flush: true)){//保存订单状态失败
                        return [recode: ReCode.SAVE_FAILED,errors:orderInfo.errors.allErrors];
                    }
                    return [recode: ReCode.OK];
                }
                else
                    return [recode: ReCode.SAVE_FAILED,errors:appraiseInfo.errors.allErrors];
            }
            else{
                return [recode: ReCode.SAVE_FAILED,errors:appraiseInfo.errors.allErrors];
            }
        }
        else{
            return [recode:ReCode.NOT_LOGIN];
        }
    }

    //评价列表
    def appraiseList(def params){
        //获取参数
        long orderId=Number.toLong(params.orderId); //订单ID
        int type=-1;
        if(params.type!=null)
            type=Number.toInteger(params.type);//类型
        long restaurantId=Number.toLong(params.restaurantId);//饭店ID
        //long userId=Number.toLong(params.userId);//用户ID
        long clientId=Number.toLong(params.clientId);//客户ID；
        Boolean isAnonymity=params.isAnonymity;//是否匿名

        def cIds=userService.getIds(ClientInfo.get(clientId));

        if (!params.max) {
            params.max = 10;
        }
        if (!params.offset) {
            params.offset = 0;
        }

        //查询条件
        def condition={
            if(orderId){
                eq("orderId",orderId);
            }
            if(type>=0){
                eq("type",type);
            }
            if(restaurantId){
                eq("restaurantId",restaurantId);
            }
            if(clientId){
                'in'("clientId",cIds);
            }
            if(isAnonymity!=null){
                eq("isAnonymity",isAnonymity);
            }
        }

        def appraiseList=AppraiseInfo.createCriteria().list(params,condition);
        def totalCount=AppraiseInfo.createCriteria().count(condition);

        if(appraiseList){
            return [recode: ReCode.OK,totalCount:totalCount,appraiseList:appraiseList,params:params];
        }
        else{
            return [recode: ReCode.NO_RESULT,totalCount:totalCount,appraiseList:appraiseList,params:params]
        }
    }


    //订单详情,根据订单ID查询
    def appraiseInfo(def params,boolean byWaiter){
        def session=webUtilService.getSession();
        //SimpleDateFormat sdfDate=new SimpleDateFormat("yyyy-MM-dd");
        //SimpleDateFormat sdfTime=new SimpleDateFormat("HH:mm:ss");
        //取出用户ID
        //long userId=Number.toLong(session.userId);//用户ID
        long clientId=Number.toLong(webUtilService.getClientId());
        if (byWaiter) {//如果是服务员帮助更新订单状态为点菜完成，则取服务员ID作为用户ID
            clientId = Number.toLong(session.staffId);
        }
        if(clientId){
            //获取参数
            long orderId=Number.toLong(params.orderId);//订单号
            AppraiseInfo appraiseInfo=null;
            if(byWaiter){
                appraiseInfo=AppraiseInfo.findByOrderId(orderId);
            }
            else{
                appraiseInfo=AppraiseInfo.findByOrderIdAndClientId(orderId,clientId);
            }
            if(appraiseInfo){
                return [recode: ReCode.OK,appraiseInfoInstance:appraiseInfo];
            }
            else{
                return [recode: ReCode.ORDER_NOT_EXIST];
            }
        }
        else{
            return [recode:ReCode.NOT_LOGIN];
        }
    }
}
