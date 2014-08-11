package lj.data

class CartInfo {
    //饭店ID
    long restaurantId=0;
    //用户ID
    long clientId=0;
    //会话标志，用于用户没有登录是创建的购物车
    String sessionMark;
    //日期
    Date date;
    //到店时间/s送餐时间
    Date time;
    //外卖地址
    long addressId=0;
    //备注
    String remark;
    //创建时间
    Date createTime=new Date();
    //总金额
    double totalAccount=0;
    //运费
    double postage;
    //实际总金额
    double realAccount;
    //工作人员
    long waiterId=0;
    /*******************为了方便加的冗余数据*****************/
    //饭店Name
    String restaurantName;

    static constraints = {
        restaurantId(nullable:false,min:1l);
        clientId(nullable:false,min:0l);
        sessionMark(nullable: true,maxSize: 32);
        date(nullable: true);
        time(nullable: true);
        addressId(nullable:false,min:0l);
        remark(nullable:true,maxSizes:256);
        createTime(nullable: true);
        totalAccount(nullable:true,min: 0d);
        postage(nullable:true,min: 0d);
        realAccount(nullable:true,min: 0d);
        waiterId(nullable:true,min: 0l);
        restaurantName(nullable:true,blank: true,maxSize:256);
    }

}
