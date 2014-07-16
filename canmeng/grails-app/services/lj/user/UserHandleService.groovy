package lj.user

import lj.common.DesUtilGy
import lj.data.ClientInfo
import lj.data.UserInfo
import lj.enumCustom.ClientType
import lj.enumCustom.ReCode
import lj.util.WebUtilService
import lj.common.Result
import lj.util.ValidationService
import lj.enumCustom.AuthCodeType
import lj.data.AddressInfo
import lj.data.FoodCollectInfo
import lj.data.RestaurantCollectInfo

//用户相关操作服务
class UserHandleService {

    WebUtilService webUtilService
    ValidationService validationService
    def messageSource
    def mailService
    //用户注册
    def register(def params) {
        UserInfo userInfo = new UserInfo(params)
        //验证
        if (userInfo.validate()) {
            userInfo.save(true)
            webUtilService.user = userInfo;

            //客户端登录
            ClientInfo clientInfo=ClientInfo.findByUserNameAndClientType(userInfo.userName,ClientType.WEB_SITE.code);
            if(!clientInfo){
                clientInfo = new ClientInfo();
                clientInfo.userName = userInfo.userName;
                //clientInfo.passWord=userInfo.passWord;
                clientInfo.userId = userInfo.id;
                clientInfo.clientType=ClientType.WEB_SITE.code;
                clientInfo.save(true);
            }
            webUtilService.setClient(clientInfo.id);

            return [success: true, message: '注册成功']
        } else {
            return [success: false, message: "注册失败", errors: userInfo.errors.allErrors, user: userInfo]
        }
    }
    //判定用户是否登录成功
    boolean login(String userName, String passWord) {
        def userInfo = UserInfo.findWhere(userName: userName, passWord: passWord)
        if (userInfo) {
            //session保存
            webUtilService.user = userInfo

            return [success: true, message: '登录成功']
        }
        return [success: false, message: '登录失败']
    }
    //更新用户信息
    Result updateInfo(String key, def params) {
        Result res = new Result()
        UserInfo userInfo = UserInfo.get(webUtilService.user.id)
        if (!userInfo) {
            res.msg = '该用户不存在!'
            res.success = false
            return res
        }

        if (key == "all" || key.equals(""))
            userInfo.properties = params
        else if (key == "email") {
            userInfo.email = params.email
            userInfo.emailEnabled = false
        } else if (key == "emailEnabled") {
            String authCode = webUtilService.session.authCode
            long authTime = webUtilService.session.authTime
            if ((new Date().getTime() - authTime) / (1000 * 60) <= 5) {
                if (authCode == params.authCode) {
                    userInfo.emailEnabled = true
                    res.setMsg("邮箱验证成功!")
                } else {
                    res.success = false
                    res.setMsg("验证码错误!")
                    return res
                }
            } else {
                res.success = false
                res.setMsg("验证码已经过时，请重新发送获取验证码!")
                return res
            }
        } else if (key == "phone") {
            userInfo.phone = params.phone
            userInfo.phoneEnabled = false
        } else if (key == "phoneEnabled") {
            String authCode = webUtilService.session.authCode
            long authTime = webUtilService.session.authTime
            if ((new Date().getTime() - authTime) / (1000 * 60) <= 5) {
                if (authCode == params.authCode) {
                    userInfo.phoneEnabled = true
                    res.setMsg("手机验证成功!")
                } else {
                    res.success = false
                    res.setMsg("验证码错误!")
                    return res
                }
            } else {
                res.success = false
                res.setMsg("验证码已经过时，请重新发送获取验证码!")
                return res
            }
        } else if (key == "passWord") {
            def passWord = DesUtilGy.encryptDES(params.oldPsd, userInfo.userName)
            if (passWord != userInfo.passWord) {
                res.success = false
                res.msg = '账号或密码不正确!'
                return res
            }

            userInfo.passWord = params.newPsd
        } else if (key == "question") {
            if (params.type == 0) {//新设置密保
                userInfo.question = params.question
                userInfo.answer = params.answer
            } else {//更改密保设置
                if (params.oldAnswer.equals(userInfo.answer)) {
                    userInfo.question = params.question
                    userInfo.answer = params.answer
                    res.msg = "密保设置成功!"
                } else {
                    res.msg = "旧密保答案不正确!"
                    res.success = false
                    return res
                }
            }
        }

        if (!userInfo.save(flush: true)) {
            res.msg = userInfo.errors.allErrors
            res.success = false
            return res
        }

        webUtilService.user = userInfo

        //如果操作成功，进行其他操作
        switch (key) {
            case "email":
                res.msg = "邮件发送成功，请进入邮箱获取验证码并进行提交操作!<br/>如果没有获取到邮件,请一分钟之后重新获取!"
                emailAuthSendMail();
                break

        }

        return res

    }

