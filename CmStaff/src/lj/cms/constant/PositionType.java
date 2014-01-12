package lj.cms.constant;

import java.util.ArrayList;

public enum PositionType {
	SHOPKEEPER(0,"店主"),
    WAITER(1,"服务员"),
    WAITER_HEADER(2,"服务员总管"),
    COOK(3,"厨师"),
    BAD_TYPE(4,"收银员") ,
    CLEANER(5,"清洁工");

    public Integer code;
    public String label;
    PositionType(Integer code,String label){
        this.code=code;
        this.label=label;
    }

    public static PositionType getPositionType(int code){
        switch (code){
            case 0:
                return SHOPKEEPER;
            case 1:
                return WAITER;
            case 2:
                return WAITER_HEADER;
            case 3:
                return COOK;
            case 4:
                return BAD_TYPE;
            case 5:
                return CLEANER;
            default:
                return WAITER;
        }
    }

    public static PositionType [] positionTypes={WAITER,WAITER_HEADER,COOK,BAD_TYPE,CLEANER};
    public static boolean isInList(int code,ArrayList<Integer> codeArray){
    	if(codeArray==null){
    		return false;
    	}
    	for(int c : codeArray){
    		if(c==code){
    			return true;
    		}
    	}
    	return false;
    }
}
