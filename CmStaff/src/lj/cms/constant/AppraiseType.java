package lj.cms.constant;

public enum AppraiseType {
	GOOD_TYPE(0,"∫√∆¿"),
    NOTGOODNOTBAD_TYPE(1,"÷–∆¿"),
    BAD_TYPE(2,"≤Ó∆¿");

    public Integer code;
    public String label;
    AppraiseType(Integer code,String label){
        this.code=code;
        this.label=label;
    }
}
