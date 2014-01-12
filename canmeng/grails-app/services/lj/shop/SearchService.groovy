package lj.shop

import lj.FormatUtil
import lj.data.AreaInfo
import lj.data.CityInfo
import lj.data.FoodInfo
import lj.data.OrderInfo
import lj.data.ReserveTypeInfo
import lj.data.RestaurantInfo
import lj.data.TableInfo
import lj.enumCustom.OrderStatus
import lj.enumCustom.OrderValid
import lj.enumCustom.ReCode

import java.text.SimpleDateFormat

class SearchService {

    def serviceMethod() {

    }
    //查询菜单
    def searchFood(def params) {
        //获取查询条件参数
//        String keyWord=params.keyWord;  //关键字
//        long restaurantId=lj.Number.toLong(params.restaurantId);//饭店ID
        double priceLow = lj.Number.toDouble(params.priceLow);//最低价格
        double priceHigh = lj.Number.toDouble(params.priceHigh);//最高价格
//        boolean canTakeOut=params.canTakeOut;//是否支持外卖
//        boolean isSetMeal=params.isSetMeal;//是否是套餐
        //params.areaId
        //params.cityId
        //params.provinceId
        long foodId = lj.Number.toLong(params.foodId);

        if (!params.max) {
            params.max = 10
        }
        if (!params.offset) {
            params.offset = 0;
        }
        println(params.offset+"---"+params.max);
        //def restaurantIds = [0l];//因为城市id一定会有的
        def restaurantIds =null;
        if (params.restaurantId) {
            restaurantIds = [lj.Number.toLong(params.restaurantId)];
        } else {
            def areaIds = null;
            long areaId = lj.Number.toLong(params.areaId);
            long cityId = lj.Number.toLong(params.cityId);
            long provinceId = lj.Number.toLong(params.provinceId);
            //区域ID
            if (areaId) {
                areaIds = [areaId];
            } else if (cityId) {
                areaIds = getAreaIdsFromCity(cityId);
            } else if (provinceId) {
                areaIds = getAreaIdsFromProvince(provinceId);
            }
            //菜系
            long cuisineId = lj.Number.toLong(params.cuisineId);
            if (areaIds && cuisineId) {
                def restaurantInfoList = RestaurantInfo.findAllByAreaIdInListAndCuisineId(areaIds, cuisineId);
                if (restaurantInfoList) {
                    restaurantIds = [];
                    restaurantInfoList.each {
                        restaurantIds.add(it.id);
                    }
                }
            } else if (areaIds) {
                def restaurantInfoList = RestaurantInfo.findAllByAreaIdInList(areaIds);
                if (restaurantInfoList) {
                    restaurantIds = [];
                    restaurantInfoList.each {
                        restaurantIds.add(it.id);
                    }
                }
            } else if (cuisineId) {
                def restaurantInfoList = RestaurantInfo.findAllByCuisineId(cuisineId);
                if (restaurantInfoList) {
                    restaurantIds = [];
                    restaurantInfoList.each {
                        restaurantIds.add(it.id);
                    }
                }
            }
        }
        //查询条件
        def condition = {
            if (foodId) {
                eq("id", foodId);
            }else if (restaurantIds) {
                'in'("restaurantId", restaurantIds);
            }
            if (params.keyWord) { //关键字
                like("name", "%" + params.keyWord + "%");
            }
//            if(params.restaurantId){ //饭店ID
//                eq("restaurantId",params.restaurantId);
//            }

            if (priceLow) { //最低价格
                ge("price", priceLow);
            }
            if (priceHigh) { //最高价格
                le("price", priceHigh);
            }
            if (params.canTakeOut != null) {//是否外卖
                eq("canTakeOut", lj.TypeConversion.toBoolean(params.canTakeOut));
            }
            if (params.enabled != null) {//是否有效
                eq("enabled", lj.TypeConversion.toBoolean(params.enabled));
            }
            if (params.isSetMeal != null) { //是否套餐
                eq("isSetMeal", lj.TypeConversion.toBoolean(params.isSetMeal));
            }
        }

        def foodList = FoodInfo.createCriteria().list(params, condition);
        def totalCount = FoodInfo.createCriteria().count(condition);

        return [recode: ReCode.OK, foodList: foodList, totalCount: totalCount, params: params];

    }

