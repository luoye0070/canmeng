package lj.cms.model;

import java.util.Date;

public class OrderInfo {
	@Override
	public String toString() {
		return "OrderInfo [id=" + id + ", restaurantId=" + restaurantId
				+ ", userId=" + userId + ", tableId=" + tableId + ", date="
				+ date + ", time=" + time + ", reserveType=" + reserveType
				+ ", status=" + status + ", valid=" + valid + ", cancelReason="
				+ cancelReason + ", addressId=" + addressId + ", waiterId="
				+ waiterId + ", listenWaiterId=" + listenWaiterId
				+ ", cashierId=" + cashierId + ", remark=" + remark
				+ ", numInRestaurant=" + numInRestaurant + ", orderNum="
				+ orderNum + ", partakeCode=" + partakeCode + ", totalAccount="
				+ totalAccount + ", postage=" + postage + ", realAccount="
				+ realAccount + ", personCount=" + personCount + "]";
	}
	//订单ID
	public long id;
	//饭店ID
    public long restaurantId;
    //用户ID
    public long userId;
    //桌位
    public long tableId;
    //日期
    public Date date;
    //到店时间/s送餐时间
    public Date time;
    //预定类别
    public int reserveType;
    //订单状态
    public Integer status=0;
    //有效性
    public Integer valid=0;
    //饭店取消原因
    public String cancelReason;
    //外卖地址
    public long addressId;
    //跟进服务员ID
    public long waiterId;
    //接收消息服务员ID
    public long listenWaiterId;
    //收银员ID
    public long cashierId;
    //备注
    public String remark;
    //店内编号
    public Integer numInRestaurant;
    //流水号
    public long orderNum;
    //订单参与验证码
    public String partakeCode;
    //总金额
    public Double totalAccount;
    //运费
    public Double postage;
    //实收金额
    public Double realAccount;
    //用餐人数
    public int personCount;
    
}
