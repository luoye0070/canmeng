package lj.cms.string_analysis;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.stream.JsonReader;

import lj.cms.model.OrderInfo;
import lj.cms.model.RestaurantCollectInfo;

public class DataOfCollectShopAnalyzeHelper extends AnalyzeHelper {
	public int totalCount=0;
	public ArrayList<RestaurantCollectInfo> restaurantCollectInfoList=null;
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
				else if(tagName.equals("collectList")){
//					if(this.recode==0)
//					{
						this.restaurantCollectInfoList=new ArrayList<RestaurantCollectInfo>();
						jrd.beginArray();
						while(jrd.hasNext())
						{
//							if(this.recode==0)
//							{
							RestaurantCollectInfo restaurantCollectInfo=new RestaurantCollectInfo();
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
											restaurantCollectInfo.id=id;
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
											restaurantCollectInfo.restaurantId=l_l;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									else if(tagName.equals("userId"))
									{//�û�ID
										try{
											long l_l=jrd.nextLong();
											System.out.println(l_l);
											restaurantCollectInfo.userId=l_l;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									else if(tagName.equals("collectTime"))
									{//�ղ�ʱ��
										try{
											Date d_d=null;
											try {
												d_d = new SimpleDateFormat().parse(jrd.nextString());
											} catch (ParseException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											System.out.println(d_d);
											restaurantCollectInfo.collectTime=d_d;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}																		
									else if(tagName.equals("name"))
									{//������
										try{
											String str=jrd.nextString();
											System.out.println(str);
											restaurantCollectInfo.name=str;
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
											restaurantCollectInfo.image=str;
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
								this.restaurantCollectInfoList.add(restaurantCollectInfo);
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
