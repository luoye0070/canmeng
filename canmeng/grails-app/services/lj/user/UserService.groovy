package lj.user

import lj.IPUtil
import lj.common.DesUtilGy
import lj.common.Result
import lj.data.FoodCollectInfo
import lj.data.FoodInfo
import lj.data.RestaurantCollectInfo
import lj.data.RestaurantInfo
import lj.data.UserInfo
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
                    []
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
                    []
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
