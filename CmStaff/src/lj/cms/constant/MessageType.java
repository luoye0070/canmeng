package lj.cms.constant;

public enum MessageType {
	ORDER_HANDLE_TYPE(0,"��������"),
    CHECKOUT_REQUEST_TYPE(1,"��������"),
    TEAWATER_TYPE(2,"��ˮ"),
    OTHER_TYPE(3,"����"),
    PACKAGE(4,"���"),
    CLEAR_AWAY(5,"������ʰ");
    public Integer code;
    public String label;
    MessageType(Integer code,String label){
        this.code=code;
        this.label=label;
    }
}
