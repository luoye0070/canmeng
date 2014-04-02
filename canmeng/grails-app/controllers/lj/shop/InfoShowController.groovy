package lj.shop

import lj.data.FoodInfo
import lj.data.RestaurantInfo
import lj.enumCustom.ReCode
import lj.enumCustom.VerifyStatus
import lj.order.customer.CustomerAppraiseService

class InfoShowController {
    SearchService searchService;
    CustomerAppraiseService customerAppraiseService;
    //店铺展示
    def shopShow(){
        def err=null;
        def msg=null;
        RestaurantInfo restaurantInfo=null;
        //查询店铺信息
        params.enabled=true;
        params.verifyStatus=VerifyStatus.PASS.code;
        if(lj.Number.toLong(params.id)==0){
            params.restaurantId=-1;
        }
        else{
            params.restaurantId=params.id;
        }
        def reInfo=searchService.searchShop(params);
        if (reInfo.recode==ReCode.OK){
            if(reInfo.restaurantList&&reInfo.restaurantList.size()>0){
                restaurantInfo=reInfo.restaurantList.get(0);
            }
        }
        if(restaurantInfo){
            //将菜系ID换成菜系名称
            render(view: "shopShow",model: [restaurantInfo:restaurantInfo]);
        }
        else
            render(view: "../error",model: [error:"饭店不存在或已经关闭了"]);
    }
    //菜谱展示
    def foodShow(){
        def err=null;
        def msg=null;
        FoodInfo foodInfo=null;
        RestaurantInfo restaurantInfo=null;
        def foodList=null;
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
                        //查询出饭店中5个菜
                        paramsT.max=5;
                        reInfo=searchService.searchFood(paramsT);
                        if(reInfo.recode==ReCode.OK){
                            foodList=reInfo.foodList;
                        }
                    }
                    else{
                        foodInfo=null;
                    }
                }
            }
        }
        if(foodInfo)
            render(view: "foodShow",model: [foodInfo:foodInfo,restaurantInfo:restaurantInfo,foodList:foodList]);
        else
            render(view: "../error",model: [error:"菜谱不存在或已经下架了"]);
    }

    //预定的桌位列表
    def tablesShow(){
        //获取参数
//        String dateStr=params.date;//用餐日期
//        int reserveType=params.reserveType;//预定类型
//        long restaurantId=params.restaurantId;//饭店ID
        def err=null;
        def msg=null;

        if(!params.date){
            params.date=lj.FormatUtil.dateFormat(new Date());
        }
        if(!params.reserveType){
            params.reserveType="1";
        }
        params.enabled=true;
        params.canReserve=true;
        def tableId=params.tableId;
        params.remove("tableId");
        println("params-->"+params);
        def reInfo=searchService.searchTable(params);
        println("reInfo-->"+reInfo);

        //查询出饭店信息
        def paramsT=[restaurantId:params.restaurantId];
        def reInfo1=searchService.searchShop(paramsT);
        RestaurantInfo restaurantInfo=null;
        if(reInfo1.restaurantList&&reInfo1.restaurantList.size()>0){
            restaurantInfo=reInfo1.restaurantList.get(0);
        }

        reInfo.params<<[tableId:tableId];
        reInfo<<[restaurantInfo:restaurantInfo];
        render(view:"tablesShow",model: reInfo);

    }

    //到店吃饭创建订单输入界面
    def orderInput(){
    }


    //饭店评价信息
    def appraiseList(){
        long restaurantId=lj.Number.toLong(params.restaurantId);//饭店ID
        if(restaurantId==0){
            params.restaurantId=-1;
        }
        def reInfo=customerAppraiseService.appraiseList(params);
        render(view: "appraiseListInShop",model: reInfo);
    }

}
