package lj.cmc.constant;

public enum DishesValid {
	//��ʼ״̬����ȷ������Ч��
    ORIGINAL_VALID(0,"��ʼ״̬"),
    //��Ч
    EFFECTIVE_VALID(1,"��Ч"),
    //�û�ȡ��
    USER_CANCEL_VALID(2,"�û�ȡ��"),
    //�����ڶ���ȷ��ǰȡ��
    RESTAURANT_BEFORE_VERIFYED_CANCEL_VALID(3,"�����ڶ���ȷ��ǰȡ��"),
    //�����ڶ���ȷ�Ϻ�ȡ��
    RESTAURANT_AFTER_VERIFYED_CANCEL_VALID(4,"�����ڶ���ȷ�Ϻ�ȡ��");
    public Integer code;
    public String label;
    DishesValid(Integer code,String label){
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
            return  RESTAURANT_BEFORE_VERIFYED_CANCEL_VALID.label;
        case 4:
            return  RESTAURANT_AFTER_VERIFYED_CANCEL_VALID.label;
            default:
                return "δ֪��Ч��";
        }
    }
}
