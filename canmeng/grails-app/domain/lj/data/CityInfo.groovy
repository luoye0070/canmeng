package lj.data

//城市信息
class CityInfo {

    //所属省份ID
    long provinceId
    //城市名称
    String city

    static constraints = {
        provinceId(nullable:false,min: 1l);
        city blank:false,maxSize:32
    }
}
