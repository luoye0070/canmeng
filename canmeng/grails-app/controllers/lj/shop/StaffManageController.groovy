package lj.shop

import lj.enumCustom.PositionType
import lj.enumCustom.ReCode
import lj.util.WebUtilService

class StaffManageController {
    StaffManageService staffManageService;
    WebUtilService webUtilService;
    def index() {}
    //编辑工作人员
    def editStaffInfo(){
        def err=null;
        def msg=null;
        def staffInfoInstance=null;
        if(request.method=="GET"){
            if(!params.id){
                params.id=-1;
            }
            def reInfo=staffManageService.getStaffInfo(params); //根据ID查询到一个工作人员信息
            if(reInfo.recode==ReCode.OK){
                staffInfoInstance=reInfo.staffInfo;
            }
        }
        else if(request.method=="POST"){
            params.id=params.staffId;
            def reInfo=staffManageService.save(params);
            if(reInfo.recode==ReCode.OK){
                msg="保存成功";
            }
            else if(reInfo.recode==ReCode.SAVE_FAILED){
                err=lj.I118Error.getMessage(g,reInfo.errors,0);
            }
            else {
                err=lj.I118Error.getMessage(g,reInfo.recode.label,0);
            }
            staffInfoInstance=reInfo.staffInfo;
        }
        def positionTypes=PositionType.positionTypes;
        //println("positionTypes-->"+positionTypes[0].code);
        render(view: "editStaffInfo",model: [staffInfoInstance:staffInfoInstance,positionTypes:positionTypes,err:err,msg:msg]);
    }
    //工作人员列表
    def staffList(){
        def err=null;
        def msg=null;

        def reInfo=staffManageService.search(params);

        render(view: "staffList",model: reInfo);
    }
    //删除工作人员
    def delStaff(){
        def reInfo=staffManageService.delete(params);
        if(reInfo.recode==ReCode.OK){//删除成功
            flash.message=reInfo.recode.label;
        }
        else{
            flash.message=reInfo.recode.label;
        }
        redirect(controller: "staffManage",action: "staffList");
    }

    //工作人员登录
    def staffLogin(){
        def err=null;
        def msg=null;
        jump();
        if(request.method=="POST"){//提交登录信息
            println("llllll");
            def reInfo=staffManageService.staffLogin(params);
            if(reInfo.recode==ReCode.OK){//登录成功
                jump();
            }
            else{
                err=reInfo.recode.label;
            }
        }
        render(view: "staffLogin",model: [err:err,msg:msg,params:params]);
    }
    def jump(){
        def defaultUrl="/";
        if (webUtilService.isStaffLoggedIn()) {
            Map urlBackParams = webUtilService.session['urlBackParams']
            if (urlBackParams) {
                String ctr = urlBackParams['controller']
                String act = urlBackParams['action']
                urlBackParams.remove('controller')
                urlBackParams.remove('action')
                def res = [controller: ctr, action: act, staff: webUtilService.staff]
                res << [params:  urlBackParams]
                redirect res
                webUtilService.session['urlBackParams'] = null
                return
            } else {
                redirect(uri: defaultUrl)
                return
            }
        }
    }
    //工作人员推出
    def staffLogout(){
        def defaultUrl=[controller: "staffManage",action: "staffLogin"];
        webUtilService.clearSession();
        redirect(defaultUrl);
        return ;
    }

}
