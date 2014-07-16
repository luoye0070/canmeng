package lj.user

import grails.converters.JSON
import lj.common.DESUtil
import lj.data.UserInfo
import lj.enumCustom.ReCode

class UserAjaxController {
    UserService userService;
    //登录
    def loginAjax(){
        String USER_SECRET_KEY="MErhzSsJ";//用户中心需要的密钥
        String originalPassWord=params.passWord?:"";
        String passWord=DESUtil.decryptDES(params.passWord,USER_SECRET_KEY);
        println("passWord-->"+passWord);
        params.passWord=passWord;
        //render(userService.login(params,request)<<[originalPassWord:originalPassWord] as JSON);
        render(userService.loginByUser(params,request)<<[originalPassWord:originalPassWord] as JSON);
    }
    //退出
    def logoutAjax(){
        render(userService.logout() as JSON);
    }

    //我的收藏夹
    def myFavorites(){
        if(!params.type)
            params.type="restaurant"
        def reInfo= userService.getMyFavorites(params)
        println(reInfo as JSON);
        render(reInfo as JSON);
    }
    //删除
    def delFavorite()
    {
        def reInfo=userService.delFavorite(params)
        println(reInfo as JSON);
        render(reInfo as JSON);
    }
}
