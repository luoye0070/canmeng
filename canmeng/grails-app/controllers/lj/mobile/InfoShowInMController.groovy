package lj.mobile

import lj.data.FoodInfo
import lj.data.RestaurantInfo
import lj.enumCustom.ReCode
import lj.enumCustom.VerifyStatus
import lj.order.customer.CustomerAppraiseService
import lj.shop.SearchService

class InfoShowInMController {
    SearchService searchService;
    CustomerAppraiseService customerAppraiseService;

    //菜谱展示
    def foodShow(){
        def err=null;
        def msg=null;
        FoodInfo foodInfo=null;
        RestaurantInfo restaurantInfo=null;
        //查询菜单
        params.enabled=true;
        if(lj.Number.toLong(params.id)==0){
            params.foodId=-1;
        }
        else{
            params.foodId=params.id;
        }
        def reInfo=searchService.searchFood(params);
        if (reInfo.recode==ReCode.OK){
            if(reInfo.foodList&&reInfo.foodList.size()>0){
                foodInfo=reInfo.foodList.get(0);
                //查询饭店
                def paramsT=[:];
                paramsT.restaurantId=foodInfo.restaurantId;
                paramsT.enabled=true;
                paramsT.verifyStatus=VerifyStatus.PASS.code;
                reInfo=searchService.searchShop(paramsT);
                if(reInfo.recode==ReCode.OK){
                    if(reInfo.restaurantList&&reInfo.restaurantList.size()>0){
                        restaurantInfo=reInfo.restaurantList.get(0);
                    }
                    else{
                        foodInfo=null;
                    }
                }
            }
        }
        if(foodInfo)
            render(view: "foodShow",model: [foodInfo:foodInfo,restaurantInfo:restaurantInfo]);
        else
            render(view: "../error",model: [error:"菜谱不存在或已经下架了"]);
    }
}
