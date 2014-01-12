package lj.shop

import lj.data.AuthenticationInfo
import lj.enumCustom.ReCode
import lj.sysparameter.PapersParamService

//认证相关控制器
class AuthenticationController {
    AuthenticationService authenticationService;
    PapersParamService papersParamService;
    def index() { }

    //认证列表
    def authList(){
        //获取认证列表
        //params.id=null;
        def reInfo=authenticationService.showAuth(params);
        switch (reInfo.recode){
                case ReCode.NOT_REGISTER_RESTAURANT://用户没有注册店铺,这个在过滤器中验证过了，这里不做任何处理
                    break;
                case ReCode.NOT_LOGIN://用户没有登录,这个在过滤器中验证过了，这里不做任何处理
                    break;
                case ReCode.NO_RESULT://认证记录不存在,跳转到认证界面
                    redirect(action: "editAuth");
                    return ;
                    break;
                case ReCode.OK://,authList:authList,totalCount:totalCount://成功
                    break;
        }
        render(view:"authList",model: [reInfo:reInfo] );
    }
    //删除认证
    def delAuth(){
        def reInfo=authenticationService.delAuth(params);
        if(reInfo.recode!=ReCode.OK){
            flash.message=reInfo.recode.label;
        }
        redirect(controller: "authentication",action: "authList");
    }
    //编辑认证
    def editAuth(){
        def errors=null;
        AuthenticationInfo authenticationInfo=null;
        if(params.id){
            def reInfo=authenticationService.showAuth(params);
            if(reInfo.recode==ReCode.OK){
                authenticationInfo=reInfo.authList[0];
            }
        }
        if(request.method=="POST"){//提交数据
            println("params::->>>"+params)
            params.id=params.authId;
            def reInfo=authenticationService.saveAuth(params,request);
            if(reInfo.recode==ReCode.OK){// 成功跳转到列表页面
                flash.message="保存认证信息成功";
                redirect(controller: "authentication",action:  "authList");
                return ;
            }
            else{
                errors=reInfo.errors;
                authenticationInfo=reInfo.authenticationInfo;
            }
        }

        //获取证件类型列表
        def papersList=[];
        papersList.add([id:0,name:"请选择"]);
        def papersListTemp=papersParamService.getPapersList()?.papersList;
        if(papersListTemp){
            papersListTemp.each {
                papersList.add([id:it.id,name:it.name]);
            }
        }

        render(view: "editAuth",model: [errors:errors,authenticationInfo:authenticationInfo,papersList:papersList]);
    }

}