    //用户退出
    void logout() {
        //清除session
        webUtilService.clearSession()
    }

    void emailAuthSendMail() {
        String authCode = validationService.getAuthCodeStr(digit: 6, codeType: AuthCodeType.NUMBER).authCode
        webUtilService.session.authCode = authCode
        webUtilService.session.authTime = new Date().getTime()

        def user = webUtilService.user
        String title = "餐萌邮箱验证(系统邮件，请勿回复）"

        StringBuffer content = new StringBuffer('<table class="main" border="0" cellspacing="0" cellpadding="10" width="570">')
        content.append('<td bgcolor="#ffffcc">亲爱的' + user.userName + ':<br  /><br  />您好！感谢您使用餐萌网。<br/><br/>您正在进行邮箱验证，请在校验码输入框中输入：')
        content.append(authCode)
        content.append('，以完成操作。<br/><br/><font style="COLOR: red; FONT-WEIGHT: bold">注意：</font>此操作用于进行密码查找操作。（工作人员不会向您索取此校验码，请勿泄漏！）</td></tr></tbody></table>')

        sendEmail(user.email, title, content.toString())
    }

    boolean findPsdSendMail(String email) {
        String authCode = validationService.getAuthCodeStr(digit: 6, codeType: AuthCodeType.NUMBER).authCode
        webUtilService.session.findPsdAuthCode = authCode
        webUtilService.session.findPsdAuthTime = new Date().getTime()

        String title = "餐萌通过邮箱找回密码(系统邮件，请勿回复）"

        StringBuffer content = new StringBuffer('<table class="main" border="0" cellspacing="0" cellpadding="10" width="570">')
        content.append('<td bgcolor="#ffffcc">亲爱的' + webUtilService.session.findPsdUserName + ':<br  /><br  />您好！感谢您使用餐萌网。<br/><br/>您正在通过邮箱找回密码，请在校验码输入框中输入：')
        content.append(authCode)
        content.append('，以完成操作。<br/><br/><font style="COLOR: red; FONT-WEIGHT: bold">注意：</font>此操作用于进行密码查找操作。（工作人员不会向您索取此校验码，请勿泄漏！）</td></tr></tbody></table>')

        sendEmail(email, title, content.toString())

        return true
    }

    //发送邮件
    void sendEmail(String toEmail, String title, String content) {
        mailService.sendMail {
            to toEmail
            from "ljsj_2009@sina.com"
            subject title
            html content
        }
    }

    //发送短信
    void sendMessage(String phone) {

    }
    //查看手机是否已经注册
    boolean phoneValidate(String userName, String phone) {

    }

    Result findPsdInfo(def params) {
        Result res = new Result()

        if (!webUtilService.session.findPsdUserName) {
            res.success = false
            res.msg = "找回密码失败!"
            return res
        }
        UserInfo user = UserInfo.findByUserName(webUtilService.session.findPsdUserName)
        if (!user) {
            res.success = false
            res.msg = "找回密码失败!"
            return res
        }
        switch (params.findType) {
            case "email":
                String str = "已经向您的邮箱${user.email[0..2]}...${user.email.substring(user.email.indexOf("@"))}发送了验证码,请获取验证码并进行提交操作!<br/>如果没有收到邮件，请一分钟之后再次获取!"
                res.msg = str
                findPsdSendMail(user.email)
                break;
            case "phone":
                res.msg = "已经向您的手机发送了验证码"
                break;
            case "question":
                String str = "您的密保设置问题是：${user.question},请输入密码问题答案并进行提交操作!"
                res.msg = str
                break;
        }

        return res
    }


    Result findPsdAuth(def params) {
        Result res = new Result()

        if (!webUtilService.session.findPsdUserName) {
            res.success = false
            res.msg = "找回密码失败!"
            return res
        }
        UserInfo user = UserInfo.findByUserName(webUtilService.session.findPsdUserName)
        if (!user) {
            res.success = false
            res.msg = "找回密码失败!"
            return res
        }
        switch (params.findType) {
            case "email":
            case "phone":
                if (!webUtilService.session.findPsdAuthCode) {
                    res.success = false
                    res.msg = "找回密码失败!"
                    return res
                }
                if (!webUtilService.session.findPsdAuthCode.equals(params.authCode)) {
                    res.success = false
                    res.msg = "验证码错误!"
                    return res
                }
                break;
            case "question":
                if (!params.answer.equals(user.answer)) {
                    res.success = false
                    res.msg = "密保答案错误!"
                    return res
                }
                break;
        }


        webUtilService.session.findPsd = true

        return res


    }

