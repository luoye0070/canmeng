package lj.data
import lj.enumCustom.*
//套餐
class SetMealInfo {

    //套餐描述菜单ID
    long host
    //从属菜单
    long followFoodId

    static constraints = {
        host(nullable:false,min: 1l)
        followFoodId(nullable:false,min: 1l)
    }
}
