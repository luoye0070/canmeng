package lj.cmc.constant;

public enum OrderValid {
	//��ʼ״̬����ȷ������Ч��
    ORIGINAL_VALID(0,"��ʼ״̬"),
    //��Ч
    EFFECTIVE_VALID(1,"��Ч"),
    //�û�ȡ��
    USER_CANCEL_VALID(2,"�û�ȡ��"),
    //����ȡ��
    RESTAURANT_CANCEL_VALID(3,"����ȡ��");
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
                return "δ֪��Ч��";
        }
    }
}
