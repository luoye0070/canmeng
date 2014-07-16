package lj.shop

import lj.common.DesUtilGy
import lj.common.UploadFile
import lj.data.ReserveTypeInfo
import lj.data.RestaurantInfo
import lj.data.StaffInfo
import lj.data.StaffPositionInfo
import lj.data.UserInfo
import lj.enumCustom.PositionType
import lj.enumCustom.ReCode
import lj.enumCustom.ReserveType
import lj.enumCustom.VerifyStatus
import org.springframework.web.multipart.MultipartHttpServletRequest
import org.springframework.web.multipart.commons.CommonsMultipartFile

import java.text.SimpleDateFormat

/**
 * ShopService
 * <p>店铺相关的服务</p>
 * @author 刘兆国
 * @version 1.0
 */
class ShopService {
    def webUtilService;
    def uploadFileService;
    def serviceMethod() {

    }

    /***************
     * 创建店铺
     *
     * params是传入的参数
     * 参数格式为：
     * name 店名，必须
     * areaId 地区ID，必须
     * province 地址中省份，必须
     * city 地址中城市，必须
     * area 地址中区，必须
     * street 地址中街道，必须
     * lonqitude 地址中经度，非必须
     * latitude 地址中纬度，非必须
     * phone 联系电话，必须
     * masterName 店主法人，必须
     * shopHoursBeginTime 营业时间起（HH:mm:ss），必须
     * shopHoursEndTime 营业时间止（HH:mm:ss），必须
     * cuisineId 菜系Id，必须
     *
     * 返回值
     * [recode: ReCode.CANNOT_REPEAT_REGISTER_SHOP];不能重复注册店铺
     * [recode: ReCode.SAVE_FAILED,errors:restaurantInfo.errors.allErrors];保存数据失败
     * [recode:ReCode.NOT_LOGIN];用户没有登录
     * [recode: ReCode.OK];注册店铺成功
     * **********************/
    def createShop(def params){
        def session=webUtilService.getSession();
        SimpleDateFormat sdfDate=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfTime=new SimpleDateFormat("HH:mm:ss");
        //取出用户ID
        //UserInfo user=session.user;
        UserInfo user=webUtilService.getUser();
        long userId=lj.Number.toLong(session.userId);//用户ID
        if(user){

            //初始参数处理
            params.userId=userId;//用户名
            String shopHoursBeginTimeStr=params.shopHoursBeginTime; //营业时间起,HH:mm:ss
            try{params.shopHoursBeginTime=sdfTime.parse(shopHoursBeginTimeStr);}catch (Exception ex){ex.printStackTrace();}
            String shopHoursEndTimeStr=params.shopHoursEndTime; //营业时间止,HH:mm:ss
            try{params.shopHoursEndTime=sdfTime.parse(shopHoursEndTimeStr);}catch (Exception ex){ex.printStackTrace();}
            //手机号正确性验证

            //检查用户是否已经注册过店铺
            RestaurantInfo restaurantInfo1=RestaurantInfo.findByUserId(userId);
            if(restaurantInfo1){
                return [recode: ReCode.CANNOT_REPEAT_REGISTER_SHOP];
            }

            RestaurantInfo restaurantInfo=new RestaurantInfo(params);
            if(restaurantInfo.validate()){
                if(restaurantInfo.save(flush: true)){
                    //添加一个店主的工作人员
                    StaffInfo staffInfo=new StaffInfo();
                    staffInfo.restaurantId=restaurantInfo.id;
                    staffInfo.loginName=user.userName;
                    staffInfo.passWord=DesUtilGy.decryptDES(user.passWord,user.userName);
                    staffInfo.name=restaurantInfo.masterName;
                    if(staffInfo.save(flush: true)){
                        StaffPositionInfo staffPositionInfo=new StaffPositionInfo();
                        staffPositionInfo.restaurantId=restaurantInfo.id;
                        staffPositionInfo.staffId=staffInfo.id;
                        staffPositionInfo.positionType=PositionType.SHOPKEEPER.code;
                        if(!staffPositionInfo.save(flush: true)){
                            println("保存店主工作人员职务错误，请查证：");
                            staffPositionInfo.errors.allErrors.each {
                                println(it);
                            }
                        }
                    }
                    else{
                        println("保存店主工作人员错误，请查证：");
                        staffInfo.errors.allErrors.each {
                            println(it);
                        }
                    }
                    return [recode: ReCode.OK,restaurantInfo:restaurantInfo];
                }else
                    return [recode: ReCode.SAVE_FAILED,restaurantInfo:restaurantInfo,errors:restaurantInfo.errors.allErrors];
            }
            else{
                return [recode: ReCode.SAVE_FAILED,restaurantInfo:restaurantInfo,errors:restaurantInfo.errors.allErrors];
            }
        }
        else{
            return [recode:ReCode.NOT_LOGIN];
        }
    }

