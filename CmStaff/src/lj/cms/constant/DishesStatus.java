package lj.cms.constant;

public enum DishesStatus {
	//��ʼ״̬,�����������
    ORIGINAL_STATUS(0,"��ʼ״̬"),
    //ȷ�����
    VERIFYING_STATUS(1,"ȷ�����"),
    //������
    COOKING_ORDERED_STATUS(2,"������"),
    //�������
    COOKED_STATUS(3,"�������"),
    //�ϲ����
    SERVED_STATUS(4,"�ϲ����");

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
                return "δ֪״̬";
        }
    }
}
