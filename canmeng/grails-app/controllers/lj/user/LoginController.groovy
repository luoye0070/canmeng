package lj.user

import lj.common.DesUtilGy
import lj.data.UserInfo

class LoginController {

    def webUtilService

    //登录界面
    def index() {
        def userInfo=new UserInfo()
        userInfo.properties=params
        return [userInfo:userInfo]
    }



    //退出登录
    def logout(){

    }



}
