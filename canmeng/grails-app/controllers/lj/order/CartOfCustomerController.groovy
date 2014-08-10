package lj.order

import lj.enumCustom.ReCode
import lj.order.customer.CartOfCustomerService

class CartOfCustomerController {
    def userSearchService;
    CartOfCustomerService cartOfCustomerService;
    def webUtilService;
    //结算餐车
    def cartCheckout(){
        flash.message=null;
        flash.error=null;
        if(request.method=="POST"){
            //从餐车产生订单
            def reInfo=cartOfCustomerService.makeOrderFromCarts(params);
            if(reInfo.recode== ReCode.OK){
                redirect(controller: "customer",action: "orderList");
                return;
            }else{
                flash.error=reInfo.recode.label;
            }
        }
        //查询地址信息
        def res=[addresses:userSearchService.getAddresses(),defaultAddrId:webUtilService.getClient()?.defaultAddrId];
        println("res-->"+res);
        render(view: "cartCheckout",model: res);
    }

}
