package lj.cmc.model;

public class TableInfo {
	public long id;
	//����ID
    public long restaurantId;
    //����
    public String name;
    //��������
    public int minPeople;
    //�������
    public int maxPeople;
    //�Ƿ�൥����
    public boolean canMultiOrder=false;
    //�Ƿ�֧��Ԥ��
    public boolean canReserve=true;
    //����
    public String description;
    //�Ƿ���Ч
    public boolean enabled=true;
}
