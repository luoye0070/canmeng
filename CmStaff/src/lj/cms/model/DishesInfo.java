package lj.cms.model;

import java.util.Date;

public class DishesInfo {
	//ID 
	public long id;
	 //订单ID
    public long orderId;
    //菜单Id
    public long foodId;
    //状态
    public int status=0;
    //有效性
    public int valid=0;
    //取消原因
    public String cancelReason;
    //备注
    public String remark;
    //店内编号
    public int numInRestaurant=0;
    //份数
    public int num=1;
    //厨师ID
    public long cookId;
    //单价
    public Double foodPrice=0d;

    /*******为了方便添加的冗余数据******/
    //饭店ID
    public long restaurantId=0;
    //菜名
    public String foodName;
  //用餐日期
    public Date date;
    //到店时间/s送餐时间
    public Date time;
    //菜谱图片
    public String foodImg;
}
