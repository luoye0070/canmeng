package lj.data

//饭店收藏
class RestaurantCollectInfo {

    //饭店ID
    long restaurantId
    //用户ID
    long clientId
    //收藏时间
    Date collectTime=new Date();

    static constraints = {
        restaurantId(nullable:false,min: 1l);
        clientId(nullable:false,min:1l);
        collectTime(nullable:false);
    }
}
