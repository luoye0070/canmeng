package lj.shop

import lj.data.FoodCollectInfo
import lj.data.FoodInfo
import lj.data.RestaurantInfo
import lj.data.SetMealInfo
import lj.enumCustom.ReCode
import lj.enumCustom.VerifyStatus

import java.text.SimpleDateFormat

class FoodManageService {

    def webUtilService;
    def searchService;
    def shopService;
    def serviceMethod() {

    }

    /***************
     * 保存菜单
     *
     * params是传入的参数
     * 参数格式为：
     * id 菜单id，非必须，如果id传入了的话，下面的参数都是非必须的
     * name 菜名，必须
     * price 价格，必须
     * originalPrice 原价，非必须
     * image 图片地址，非必须
     * canTakeOut 是否支持外卖，非必须，默认是不支持
     * description 菜单详细描述，非必须
     * enabled 菜单是否有效，非必须，默认有效
     * countLimit 每天限量，非必须，默认为0表示不限量
     * isSetMeal 是否是套餐，非必须，默认不是套餐
     * followFoodIds 套餐从属菜单数组，例如：12或[23,22,...]
     * sellCount 销量，必须没有
     *
     * 返回值
     * [recode: ReCode.SHOP_NOT_ENABLED];店铺是关闭状态，不能编辑菜单
     * [recode: ReCode.SHOP_WAIT_VERIFY];饭店正在审核中，不能编辑菜单
     * [recode: ReCode.SHOP_VERIFY_NOT_PASS];饭店审核没有通过
     * [recode: ReCode.NOT_REGISTER_RESTAURANT];用户没有注册店铺
     * [recode: ReCode.SAVE_FAILED,errors:foodInfo.errors.allErrors];保存菜单信息失败
     * [recode:ReCode.NOT_LOGIN];用户没有登录
     * [recode: ReCode.OK];菜单信息保存成功
     * **********************/
    def save(def params){
        def session=webUtilService.getSession();
        //SimpleDateFormat sdfDate=new SimpleDateFormat("yyyy-MM-dd");
        //SimpleDateFormat sdfTime=new SimpleDateFormat("HH:mm:ss");
        //取出用户ID
        long userId=lj.Number.toLong(session.userId);//用户ID
        if(userId){
            //取参数
            Long id=lj.Number.toLong(params.id);

            //检查店铺可用性
            def reInfo=shopService.getShopEnabled();
            if(reInfo.recode!=ReCode.OK){
                return reInfo;
            }
            else{
                params.restaurantId=reInfo.restaurantInfo.id;
            }

            FoodInfo foodInfo=null;
            if(id){
                foodInfo=FoodInfo.get(id);
            }
            if(foodInfo){
               foodInfo.setProperties(params);
            }
            else{
                foodInfo=new FoodInfo(params);
            }
            if(foodInfo.validate()){
                if(foodInfo.save(flush: true)){
                    //套餐设置
                    if(params.followFoodIds){
                        //删除原来的
                        SetMealInfo.executeUpdate("delete from SetMealInfo where host="+foodInfo.id);
                        //设置新的
                        if(params.followFoodIds instanceof String){//传入一个值
                            long followFoodId=lj.Number.toLong(params.followFoodIds);
                            SetMealInfo setMealInfo=new SetMealInfo();
                            setMealInfo.host=foodInfo.id;
                            setMealInfo.followFoodId=followFoodId;
                            setMealInfo.save(flush: true);
                        }
                        else if(params.followFoodIds  instanceof String[]){//传入一组值
                            for (int i=0;i<params.followFoodIds.length;i++){
                                long followFoodId=lj.Number.toLong(params.followFoodIds[i]);
                                SetMealInfo setMealInfo=new SetMealInfo();
                                setMealInfo.host=foodInfo.id;
                                setMealInfo.followFoodId=followFoodId;
                                setMealInfo.save(flush: true);
                            }
                        }

                    }
                    return [recode: ReCode.OK,foodInfo:foodInfo];
                }
                else
                    return [recode: ReCode.SAVE_FAILED,foodInfo:foodInfo,errors:foodInfo.errors.allErrors];
            }
            else{
                return [recode: ReCode.SAVE_FAILED,foodInfo:foodInfo,errors:foodInfo.errors.allErrors];
            }
        }
        else{
            return [recode:ReCode.NOT_LOGIN];
        }
    }

