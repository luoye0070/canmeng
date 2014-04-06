package lj.message

import lj.enumCustom.MessageType
import lj.enumCustom.MsgSendType
import lj.enumCustom.OrderStatus
import lj.order.common.MessageService

class MessageController {
    MessageService messageService;
    def index() {

    }
    def sendMsg(){
        if(request.method=="POST"){
        def msgParams=[:];
        msgParams.orderId=0;
        msgParams.type=MessageType.OTHER_TYPE.code;
        msgParams.receiveId=params.receiveId;
        msgParams.content=params.content;
        if(params.sendType=="1")
            msgParams.sendType=MsgSendType.CUSTOMER_TO_STAFF.code;
        else
            msgParams.sendType=MsgSendType.STAFF_TO_CUSTOMER.code;
        msgParams.restaurantId=0;
        def reInfo=messageService.createMsg(msgParams);
            flash.message=reInfo.recode.label;
        }
        render(view: "sendMsg");
    }

}
