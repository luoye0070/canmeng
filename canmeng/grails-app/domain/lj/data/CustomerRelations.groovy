package lj.data

//客户关系表
class CustomerRelations {
    Long restaurantId=0;//饭店ID
    Long customerClientId=0;//客户用户ID
    String customerUserName;//客户用户名
    Integer type=0;//客户类型
    static constraints = {
        restaurantId(nullable: false);//饭店ID
        customerClientId(nullable: false);//用户ID
        customerUserName(nullable: true,blank: true,maxSize:32);//客户用户名
        type(nullable: false);//客户类型
    }
}
