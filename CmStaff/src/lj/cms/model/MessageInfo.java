package lj.cms.model;

import java.util.Date;

public class MessageInfo {
	@Override
	public String toString() {
		return "MessageInfo [id=" + id + ", orderId=" + orderId + ", type="
				+ type + ", status=" + status + ", receiveId=" + receiveId
				+ ", sendId=" + sendId + ", content=" + content + ", recTime="
				+ recTime + ", sendType=" + sendType + ", sendTime=" + sendTime
				+ "]";
	}
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
    
    
}
