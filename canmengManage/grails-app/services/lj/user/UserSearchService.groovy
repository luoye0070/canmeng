package lj.user

import lj.data.UserInfo
import lj.util.WebUtilService

import javax.servlet.http.HttpSession

//用户相关查询服务
class UserSearchService {

    WebUtilService webUtilService

    static transactional=false
    
    //判定该用户是否存在
    boolean isExists(String username){
        return (UserInfo.findByUserName(username)!=null)
    }

}
