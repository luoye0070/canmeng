package lj.cmc.string_analysis;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.stream.JsonReader;

import lj.cmc.model.RestaurantInfo;

public class DataOfShopsAnalyzeHelper extends AnalyzeHelper {

	public ArrayList<RestaurantInfo> shopList=null;
	public int totalCount=0;
	@Override
	public void analyze(String dataStr) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		/*开始解析*/
		System.out.println("dataStr:"+dataStr);	
		JsonReader jrd=new JsonReader(new StringReader(dataStr));
		try {
			jrd.beginObject();
			while(jrd.hasNext())
			{				
				String tagName=jrd.nextName();
				System.out.println(tagName+":");
				if(tagName.equals("recode"))
				{//返回的结果码
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
				else if(tagName.equals("restaurantList")){
//					if(this.recode==0)
//					{
						this.shopList=new ArrayList<RestaurantInfo>();
						jrd.beginArray();
						while(jrd.hasNext())
						{
//							if(this.recode==0)
//							{
								RestaurantInfo restaurantInfo=new RestaurantInfo();
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
											restaurantInfo.id=id;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									else if(tagName.equals("name"))
									{//名称
										try{
											String str=jrd.nextString();
											System.out.println(str);
											restaurantInfo.name=str;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									else if(tagName.equals("image"))
									{//
										try{
											String str=jrd.nextString();
											System.out.println(str);
											restaurantInfo.image=str;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									else if(tagName.equals("areaId"))
									{//
										try{
											long l_l=jrd.nextLong();
											System.out.println(l_l);
											restaurantInfo.areaId=l_l;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									
									else if(tagName.equals("province"))
									{//
										try{
											String str=jrd.nextString();
											System.out.println(str);
											restaurantInfo.province=str;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									else if(tagName.equals("city"))
									{//
										try{
											String str=jrd.nextString();
											System.out.println(str);
											restaurantInfo.city=str;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									else if(tagName.equals("area"))
									{//
										try{
											String str=jrd.nextString();
											System.out.println(str);
											restaurantInfo.area=str;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									else if(tagName.equals("street"))
									{//
										try{
											String str=jrd.nextString();
											System.out.println(str);
											restaurantInfo.street=str;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									else if(tagName.equals("longitude"))
									{//
										try{
											double d_d=jrd.nextDouble();
											System.out.println(d_d);
											restaurantInfo.longitude=d_d;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									else if(tagName.equals("latitude"))
									{//
										try{
											double d_d=jrd.nextDouble();
											System.out.println(d_d);
											restaurantInfo.latitude=d_d;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									else if(tagName.equals("phone"))
									{//
										try{
											String str=jrd.nextString();
											System.out.println(str);
											restaurantInfo.phone=str;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									else if(tagName.equals("masterName"))
									{//
										try{
											String str=jrd.nextString();
											System.out.println(str);
											restaurantInfo.masterName=str;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									else if(tagName.equals("userId"))
									{//
										try{
											long l_l=jrd.nextLong();
											System.out.println(l_l);
											restaurantInfo.userId=l_l;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									else if(tagName.equals("shopHoursBeginTime"))
									{//
										try{
											Date d_d=null;
											try {
												d_d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(jrd.nextString());
											} catch (ParseException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											System.out.println(d_d);
											restaurantInfo.shopHoursBeginTime=d_d;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									else if(tagName.equals("shopHoursEndTime"))
									{//
										try{
											Date d_d=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(jrd.nextString());
											System.out.println(d_d);
											restaurantInfo.shopHoursEndTime=d_d;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										} catch (ParseException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
									else if(tagName.equals("enabled"))
									{//
										try{
											boolean b_b=jrd.nextBoolean();
											System.out.println(b_b);
											restaurantInfo.enabled=b_b;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									else if(tagName.equals("cuisineId"))
									{//
										try{
											long l_l=jrd.nextLong();
											System.out.println(l_l);
											restaurantInfo.cuisineId=l_l;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									else if(tagName.equals("freight"))
									{//
										try{
											double d_d=jrd.nextDouble();
											System.out.println(d_d);
											restaurantInfo.freight=d_d;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									else if(tagName.equals("freeFreight"))
									{//
										try{
											double d_d=jrd.nextDouble();
											System.out.println(d_d);
											restaurantInfo.freeFreight=d_d;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									else if(tagName.equals("packId"))
									{//
										try{
											long l_l=jrd.nextLong();
											System.out.println(l_l);
											restaurantInfo.packId=l_l;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									else if(tagName.equals("imageSpaceSize"))
									{//
										try{
											long l_l=jrd.nextLong();
											System.out.println(l_l);
											restaurantInfo.imageSpaceSize=l_l;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									else if(tagName.equals("imageSpaceUsedSize"))
									{//
										try{
											long l_l=jrd.nextLong();
											System.out.println(l_l);
											restaurantInfo.imageSpaceUsedSize=l_l;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									else if(tagName.equals("verifyStatus"))
									{//
										try{
											int i_i=jrd.nextInt();
											System.out.println(i_i);
											restaurantInfo.verifyStatus=i_i;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									else if(tagName.equals("deliverRange"))
									{//
										try{
											long l_l=jrd.nextLong();
											System.out.println(l_l);
											restaurantInfo.deliverRange=l_l;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									else if(tagName.equals("averageConsume"))
									{//
										try{
											double d_d=jrd.nextDouble();
											System.out.println(d_d);
											restaurantInfo.averageConsume=d_d;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									else if(tagName.equals("remark"))
									{//
										try{
											String str=jrd.nextString();
											System.out.println(str);
											restaurantInfo.remark=str;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									else if(tagName.equals("description"))
									{//
										try{
											String str=jrd.nextString();
											System.out.println(str);
											restaurantInfo.description=str;
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
								this.shopList.add(restaurantInfo);
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
