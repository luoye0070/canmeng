package lj.cmc.model;

public class FoodInfo {
	
	public long id;//ID
	
    public long restaurantId;//����ID

    public String name;//����

    public Double price=0.0;//�۸�
 
    public Double originalPrice;//ԭ��

    public String image;//ͼƬ

    public Boolean canTakeOut=false;//�Ƿ�֧������true֧��false��֧��

    public String description;//����

    public Boolean enabled=true;//״̬����Ч�ԣ�true��Чfalse��Ч

    public Integer countLimit=0;//ÿ������0��ʾ������

    public Boolean isSetMeal=false;//�Ƿ��ײ�

    public int sellCount=0;//��������

    public int totalSellCount=0;//�ۼ�����
}
