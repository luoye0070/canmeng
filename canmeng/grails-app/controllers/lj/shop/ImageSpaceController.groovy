package lj.shop

import lj.enumCustom.ReCode

class ImageSpaceController {
    ImageSpaceService imageSpaceService;
    ShopService shopService;
    def index() { }

    //上传图片
    def upload(){
        def err=null;
        def msg=null;
        def reInfo=null;

        if(request.method=="POST"){
            reInfo=imageSpaceService.upload(params,request);
            if(reInfo.recode==ReCode.OK){
                msg="图片上传成功";
                redirect(action:"imageList")
                return
            }
            else if(reInfo.recode==ReCode.SAVE_FAILED){
                err=lj.I118Error.getMessage(g,reInfo.errors,0);
            }
            else {
                err=reInfo.recode.label;
            }
        }

        //获取类别列表
        def imgClassList=[];
        imgClassList.add([id:0,name:"请选择"]);
        def imgClassListTemp=imageSpaceService.searchClass().classList;
        if(imgClassListTemp){
            imgClassListTemp.each {
                imgClassList.add([id:it.id,name:it.name]);
            }
        }
        render(view: "upload",model: [imgClassList:imgClassList,msg:msg,err:err]);

    }

    //图片放入回收站
    def recycleToRecycled(){
        params.isDel="true";
        def reInfo=imageSpaceService.recycleOrRecover(params);
        if(reInfo.recode==ReCode.OK){
            flash.message="移入回收站成功";
        }
        else{
            flash.message=reInfo.recode.label;
        }
        redirect(controller: "imageSpace",action: "imageList");
    }
    //图片从回收站中恢复
    def recoverFromRecycled(){
        params.isDel="false";
        def reInfo=imageSpaceService.recycleOrRecover(params);
        if(reInfo.recode==ReCode.OK){
            flash.message="恢复成功";
        }
        else{
            flash.message=reInfo.recode.label;
        }
        redirect(controller: "imageSpace",action: "imageList");
    }
    //删除图片
    def delImage(){
        def reInfo=imageSpaceService.deleteImage(params);
        if(reInfo.recode==ReCode.OK){
            flash.message="删除成功";
        }
        else{
            flash.message=reInfo.recode.label;
        }
        redirect(controller: "imageSpace",action: "imageList");
    }

    //图片列表
    def imageList(){
        def err=null;
        def msg=null;
        def reInfo=null;
        def imageList=null;
        def totalCount=0;
        def restaurantInfo=null;

        //获取图片列表
        reInfo=imageSpaceService.search(params);
        if(reInfo.recode==ReCode.OK){//成功
            imageList=reInfo.imageList;
            totalCount=reInfo.totalCount;
        }else {
            err=reInfo.recode.label;
        }

        //获取店铺信息
        reInfo=shopService.getShopInfo(null);
        if(reInfo.recode==ReCode.OK){
            restaurantInfo=reInfo.restaurantInfo;
        }
        else{
            err=reInfo.recode.label;
        }

        //获取类别列表
        def imgClassList=[];
        imgClassList.add([id:0,name:"请选择"]);
        def imgClassListTemp=imageSpaceService.searchClass().classList;
        if(imgClassListTemp){
            imgClassListTemp.each {
                imgClassList.add([id:it.id,name:it.name]);
            }
        }

        render(view: "imageList",model: [err:err,msg: msg,imageList:imageList,totalCount:totalCount,restaurantInfo:restaurantInfo,imgClassList:imgClassList,params:params])

    }

    //图片分类列表
    def imageClassList(){
        def err=null;
        def msg=null;

        def reInfo=imageSpaceService.searchClass(params);

        render(view: "imageClassList",model: reInfo);
    }
    //图片分类保存
    def editImageClass(){
        def err=null;
        def msg=null;
        def reInfo=null;
        def imageClassInfo=null;

        if(request.method=="GET"){
            reInfo=imageSpaceService.getClass(params); //根据ID查询到一个图片类别
            if(reInfo.recode==ReCode.OK){
                imageClassInfo=reInfo.imageClassInfo;
            }
        }
        else if(request.method=="POST"){
            params.id=params.classId;
            reInfo=imageSpaceService.saveClass(params);
            if(reInfo.recode==ReCode.OK){
                msg="图片分类保存成功";
            }
            else if(reInfo.recode==ReCode.SAVE_FAILED){
                err=lj.I118Error.getMessage(g,reInfo.errors,0);
            }
            else {
                err=reInfo.recode.label;
            }
            imageClassInfo=reInfo.imageClassInfo;
        }

        render(view: "editImageClass",model: [imageClassInfo:imageClassInfo,msg:msg,err:err]);
    }
    //图片分类删除
    def delImageClass(){
        def reInfo=imageSpaceService.deleteClass(params);
        if(reInfo.recode==ReCode.OK){//删除成功
            flash.message=reInfo.recode.label;
        }
        else{
            flash.message=reInfo.recode.label;
        }
        redirect(controller: "imageSpace",action: "imageClassList");
    }

    //选择图片
    def selectImage(){
        def err=null;
        def msg=null;
        def reInfo=null;
        def imageList=null;
        def totalCount=0;

        //获取图片列表
        reInfo=imageSpaceService.search(params);
        if(reInfo.recode==ReCode.OK){//成功
            imageList=reInfo.imageList;
            totalCount=reInfo.totalCount;
        }else {
            err=reInfo.recode.label;
        }

        //获取类别列表
        def imgClassList=[];
        imgClassList.add([id:0,name:"请选择"]);
        def imgClassListTemp=imageSpaceService.searchClass().classList;
        if(imgClassListTemp){
            imgClassListTemp.each {
                imgClassList.add([id:it.id,name:it.name]);
            }
        }

        render(view: "selectImage",model: [err:err,msg: msg,imageList:imageList,totalCount:totalCount,imgClassList:imgClassList,params:params])

    }
}
