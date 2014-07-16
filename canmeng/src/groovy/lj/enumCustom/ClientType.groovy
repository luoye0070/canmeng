package lj.enumCustom

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-7-4
 * Time: 上午1:02
 * To change this template use File | Settings | File Templates.
 */
public enum ClientType {
    PHONE(0,"手机登手持设备"),
    WEB_SITE(1,"网站"),
    WEIXIN(2,"微信");

    public int code
    public String label

    ClientType(int code,String label){
        this.code=code
        this.label=label
    }

    public static def getCodeList(){
        return [
                PHONE.code,
                WEB_SITE.code,
                WEIXIN.code
        ];
    }
}