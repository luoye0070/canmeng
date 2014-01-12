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
	//����ID
	public long id;
	//����ID
    public long restaurantId;
    //�û�ID
    public long userId;
    //��λ
    public long tableId;
    //����
    public Date date;
    //����ʱ��/s�Ͳ�ʱ��
    public Date time;
    //Ԥ�����
    public int reserveType;
    //����״̬
    public Integer status=0;
    //��Ч��
    public Integer valid=0;
    //����ȡ��ԭ��
    public String cancelReason;
    //������ַ
    public long addressId;
    //��������ԱID
    public long waiterId;
    //������Ϣ����ԱID
    public long listenWaiterId;
    //����ԱID
    public long cashierId;
    //��ע
    public String remark;
    //���ڱ��
    public Integer numInRestaurant;
    //��ˮ��
    public long orderNum;
    //����������֤��
    public String partakeCode;
    //�ܽ��
    public Double totalAccount;
    //�˷�
    public Double postage;
    //ʵ�ս��
    public Double realAccount;
    //�ò�����
    public int personCount;
    
}
