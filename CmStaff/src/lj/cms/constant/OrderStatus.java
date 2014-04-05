package lj.cms.constant;

public enum OrderStatus {
	//��ʼ״̬,�����������
    ORIGINAL_STATUS(0,"��ʼ״̬"),
    //������
    ORDERED_STATUS(1,"������"),
    //ȷ�ϵ�����
    VERIFY_ORDERED_STATUS(2,"ȷ�ϵ�����"),
    //�ϲ����
    SERVED_STATUS(3,"�ϲ����"),
    //������
    SHIPPING_STATUS(4,"������"),
    //�������
    CHECKOUTED_STATUS(5,"�������"),
    //�������
    APPRAISED_STATUS(6,"�������");

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
                return "δ֪״̬";
        }
    }
}
