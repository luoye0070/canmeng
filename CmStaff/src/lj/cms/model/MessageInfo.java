package lj.cms.model;

import java.util.Date;

public class MessageInfo {
	//id
	public long id;
	//����ID
    public long orderId;
    //����
    public int type=0;
    //״̬
    public int status=0;
    //���ܷ�ID
    public long receiveId;
    //���ͷ�ID
    public long sendId;
    //����
    public String content;
    //Ԥ������ʱ��
    public Date recTime;
    //���ͷ���
    public int sendType=0;
    //����ʱ��
    public Date sendTime;
    //����ID
    public long restaurantId;
}
