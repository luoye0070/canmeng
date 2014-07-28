package lj.data

import lj.enumCustom.DishesStatus
import lj.enumCustom.DishesValid
import lj.enumCustom.OrderType

class DishesForCartInfo {
    //购物车id
    long cartId;
    //菜单Id
    long foodId;
    //备注
    String remark;
    //份数
    int num=0;
    //单价
    double foodPrice=0d;

    /*******为了方便添加的冗余数据******/
    //菜名
    String foodName;
    //菜谱图片
    String foodImg;
    static constraints = {
        cartId(nullable:false,min: 1l)
        foodId(nullable:false,min: 1l)
        remark(nullable:true,blank: true,maxSize: 256);
        num(nullable:false,min: 0);
        foodPrice(nullable: false,min: 0d);
        foodName(nullable:true,blank: true);
        foodImg(nullable:true,blank: true, maxSize:128);
    }
}
