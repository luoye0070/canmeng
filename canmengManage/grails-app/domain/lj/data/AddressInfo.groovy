package lj.data

//地址信息
class AddressInfo {

    //用户ID
    long userId
    //区号
    long areaId
    //省
    String province
    //市
    String city
    //区
    String area
    //街道
    String street
    //经度
    double longitude =0.0
    //维度
    double latitude =0.0
    //手机号码
    String phone
    //联系人
    String linkManName

    static constraints = {
        userId()
        areaId()
        province nullable:true,maxSize:32
        city nullable:true,maxSize:32
        area nullable:true,maxSize:32
        street nullable:true,maxSize:32
        longitude nullable:true
        latitude nullable:true
        phone nullable:true,maxSize:16
        linkManName nullable:true,maxSize:32
    }
}