    //查询桌位
    def searchTable(def params) {

//        if (!params.max) {
//            params.max = 10
//        }
//        if (!params.offset) {
//            params.offset = 0;
//        }

        //查询条件
        def condition = {
            if (params.tableId) {
                eq("id", lj.Number.toLong(params.tableId));
            }
            //if(params.restaurantId){ //饭店ID
            eq("restaurantId", lj.Number.toLong(params.restaurantId));
            //}
            if (params.peopleCount) { //就餐人数
                ge("maxPeople", lj.Number.toInteger(params.peopleCount));
                le("minPeople", lj.Number.toInteger(params.peopleCount));
            }
            if (params.canMultiOrder != null) {//是否支持多人共桌
                eq("canMultiOrder", params.canMultiOrder);
            }
            if (params.canReserve != null) { //是否支持预定
                eq("canReserve", params.canReserve);
            }
            if (params.enabled != null) { //是否有效
                eq("enabled", params.enabled);
            }
        }

        def tableListTemp = TableInfo.createCriteria().list(params, condition);
        def totalCount = TableInfo.createCriteria().count(condition);
        def tableList = tableListTemp.collect {//检查是否可以使用
            if (!it.canMultiOrder) {//不支持多单共桌
                //检查桌号是否被占
                Date date = null;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try { date = sdf.parse(params.date); } catch (Exception ex) {}
                int reserveType = lj.Number.toInteger(params.reserveType);
                int orderCount = 0;
                println("date-->"+date+"---reserveType-->"+reserveType);
                long tableId= it.id;
                ReserveTypeInfo reserveTypeInfo=ReserveTypeInfo.findByRestaurantIdAndReserveType(lj.Number.toLong(params.restaurantId),reserveType);
                if (date && reserveType) {
                    orderCount = OrderInfo.createCriteria().count() {
                        eq("restaurantId", lj.Number.toLong(params.restaurantId));
                        eq("tableId", tableId);
                        eq("date", date);
                        if(reserveTypeInfo){
                            le("time",reserveTypeInfo.endTime);
                            ge("time",reserveTypeInfo.beginTime);
                        }
                        //eq("reserveType", reserveType);
                        lt("status", OrderStatus.CHECKOUTED_STATUS.code);
                        lt("valid", OrderValid.USER_CANCEL_VALID.code);
                    }
                    println("orderCount-->"+orderCount);
                }//findByRestaurantIdAndTableIdAndDateAndReserveTypeIdAndStatus(restaurantId,tableId,date,reserveType,OrderStatus.CHECKOUTED_STATUS.code);
                if (orderCount) {
                    [canUse: false, tableInfo: it];
                } else {
                    [canUse: true, tableInfo: it];
                }
            } else {
                [canUse: true, tableInfo: it];
            }
        }

        //根据预定类型查出饭店对应的时间
        long restaurantId=lj.Number.toLong(params.restaurantId);
        int reserveType=lj.Number.toInteger(params.reserveType);
        ReserveTypeInfo reserveTypeInfo=ReserveTypeInfo.findByRestaurantIdAndReserveType(restaurantId,reserveType);
        String reserveTypeTimes="";
        if(reserveTypeInfo){
            reserveTypeTimes=FormatUtil.timeFormat(reserveTypeInfo.beginTime)+"-"+FormatUtil.timeFormat(reserveTypeInfo.endTime);
        }
        params.reserveTypeTimes=reserveTypeTimes;
        return [recode: ReCode.OK, tableList: tableList, totalCount: totalCount, params: params];
    }
    //饭店搜索
    def searchShop(def params) {
        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
        long areaId = lj.Number.toLong(params.areaId);
        long cityId = lj.Number.toLong(params.cityId);
        long provinceId = lj.Number.toLong(params.provinceId);
        long restaurantId = lj.Number.toLong(params.restaurantId);
        long cuisineId = lj.Number.toLong(params.cuisineId);
        double averageConsume = lj.Number.toDouble(params.averageConsume);
        int verifyStatus = lj.Number.toInteger(params.verifyStatus);
        if (!params.max) {
            params.max = 10
        }
        if (!params.offset) {
            params.offset = 0;
        }

        //def areaIds = [0l];//因为城市ID一定要传的
        def areaIds =null;
        //区域ID
        if (areaId) {
            areaIds = [areaId];
        } else if (cityId) {
            areaIds = getAreaIdsFromCity(cityId);
        } else if (provinceId) {
            areaIds = getAreaIdsFromProvince(provinceId);
        }

        //查询条件
        def condition = {
            if (restaurantId) { //饭店ID
                eq("id", restaurantId);
                println("id-->" + params.restaurantId);
            }else if (areaIds) {
                'in'("areaId", areaIds);
                println("areaIds-->" + areaIds);
            }
            if (params.name) { //饭店名称
                like("name", "%" + params.name + "%");
                println("name-->" + params.name);
            }
//            if(params.province){//省
//                eq("province",params.province);
//            }
//            if(params.city){//市
//                eq("city",params.city);
//            }
//            if(params.area){//区
//                eq("area",params.area);
//            }

            if (params.shopHours) { //营业时间
                Date time = null;//时间
                try { time =sdfTime.parse(params.shopHours); } catch (Exception ex) { ex.printStackTrace(); }
                le("shopHoursBeginTime", time);
                ge("shopHoursEndTime", time);
                println("shopHours-->" + params.shopHours);
            }
            if (params.enabled != null) { //是否有效
                eq("enabled", lj.TypeConversion.toBoolean(params.enabled));
                println("enabled-->" + params.enabled);
            }
            if (cuisineId) {//菜系ID
                eq("cuisineId", cuisineId);
                println("cuisineId-->" + params.cuisineId);
            }
            if (verifyStatus) {//审核状态
                eq("verifyStatus", verifyStatus);
                println("verifyStatus-->" + params.verifyStatus);
            }
            if (averageConsume) {//人均消费水平
                eq("averageConsume", averageConsume);
                println("averageConsume-->" + params.averageConsume);
            }
        }

        def restaurantList = RestaurantInfo.createCriteria().list(params, condition);
        def totalCount = RestaurantInfo.createCriteria().count(condition);

        return [recode: ReCode.OK, restaurantList: restaurantList, totalCount: totalCount, params: params];
    }
    //根据省份id获取对应的区域列表
    def getAreaIdsFromProvince(long provinceId) {
        def areaIds = [0l];
        def cityList = CityInfo.findAllByProvinceId(provinceId);
        if (cityList) {
            cityList.each {
                def areaList = AreaInfo.findAllByCityId(it.id);
                if (areaList) {
                    areaList.each {
                        areaIds.add(it.id);
                    }
                }
            }
        }
        return areaIds;
    }
    //根据城市id获取对应的区域列表
    def getAreaIdsFromCity(long cityId) {
        def areaIds = [0l];
        def areaList = AreaInfo.findAllByCityId(cityId);
        if (areaList) {
            areaList.each {
                areaIds.add(it.id);
            }
        }
        return areaIds;
    }
}
