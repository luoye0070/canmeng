package lj.user

import grails.converters.JSON
import lj.IPUtil
import lj.common.DesUtilGy
import lj.data.ClientInfo
import lj.data.RestaurantInfo
import lj.data.UserInfo
import lj.enumCustom.ClientType
import lj.shop.StaffManageService
import lj.util.ValidationService

import javax.imageio.ImageIO
import lj.I118Error
import javax.naming.spi.DirStateFactory
import lj.common.Result

//用户相关控制器
class UserController {
    //用户相关操作服务
    def userSearchService
    def userHandleService
    //地址相关服务
    def areaParamService
    //web session相关操作服务
    def webUtilService
    ValidationService validationService;
    StaffManageService staffManageService;

    //注册前端
    def register() {
        if(webUtilService.isLoggedIn()){
            webUtilService.clearSession()
        }
        def userInfo = new UserInfo()
        [user: userInfo]
    }

    def handleRegister() {
        String resAuthCode=params.captchaResponse;
        String sessAuthCode=webUtilService.session.getAttribute("registerAuthCode");
        if (resAuthCode.toLowerCase()==sessAuthCode.toLowerCase()){
            def res = userHandleService.register(params)
            if (res.success) {
                render(view:'viewUserInfo',model:[user:res.user])
            } else {
                render(view: 'register', model: [user: res.user, errors:I118Error.getMessage(g,res.errors)])
            }

        } else {
            render(view: "register", model: [user: new UserInfo(params), errors: "验证码错误"])
        }
    }
    //获取注册验证码显示到页面
    def getValidateImage(){
        params.width=90;
        params.height=40;

        def reInfo=validationService.getauthCode(params);
        //将验证码写入session
        webUtilService.session.setAttribute("registerAuthCode",reInfo.authCode);
        response.contentType = "image/jpeg";
        def os = response.outputStream;
        //将验证码图片写入到输出流
        ByteArrayInputStream bais=new ByteArrayInputStream(reInfo.acImgObj);
        ImageIO.write(ImageIO.read(bais),"PNG",os);
        os.flush();
        os.close();
    }

    //验证验证码是否正确
    def validateResponse() {
        String resAuthCode=params.captchaResponse;
        String sessAuthCode=webUtilService.session.getAttribute("registerAuthCode");
        render (resAuthCode.toLowerCase()==sessAuthCode.toLowerCase());
    }

    //验证用户名是否已经存在，异步请求
    def validateUser() {
        def res = UserInfo.findWhere(userName: params.userName)
        if (res) {
            render false
            return
        }

        render true
    }

    //登录操作
    def login() {
        def defaultUrl="/";
        if (webUtilService.isLoggedIn()) {
            Map urlBackParams = webUtilService.session['urlBackParams']
            if (urlBackParams) {
                String ctr = urlBackParams['controller']
                String act = urlBackParams['action']
                urlBackParams.remove('controller')
                urlBackParams.remove('action')
                def res = [controller: ctr, action: act, user: webUtilService.user]
                res << [params:urlBackParams];
                redirect res
                webUtilService.session['urlBackParams'] = null
                return
            } else {
                redirect(uri: defaultUrl)
                return
            }
        }

    }

    //退出登录操作
    def logout() {
        webUtilService.clearSession()
        redirect(url:"/")
    }

    //查看用户信息前端
    def viewUserInfo() {
        def userInfo=webUtilService.user
        [user: userInfo]
    }

    //提交登录验证处理
    def auth() {

        def defaultUrl = "/"  //默认跳转url
        //判定是否登录
        if (webUtilService.isLoggedIn()) {
            //返回跳转过来的页面
            render 'ok'
        } else {
            def userInfo = new UserInfo(params)
            if (!userInfo.hasErrors()) {
                userInfo.passWord = DesUtilGy.encryptDES(params.passWord, params.userName)
                def resultUser = UserInfo.findByUserNameAndPassWord(userInfo.userName, userInfo.passWord)
                if (resultUser) {
                    //记录登录信息
                    resultUser.loginTime = new Date()
                    resultUser.loginIp = IPUtil.getClientIp(request)
                    resultUser.save(true)
                    //检查是否有店铺，如果有店铺则做工作人员登录
                    RestaurantInfo restaurantInfo=RestaurantInfo.findByUserId(resultUser.id);
                    if(restaurantInfo){
                        def paramsT=[
                                restaurantId:restaurantInfo.id,//饭店ＩＤ
                                loginName:resultUser.userName,//登录名
                                passWord:DesUtilGy.decryptDES(resultUser.passWord,resultUser.userName)//密码
                        ];
                        staffManageService.staffLogin(paramsT);
                    }

                    //客户端登录
                    ClientInfo clientInfo=ClientInfo.findByUserNameAndClientType(resultUser.userName,ClientType.WEB_SITE.code);
                    if(!clientInfo){
                        clientInfo = new ClientInfo();
                        clientInfo.userName = resultUser.userName;
                        //clientInfo.passWord=userInfo.passWord;
                        clientInfo.userId = resultUser.id;
                        clientInfo.clientType=ClientType.WEB_SITE.code;
                        clientInfo.save(true);
                    }
                    webUtilService.setClient(clientInfo.id);

                    //设定session
                    webUtilService.user = resultUser
                    //跳转回登录之前的界面
                    Map urlBackParams = webUtilService.session['urlBackParams']
                    if (urlBackParams) {
                        String ctr = urlBackParams['controller']
                        String act = urlBackParams['action']
                        urlBackParams.remove('controller')
                        urlBackParams.remove('action')
                        def res = [controller: ctr, action: act, user: userInfo]
                        res << [params:  urlBackParams]
                        redirect res
                        webUtilService.session['urlBackParams'] = null
                        return
                    } else {
                        redirect(uri: defaultUrl)
                        return
                    }

                } else {
                    render(view: 'login', model: [user: userInfo, errors: "用户名或密码错误"])
                }
            } else {
                render(view:'login')
            }
        }
    }

