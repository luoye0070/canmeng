package lj.shop

import lj.enumCustom.ReCode

//菜单管理
class FoodManageController {
    FoodManageService foodManageService;
    def index() {}

    //菜单列表
    def foodList(){
        def err=null;
        def msg=null;

        def reInfo=foodManageService.foodList(params);

        render(view: "foodList",model: reInfo);
    }
    //菜单编辑
    def editFoodInfo(){
        def err=null;
        def msg=null;
        def foodInfoInstance=null;
        if(request.method=="GET"){
            if(!params.id){
                params.id=-1;
            }
            def reInfo=foodManageService.getFoodInfo(params); //根据ID查询到一个菜单
            if(reInfo.recode==ReCode.OK){
                foodInfoInstance=reInfo.foodInfo;
            }
        }
        else if(request.method=="POST"){
            params.id=params.foodId;
            def reInfo=foodManageService.save(params);
            if(reInfo.recode==ReCode.OK){
                msg="保存成功";
            }
            else if(reInfo.recode==ReCode.SAVE_FAILED){
                err=lj.I118Error.getMessage(g,reInfo.errors,0);
            }
            else {
                err=lj.I118Error.getMessage(g,reInfo.recode.label,0);
            }
            foodInfoInstance=reInfo.foodInfo;
        }

        render(view: "editFoodInfo",model: [foodInfoInstance:foodInfoInstance,err:err,msg:msg]);
    }
    //菜单删除
    def delFoodInfo(){
        def reInfo=foodManageService.deleteFoodInfo(params);
        if(reInfo.recode==ReCode.OK){//删除成功
            flash.message=reInfo.recode.label;
        }
        else{
            flash.message=reInfo.recode.label;
        }
        redirect(controller: "foodManage",action: "foodList");
    }
}
