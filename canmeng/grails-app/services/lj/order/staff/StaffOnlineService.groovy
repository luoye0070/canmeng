package lj.order.staff

import lj.Number
import lj.common.DesUtilGy
import lj.data.StaffInfo
import lj.enumCustom.ReCode
import lj.util.WebUtilService;

class StaffOnlineService {
    WebUtilService webUtilService;

    def serviceMethod() {

    }

    //登录
    def login(def params){
        //获取参数
        Long restaurantId=Number.toLong(params.restaurantId);//饭店ID
        String loginName=params.loginName?:"";//登录用户名
        String passWord=params.passWord?:"";//登录密码
        passWord=DesUtilGy.encryptDES(passWord,loginName);

        //根据用户名和饭店ID查找工作人员
        StaffInfo staffInfo=StaffInfo.findAllByRestaurantIdAndLoginName(restaurantId,loginName);
        if(staffInfo){
            if(staffInfo.passWord.equals(passWord)){//登录成功
                webUtilService.setStaff(staffInfo);
                return [recode: ReCode.OK];
            }
            else{//密码错误
                return [recode:  ReCode.PASSWORD_INCORRECT];
            }
        }
        else{ //用户名错误
            return [recode:ReCode.USERNAME_NOT_EXIST];
        }

    }
    //登出
    def logout(def params){
        webUtilService.clearSession();
        return [recode: ReCode.OK];
    }

}
