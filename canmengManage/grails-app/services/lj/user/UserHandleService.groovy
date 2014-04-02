package lj.user

import lj.data.UserInfo
import lj.util.WebUtilService

import javax.servlet.http.HttpSession

//用户相关操作服务
class UserHandleService {

    WebUtilService webUtilService

   //用户注册
    def register(def params){
            UserInfo userInfo=new UserInfo(params)
            //验证
            if(userInfo.validate()){
                userInfo.save(true)
                webUtilService.user=userInfo
                return [success:true,message:'注册成功']
            }else{
                userInfo.errors.allErrors.each {
                    println it
                }
            }
    }
    //判定用户是否登录成功
    boolean login(String userName,String passWord) {
         def userInfo=UserInfo.findWhere(userName:userName,passWord:passWord)
         if(userInfo){
             //session保存
             webUtilService.user=userInfo

             return [success:true,message:'登录成功']
         }
          return [success: false,message: '登录失败']
    }

    //用户退出
    void logout(){
         //清除session
        webUtilService.clearSession()
    }


}