    //店铺信息查询
    def getShopInfo(def params){
        def session=webUtilService.getSession();
        //SimpleDateFormat sdfDate=new SimpleDateFormat("yyyy-MM-dd");
        //SimpleDateFormat sdfTime=new SimpleDateFormat("HH:mm:ss");
        //取出用户ID
        long userId=lj.Number.toLong(session.userId);//用户ID
        if(userId){
            //获取参数
            Long id=lj.Number.toLong(params?.id);//店铺id

            //查找店铺信息
            RestaurantInfo restaurantInfo=null;
            if(id){ //根据ID查询到饭店
                restaurantInfo=RestaurantInfo.get(id);
            }
            else{ //根据用户ID查询出店铺信息
                restaurantInfo=RestaurantInfo.findByUserId(userId);
            }
            if(restaurantInfo){
                return [recode: ReCode.OK,restaurantInfo:restaurantInfo];
            }
            else{//还没有注册饭店
                return [recode: ReCode.NOT_REGISTER_RESTAURANT];
            }
        }
        else{
            return [recode:ReCode.NOT_LOGIN];
        }
    }

    //店铺属性信息设置
    def setShopInfo(def params,def request){
        def session=webUtilService.getSession();
        SimpleDateFormat sdfDate=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfTime=new SimpleDateFormat("HH:mm:ss");
        //取出用户ID
        long userId=lj.Number.toLong(session.userId);//用户ID
        if(userId){
            //获取参数
            Long id= lj.Number.toLong(params.id);//店铺id
//            String name=params.name;//店名
//            String image=params.image;//店招图片地址
//            Long areaId=lj.Number.toLong(params.areaId);//地区ID
//            String province=params.province;//地址中省份
//            String city
//            String area  //区
//            String street             //街道
//            Double lonqitude                    //经度
//            Double latitude                      //维度
//            String phone            //电话
//            String masterName              //店主姓名
//            Date shopHoursBeginTime                 //营业时间起
//            Date shopHoursEndTime                //营业时间止
//            Boolean enabled=false                 //开启关闭状态true开启，false关闭
//            long cuisineId              //菜系
//            Double freight=0      //外卖运费
//            Double freeFreight=0  //外卖面运费条件（订单总金额达到多少面运费，0表示不免运费）
//            long packId            //外卖包装方式
//            Long deliverRange;     //外卖配送范围半斤,单位米
//            Double averageConsume;   //人均消费水平，单位元

            //初始参数处理
            params.userId=userId;//用户名
            if(params.shopHoursBeginTime){
                String shopHoursBeginTimeStr=params.shopHoursBeginTime; //营业时间起,HH:mm:ss
                try{params.shopHoursBeginTime=sdfTime.parse(shopHoursBeginTimeStr);}catch (Exception ex){ex.printStackTrace();}
            }
            if(params.shopHoursEndTime){
                String shopHoursEndTimeStr=params.shopHoursEndTime; //营业时间止,HH:mm:ss
                try{params.shopHoursEndTime=sdfTime.parse(shopHoursEndTimeStr);}catch (Exception ex){ex.printStackTrace();}
            }
            //手机号正确性验证

            //查找店铺信息
            RestaurantInfo restaurantInfo=null;
            if(id){ //根据ID查询到饭店
                restaurantInfo=RestaurantInfo.get(id);
            }
            else{ //根据用户ID查询出店铺信息
                restaurantInfo=RestaurantInfo.findByUserId(userId);
            }

            if(restaurantInfo){

                //如果上传了店招图片
                if (request instanceof MultipartHttpServletRequest) {
                    MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
                    CommonsMultipartFile orginalFile = (CommonsMultipartFile) multiRequest.getFile("imgfile");
                    // 判断是否上传文件
                    if (orginalFile != null && !orginalFile.isEmpty()) {
                        // 获取原文件名称
                        String originalFilename = orginalFile.getOriginalFilename();
                        //如果原先有图片则删除原先的
                        if(restaurantInfo.image){
                            uploadFileService.delete(restaurantInfo.image);
                        }
                        //上传文件到文件系统
                        //String fileFullName=UploadFile.uploadToFileSystem(orginalFile.getInputStream(),originalFilename);
                        String fileFullName=uploadFileService.upload(orginalFile.getInputStream(),originalFilename);
                        if(fileFullName){
                            params.image=fileFullName;
                        }
                    }
                }

                //如果修改了联系电话或者修改了店主姓名则需要重新审核
                if(params.phone){
                    if(!restaurantInfo.phone.equals(params.phone)){
                        params.verifyStatus=VerifyStatus.PROCESSING.code;
                    }
                }
                if(params.masterName){
                    if(!restaurantInfo.masterName.equals(params.masterName)){
                        params.verifyStatus=VerifyStatus.PROCESSING.code;
                    }
                }
                //设置属性值
                restaurantInfo.setProperties(params);
                //保存数据
                if(restaurantInfo.save(flush: true))
                    return [recode: ReCode.OK,restaurantInfo:restaurantInfo];
                else
                    return [recode: ReCode.SAVE_FAILED,restaurantInfo:restaurantInfo,errors:restaurantInfo.errors.allErrors];
            }
            else{//还没有注册饭店
                return [recode: ReCode.NOT_REGISTER_RESTAURANT];
            }
        }
        else{
            return [recode:ReCode.NOT_LOGIN];
        }
    }

