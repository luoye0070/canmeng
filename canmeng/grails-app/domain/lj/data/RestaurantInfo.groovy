package lj.data

import lj.common.StrCheckUtil

//饭店信息
class RestaurantInfo {

    //店名
    String name
    //招牌图片
    String image
    //区号
    Long areaId
    //省
    String province
    //市
    String city
    //区
    String area
    //街道
    String street
    //经度
    Double longitude=0.0
    //维度
    Double latitude=0.0
    //电话
    String phone
    //店主姓名
    String masterName
    //用户id
    long userId
    //营业时间起
    Date shopHoursBeginTime
    //营业时间止
    Date shopHoursEndTime
    //开启关闭状态true开启，false关闭
    Boolean enabled=true
    //进店吃饭是否必须扫描二维码true是，false否
//    Boolean need2Code=true
    //菜系
    long cuisineId
    //外卖运费
    Double freight=0
    //外卖面运费条件（订单总金额达到多少面运费，0表示不免运费）
    Double freeFreight=0
    //外卖包装方式
    long packId=0
    //图片空间大小，初始大小为1G，单位字节
    long imageSpaceSize=1024*1024l*1024
    //图片空间已用大小，初始大小为0，单位字节
    long imageSpaceUsedSize=0l
    //审核状态
    Integer verifyStatus=0
    //外卖配送范围半斤,单位米
    Long deliverRange;
    //人均消费水平，单位元
    Double averageConsume;
    //备注，填写审核没有通过原因等
    String remark;
    //简单描述
    String description;
    //店铺url地址
    String url;

    /*************为了方便加的冗余数据*************/
    String cuisineName;
    String packName;

    static constraints = {
        name (nullable: false,blank:false,maxSize:256)
        image (nullable:true,blank:true,maxSize:128)
        areaId(nullable: false,min: 1l)
        province (nullable: false,blank:false,maxSize:32)
        city (nullable: false,blank:false,maxSize:32)
        area (nullable: false,blank:false,maxSize:32)
        street(nullable: false,blank:false,maxSize:256)
        longitude (nullable:true,blank:true);
        latitude (nullable:true,blank:true);
        phone (bullable:false,blank:false,maxSize:16, validator: {
            if (it) {
                if (!StrCheckUtil.chkStrFormat(it, "phone")) {
                    return ["formatError"]
                }
            }
        });
        masterName (nullable: false,blank:false,maxSize:32)
        userId(nullable:false,blank:false,min: 1l);
        shopHoursBeginTime(nullable:false,blank:false)
        shopHoursEndTime(nullable:false,blank:false)
        enabled(nullable:false,blank:false);
//        need2Code()
        cuisineId(nullable:false,blank:false,min: 1l)
        freight(nullable: true,min: 0d);
        freeFreight(nullable: true,min: 0d);
        packId(nullable: true,min: 0l);
        imageSpaceSize(nullable: false,min: 0l);
        imageSpaceUsedSize(nullable: false,min: 0l);
        verifyStatus(nullable: false,min: 0)
        deliverRange(nullable: true,min: 0l);
        averageConsume(nullable: true,min: 0d);
        remark(nullable:true,blank:true,maxSize:128);
        description (nullable:true,blank: true,maxSize:1024*128);
        url(nullable:true,blank: true,maxSize:512);
        cuisineName(nullable: true,blank: true,maxSize: 16);
        packName(nullable: true,blank: true,maxSize: 16);
    }
}
