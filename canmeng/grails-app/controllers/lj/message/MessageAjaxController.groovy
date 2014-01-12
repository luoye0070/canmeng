package lj.message

import grails.converters.JSON
import lj.order.common.MessageService

class MessageAjaxController {
    MessageService messageService;
    def index() {}

    //获取一条消息
    def getMsg(){
       def reInfo=messageService.getMsg(params);
       println("reInfo-->"+reInfo);
       render(reInfo as JSON);
    }
}
