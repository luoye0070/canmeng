package lj.data
import lj.enumCustom.*
//预定早中晚餐时间设置
class ReserveTypeInfo {

    //饭店ID
    long restaurantId
    //预订类型
    int reserveType
    //开始时间
    Date beginTime
    //结束时间
    Date endTime
    static constraints = {
        restaurantId(nullable:false,min:1l);
        reserveType(nullable: false,inList: [ReserveType.MORNING.code,ReserveType.NOON.code,ReserveType.NIGHT.code]);
        beginTime(nullable: false,blank:false);
        endTime(nullable: false,blank:false);
    }
}
