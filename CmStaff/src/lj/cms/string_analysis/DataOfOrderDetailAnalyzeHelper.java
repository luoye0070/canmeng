package lj.cms.string_analysis;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.stream.JsonReader;

import lj.cms.model.DishesInfo;
import lj.cms.model.FoodInfo;
import lj.cms.model.OrderInfo;

public class DataOfOrderDetailAnalyzeHelper extends AnalyzeHelper {

	public OrderInfo orderInfo=null;
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
				else if(tagName.equals("orderInfoInstance"))
				{//返回订单信息
//					if(this.recode==0)
//					{
						this.orderInfo=new OrderInfo();
						jrd.beginObject();
						while(jrd.hasNext())
						{
							tagName=jrd.nextName();
							System.out.print(tagName+":");							
							if(tagName.equals("id"))
							{//订单ID
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
							else if(tagName.equals("restaurantId"))
							{//
								try{
									long l_l=jrd.nextLong();
									System.out.println(l_l);
									this.orderInfo.restaurantId=l_l;
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
									this.orderInfo.userId=l_l;
								}
								catch(IllegalStateException e)
								{
									e.printStackTrace();
									jrd.nextNull();
								}
							}
							else if(tagName.equals("tableId"))
							{//
								try{
									long l_l=jrd.nextLong();
									System.out.println(l_l);
									this.orderInfo.tableId=l_l;
								}
								catch(IllegalStateException e)
								{
									e.printStackTrace();
									jrd.nextNull();
								}
							}
							else if(tagName.equals("date"))
							{//用餐日期
								try{
									Date d_d=null;
									try {
										d_d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(jrd.nextString());
									} catch (ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									System.out.println(d_d);
									this.orderInfo.date=d_d;
								}
								catch(IllegalStateException e)
								{
									e.printStackTrace();
									jrd.nextNull();
								}
							}
							else if(tagName.equals("time"))
							{//用餐时间
								try{
									Date d_d=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(jrd.nextString());
									System.out.println(d_d);
									this.orderInfo.time=d_d;
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
							else if(tagName.equals("reserveType"))
							{//预定类型
								try{
									int i_i=jrd.nextInt();
									System.out.println(i_i);
									this.orderInfo.reserveType=i_i;
								}
								catch(IllegalStateException e)
								{
									e.printStackTrace();
									jrd.nextNull();
								}
							}
							else if(tagName.equals("status"))
							{//状态
								try{
									int i_i=jrd.nextInt();
									System.out.println(i_i);
									this.orderInfo.status=i_i;
								}
								catch(IllegalStateException e)
								{
									e.printStackTrace();
									jrd.nextNull();
								}
							}
							else if(tagName.equals("valid"))
							{//有效性
								try{
									int i_i=jrd.nextInt();
									System.out.println(i_i);
									this.orderInfo.valid=i_i;
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
									this.orderInfo.cancelReason=str;
								}
								catch(IllegalStateException e)
								{
									e.printStackTrace();
									jrd.nextNull();
								}
							}	
							else if(tagName.equals("addressId"))
							{//
								try{
									long l_l=jrd.nextLong();
									System.out.println(l_l);
									this.orderInfo.addressId=l_l;
								}
								catch(IllegalStateException e)
								{
									e.printStackTrace();
									jrd.nextNull();
								}
							}
							else if(tagName.equals("waiterId"))
							{//
								try{
									long l_l=jrd.nextLong();
									System.out.println(l_l);
									this.orderInfo.waiterId=l_l;
								}
								catch(IllegalStateException e)
								{
									e.printStackTrace();
									jrd.nextNull();
								}
							}
							else if(tagName.equals("listenWaiterId"))
							{//
								try{
									long l_l=jrd.nextLong();
									System.out.println(l_l);
									this.orderInfo.listenWaiterId=l_l;
								}
								catch(IllegalStateException e)
								{
									e.printStackTrace();
									jrd.nextNull();
								}
							}
							else if(tagName.equals("cashierId"))
							{//
								try{
									long l_l=jrd.nextLong();
									System.out.println(l_l);
									this.orderInfo.cashierId=l_l;
								}
								catch(IllegalStateException e)
								{
									e.printStackTrace();
									jrd.nextNull();
								}
							}
							else if(tagName.equals("remark"))
							{//备注
								try{
									String str=jrd.nextString();
									System.out.println(str);
									this.orderInfo.remark=str;
								}
								catch(IllegalStateException e)
								{
									e.printStackTrace();
									jrd.nextNull();
								}
							}	
							else if(tagName.equals("numInRestaurant"))
							{//店内编号
								try{
									int i_i=jrd.nextInt();
									System.out.println(i_i);
									this.orderInfo.numInRestaurant=i_i;
								}
								catch(IllegalStateException e)
								{
									e.printStackTrace();
									jrd.nextNull();
								}
							}
							else if(tagName.equals("orderNum"))
							{//
								try{
									long l_l=jrd.nextLong();
									System.out.println(l_l);
									this.orderInfo.orderNum=l_l;
								}
								catch(IllegalStateException e)
								{
									e.printStackTrace();
									jrd.nextNull();
								}
							}
							
							else if(tagName.equals("partakeCode"))
							{//点菜参与验证码
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
							else if(tagName.equals("totalAccount"))
							{//
								try{
									double d_d=jrd.nextDouble();
									System.out.println(d_d);
									this.orderInfo.totalAccount=d_d;
								}
								catch(IllegalStateException e)
								{
									e.printStackTrace();
									jrd.nextNull();
								}
							}
							else if(tagName.equals("postage"))
							{//
								try{
									double d_d=jrd.nextDouble();
									System.out.println(d_d);
									this.orderInfo.postage=d_d;
								}
								catch(IllegalStateException e)
								{
									e.printStackTrace();
									jrd.nextNull();
								}
							}
							else if(tagName.equals("realAccount"))
							{//
								try{
									double d_d=jrd.nextDouble();
									System.out.println(d_d);
									this.orderInfo.realAccount=d_d;
								}
								catch(IllegalStateException e)
								{
									e.printStackTrace();
									jrd.nextNull();
								}
							}
							else if(tagName.equals("personCount"))
							{//用餐人数
								try{
									int i_i=jrd.nextInt();
									System.out.println(i_i);
									this.orderInfo.personCount=i_i;
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
									else if(tagName.equals("foodImg"))
									{//菜图片
										try{
											String str=jrd.nextString();
											System.out.println(str);
											dishesInfo.foodImg=str;
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
