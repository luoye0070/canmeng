package lj.cms.model;

import java.util.Date;

public class DishesInfo {
	//ID 
	public long id;
	 //����ID
    public long orderId;
    //�˵�Id
    public long foodId;
    //״̬
    public int status=0;
    //��Ч��
    public int valid=0;
    //ȡ��ԭ��
    public String cancelReason;
    //��ע
    public String remark;
    //���ڱ��
    public int numInRestaurant=0;
    //����
    public int num=1;
    //��ʦID
    public long cookId;
    //����
    public Double foodPrice=0d;

    /*******Ϊ�˷�����ӵ���������******/
    //����ID
    public long restaurantId=0;
    //����
    public String foodName;
  //�ò�����
    public Date date;
    //����ʱ��/s�Ͳ�ʱ��
    public Date time;
    //����ͼƬ
    public String foodImg;
}
