package lj.cmc.constant;

public enum OrderValid {
	//初始状态，待确认其有效性
    ORIGINAL_VALID(0,"初始状态"),
    //有效
    EFFECTIVE_VALID(1,"有效"),
    //用户取消
    USER_CANCEL_VALID(2,"用户取消"),
    //饭店取消
    RESTAURANT_CANCEL_VALID(3,"饭店取消");
    public Integer code;
    public String label;
    OrderValid(Integer code,String label){
        this.code=code;
        this.label=label;
    }
    public static String getLable(int code){
        switch (code){
        case 0:
            return  ORIGINAL_VALID.label;
        case 1:
            return  EFFECTIVE_VALID.label;
        case 2:
            return  USER_CANCEL_VALID.label;
        case 3:
            return  RESTAURANT_CANCEL_VALID.label;
        default:
                return "未知有效性";
        }
    }
}
