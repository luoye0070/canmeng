package lj.cms.string_analysis;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import lj.cms.model.DishesInfo;
import lj.cms.model.OrderInfo;

import com.google.gson.stream.JsonReader;

public class DataOfDishListAnalyzeHelper extends AnalyzeHelper {

	public ArrayList<DishesInfo> dishesList=null;
	public int totalCount=0;
	@Override
	public void analyze(String dataStr) {
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
				else if(tagName.equals("dishList")){
//					if(this.recode==0)
//					{
						this.dishesList=new ArrayList<DishesInfo>();
						jrd.beginArray();
						while(jrd.hasNext())
						{
//							if(this.recode==0)
//							{
								DishesInfo dishesInfo=new DishesInfo();
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
											dishesInfo.id=id;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									else if(tagName.equals("orderId"))
									{//
										try{
											long l_l=jrd.nextLong();
											System.out.println(l_l);
											dishesInfo.orderId=l_l;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									else if(tagName.equals("foodId"))
									{//
										try{
											long l_l=jrd.nextLong();
											System.out.println(l_l);
											dishesInfo.foodId=l_l;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									else if(tagName.equals("status"))
									{//
										try{
											int i_i=jrd.nextInt();
											System.out.println(i_i);
											dishesInfo.status=i_i;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									else if(tagName.equals("valid"))
									{//
										try{
											int i_i=jrd.nextInt();
											System.out.println(i_i);
											dishesInfo.valid=i_i;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									else if(tagName.equals("cancelReason"))
									{//
										try{
											String str=jrd.nextString();
											System.out.println(str);
											dishesInfo.cancelReason=str;
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
											dishesInfo.remark=str;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}	
									else if(tagName.equals("numInRestaurant"))
									{//
										try{
											int i_i=jrd.nextInt();
											System.out.println(i_i);
											dishesInfo.numInRestaurant=i_i;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									else if(tagName.equals("num"))
									{//
										try{
											int i_i=jrd.nextInt();
											System.out.println(i_i);
											dishesInfo.num=i_i;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									else if(tagName.equals("cookId"))
									{//饭店ID
										try{
											long l_l=jrd.nextLong();
											System.out.println(l_l);
											dishesInfo.cookId=l_l;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									
									else if(tagName.equals("foodPrice"))
									{//价格
										try{
											double d_d=jrd.nextDouble();
											System.out.println(d_d);
											dishesInfo.foodPrice=d_d;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									
									else if(tagName.equals("restaurantId"))
									{//饭店ID
										try{
											long l_l=jrd.nextLong();
											System.out.println(l_l);
											dishesInfo.restaurantId=l_l;
										}
										catch(IllegalStateException e)
										{
											e.printStackTrace();
											jrd.nextNull();
										}
									}
									else if(tagName.equals("foodName"))
									{//菜名
										try{
											String str=jrd.nextString();
											System.out.println(str);
											dishesInfo.foodName=str;
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
								this.dishesList.add(dishesInfo);
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
				else if(tagName.equals("appraiseInfoInstance")){
					jrd.skipValue();//暂时不解析
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
