package lj.cms.model;

import java.util.Date;

public class RestaurantInfo {
	public long id;
	//����
	public String name;
    //����ͼƬ
	public String image;
    //����
	public Long areaId;
    //ʡ
	public String province;
    //��
	public String city;
    //��
	public String area;
    //�ֵ�
	public String street;
    //����
	public double longitude;
    //ά��
	public double latitude;
    //�绰
	public String phone;
    //��������
	public String masterName;
    //�û�id
	public long userId;
    //Ӫҵʱ����
	public Date shopHoursBeginTime;
    //Ӫҵʱ��ֹ
	public Date shopHoursEndTime;
    //�����ر�״̬true������false�ر�
	public boolean enabled;
    //��ϵ
	public long cuisineId;
    //�����˷�
	public double freight;
    //�������˷������������ܽ��ﵽ�������˷ѣ�0��ʾ�����˷ѣ�
	public double freeFreight;
    //������װ��ʽ
	public long packId;
    //ͼƬ�ռ��С����ʼ��СΪ1G����λ�ֽ�
	public long imageSpaceSize;
    //ͼƬ�ռ����ô�С����ʼ��СΪ0����λ�ֽ�
	public long imageSpaceUsedSize;
    //���״̬
	public int verifyStatus;
    //�������ͷ�Χ���,��λ��
	public long deliverRange;
    //�˾�����ˮƽ����λԪ
	public double averageConsume;
    //��ע����д���û��ͨ��ԭ���
	public String remark;
    //������
	public String description;
}
