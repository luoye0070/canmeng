package lj.shop

import lj.common.UploadFile
import lj.data.CuisineInfo
import lj.enumCustom.ReCode
import lj.sysparameter.AreaParamService
import lj.sysparameter.CuisineParamService
import lj.sysparameter.PackParamService

//店铺相关控制器
class ShopController {
    ShopService shopService;
    ImageSpaceService imageSpaceService;
    CuisineParamService cuisineParamService;
    AreaParamService areaParamService;
    PackParamService packParamService;


//    def index() {
//
//    }
    //店铺信息页面
    def shopInfo(){
        shopService.getShopInfo(params)
    }

    //店铺注册
    def shopRegister(){
        def errors=null;
        def restaurantInfoInstance=null;
        flash.message="";
        if(request.method=="POST"){//提交注册信息
            //根据菜系ID查询菜系名
            def reInfo=cuisineParamService.getCuisineById(lj.Number.toLong(params.cuisineId))
            if(reInfo.recode==ReCode.OK){
                params.cuisineName=reInfo.cuisineInfo?.name;
            }
            //根据包装方式id查询包装方式名
            reInfo=packParamService.getPackById(lj.Number.toLong(params.packId))
            if(reInfo.recode==ReCode.OK){
                params.packName=reInfo.packInfo?.name;
            }
            def recode=shopService.createShop(params);//创建店铺
            println(recode);
            if(recode.recode==ReCode.OK){//创建店铺成功,跳转到店铺信息显示页面
                flash.message="注册店铺成功";
                redirect(action:"shopInfo")
                return
            }
            else if(recode.recode==ReCode.CANNOT_REPEAT_REGISTER_SHOP){//不能重复注册店铺
                flash.message="您已经注册过店铺了，请不要重复注册店铺";
                redirect(action: "shopInfo")
                return
            }
            else{//创建店铺失败,显示错误信息
                restaurantInfoInstance=recode.restaurantInfo;
                errors=recode.errors;
                println(errors);
            }
        }

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
        render(view: "shopRegister",model: [cuisineList:cuisineList,provinceList:provinceList,restaurantInfoInstance:restaurantInfoInstance,errors:errors]);
    }


    //店铺基本信息设置
    def editShopInfo(){
        def errors=null;
        def restaurantInfoInstance=null;
        if(request.method=="GET"){
            def reInfo=shopService.getShopInfo(params);
            if(reInfo.recode==ReCode.OK){
                restaurantInfoInstance=reInfo.restaurantInfo;
            }
        }
        else if(request.method=="POST"){
            //根据菜系ID查询菜系名
            if(params.cuisineId){
                def reInfo=cuisineParamService.getCuisineById(lj.Number.toLong(params.cuisineId))
                if(reInfo.recode==ReCode.OK){
                    params.cuisineName=reInfo.cuisineInfo?.name;
                }
            }
            //根据包装方式id查询包装方式名
            if(params.packId){
                def reInfo=packParamService.getPackById(lj.Number.toLong(params.packId))
                if(reInfo.recode==ReCode.OK){
                    params.packName=reInfo.packInfo?.name;
                }
            }
            def reInfo=shopService.setShopInfo(params,request);
            if(reInfo.recode==ReCode.OK){
                flash.message="保存成功";
            }
            else {
                errors=reInfo.errors;
            }
            restaurantInfoInstance=reInfo.restaurantInfo;
        }

        //省份
        def provinceList=[];
        provinceList.add([id:0,province:"请选择"]);
        def provinceListTemp=areaParamService.getProvinceList().provinceList;
        if(provinceListTemp){
            provinceListTemp.each {
                provinceList.add([id:it.id,province:it.province]);
            }
        }
        //菜系
        def cuisineList=[];
        cuisineList.add([id:0,name:"请选择"]);
        def cuisineListTemp=cuisineParamService.getCuisineList().cuisineList;
        if(cuisineListTemp){
            cuisineListTemp.each {
                cuisineList.add([id:it.id,name:it.name]);
            }
        }
        //包装方式
        def packList=[];
        packList.add([id:0,name:"请选择"]);
        def packListTemp=packParamService.getPackList().packList;
        if(packListTemp){
            packListTemp.each {
                packList.add([id:it.id,name:it.name]);
            }
        }
        render(view: "editShopInfo",model: [packList:packList,cuisineList:cuisineList,provinceList:provinceList,restaurantInfoInstance:restaurantInfoInstance,errors:errors]);
    }

    //店铺预定早中晚时间设置
    def editReserveType(){
        def err=null;
        def reserveTypes=null;
        def msg=null;

        def reInfo=shopService.getReserveTypes(null);
        if(reInfo.recode==ReCode.OK){
            reserveTypes=reInfo.reserveTypes;
        }

        if(request.method=="POST"){//提交数据
            reInfo=shopService.saveReserveTypes(params);
            if(reInfo.recode==ReCode.OK){
                println("chenggong1")
                reInfo=shopService.getReserveTypes(null);
                if(reInfo.recode==ReCode.OK){
                    reserveTypes=reInfo.reserveTypes;
                }
                println("chenggong2")
                msg="保存成功";
            }
            else if(reInfo.recode==ReCode.SAVE_FAILED){
                err=lj.I118Error.getMessage(g,reInfo.errors,0);
            }
            else {
                err=lj.I118Error.getMessage(g,reInfo.recode.label,0);
            }
        }
        println("reserveTypes-->"+reserveTypes);
        render(view: "editReserveType",model: [reserveTypes:reserveTypes,err:err,msg:msg]);

    }
}
