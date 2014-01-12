package lj.cms.model;

import java.util.Date;

public class RestaurantInfo {
	public long id;
	//店名
	public String name;
    //招牌图片
	public String image;
    //区号
	public Long areaId;
    //省
	public String province;
    //市
	public String city;
    //区
	public String area;
    //街道
	public String street;
    //经度
	public double longitude;
    //维度
	public double latitude;
    //电话
	public String phone;
    //店主姓名
	public String masterName;
    //用户id
	public long userId;
    //营业时间起
	public Date shopHoursBeginTime;
    //营业时间止
	public Date shopHoursEndTime;
    //开启关闭状态true开启，false关闭
	public boolean enabled;
    //菜系
	public long cuisineId;
    //外卖运费
	public double freight;
    //外卖面运费条件（订单总金额达到多少面运费，0表示不免运费）
	public double freeFreight;
    //外卖包装方式
	public long packId;
    //图片空间大小，初始大小为1G，单位字节
	public long imageSpaceSize;
    //图片空间已用大小，初始大小为0，单位字节
	public long imageSpaceUsedSize;
    //审核状态
	public int verifyStatus;
    //外卖配送范围半斤,单位米
	public long deliverRange;
    //人均消费水平，单位元
	public double averageConsume;
    //备注，填写审核没有通过原因等
	public String remark;
    //简单描述
	public String description;
}
