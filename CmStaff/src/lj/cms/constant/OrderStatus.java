package lj.cms.constant;

public enum OrderStatus {
	//初始状态,订单创建完成
    ORIGINAL_STATUS(0,"初始状态"),
    //点菜完成
    ORDERED_STATUS(1,"点菜完成"),
    //确认点菜完成
    VERIFY_ORDERED_STATUS(2,"确认点菜完成"),
    //上菜完成
    SERVED_STATUS(3,"上菜完成"),
    //运送中
    SHIPPING_STATUS(4,"运送中"),
    //结账完成
    CHECKOUTED_STATUS(5,"结账完成"),
    //评价完成
    APPRAISED_STATUS(6,"评价完成");

    public Integer code;
    public String label;
    OrderStatus(Integer code,String label){
        this.code=code;
        this.label=label;
    }
    public static String getLable(int code){
        switch (code){
        case 0:
            return  ORIGINAL_STATUS.label;
        case 1:
            return  ORDERED_STATUS.label;
        case 2:
            return  VERIFY_ORDERED_STATUS.label;
        case 3:
            return  SERVED_STATUS.label;
        case 4:
            return  SHIPPING_STATUS.label;
        case 5:
            return  CHECKOUTED_STATUS.label;
        case 6:
            return  APPRAISED_STATUS.label;
            default:
                return "未知状态";
        }
    }
}
