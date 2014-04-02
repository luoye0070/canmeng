package lj.shop

import lj.enumCustom.VerifyStatus
import lj.sysparameter.AreaParamService
import lj.sysparameter.CuisineParamService

class SearchController {
    SearchService searchService;
    AreaParamService areaParamService;
    CuisineParamService cuisineParamService;
    //饭店检索
    def searchShop(){
        def err=null;
        def msg=null;
        def reInfo=[:];
        //if(request.method=="POST"){
            params.enabled=true;
            params.verifyStatus=VerifyStatus.PASS.code;
            reInfo<<searchService.searchShop(params);
        //}
        def provinceList=[];
        provinceList.add([id:0,province:"请选择"]);
        def provinceListTemp=areaParamService.getProvinceList().provinceList;
        if(provinceListTemp){
            provinceListTemp.each {
                provinceList.add([id:it.id,province:it.province]);
            }
        }
        def cuisineList=[];
        cuisineList.add([id:0,name:"请选择"]);
        def cuisineListTemp=cuisineParamService.getCuisineList().cuisineList;
        if(cuisineListTemp){
            cuisineListTemp.each {
                cuisineList.add([id:it.id,name:it.name]);
            }
        }
        reInfo<<[provinceList:provinceList];
        reInfo<<[cuisineList:cuisineList];
        println("reInfo-->"+reInfo);
        if(params.showPlace=="page")
            render(view: "searchShopInPage",model: reInfo);
        else
            render(view: "searchShop",model: reInfo);
    }

    //菜单搜索
    def searchFood(){
        def err=null;
        def msg=null;
        def reInfo=[:];
        //if(request.method=="POST"){
        if(params.inShop){
            params.max=20;
        }
        else{
            params.max=28;
        }

            params.enabled=true;
            reInfo<<searchService.searchFood(params);
        //}
        def provinceList=[];
        provinceList.add([id:0,province:"请选择"]);
        def provinceListTemp=areaParamService.getProvinceList().provinceList;
        if(provinceListTemp){
            provinceListTemp.each {
                provinceList.add([id:it.id,province:it.province]);
            }
        }
        def cuisineList=[];
        cuisineList.add([id:0,name:"请选择"]);
        def cuisineListTemp=cuisineParamService.getCuisineList().cuisineList;
        if(cuisineListTemp){
            cuisineListTemp.each {
                cuisineList.add([id:it.id,name:it.name]);
            }
        }
        reInfo<<[provinceList:provinceList];
        reInfo<<[cuisineList:cuisineList];
        println("reInfo-->"+reInfo);
        if(params.inShop)
            render(view: "searchFoodInShop",model: reInfo);
        else if(params.showPlace=="page")
            render(view: "searchFoodInPage",model: reInfo);
        else
            render(view: "searchFood",model: reInfo);
    }
}
