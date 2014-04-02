package lj.data

import lj.common.StrCheckUtil
import lj.enumCustom.OrderStatus
import lj.enumCustom.OrderValid

//订单信息
class OrderInfo {
    //饭店ID
    long restaurantId
    //用户ID
    long userId
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

    /*******************为了方便加的冗余数据*****************/
    //饭店Name
    String restaurantName;
    //用户ID
    String userName;
    //桌位
    String tableName;

    static constraints = {
        restaurantId(nullable:false,min:1l);
        userId(nullable:false,min:0l)
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
        restaurantName(nullable:true,blank: true);
        userName(nullable:true,blank: true);
        tableName(nullable:true,blank: true);
    }

}
