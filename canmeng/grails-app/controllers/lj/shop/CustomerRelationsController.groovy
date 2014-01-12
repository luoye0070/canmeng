package lj.shop

import grails.converters.JSON
import lj.enumCustom.CustomerRelationsType
import lj.enumCustom.ReCode

class CustomerRelationsController {
    CustomerRelationsService customerRelationsService;
    def index() { }

    //客户关系列表
    def crList(){
        def err=null;
        def msg=null;

        def reInfo=customerRelationsService.search(params);

        render(view: "crList",model: reInfo);
    }

    //编辑客户关系
    def editCr(){
        def err=null;
        def msg=null;
        def customerRelationsInstance=null;
        if(request.method=="GET"){
            def reInfo=customerRelationsService.getCrInfo(params); //根据ID查询到一个菜单
            if(reInfo.recode==ReCode.OK){
                customerRelationsInstance=reInfo.customerRelations;
            }
        }
        else if(request.method=="POST"){
            params.id=params.crId;
            def reInfo=customerRelationsService.save(params);
            if(reInfo.recode==ReCode.OK){
                msg="保存成功";
            }
            else if(reInfo.recode==ReCode.SAVE_FAILED){
                err=lj.I118Error.getMessage(g,reInfo.errors,0);
            }
            else {
                err=lj.I118Error.getMessage(g,reInfo.recode.label,0);
            }
            customerRelationsInstance=reInfo.customerRelations;
        }

        //从订单中获取客户列表
        def userList=null;
        def reInfo=customerRelationsService.getCustomerFromOrder(params);
        if(reInfo.recode==ReCode.OK){
            userList=reInfo.userList;
        }
        println("userList--->"+userList);
        //获取客户类型列表
        def customerRelationsTypes=CustomerRelationsType.customerRelationsTypes;
        render(view: "editCr",model: [customerRelationsInstance:customerRelationsInstance,err:err,msg:msg,userList:userList,customerRelationsTypes:customerRelationsTypes]);
    }

    //删除客户关系
    def delCr(){
        def reInfo=customerRelationsService.delete(params);
        if(reInfo.recode==ReCode.OK){//删除成功
            flash.message=reInfo.recode.label;
        }
        else{
            flash.message=reInfo.recode.label;
        }
        redirect(controller: "customerRelations",action: "crList");
    }

    //从订单中获取客户信息
    def cListFromOrderAsJson(){
        def err=null;
        def msg=null;

        def reInfo=customerRelationsService.getCustomerFromOrder(params);

        render reInfo as JSON;
    }


}
