package lj.data

//菜单收藏
class FoodCollectInfo {

    //菜单ID
    long foodId
    //用户ID
    long clientId
    //收藏时间
    Date collectTime=new Date();
    static constraints = {
        foodId(nullable:false,min: 1l);
        clientId(nullable:false,min:1l);
        collectTime(nullable:false);
    }
}
