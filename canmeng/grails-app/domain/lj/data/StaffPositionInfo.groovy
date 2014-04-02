package lj.data

//员工职位
class StaffPositionInfo {

    //饭店ID,为了方便加的冗余数据
    long restaurantId
    //员工
    long staffId
    //职位
    int positionType

    static constraints = {
        restaurantId(nullable:false,blank:false,min: 1l);
        staffId(nullable:false,min:1l);
        positionType(nullable:false,min:0);
    }
}
