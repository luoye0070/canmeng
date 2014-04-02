package lj.data

//菜单收藏
class FoodCollectInfo {

    //菜单ID
    long foodId
    //用户ID
    long userId
    //收藏时间
    Date collectTime=new Date();
    static constraints = {
        foodId(nullable:false,min: 1l);
        userId(nullable:false,min:1l);
        collectTime(nullable:false);
    }
}
