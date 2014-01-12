package lj.cmc.string_analysis;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import com.google.gson.stream.JsonReader;

import lj.cmc.model.FoodInfo;
import lj.cmc.model.OrderInfo;

public class DataOfDishAnalyzeHelper extends AnalyzeHelper {
	
	public int recode=0;
	public String remsg="";
	public OrderInfo orderInfo=null;
	public ArrayList<FoodInfo> foodInfoList=null;
	public int totalCount=0;
	@Override
	public void analyze(String dataStr) {
		// TODO Auto-generated method stub
		/*��ʼ����*/
		System.out.println("dataStr:"+dataStr);	
		JsonReader jrd=new JsonReader(new StringReader(dataStr));
		try {
			jrd.beginObject();
			while(jrd.hasNext())
			{				
				String tagName=jrd.nextName();
				System.out.println(tagName+":");
				if(tagName.equals("recode"))
				{//���صĽ����
					jrd.beginObject();
					while(jrd.hasNext())
					{
						tagName=jrd.nextName();
						System.out.print(tagName+":");
						if(tagName.equals("code")){
							try{
								int i_rd=jrd.nextInt();
								System.out.println(i_rd);
								this.recode=i_rd;
							}
							catch(IllegalStateException e)
							{
								e.printStackTrace();
								jrd.nextNull();
							}
						}
						else if(tagName.equals("label")){
							try{
								String str=jrd.nextString();
								System.out.println(str);
								this.remsg=str;
							}
							catch(IllegalStateException e)
							{
								e.printStackTrace();
								jrd.nextNull();
							}
						}
						else{
							jrd.skipValue();
						}
					}
					jrd.endObject();
				}
				else if(tagName.equals("orderInfoInstance"))
				{//���ض�����Ϣ
//					if(this.recode==0)
//					{
						this.orderInfo=new OrderInfo();
						jrd.beginObject();
						while(jrd.hasNext())
						{
							tagName=jrd.nextName();
							System.out.print(tagName+":");							
							if(tagName.equals("id"))
							{//����ID
								try{
									long id=jrd.nextLong();
									System.out.println(id);
									this.orderInfo.id=id;
								}
								catch(IllegalStateException e)
								{
									e.printStackTrace();
									jrd.nextNull();
								}
							}
							else if(tagName.equals("partakeCode"))
							{//��˲�����֤��
								try{
									String str=jrd.nextString();
									System.out.println(str);
									this.orderInfo.partakeCode=str;
								}
								catch(IllegalStateException e)
								{
									e.printStackTrace();
									jrd.nextNull();
								}
							}					
							else
							{
								jrd.skipValue();
							}
						}
						jrd.endObject();
//					}
				}
				else if(tagName.equals("foodList")){
//					if(this.recode==0)
//					{
						this.foodInfoList=new ArrayList<FoodInfo>();
						jrd.beginArray();
						while(jrd.hasNext())
						{
//							if(this.recode==0)
//							{
								FoodInfo foodInfo=new FoodInfo();
								jrd.beginObject();
								while(jrd.hasNext())
								{
									tagName=jrd.nextName();
									System.out.print(tagName+":");
									if(tagName.equals("id"))
									{//ID
										try{
											long id=jrd.nextLong();
											System.out.println(id);
											foodInfo.id=id;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									else if(tagName.equals("restaurantId"))
									{//����ID
										try{
											long l_l=jrd.nextLong();
											System.out.println(l_l);
											foodInfo.restaurantId=l_l;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									else if(tagName.equals("name"))
									{//����
										try{
											String str=jrd.nextString();
											System.out.println(str);
											foodInfo.name=str;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}	
									else if(tagName.equals("price"))
									{//�۸�
										try{
											double d_d=jrd.nextDouble();
											System.out.println(d_d);
											foodInfo.price=d_d;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									else if(tagName.equals("originalPrice"))
									{//ԭ��
										try{
											double d_d=jrd.nextDouble();
											System.out.println(d_d);
											foodInfo.originalPrice=d_d;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									else if(tagName.equals("image"))
									{//ͼƬ
										try{
											String str=jrd.nextString();
											System.out.println(str);
											foodInfo.image=str;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}									
									else if(tagName.equals("canTakeOut"))
									{//�Ƿ�֧������
										try{
											boolean b_b=jrd.nextBoolean();
											System.out.println(b_b);
											foodInfo.canTakeOut=b_b;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}									
									else if(tagName.equals("description"))
									{//����
										try{
											String str=jrd.nextString();
											System.out.println(str);
											foodInfo.description=str;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									
									else if(tagName.equals("enabled"))
									{//�Ƿ���Ч
										try{
											boolean b_b=jrd.nextBoolean();
											System.out.println(b_b);
											foodInfo.enabled=b_b;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									else if(tagName.equals("countLimit"))
									{//ÿ������
										try{
											int i_i=jrd.nextInt();
											System.out.println(i_i);
											foodInfo.countLimit=i_i;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									else if(tagName.equals("isSetMeal"))
									{//�Ƿ��ײ�
										try{
											boolean b_b=jrd.nextBoolean();
											System.out.println(b_b);
											foodInfo.isSetMeal=b_b;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									else if(tagName.equals("sellCount"))
									{//��������
										try{
											int i_i=jrd.nextInt();
											System.out.println(i_i);
											foodInfo.sellCount=i_i;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									else if(tagName.equals("totalSellCount"))
									{//�ۼ�����
										try{
											int i_i=jrd.nextInt();
											System.out.println(i_i);
											foodInfo.totalSellCount=i_i;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									else
									{
										jrd.skipValue();
									}
								}
								jrd.endObject();
								this.foodInfoList.add(foodInfo);
//							}
						}
						jrd.endArray();
//					}					
				}
				else if(tagName.equals("totalCount")){
					try{
						int i_i=jrd.nextInt();
						System.out.println(i_i);
						this.totalCount=i_i;
					}
					catch(IllegalStateException e)
					{
						e.printStackTrace();
						jrd.nextNull();
					}
				}
				else
				{
					jrd.skipValue();
				}
			}
			jrd.endObject();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