    //更改密码前端
    def changePsd(){

    }

    //后端更改密码
    def updatePsd(){
         Result res = userHandleService.updateInfo("passWord",params)
        if(res.success){
            render(view:"/viewUserInfo",model:[msg:"密码更改成功!"])
            return
        }else{
           render(view:'changePsd',model:[errors: res.getMsg(g)])
        }
    }

    //邮箱验证前端
    def emailAuth(){
        [emailEnabled:webUtilService.user.emailEnabled,email:webUtilService.user.email]
    }

    //验证邮箱是否已经存在
    def ajaxValidateEmail(){
        render userSearchService.emailValidate(webUtilService.user.userName,params.email)
    }

    //处理前端返回的邮箱发送邮件
    def ajaxEmailAuth(){
        Result res = userHandleService.updateInfo("email",params)
        render res.getJOSN(g)
    }

    //邮箱激活处理
    def ajaxEmailActive(){
        Result res=userHandleService.updateInfo("emailEnabled",params)
        render res.getJOSN(g)
    }


    //手机验证前端
    def phoneAuth(){
        [phoneEnabled:webUtilService.user.phoneEnabled,phone:webUtilService.user.phone]
    }

    //验证手机是否已经存在
    def ajaxValidatePhone(){
        render userSearchService.phoneValidate(webUtilService.user.userName,params.phone)
    }

    //处理前端返回的手机号码发送邮件
    def ajaxPhoneAuth(){
        Result res = userHandleService.updateInfo("phone",params)
        render res.getJOSN(g)
    }

    //手机激活处理
    def ajaxPhoneActive(){
        Result res=userHandleService.updateInfo("phoneEnabled",params)
        render res.getJOSN(g)
    }

    //密保问题设置
    def securityQuestion(){
        [question:webUtilService.user.question]
    }

    def ajaxSecurityQuestion(){
        Result res=userHandleService.updateInfo("question",params)
        render res.getJOSN(g)
    }

    //找回密码
    def findPsd(){
        webUtilService.session.findPsdUserName=null
        []
    }

    //找回密码第一步
    def findPsdFirst(){
        String resAuthCode=params.captchaResponse;
        String sessAuthCode=webUtilService.session.getAttribute("registerAuthCode");
         if(resAuthCode.toLowerCase().equals(sessAuthCode.toLowerCase())){
             webUtilService.session.findPsdUserName=params.userName
             render(view: 'findPsd',model: [step:2])
         }else{
             render(view: 'findPsd',model: [step:1,error:"验证码错误!",userName: params.userName])
         }
    }

    def findPsdInfo(){
        Result res=userHandleService.findPsdInfo(params)
        render res.getJOSN(g)
    }

    def newPsd(){

    }

    //新密码保存
    def newPsdSave(){
        Result res=userHandleService.newPsdSave(params)
        if (res.success){
            render(view:"login",model:[msg:"新密码设置成功，请进行登录!"])
        }  else{
            render(view:"login",model:[ errors: "用户名或密码错误"])
        }
    }
    //密码找回第二步验证
    def ajaxFindPsdSecond(){
        Result res=userHandleService.findPsdAuth(params)
        render res.getJOSN(g)
    }

    //判定用户是否存在
    def isUser(){
        if(UserInfo.findByUserName(params.userName))   {
            render true
            return
        }

        render false
    }

    //地址管理
    def userAddresses(){
        def res=areaParamService.getProvinceList()
        List addresses=
        println res as JSON
        if(params.showInDialog){
            render(view: "userAddresses_dialog",model: [provinces:res.provinceList]);
            return ;
        }
        render(view: "userAddresses",model: [provinces:res.provinceList]);
    }

    def ajaxGetAddresses(){
        def res=[addresses:userSearchService.getAddresses(),defaultAddrId:webUtilService.getClient().defaultAddrId]
        render res as JSON
    }

    //添加地址
    def addAddress(){
        Result res=userHandleService.addAddress(params)
        def map=[success:res.success,msg:res.getMsg(g)]
        if(res.obj)
            map['id']=res.obj as Long
        render map as JSON
    }

    //删除地址
    def delAddress(){
        render userHandleService.delAddress(params).getJOSN(g)
    }

    //更新地址
    def updAddress(){
        render userHandleService.updAddress(params).getJOSN(g)
    }
    //我的收藏夹
    def myFavorites(){
        if(!params.type)
            params.type="restaurant";
        if(!params.max)
            params.max=12;
        def res= userSearchService.getMyFavorites(params)
        res<<[type:params.type]
        println res as JSON
        res
    }

    //添加到收藏夹
    def addFavorite(){
        render userHandleService.addFavorite(params).getJOSN(g)
    }

    //删除
    def delFavorite()
    {
        userHandleService.delFavorite(params).getJOSN(g)
        redirect(action:"myFavorites",params: [type:params.type])
    }
}
