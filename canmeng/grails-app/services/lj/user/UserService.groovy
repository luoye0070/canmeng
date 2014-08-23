package lj.user

import lj.IPUtil
import lj.common.DesUtilGy
import lj.common.Result
import lj.data.ClientInfo
import lj.data.FoodCollectInfo
import lj.data.FoodInfo
import lj.data.RestaurantCollectInfo
import lj.data.RestaurantInfo
import lj.data.UserInfo
import lj.enumCustom.ClientType
import lj.enumCustom.ReCode
import lj.shop.StaffManageService
import lj.util.WebUtilService
import lj.common.DESUtil;

class UserService {
    StaffManageService staffManageService;
    WebUtilService webUtilService;
    //登录判断
    def login(def params,def request){
        //获取用户名和密码
        if(!params.userName||!params.passWord){
            return [recode: ReCode.NO_ENOUGH_PARAMS];
        }
        String userName=params.userName;
        String passWord=params.passWord;
        UserInfo userInfo=UserInfo.findByUserName(userName);
        if(userInfo){
            passWord = DesUtilGy.encryptDES(passWord, userName);
            println("passWord-->"+passWord);
            if(passWord==userInfo.passWord){
                userInfo.loginTime = new Date()
                userInfo.loginIp = IPUtil.getClientIp(request)
                userInfo.save(true)
                //检查是否有店铺，如果有店铺则做工作人员登录
                RestaurantInfo restaurantInfo=RestaurantInfo.findByUserId(userInfo.id);
                if(restaurantInfo){
                    def paramsT=[
                            restaurantId:restaurantInfo.id,//饭店ＩＤ
                            loginName:userInfo.userName,//登录名
                            passWord:DesUtilGy.decryptDES(userInfo.passWord,userInfo.userName)//密码
                    ];
                    staffManageService.staffLogin(paramsT);
                }
                //设定session
                webUtilService.user = userInfo;
                //登录成功
                return [recode: ReCode.OK,userInfo:userInfo];
            }
            else{//密码错误
                return [recode: ReCode.PASSWORD_INCORRECT];
            }
        }
        else{
            return [recode:ReCode.CUSTOMER_NOT_EXIST];
        }
    }

    //退出
    def logout(){
        webUtilService.clearSession();
        return [recode: ReCode.OK];
    }

    //通过注册账户进行登录
    def loginByUser(def params,def request){
        def reInfo = login(params,request);
        if (reInfo.recode == ReCode.OK) {//登录成功
            UserInfo userInfo = reInfo.userInfo;
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
        }//else{
            return reInfo;
        //}
    }

    //通过手机号自动登录
    def loginByPhone(def params) {
        //用手机号和设备序号来登录
        def phoneNum = params.phoneNum//用户名
        if (!phoneNum) {//没有手机号
            return [recode: ReCode.NOT_GET_PHONE_NUM];
        }
        def sn = params.sn//密码
        if (sn == null) {
            sn = ""
        }
        //根据电话和设备号去查询是否存在记录
        ClientInfo clientInfo = ClientInfo.findByPhoneNumAndSnAndClientType(phoneNum, sn,ClientType.PHONE.code);
        if (!clientInfo) {
            clientInfo = new ClientInfo();
            clientInfo.phoneNum = phoneNum;
            clientInfo.sn = sn;
            clientInfo.clientType=ClientType.PHONE.code;
            if (!clientInfo.save(flush: true)) {
                return [recode: ReCode.SAVE_FAILED, clientInfo: clientInfo, errors: clientInfo.errors.allErrors];
            }
        }
        webUtilService.setClient(clientInfo.id);
        return [recode: ReCode.OK];
    }

    //绑定手机客户端到注册账户
    def bindPhoneToUser(def params,def request){
        def reInfo = login(params,request);
        if (reInfo.recode == ReCode.OK) {//登录成功
            UserInfo userInfo = reInfo.userInfo;
            def phoneNum = params.phoneNum;//用户名
            if (phoneNum == null) {
                phoneNum = ""
            }
            def sn = params.sn//密码
            if (sn == null) {
                sn = ""
            }
            //根据电话和设备号去查询是否有Client
            ClientInfo clientInfo=ClientInfo.findByPhoneNumAndSn(phoneNum,sn);
            if(!clientInfo){
                clientInfo=webUtilService.getClient();
            }
            if(clientInfo){
                clientInfo.userId=userInfo.id;
                clientInfo.userName=userInfo.userName;
                if (!clientInfo.save(flush: true)) {
                    return [recode: ReCode.SAVE_FAILED, clientInfo: clientInfo, errors: clientInfo.errors.allErrors];
                }
                return [recode: ReCode.OK];
            }else{
                return [recode: ReCode.NO_CLIENT_OR_NOT_LOGIN];
            }
        } else {
            return reInfo;
        }

    }

