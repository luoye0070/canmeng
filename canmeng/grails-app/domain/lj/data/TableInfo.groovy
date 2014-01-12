package lj.data

//桌位
class TableInfo {

    //饭店ID
    long restaurantId
    //桌名
    String name
    //最少人数
    Integer minPeople;
    //最大人数
    Integer maxPeople;
    //是否多单共桌
    Boolean canMultiOrder=false;
    //是否支持预订
    Boolean canReserve=true;
    //描述
    String description
    //是否有效
    Boolean enabled=true;

    static constraints = {
        restaurantId(nullable:false,min: 1l);
        name(nullable:false,blank:false,maxSize:32);
        minPeople(nullable:false,min:1);
        maxPeople(nullable:false,min:1);
        canMultiOrder(nullable: false);
        canReserve(nullable: false);
        description(nullable:true,blank:true,maxSize:1024);
        enabled(nullable: false)
    }
}
