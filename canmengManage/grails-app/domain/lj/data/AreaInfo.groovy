package lj.data

//区块信息
class AreaInfo {

    //所属城市id
    long cityId
    //区域名称
    String area


    static constraints = {
        cityId(nullable:false,min: 1l);
        area blank:false,maxSize:32
    }
}
