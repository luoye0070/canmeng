package lj.data

import lj.enumCustom.DishesStatus
import lj.enumCustom.DishesValid

//点菜
class DishesInfo {

    //订单ID
    long orderId
    //菜单Id
    long foodId
    //状态
    int status=0;
    //有效性
    int valid=0;
    //取消原因
    String cancelReason
    //备注
    String remark
    //店内编号
    int numInRestaurant=0
    //份数
    int num=1
    //厨师ID
    long cookId
    //单价
    Double foodPrice=0d;

    /*******为了方便添加的冗余数据******/
    //饭店ID
    long restaurantId=0;
    //菜名
    String foodName;
    //用餐日期
    Date date;
    //到店时间/s送餐时间
    Date time;
    //菜谱图片
    String foodImg;
    //桌位
    String tableName;

    static constraints = {
        orderId(nullable:false,min: 1l)
        foodId(nullable:false,min: 1l)
        status(nullable:false,inList:DishesStatus.codeList)
        valid(nullable:false,inList:DishesValid.codeList)
        cancelReason(nullable:true,blank: true,maxSize: 128);
        remark(nullable:true,blank: true,maxSize: 256);
        numInRestaurant(nullable:false,min: 0);
        num(nullable:false,min: 1);
        cookId(nullable:true,min: 0l)
        foodPrice(nullable: false,min: 0d);
        restaurantId(nullable:false,min:1l);
        foodName(nullable:true,blank: true);
        date(nullable: false);
        time(nullable: false);
        foodImg(nullable:true,blank: true, maxSize:128);
        tableName(nullable:true,blank: true,maxSize: 64);
    }


    @Override
    public java.lang.String toString() {
        return "DishesInfo{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", foodId=" + foodId +
                ", status=" + status +
                ", valid=" + valid +
                ", cancelReason='" + cancelReason + '\'' +
                ", remark='" + remark + '\'' +
                ", numInRestaurant=" + numInRestaurant +
                ", num=" + num +
                ", cookId=" + cookId +
                ", foodPrice=" + foodPrice +
                ", restaurantId=" + restaurantId +
                ", foodName='" + foodName + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", foodImg='" + foodImg + '\'' +
                ", tableName='" + tableName + '\'' +
                ", version=" + version +
                '}';
    }
}
