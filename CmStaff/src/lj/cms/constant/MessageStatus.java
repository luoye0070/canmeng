package lj.cms.constant;

public enum MessageStatus {
	ORIGINAL_STATUS(0,"��ʼ״̬"),
    READED_STATUS(1,"�Ѷ�");

    public Integer code;
    public String label;
    MessageStatus(Integer code,String label){
        this.code=code;
        this.label=label;
    }
}
