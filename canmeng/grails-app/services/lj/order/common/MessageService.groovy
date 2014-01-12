package lj.order.common

import lj.data.MessageInfo
import lj.data.OrderInfo
import lj.enumCustom.MessageStatus
import lj.enumCustom.MsgSendType
import lj.enumCustom.OrderValid
import lj.enumCustom.ReCode
import lj.Number

import java.text.SimpleDateFormat

class MessageService {
    def webUtilService;
    def serviceMethod() {

    }

    /***************
     * 消息生成
     *
     * @param params是传入的参数map
     * params参数格式为：
     * orderId=12,订单ID，可选
     * type=1,消息类型，必须
     * receiveId=12,接收方ID，必须
     * sendId=12,发送方ID，可选
     * content,消息内容，可选
     * recTime,预定接收时间，可选
     * sendType,必须
     *
     * @return 返回值
     * [recode: ReCode.OK];成功
     * [recode: ReCode.SAVE_FAILED,errors:messageInfo.errors.allErrors];保存到数据库失败
     * [recode:ReCode.NOT_LOGIN];没登录
     * *****************/
    def createMsg(def params){
        def session = webUtilService.getSession();
        long userId=Number.toLong(session.userId);//用户ID
        if(!userId){
            userId=Number.toLong(session.staffId);//取工作人员ID,
        }
        if(userId){
            if(!params.sendId){
                params.sendId=userId;
            }
            if(!params.recTime){
                params.recTime=new Date();
            }
            MessageInfo messageInfo=new MessageInfo(params);
            if(messageInfo.validate()){
                if(messageInfo.save(flush: true))
                    return [recode: ReCode.OK];
                else
                    return [recode: ReCode.SAVE_FAILED,errors:messageInfo.errors.allErrors];
            }
            else{
                return [recode: ReCode.SAVE_FAILED,errors:messageInfo.errors.allErrors];
            }
        }
        else{
            return [recode:ReCode.NOT_LOGIN];
        }
    }

    //接收消息
    def getMsg(def params){
        def session = webUtilService.getSession();
        long userId=Number.toLong(session.userId);//用户ID
        if(!userId){
            userId=Number.toLong(session.staffId);//取工作人员ID,
            println("session.staffId-->"+session.staffId);
        }
        if(userId){
            //获取参数
            long msgId=Number.toLong(params.msgId);//上次获取的message的id
            if(msgId){//设置消息的状态为已读
                MessageInfo messageInfo=MessageInfo.get(msgId);
                if(messageInfo){
                    messageInfo.status= MessageStatus.READED_STATUS.code;
                    if(!messageInfo.save(flush: true)){
                        println("消息状态改变保存失败！注意查证:error:");
                        messageInfo.errors.allErrors.each {
                            println(it);
                        }
                    }
                }
            }
            //获取一条消息
            Date now=new Date();
            MessageInfo messageInfo=null;
            if(session.userId){ // 普通用户获取消息
                messageInfo=MessageInfo.findByReceiveIdAndRecTimeLessThanEqualsAndSendTypeInListAndStatus(userId,now,[MsgSendType.STAFF_TO_CUSTOMER.code,MsgSendType.CUSTOMER_TO_CUSTOMER.code],MessageStatus.ORIGINAL_STATUS.code);
                if(!messageInfo&&session.staffId){ //如果没有查询到且工作人员在线，则按工作人员ID查询
                    long sid=lj.Number.toLong(session.staffId);
                    messageInfo=MessageInfo.findByReceiveIdAndRecTimeLessThanEqualsAndSendTypeInListAndStatus(sid,now,[MsgSendType.CUSTOMER_TO_STAFF.code,MsgSendType.STAFF_TO_STAFF.code],MessageStatus.ORIGINAL_STATUS.code);
                }
            }
            else{  //工作人员获取消息
                messageInfo=MessageInfo.findByReceiveIdAndRecTimeLessThanEqualsAndSendTypeInListAndStatus(userId,now,[MsgSendType.CUSTOMER_TO_STAFF.code,MsgSendType.STAFF_TO_STAFF.code],MessageStatus.ORIGINAL_STATUS.code);
            }

            if(messageInfo)
                return [recode: ReCode.OK,messageInfo:messageInfo];
            else
                return [recode: ReCode.NO_RESULT];
        }
        else{
            return [recode:ReCode.NOT_LOGIN];
        }
    }

    //消息列表
    def msgList(def params){
        SimpleDateFormat sdfDate=new SimpleDateFormat("yyyy-MM-dd");
        def session = webUtilService.getSession();
        long userId=Number.toLong(session.userId);//用户ID
        if(!userId){
            userId=Number.toLong(session.staffId);//取工作人员ID,
        }
        if(userId){
            //获取查询参数
            int type=-1;
            if(params.type!=null)
                type=Number.toInteger(params.type);  //类型
            int status=-1;
            if(params.status!=null)
                status=Number.toInteger(params.status);//状态
            String content=params.content;//内容
            String beginDateStr=params.beginDate;//开始日期
            Date beginDate=null;
            try{beginDate=sdfDate.parse(beginDateStr);}catch (Exception ex){ex.printStackTrace();}
            String endDateStr=params.endDate;//截止日期
            Date endDate=null;
            try{endDate=sdfDate.parse(endDateStr);}catch (Exception ex){ex.printStackTrace();}
            if(endDate){
                Calendar calendar=Calendar.getInstance();
                calendar.setTime(endDate);
                calendar.set(Calendar.HOUR_OF_DAY,23);
                calendar.set(Calendar.MINUTE,59);
                calendar.set(Calendar.SECOND,59);
                calendar.set(Calendar.MILLISECOND,999);
                endDate=calendar.getTime();
            }
            //int sendType=Number.toInteger(params.sendType);//发送方向,默认为0工作人员到顾客
            int rsType=Number.toInteger(params.rsType);//收发类型,0收，1发，默认0


            //查询条件
            def condition={
                if(rsType==0){
                    if(session.userId){//普通用户
                        'in'("sendType", [MsgSendType.STAFF_TO_CUSTOMER,MsgSendType.CUSTOMER_TO_CUSTOMER]);
                    }
                    else{//工作人员
                        'in'("sendType", [MsgSendType.CUSTOMER_TO_STAFF,MsgSendType.STAFF_TO_STAFF]);
                    }
                    eq("receiveId",userId);
                }
                else{
                    if(session.userId){//普通用户
                        'in'("sendType", [MsgSendType.CUSTOMER_TO_STAFF,MsgSendType.CUSTOMER_TO_CUSTOMER]);
                    }
                    else{//工作人员
                        'in'("sendType", [MsgSendType.STAFF_TO_CUSTOMER,MsgSendType.STAFF_TO_STAFF]);
                    }
                    eq("sendId",userId);
                }
                if(type>=0){
                    eq("type",type);
                }
                if(status>=0){
                    eq("status",status);
                }
                if(content){
                    like("content","%"+content+"%");
                }
                if(beginDate){
                    ge("recTime",beginDate);
                }
                if(endDate){
                    le("recTime",endDate);
                }
            }

            def msgList=MessageInfo.createCriteria().list(params,condition);
            def totalCount=MessageInfo.createCriteria().count(condition);

            return [recode: ReCode.OK,totalCount:totalCount,msgList:msgList];

        }else{
                return [recode:ReCode.NOT_LOGIN];
        }
    }
}