package lj.data

import lj.enumCustom.ClientType

class ClientInfo {
    String phoneNum;//手机号
    String sn;//设备序号
    long userId;//用户ID
    String userName;//用户名
    String otherMark;//其他客户端标示
    long defaultAddrId; //默认地址id
    int clientType;//客户类型
    static constraints = {
        phoneNum(nullable: true,blank: true,maxSize:32);
        sn(nullable: true,blank: true,maxSize:32);
        userId(nullable: true,min: 0l);
        userName(nullable: true,blank: true,maxSize:32);
        otherMark(nullable: true,blank: true,maxSize:64);
        defaultAddrId(nullable:true,min: 0l);
        clientType(nullable:false,inList: ClientType.getCodeList());
    }

}
