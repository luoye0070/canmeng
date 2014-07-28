package lj.order

import lj.order.customer.CartOfCustomerService

class CartOfCustomerController {
    def userSearchService;
    CartOfCustomerService cartOfCustomerService;
    def webUtilService;
    //结算餐车
    def checkout(){
        //查询餐车列表
        def reInfo=cartOfCustomerService.getCartsAndDishes();
        println("reInfo-->"+reInfo);
        //查询地址信息
        //def res=[addresses:userSearchService.getAddresses(),defaultAddrId:webUtilService.getClient().defaultAddrId];

        render(view: "checkout",model: reInfo);
    }
}