    Result newPsdSave(def params) {
        Result res = new Result()

        if (!webUtilService.session.findPsd || !webUtilService.session.findPsdUserName) {
            res.msg = "找回密码失败!"
            res.success = false
            return res
        }

        UserInfo user = UserInfo.findByUserName(webUtilService.session.findPsdUserName)
        if (!user) {
            res.msg = "找回密码失败!"
            res.success = false
            return res
        }

        user.passWord = params.passWord

        if (user.save(flush: true)) {
            res.msg = "密码设置成功!"
            webUtilService.session.findPsdUserName = null
            webUtilService.session.findPsd = null
            webUtilService.session.findPsdAuthCode = null
            webUtilService.session.findPsdAuthTime = null
        } else {
            res.msg = user.errors.allErrors
            res.success = false
        }
        return res

    }


    Result addAddress(def params) {
        Result res = new Result()

        AddressInfo address = new AddressInfo(params)
        //println(webUtilService.user.id)
        address.clientId = webUtilService.getClientId();
        if (!address.save(flush: true)) {
            res.success = false
            res.msg = address.errors.allErrors
            return res
        }
        if (params.defaultAddrId == '1') {
//            UserInfo user = UserInfo.get(webUtilService.user.id)
//            if (user) {
//                user.defaultAddrId = address.id
//                user.save(flush: true)
//                webUtilService.user = user
//            }
            ClientInfo clientInfo=webUtilService.getClient();
            if(clientInfo){
                clientInfo.defaultAddrId=address.id;
                clientInfo.save(flush: true);
            }
        }
        res.obj = address.id
        res.msg = "添加地址成功!"

        return res
    }

    Result updAddress(def params) {
        Result res = new Result()
        AddressInfo address = AddressInfo.get(params.id)
        if (!address) {
            res.success = false
            res.msg = "该地址不存在!"
            return res
        }

        address.properties = params
        if (!address.save(flush: true)) {
            res.success = false
            res.msg = address.errors.allErrors
            return res
        }
        if (params.defaultAddrId == '1') {
//            UserInfo user = UserInfo.get(webUtilService.user.id)
//            if (user) {
//                user.defaultAddrId = address.id
//                user.save(flush: true)
//                webUtilService.user = user
//            }
            ClientInfo clientInfo=webUtilService.getClient();
            if(clientInfo){
                clientInfo.defaultAddrId=address.id;
                clientInfo.save(flush: true);
            }
        }
        res.msg = "更新地址成功!"
        return res
    }

    Result delAddress(def params) {
        Result res = new Result()
        AddressInfo address = AddressInfo.get(params.id)
        if (!address) {
            res.success = false
            res.msg = "该地址不存在!"
            return res
        }
        try {
            address.delete()
            res.msg = "删除地址成功!"
        } catch (Exception ex) {
            res.success = false
            res.msg = "删除地址失败!"
        }

        return res
    }

    Result addFavorite(def params) {
        Result res = new Result()
        def session=webUtilService.getSession();
        long clientId = webUtilService.getClientId();//客户ID
        println("clientId--->"+clientId);
        if (clientId) {
            params.clientId = clientId;
            if ("food".equals(params.type)) {
                long foodId=lj.Number.toLong(params.foodId);
                def foodC =FoodCollectInfo.findByFoodIdAndClientId(foodId,clientId);
                if(foodC){
                    res.msg = "您已经收藏过该菜品了哦"
                    res.success = false
                    return res
                }
                else{
                    foodC =new FoodCollectInfo(params);
                }
                if (!foodC.save(flush: true)) {
                    res.msg = "收藏失败"
                    res.success = false
                    return res
                }
            } else {
                long restaurantId=lj.Number.toLong(params.restaurantId) ;
                def restaurantC =RestaurantCollectInfo.findByRestaurantIdAndClientId(restaurantId,clientId);
                if(restaurantC){
                    res.msg = "您已经收藏过该饭店了哦"
                    res.success = false
                    return res
                }
                else{
                    restaurantC =new RestaurantCollectInfo(params);
                }
                if (!restaurantC.save(flush: true)) {
                    res.msg = "收藏失败"
                    res.success = false
                    println(restaurantC.errors.allErrors);
                    return res
                }
            }
            res.msg = "收藏成功"
        } else {
            res.msg = ReCode.NOT_LOGIN.label;
            res.success = false
        }

        return res
    }

    Result delFavorite(def params) {
        Result res = new Result()

        println params.checkes
        def ids = params.checkes
        if (ids instanceof String[]) {
            ids = params.checkes.join(",")
        }
        if ("food".equals(params.type)) {
            FoodCollectInfo.executeUpdate("delete from FoodCollectInfo where id in (${ids})")
        } else {
            RestaurantCollectInfo.executeUpdate("delete from RestaurantCollectInfo where id in (${ids})")
        }

        return res
    }
}
