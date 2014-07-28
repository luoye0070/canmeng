package lj.data

class FoodClassInfo {
//饭店ID
    long restaurantId
    //名称
    String name
    //描述
    String description

    static constraints = {
        restaurantId(nullable:false,min:1l);
        name(nullable:false,blank:false,maxSize:16);
        description(nullable:true,blank:true,maxSize:256);
    }
}
