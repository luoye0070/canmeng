package lj.data

//认证
class AuthenticationInfo {

    //饭店ID
    long restaurantId
    //描述
    String description
    //证件图片
    String image
    //证件类型ID
    long papersId
    //审核状态
    int verifyStatus=0
    //备注
    String remark

    static constraints = {
        restaurantId(nullable:false,min: 1l);
        description (nullable:true,blank: true, maxSize:256);
        image (nullable:false,blank:false,maxSize:128);
        papersId(nullable:false,min: 1l);
        verifyStatus(nullable:false,min: 0);
        remark(nullable:true,blank: true, maxSize:256);
    }
}