    //取消绑定手机客户端和新疆美账户
    def unBindPhoneAndXjmUser(params){
        def phoneNum = params.phoneNum//用户名
        if (phoneNum == null) {
            phoneNum = ""
        }
        def sn = params.sn//密码
        if (sn == null) {
            sn = ""
        }
        //根据电话和设备号去查询
        ClientInfo clientInfo=ClientInfo.findByPhoneNumAndSn(phoneNum,sn);
        if(!clientInfo){
            clientInfo=webUtilService.getClient();
        }
        if(clientInfo){
            clientInfo.userId=0;
            clientInfo.userName=null;
            if (!clientInfo.save(flush: true)) {
                return [recode: ReCode.SAVE_FAILED, clientInfo: clientInfo, errors: clientInfo.errors.allErrors];
            }
            return [recode: ReCode.OK];
        }else{
            return [recode: ReCode.NO_CLIENT_OR_NOT_LOGIN];
        }
    }

    //根据用户信息或手机信息查询相关用户id
    def getIds(def clientInfo) {
        def ids = [0l];
        if(clientInfo){
            if (clientInfo.userId) {
                //根据用户ID查询
                def uiList = ClientInfo.findAllByUserId(clientInfo.userId);
                if (uiList) {
                    uiList.each {
                        ids.add(it.id);
                    }
                } else {
                    ids.add(clientInfo.id);
                }
            } else {
                ids.add(clientInfo.id);
            }
        }
        return ids;
    }

    //查找我的收藏
    def getMyFavorites(def params) {
        def queryBlock
        def dataFormat
        //食品收藏
        if ("food".equals(params.type)) {
            queryBlock = {queryParams ->
                FoodCollectInfo.createCriteria().list(queryParams) {
                    eq('userId', webUtilService.user.id)
                }
            }

            dataFormat = {foodCollectInfo ->
                def food = FoodInfo.get(foodCollectInfo.foodId)
                if (!food) {
                    [
                            'id': foodCollectInfo.id,
                            'foodId': 0,
                            'name': '该菜谱已经不存在了',
                            'image': ''
                    ]
                } else {
                    [
                            'id': foodCollectInfo.id,
                            'foodId': foodCollectInfo.foodId,
                            'name': food.name,
                            'image': food.image
                    ]
                }

            }
        } else {
            queryBlock = { queryParams ->
                RestaurantCollectInfo.createCriteria().list(queryParams) {
                    eq('userId', webUtilService.user.id)
                }
            }

            dataFormat = {restaurantCollectInfo ->
                def restaurantInfo = RestaurantInfo.get(restaurantCollectInfo.restaurantId)
                if (!restaurantInfo) {
                    ['id':restaurantCollectInfo.id,'restaurantId':0,'name':"该饭店已经不存在了",'image':'']
                } else {
                    [
                            'id': restaurantCollectInfo.id,
                            'restaurantId': restaurantCollectInfo.restaurantId,
                            'name': restaurantInfo.name,
                            'image': restaurantInfo.image
                    ]
                }

            }
        }
        def reInfo=[recode: ReCode.OK]<<search(params,queryBlock,dataFormat);
        return reInfo;
    }
    def search= {params,queryBlock,dataFormat->
        params.max=Integer.valueOf(params.max?:10)
        params.offset=params.offset ? params.offset : 0
        params.sort=params.sort?:'id'
        params.order=params.order?:'desc'
        def dataRows=queryBlock.call(params)
        def totalRows=dataRows.totalCount
        def results=dataRows?.collect{
            dataFormat(it)
        }
        [collectList:results,totalCount:totalRows]
    }
    //添加收藏
    def addFavorite(def params) {
        def session=webUtilService.getSession();
        long userId = lj.Number.toLong(session.userId);//用户ID
        println("userId--->"+userId);
        if (userId) {
            params.userId = userId;
            if ("food".equals(params.type)) {
                long foodId=lj.Number.toLong(params.foodId);
                def foodC =FoodCollectInfo.findByFoodIdAndUserId(foodId,userId);
                if(foodC){
                    return [recode: ReCode.FOOD_IS_COLLECTED];
                }
                else{
                    foodC =new FoodCollectInfo(params);
                }
                if (!foodC.save(flush: true)) {
                    return [recode: ReCode.COLLECT_FAILED];
                }
            } else {
                long restaurantId=lj.Number.toLong(params.restaurantId) ;
                def restaurantC =RestaurantCollectInfo.findByRestaurantIdAndUserId(restaurantId,userId);
                if(restaurantC){
                    return [recode: ReCode.RESTAURANT_IS_COLLECTED];
                }
                else{
                    restaurantC =new RestaurantCollectInfo(params);
                }
                if (!restaurantC.save(flush: true)) {
                    return [recode: ReCode.COLLECT_FAILED];
                }
            }
        } else {
            return [recode: ReCode.NOT_LOGIN];
        }

        return [recode: ReCode.OK];
    }
    //删除收藏
    def delFavorite(def params) {
        println params.ids
        def ids = params.ids
        if (ids instanceof String[]) {
            ids = params.ids.join(",")
        }
        if ("food".equals(params.type)) {
            FoodCollectInfo.executeUpdate("delete from FoodCollectInfo where id in (${ids})")
        } else {
            RestaurantCollectInfo.executeUpdate("delete from RestaurantCollectInfo where id in (${ids})")
        }

        return [recode: ReCode.OK];
    }
}