    //获取店铺的可用性
    def getShopEnabled(){
        def session=webUtilService.getSession();
        //SimpleDateFormat sdfDate=new SimpleDateFormat("yyyy-MM-dd");
        //SimpleDateFormat sdfTime=new SimpleDateFormat("HH:mm:ss");
        //取出用户ID
        long userId=lj.Number.toLong(session.userId);//用户ID
        if(userId){
            //取参数
            //Long id=lj.Number.toLong(params.id);

            //初始化一些参数
            RestaurantInfo restaurantInfo=RestaurantInfo.findByUserId(userId);
            if(restaurantInfo){
                if(restaurantInfo.enabled==false){//店铺关闭了
                    return [recode: ReCode.SHOP_NOT_ENABLED];
                }
                if(restaurantInfo.verifyStatus==VerifyStatus.PROCESSING.code){//等待审核中
                    return [recode: ReCode.SHOP_WAIT_VERIFY];
                }
                if(restaurantInfo.verifyStatus==VerifyStatus.NOTPASS.code){//审核没有通过
                    return [recode: ReCode.SHOP_VERIFY_NOT_PASS];
                }
                return [recode: ReCode.OK,restaurantInfo:restaurantInfo];
            }
            else{//还没有注册店铺
                return [recode: ReCode.NOT_REGISTER_RESTAURANT];
            }
        }
        else{
            return [recode:ReCode.NOT_LOGIN];
        }
    }

