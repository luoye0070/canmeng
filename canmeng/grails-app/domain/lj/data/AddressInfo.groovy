package lj.data

//地址信息
class AddressInfo {

    //用户ID
    long clientId=0;
    //区号
    long areaId=0;
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
        clientId(nullable:false,min: 0l);
        areaId(nullable:false,min: 0l);
        province(nullable:false,blank: false, maxSize:32);
        city(nullable:false,blank: false,maxSize:32);
        area(nullable:false,blank: false,maxSize:32);
        street(nullable:false,blank: false,maxSize:32);
        longitude nullable:true
        latitude nullable:true
        phone(nullable:false,blank: false,maxSize:16);
        linkManName(nullable:false,blank: false,maxSize:32);
    }
    def beforeInsert() {
        trimString();
    }

    def beforeUpdate() {
        trimString();
    }
    protected void trimString() {
        //去掉空格
        if(linkManName)
            linkManName=linkManName.replaceAll("\\s*","");
        //println("linkManName-->"+linkManName);
        if(phone)
            phone=phone.replaceAll("\\s*","");
        if(province)
            province=province.replaceAll("\\s*","");
        if(city)
            city=city.replaceAll("\\s*","");
        if(area)
            area=area.replaceAll("\\s*","");
        if(street)
            street=street.replaceAll("\\s*","");
    }
}
