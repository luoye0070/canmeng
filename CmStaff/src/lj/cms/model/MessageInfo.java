package lj.cms.model;

import java.util.Date;

public class MessageInfo {
	//id
	public long id;
	//订单ID
    public long orderId;
    //类型
    public int type=0;
    //状态
    public int status=0;
    //接受方ID
    public long receiveId;
    //发送方ID
    public long sendId;
    //内容
    public String content;
    //预定接收时间
    public Date recTime;
    //发送方向
    public int sendType=0;
    //发送时间
    public Date sendTime;
    //饭店ID
    public long restaurantId;
}
