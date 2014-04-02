package lj.enumCustom

public enum AppraiseType {

    GOOD_TYPE(0,'好评'),
    NOTGOODNOTBAD_TYPE(1,'中评'),
    BAD_TYPE(2,'差评')

    public Integer code
    public String label
    AppraiseType(Integer code,String label){
        this.code=code
        this.label=label
    }

    public static def getCodeList(){
        return [
                GOOD_TYPE.code,
                NOTGOODNOTBAD_TYPE.code,
                BAD_TYPE.code
        ];
    }

    public static AppraiseType[] appraiseTypes=[GOOD_TYPE,NOTGOODNOTBAD_TYPE,BAD_TYPE];
}
