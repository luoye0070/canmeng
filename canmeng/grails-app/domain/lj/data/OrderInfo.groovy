package lj.data

import lj.common.StrCheckUtil
import lj.enumCustom.OrderStatus
import lj.enumCustom.OrderType
import lj.enumCustom.OrderValid

//订单信息
class OrderInfo {
    //饭店ID
    long restaurantId
    //用户ID
    long clientId
    //桌位
    long tableId
    //日期
    Date date
    //到店时间/s送餐时间
    Date time
    //预定类别
    int reserveType
    //订单状态
    Integer status=0
    //有效性
    Integer valid=0
    //饭店取消原因
    String cancelReason
    //外卖地址
    long addressId
    //跟进服务员ID
    long waiterId
    //接收消息服务员ID
    long listenWaiterId
    //收银员ID
    long cashierId
    //备注
    String remark
    //店内编号
    Integer numInRestaurant
    //流水号
    long orderNum
    //订单参与验证码
    String partakeCode
    //总金额
    Double totalAccount;
    //运费
    Double postage;
    //实收金额
    Double realAccount;
    //用餐人数
    int personCount;
    //创建时间
    Date createTime=new Date();
    //联系电话
    String phone;
    //联系人
    String customerName;
    //订单类型
    int orderType=0;
    //顾客到店,预定类型订单，当顾客到店后设置为true,到店用餐类型订单初始为true，这时所点菜才显示在厨师的终端，可以开始做菜
    boolean reachRestaurant=false;
    /*******************为了方便加的冗余数据*****************/
    //饭店Name
    String restaurantName;
    //用户ID
    String userName;
    //桌位
    String tableName;

    static constraints = {
        restaurantId(nullable:false,min:1l);
        clientId(nullable:false,min:0l)
        tableId(nullable:false,min:0l)
        date(nullable: false)
        time(nullable: false)
        reserveType(nullable:false,min: 0)
        status inList: OrderStatus.getCodeList()
        valid  inList: OrderValid.getCodeList()
        cancelReason nullable:true,maxSize:128
        addressId(nullable:false,min:0l)
        waiterId nullable:true
        listenWaiterId nullable:true
        cashierId nullable:true
        remark nullable:true,maxSizes:256
        numInRestaurant(nullable: false,min: 0)
        orderNum(nullable:false,unique: true)
        partakeCode(nullable:false,maxSize: 4)
        totalAccount(nullable:true)
        postage(nullable:true)
        realAccount(nullable:true)
        personCount(nullable:true);
        createTime(nullable: true);
        phone(nullable: true,blank: true,maxSize: 16 ,validator: {
            if (it) {
                if (!StrCheckUtil.chkStrFormat(it, "phone")) {
                    return ["formatError"]
                }
            }
        });
        customerName(nullable: true,blank: true,maxSize: 128);
        orderType(nullable:false,inList: OrderType.getCodeList());
        reachRestaurant(nullable:false);
        restaurantName(nullable:true,blank: true,maxSize:256);
        userName(nullable:true,blank: true,maxSize: 32);
        tableName(nullable:true,blank: true,maxSize: 64);
    }

}
