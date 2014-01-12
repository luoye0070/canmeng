package lj.cms.constant;

public enum DishesStatus {
	//初始状态,订单创建完成
    ORIGINAL_STATUS(0,"初始状态"),
    //确认完成
    VERIFYING_STATUS(1,"确认完成"),
    //做菜中
    COOKING_ORDERED_STATUS(2,"做菜中"),
    //做菜完成
    COOKED_STATUS(3,"做菜完成"),
    //上菜完成
    SERVED_STATUS(4,"上菜完成");

    public int code;
    public String label;
    DishesStatus(int code,String label){
        this.code=code;
        this.label=label;
    }
    public static String getLable(int code){
        switch (code){
        case 0:
            return  ORIGINAL_STATUS.label;
        case 1:
            return  VERIFYING_STATUS.label;
        case 2:
            return  COOKING_ORDERED_STATUS.label;
        case 3:
            return  COOKED_STATUS.label;
        case 4:
            return  SERVED_STATUS.label;
            default:
                return "未知状态";
        }
    }
}