    //查询菜单信息
    def getFoodInfo(def params){
        def session=webUtilService.getSession();
        //SimpleDateFormat sdfDate=new SimpleDateFormat("yyyy-MM-dd");
        //SimpleDateFormat sdfTime=new SimpleDateFormat("HH:mm:ss");
        //取出用户ID
        long userId=lj.Number.toLong(session.userId);//用户ID
        if(userId){
            //取参数
            //Long id=lj.Number.toLong(params.id);

            //检查店铺可用性
            def reInfo=shopService.getShopEnabled();
            if(reInfo.recode!=ReCode.OK){
                return reInfo;
            }
            long restaurantId=reInfo.restaurantInfo.id;
            long id=lj.Number.toLong(params.id);
            FoodInfo foodInfo=FoodInfo.findByRestaurantIdAndId(restaurantId,id);
            if(foodInfo)
                return [recode: ReCode.OK,foodInfo:foodInfo];
            else
                return [recode: ReCode.NO_RESULT];
        }
        else{
            return [recode:ReCode.NOT_LOGIN];
        }
    }

    //查询菜单信息
    def foodList(def params){
        def session=webUtilService.getSession();
        //SimpleDateFormat sdfDate=new SimpleDateFormat("yyyy-MM-dd");
        //SimpleDateFormat sdfTime=new SimpleDateFormat("HH:mm:ss");
        //取出用户ID
        long userId=lj.Number.toLong(session.userId);//用户ID
        if(userId){
            //取参数
            //Long id=lj.Number.toLong(params.id);

            //检查店铺可用性
            def reInfo=shopService.getShopEnabled();
            if(reInfo.recode!=ReCode.OK){
                return reInfo;
            }
            params.restaurantId=reInfo.restaurantInfo.id;
            return searchService.searchFood(params);
        }
        else{
            return [recode:ReCode.NOT_LOGIN];
        }
    }

    /***************
     * 菜单删除
     *
     * params是传入的参数
     * 参数格式为：
     * ids 菜单id数组或单个id，必须，ids格式例如：12或者[1,23,...]
     *
     * 返回值
     * [recode: ReCode.SHOP_NOT_ENABLED];店铺是关闭状态，不能编辑菜单
     * [recode: ReCode.SHOP_WAIT_VERIFY];饭店正在审核中，不能编辑菜单
     * [recode: ReCode.SHOP_VERIFY_NOT_PASS];饭店审核没有通过
     * [recode: ReCode.NOT_REGISTER_RESTAURANT];用户没有注册店铺
     * [recode:ReCode.NOT_LOGIN];用户没有登录
     * [recode: ReCode.OK];删除菜单成功
     * **********************/
    def deleteFoodInfo(def params){
        def session=webUtilService.getSession();
        //SimpleDateFormat sdfDate=new SimpleDateFormat("yyyy-MM-dd");
        //SimpleDateFormat sdfTime=new SimpleDateFormat("HH:mm:ss");
        //取出用户ID
        long userId=lj.Number.toLong(session.userId);//用户ID
        if(userId){
            //检查店铺可用性
            def reInfo=shopService.getShopEnabled();
            if(reInfo.recode!=ReCode.OK){
                return reInfo;
            }

            //根据ID删除记录
            if(params.ids instanceof String){ //传入id
                Long id=lj.Number.toLong(params.ids); //菜单id
                FoodInfo.executeUpdate("delete from FoodInfo where id="+id);
            }
            else if(params.ids instanceof String[]){//传入id数组
                String sql_s= "delete from FoodInfo where id in (0";
                for(int i=0;i<params.ids.length;i++){
                    Long id=lj.Number.toLong(params.ids[i]);
                    sql_s+=","+id;
                }
                sql_s+=")";
                FoodInfo.executeUpdate(sql_s);
            }

            //删除收藏
            if(params.ids instanceof String){ //传入id
                Long id=lj.Number.toLong(params.ids); //菜单id
                FoodCollectInfo.executeUpdate("delete from FoodCollectInfo where foodId="+id);
            }
            else if(params.ids instanceof String[]){//传入id数组
                String sql_s= "delete from FoodCollectInfo where foodId in (0";
                for(int i=0;i<params.ids.length;i++){
                    Long id=lj.Number.toLong(params.ids[i]);
                    sql_s+=","+id;
                }
                sql_s+=")";
                FoodCollectInfo.executeUpdate(sql_s);
            }
            return [recode: ReCode.OK];

        }
        else{
            return [recode:ReCode.NOT_LOGIN];
        }
    }
}