    //预定类型时间对应关系获取
    def getReserveTypes(def params){
        def session=webUtilService.getSession();
        //SimpleDateFormat sdfDate=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfTime=new SimpleDateFormat("HH:mm:ss");
        long restaurantId=0;
        if(params){
            restaurantId=lj.Number.toLong(params.restaurantId);
        }
        else{
            //取出用户ID
            long userId=lj.Number.toLong(session.userId);//用户ID
            if(userId){
                //检查店铺可用性
                def reInfo=getShopInfo(null);
                if(reInfo.recode!=ReCode.OK){
                    return reInfo;
                }
                else{
                    restaurantId=reInfo.restaurantInfo.id;
                }
            }
            else{
                return [recode:ReCode.NOT_LOGIN];
            }
        }
        //默认值
        Date date1=sdfTime.parse("06:00:00");
        Date date2=sdfTime.parse("10:00:00");
        def type1=[id:0,reserveType: ReserveType.MORNING,beginTime:date1,endTime:date2];

        date1=sdfTime.parse("11:00:00");
        date2=sdfTime.parse("15:00:00");
        def type2=[id:0,reserveType: ReserveType.NOON,beginTime:date1,endTime:date2];

        date1=sdfTime.parse("17:00:00");
        date2=sdfTime.parse("23:00:00");
        def type3=[id:0,reserveType: ReserveType.NIGHT,beginTime:date1,endTime:date2];
        def reserveTypes=[morning:type1,noon:type2,night:type3];
        //查询相应的预定类型
        def reserveTypeListTemp=ReserveTypeInfo.findAllByRestaurantId(restaurantId);
        if(reserveTypeListTemp){
            reserveTypeListTemp.each {
                if(it.reserveType==ReserveType.MORNING.code){
                    reserveTypes.morning=[id:it.id,reserveType: ReserveType.MORNING,beginTime:it.beginTime,endTime:it.endTime];
                }
                else if(it.reserveType==ReserveType.NOON.code){
                    reserveTypes.noon=[id:it.id,reserveType: ReserveType.NOON,beginTime:it.beginTime,endTime:it.endTime];
                }
                else if(it.reserveType==ReserveType.NIGHT.code){
                    reserveTypes.night=[id:it.id,reserveType: ReserveType.NIGHT,beginTime:it.beginTime,endTime:it.endTime];
                }
            }
        }
        return [recode: ReCode.OK,reserveTypes:reserveTypes];
    }

    //保存预定时间
    def saveReserveTypes(def params){
        def session=webUtilService.getSession();
        //SimpleDateFormat sdfDate=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfTime=new SimpleDateFormat("HH:mm:ss");
        //取出用户ID
        long userId=lj.Number.toLong(session.userId);//用户ID
        if(userId){
            long restaurantId=0;
            //检查店铺可用性
            def reInfo=getShopInfo(null);
            if(reInfo.recode!=ReCode.OK){
                return reInfo;
            }
            else{
                restaurantId=reInfo.restaurantInfo.id;
            }

            for(int i=0;i<3;i++){
                try {
                    //取参数
                    long id=lj.Number.toLong(params.id[i]);
                    int reserveType=lj.Number.toLong(params.reserveType[i]);
                    String beginTimeStr=params.beginTime[i];
                    String endTimeStr=params.endTime[i];
                    Date beginTime=sdfTime.parse(beginTimeStr);
                    Date endTime=sdfTime.parse(endTimeStr);

                    ReserveTypeInfo reserveTypeInfo=ReserveTypeInfo.findByIdAndRestaurantId(id,restaurantId);
                    if(!reserveTypeInfo){
                        reserveTypeInfo=new ReserveTypeInfo();
                    }
                    reserveTypeInfo.reserveType=reserveType;
                    reserveTypeInfo.restaurantId=restaurantId;
                    reserveTypeInfo.beginTime=beginTime;
                    reserveTypeInfo.endTime=endTime;

                    if(!reserveTypeInfo.save(flush: true)){
                        return [recode: ReCode.SAVE_FAILED,errors: reserveTypeInfo.errors.allErrors];
                    }
                }
                catch (Exception ex){//参数错误
                    return [recode: ReCode.ERROR_PARAMS];
                }
            }

            return [recode: ReCode.OK];

        }
        else{
            return [recode:ReCode.NOT_LOGIN];
        }
    }
}
